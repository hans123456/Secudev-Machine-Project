package servlets.user.cart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import models.CartDAO;
import models.CartItem;
import utilities.PayPalAccess;
import utilities.Site;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/user/cart/checkout")
public class Checkout extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Checkout() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = SecurityUtils.getSubject().getPrincipal().toString();
		CartDAO dao = new CartDAO();
		List<CartItem> list = dao.getCart(dao.getLatestCartId(username));
		int total = 0;

		if (list.size() == 0) {
			response.getWriter().println("Cart Is Empty.");
			return;
		}

		String url = Site.getSiteURL(request);

		APIContext apiContext = null;
		String accessToken = null;
		Payment createdPayment = null;

		accessToken = PayPalAccess.getAccessToken();
		apiContext = new APIContext(accessToken);
		apiContext.setConfigurationMap(PayPalAccess.getSdkConfig());

		Item item = null;
		String name, quantity, price;
		ItemList itemList = new ItemList();
		List<Item> items = new ArrayList<Item>();
		for (CartItem i : list) {
			item = new Item();
			name = i.getInfo("name");
			quantity = i.getInfo("quantity");
			price = i.getInfo("price");
			item.setName(name).setQuantity(quantity).setCurrency("USD").setPrice(price);
			total += Integer.parseInt(quantity) * Integer.parseInt(price);
			items.add(item);
		}
		itemList.setItems(items);

		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(String.valueOf(total));

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setItemList(itemList);

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		String uuid = dao.generateUUID(username);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(String.format("%suser/pay_paypal?cancel=true&username=%s&uuid=%s", url, username, uuid));
		redirectUrls.setReturnUrl(String.format("%suser/pay_paypal?success=true&username=%s&uuid=%s", url, username, uuid));
		payment.setRedirectUrls(redirectUrls);

		try {
			createdPayment = payment.create(apiContext);
			response.sendRedirect(createdPayment.getLinks().get(1).getHref());
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}

	}

}
