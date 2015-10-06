package servlets.admin;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

/**
 * Servlet implementation class test
 */
@WebServlet("/admin/backup/*")
public class Backup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Backup() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OutputStream out = null;
		try {
			String path = request.getPathInfo();
			File file = new File(System.getProperty("catalina.home") + "/secudev/backup/" + path);
			if (file.exists() && file.isFile()) {
				out = response.getOutputStream();
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "filename=\"" + file.getName() + "\"");
				FileUtils.copyFile(file, out);
			} else {
				PrintWriter pw = response.getWriter();
				pw.println("File Does Not Exist.");
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

}
