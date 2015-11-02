package servlets.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import models.DonationDAO;
import utilities.PayPalAccess;

/**
 * Servlet implementation class Donate_Paypal
 */
@WebServlet("/user/donate_paypal")
public class Donate_Paypal extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Donate_Paypal() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cancel = request.getParameter("cancel");
		String success = request.getParameter("success");
		String uuid = request.getParameter("uuid");
		String username = request.getParameter("username");
		String paymentId = request.getParameter("paymentId");
		String token = request.getParameter("token");
		String payerId = request.getParameter("PayerID");
		String status = "";

		DonationDAO dao = new DonationDAO();
		String accessToken = PayPalAccess.getAccessToken();

		if (false == dao.checkUUID(username, uuid)) {

			status = "Unsuccessful, Session Expired.";

		} else if ("true".equals(cancel)) {

			status = "Cancelled";

		} else if ("true".equals(success)) {

			APIContext apiContext = new APIContext(accessToken);
			apiContext.setConfigurationMap(PayPalAccess.getSdkConfig());
			Payment payment = new Payment();
			payment.setId(paymentId);
			PaymentExecution paymentExecute = new PaymentExecution();
			paymentExecute.setPayerId(payerId);
			try {
				Payment p = payment.execute(apiContext, paymentExecute);
				String details = p.toJSON();
				dao.setDonationDetails(username, uuid, details);
				status = "Successful";
			} catch (PayPalRESTException e) {
				e.printStackTrace();
			}

		}

		request.setAttribute("donation_status", status);

		request.getRequestDispatcher("/user/board.jsp").forward(request, response);

	}

}
