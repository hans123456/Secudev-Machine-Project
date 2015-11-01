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
 * Servlet implementation class editItem
 */
@WebServlet("/admin/store/edititem")
public class EditItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditItem() {
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
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String image = request.getParameter("image");
		String price = request.getParameter("price");

		StoreDAO dao = new StoreDAO();
		StoreItem item = new StoreItem();

		boolean success = true;
		String out = "";

		try {
			item.setIdString(id);
			item.setName(name);
			item.setDescription(description);
			item.setImage(image);
			item.setPrice(price);
			if (dao.checkIfItemDeleted(item.getId()) == false) dao.editItem(item);
			else {
				success = false;
				out = "Item Was Already Deleted.";
			}
		} catch (NotAnImageException e) {
			success = false;
			out = "Fix Image Link. Max 100 Characters.";
		} catch (SecurityBreachException e) {
			response.sendRedirect("/error.jsp");
		} catch (Exception e) {
			response.sendRedirect("/error.jsp");
		}
		if (success) response.sendRedirect("/user/store.jsp");
		else response.getWriter().print(out);
	}

}
