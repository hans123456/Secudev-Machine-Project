package servlets.user.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import models.Post;
import models.PostDAO;
import models.exceptions.NotAnImageException;
import models.exceptions.SecurityBreachException;

/**
 * Servlet implementation class Post
 */
@WebServlet("/user/createpost")
public class CreatePost extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreatePost() {
		super();
		// TODO Auto-generated constructor stub
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

			String post = request.getParameter("post");

			Post p = new Post();
			p.setUsername(SecurityUtils.getSubject().getPrincipal().toString());
			p.setPost(post);

			PostDAO dao = new PostDAO();
			if (dao.create(p)) {
				response.getWriter().print("success");
				System.out.println("Hooray Post Created!");
			} else {
				response.getWriter().print("fail");
			}

		} catch (NotAnImageException e) {
			response.getWriter().print("image");
		} catch (SecurityBreachException e) {
			response.sendRedirect("/error.jsp");
		} catch (Exception e) {
			response.sendRedirect("/error.jsp");
		}

	}

}
