package servlets.user;

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

import models.DonationDAO;
import models.DonationPack;
import models.exceptions.SecurityBreachException;
import utilities.PayPalAccess;
import utilities.Site;

/**
 * Servlet implementation class Donate
 */
@WebServlet("/user/donate")
public class Donate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Donate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DonationDAO dao = null;
		DonationPack pack = null;
		String username = SecurityUtils.getSubject().getPrincipal().toString();

		try {

			pack = new DonationPack(request.getParameter("amount"));
			dao = new DonationDAO();

			String url = Site.getSiteURL(request);

			APIContext apiContext = null;
			String accessToken = null;
			Payment createdPayment = null;

			accessToken = PayPalAccess.getAccessToken();
			apiContext = new APIContext(accessToken);
			apiContext.setConfigurationMap(PayPalAccess.getSdkConfig());

			Amount amount = new Amount();
			amount.setCurrency("USD");
			amount.setTotal(String.valueOf(pack.getAmount()));

			ItemList itemList = new ItemList();
			List<Item> items = new ArrayList<Item>();
			Item item = new Item();
			item.setName("Donation Pack $" + pack.getAmount()).setQuantity("1").setCurrency("USD")
					.setPrice(String.valueOf(pack.getAmount()));
			items.add(item);
			itemList.setItems(items);

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

			String uuid = dao.createDonation(username, pack);

			RedirectUrls redirectUrls = new RedirectUrls();
			redirectUrls.setCancelUrl(
					String.format("%suser/donate_paypal?cancel=true&username=%s&uuid=%s", url, username, uuid));
			redirectUrls.setReturnUrl(
					String.format("%suser/donate_paypal?success=true&username=%s&uuid=%s", url, username, uuid));
			payment.setRedirectUrls(redirectUrls);

			try {
				createdPayment = payment.create(apiContext);
				response.sendRedirect(createdPayment.getLinks().get(1).getHref());
			} catch (PayPalRESTException e) {
				e.printStackTrace();
			}

		} catch (SecurityBreachException e) {
			response.sendError(400);
		} catch (Exception e) {
			response.sendError(400);
		}

	}

}
