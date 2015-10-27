package servlets.admin.store;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.StoreDAO;
import models.StoreItem;
import models.exceptions.SecurityBreachException;

/**
 * Servlet implementation class deleteItem
 */
@WebServlet("/admin/store/deleteitem")
public class DeleteItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");

		StoreDAO dao = new StoreDAO();
		StoreItem item = new StoreItem();

		try {
			item.setIdString(id);
			dao.deleteItem(item);
			response.getWriter().println("Item Deleted.");
		} catch (SecurityBreachException e) {
			response.sendRedirect("/error.jsp");
		} catch (Exception e) {
			response.sendRedirect("/error.jsp");
		}

	}

}
