package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import model.User;
import model.UserDAO;

/**
 * Servlet implementation class register
 */
@WebServlet("/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public register() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Subject currentUser = SecurityUtils.getSubject();

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
			if (role != null) {
				response.sendRedirect("error.jsp");
				return;
			} else role = "user";
		}

		try {

			User user = new User();

			user.setRole(role);
			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setUsername(username);
			user.setPassword(password);
			user.setGender(gender);
			user.setSalutation(salutation);
			user.setBirthdateString(birthdate);
			user.setAboutMe(about_me);

			try {
				UserDAO.create(user);
				response.getWriter().print("success");
				System.out.println("Hooray!");
			} catch (Exception e) {
				response.getWriter().print("fail");
			}

		} catch (Exception e) {
			response.sendRedirect("error.jsp");
		}

	}

}
