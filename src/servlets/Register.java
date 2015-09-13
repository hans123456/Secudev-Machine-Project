package servlets;

import java.io.IOException;
import java.sql.SQLException;

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
 * Servlet implementation class register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			Subject currentUser = SecurityUtils.getSubject();

			if (!currentUser.hasRole("admin")) throw new SecurityBreachException();

			String role = request.getParameter("role");
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String gender = request.getParameter("gender");
			String salutation = request.getParameter("salutation");
			String birthdate = request.getParameter("birthdate");
			String username = request.getParameter("username");
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
			if (dao.create(user)) {
				response.getWriter().print("success");
				System.out.println("Hooray Registered!");
			} else {
				response.getWriter().print("fail");
			}

		} catch (SecurityBreachException e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		} catch (Exception e) {
			response.sendRedirect("error.jsp");
		}

	}

}
