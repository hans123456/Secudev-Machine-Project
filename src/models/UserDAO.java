package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class UserDAO {

	private final static String createQuery = "INSERT INTO `users` (`id`, `role_id`, `firstname`, `lastname`, `salutation_id`, `birthdate`, `username`, `password`, `about_me`) "
			+ "VALUES (NULL, (SELECT `id` FROM `roles` WHERE `role` = ?), ?, ?, "
			+ "(SELECT `id` FROM `salutations` WHERE `salutation` = ?), ?, ?, ?, ?)";
	private final static String[] createParams = { "role", "firstname", "lastname", "salutation", "birthdate",
			"username", "password", "about_me" };

	private final static String getQuery = "SELECT `firstname`, `lastname`, `gender`, `salutation`, DATE_FORMAT(`birthdate`, '%M %d, %Y') as `birthdate`, `username`, `about_me` "
			+ "FROM `users`, `genders`, `salutations` WHERE `username` = ? and `gender_id` = `genders`.`id` and `salutation_id` = `salutations`.`id`";
	private final static String[] getParams = { "firstname", "lastname", "gender", "salutation", "birthdate",
			"username", "about_me" };

	public static void create(User user) throws SQLException, ClassNotFoundException {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement ps = con.prepareStatement(createQuery);
		for (int i = 0, j = 1, k = createParams.length; i < k; i++, j++)
			ps.setString(j, user.getInfo(createParams[i]));
		ps.executeUpdate();
		ps.close();
		con.close();
	}

	public static void getUserInfo(String username, HttpServletRequest req)
			throws SQLException, ClassNotFoundException {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement ps = con.prepareStatement(getQuery);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		rs.next();
		for (int i = 0, j = 1, k = getParams.length; i < k; i++, j++)
			req.setAttribute(getParams[i], rs.getObject(j).toString());
		ps.close();
		rs.close();
		con.close();
	}

}
