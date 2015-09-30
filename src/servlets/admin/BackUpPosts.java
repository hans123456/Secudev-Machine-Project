package servlets.admin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import models.Post;
import models.PostDAO;
import models.exceptions.SecurityBreachException;

/**
 * Servlet implementation class CreatePostBackUp
 */
@WebServlet("/admin/backupposts")
public class BackUpPosts extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BackUpPosts() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String[] backUpPosts = { "username", "datetime_created", "post" };

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Subject currentUser = SecurityUtils.getSubject();

		try {

			if (!currentUser.hasRole("admin")) throw new SecurityBreachException();

			DateTimeFormatter dtf = DateTimeFormat.forPattern("MM-dd-yyyy hh.mm a");
			String fileName = LocalDateTime.now().toString(dtf) + " " + System.currentTimeMillis() + ".csv";
			String directory = System.getProperty("catalina.home") + "/secudev/backup/post/";
			String fileNameWithDirectory = directory + fileName;
			File folder = new File(directory);
			if (!folder.exists()) folder.mkdirs();
			File file = new File(fileNameWithDirectory);
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));

			PostDAO dao = new PostDAO();

			List<Post> posts = dao.backUpPosts();
			StringBuilder sb;

			for (Post p : posts) {
				sb = new StringBuilder();
				for (int i = 0; i < backUpPosts.length; i++) {
					if (i > 0) sb.append(",");
					sb.append(StringEscapeUtils.escapeCsv(StringEscapeUtils.escapeJava(p.getInfo(backUpPosts[i]))));
				}
				out.println(sb);
			}

			out.flush();
			out.close();

			response.getWriter().printf("Link To File : <a href='/admin/backup/post/%s'>Link</a>", fileName);

		} catch (SecurityBreachException e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		System.out.println("\n".replaceAll("\\n", "\\\\n"));
	}

}
