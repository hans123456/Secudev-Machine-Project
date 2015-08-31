
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;

import model.User;
import model.UserDAO;
import shiro.ShiroPasswordManager;

/**
 * Servlet implementation class register
 */
@WebServlet("/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String[] attr = { "firstname", "lastname", "gender", "salutation", "birthdate", "username", "password",
			"about_me" };
	private String[] salutationsMale = { "Mr", "Sir", "Senior", "Count" };
	private String[] salutationsFemale = { "Miss", "Ms", "Mrs", "Madame", "Majesty", "Seniora" };

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

		boolean bad = false;
		boolean badDate = false;

		for (int i = 0, j = attr.length; i < j; i++)
			if (request.getParameter(attr[i]) == null) {
				response.sendRedirect("error.jsp");
				return;
			}

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
			if (role != null)
				response.sendRedirect("error.jsp");
			else
				role = "user";
		}

		if (role == null || !role.equals("admin") && !role.equals("user"))
			bad = true;
		if (firstname.length() <= 0 || firstname.length() > 50 || !firstname.matches("^[a-zA-Z0-9 ]*$"))
			bad = true;
		if (lastname.length() <= 0 || lastname.length() > 50 || !lastname.matches("^[a-zA-Z0-9 ]*$"))
			bad = true;
		if (username.length() <= 0 || username.length() > 50 || !username.matches("^[a-zA-Z0-9_]*$"))
			bad = true;
		if (password.length() <= 0 || password.length() > 50 || !password.matches("^\\S*$"))
			bad = true;
		if (about_me.length() > 1000)
			bad = true;
		if (!((gender.equals("Male") && Arrays.asList(salutationsMale).contains(salutation))
				|| (gender.equals("Female") && Arrays.asList(salutationsFemale).contains(salutation))))
			bad = true;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			Calendar c1 = Calendar.getInstance();
			c1.setTime(sdf.parse(birthdate));
			c1.add(Calendar.DAY_OF_MONTH, -1);
			Calendar c2 = Calendar.getInstance();
			long age = (long) ((c2.getTimeInMillis() - c1.getTimeInMillis()) / 1000.0 / 60 / 60 / 24 / 365);
			if (age < 18)
				badDate = true;
		} catch (Exception e1) {
			bad = true;
		}

		if (bad) {
			response.sendRedirect("error.jsp");
			return;
		} else if (badDate) {
			response.getWriter().print("bad date");
			return;
		}

		User user = new User(username);

		for (int i = 0, j = attr.length; i < j; i++)
			user.setInfo(attr[i], request.getParameter(attr[i]));

		user.setInfo("role", role);
		user.setInfo("password", ShiroPasswordManager.encryptPassword(password));

		try {
			UserDAO.create(user);
			response.getWriter().print("success");
			System.out.println("Hooray!");
		} catch (Exception e) {
			response.getWriter().print("fail");
			e.printStackTrace();
		}

	}

}
