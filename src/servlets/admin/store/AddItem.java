package servlets.admin.store;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.StoreDAO;
import models.StoreItem;
import models.exceptions.NotAnImageException;
import models.exceptions.SecurityBreachException;

/**
 * Servlet implementation class addItem
 */
@WebServlet("/admin/store/additem")
public class AddItem extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String image = request.getParameter("image");
		String price = request.getParameter("price");

		StoreDAO dao = new StoreDAO();
		StoreItem item = new StoreItem();

		try {
			item.setName(name);
			item.setDescription(description);
			item.setImage(image);
			item.setPrice(price);
			dao.addItem(item);
			response.getWriter().println("Item Added Successfuly.");
		} catch (NotAnImageException e) {
			e.printStackTrace();
			response.getWriter().println("Link Not An Image.");
		} catch (SecurityBreachException e) {
			e.printStackTrace();
			response.sendRedirect("/error.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/error.jsp");
		}

	}

}
