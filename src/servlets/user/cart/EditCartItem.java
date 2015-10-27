package servlets.user.cart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;

import models.CartDAO;
import models.CartItem;
import models.StoreDAO;
import models.exceptions.SecurityBreachException;

/**
 * Servlet implementation class editcartitem
 */
@WebServlet("/user/cart/editcartitem")
public class EditCartItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditCartItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CartItem item = null;
		String username = null;
		String out = "";

		try {
			item = new CartItem();
			item.setItemIdString(request.getParameter("itemId"));
			item.setQuantityString(request.getParameter("quantity"));
			CartDAO cartDao = new CartDAO();
			username = SecurityUtils.getSubject().getPrincipal().toString();
			StoreDAO storeDao = new StoreDAO();
			if (storeDao.checkIfItemDeleted(Integer.parseInt(item.getInfo("itemId"))))
				throw new SecurityBreachException();
			cartDao.generateUUID(username);
			if (Integer.parseInt(item.getInfo("quantity")) <= 0) {
				cartDao.deleteCartItem(username, item);
				out = "Successfully Deleted From Cart.";
			} else {
				cartDao.editCartItem(username, item);
				out = "Successfully Added or Edited To Cart.";
			}
		} catch (SecurityBreachException e) {
			response.sendRedirect("/error.jsp");
		} catch (Exception e) {
			response.sendRedirect("/error.jsp");
		}
		response.getWriter().println(out);
	}

}
