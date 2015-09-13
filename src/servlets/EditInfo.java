package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import models.User;
import models.UserDAO;
import models.exceptions.SecurityBreachException;

/**
 * Servlet implementation class EditInfo
 */
@WebServlet("/editinfo")
public class EditInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditInfo() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) System.out.println("logged in");
		else System.out.println("not logged in");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			Subject currentUser = SecurityUtils.getSubject();

			if (!currentUser.isAuthenticated()) throw new SecurityBreachException();

			boolean isAdmin = currentUser.hasRole("admin");

			String role = request.getParameter("role");
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String gender = request.getParameter("gender");
			String salutation = request.getParameter("salutation");
			String birthdate = request.getParameter("birthdate");
			String username = currentUser.getPrincipal().toString();
			String password = request.getParameter("password");
			String about_me = request.getParameter("about_me");

			if (!currentUser.hasRole("admin")) {
				if (role != null) throw new SecurityBreachException();
				else role = "user";
			}

			User user = new User();

			user.setRole(role);
			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setUsername(username);
			user.setPassword(password);
			user.setSalutation(gender, salutation);
			user.setBirthdateString(birthdate);
			user.setAboutMe(about_me);

			UserDAO dao = new UserDAO();
			if (dao.edit(user)) {
				if (isAdmin && role.equals("user")) {
					currentUser.logout();
					response.getWriter().print("logout");
				} else {
					response.getWriter().print("success");
				}
				System.out.println("Hooray Edited Info!");
			}

		} catch (SecurityBreachException e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		} catch (Exception e) {
			response.sendRedirect("error.jsp");
		}

	}

}
