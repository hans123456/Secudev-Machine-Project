package models;

import java.sql.SQLException;

public class UserDAO extends DAO {

	private final static String createQuery = "INSERT INTO `users` (`id`, `role_id`, `firstname`, `lastname`, `salutation_id`, `birthdate`, `username`, `password`, `about_me`, `datetime_joined`) "
			+ "VALUES (NULL, (SELECT `id` FROM `roles` WHERE `role` = ?), ?, ?, "
			+ "(SELECT `id` FROM `salutations` WHERE `salutation` = ?), ?, ?, ?, ?, NOW())";
	private final static String[] createParams = { "role", "firstname", "lastname", "salutation", "birthdate",
			"username", "password", "about_me" };

	private final static String editQuery = "UPDATE `users` SET role_id = (SELECT `id` FROM `roles` WHERE `role` = ?), firstname = ?, "
			+ "lastname = ?, salutation_id = (SELECT `id` FROM `salutations` WHERE `salutation` = ?), birthdate = ?, "
			+ "password = ?, about_me = ? WHERE username = ?";
	private final static String[] editParams = { "role", "firstname", "lastname", "salutation", "birthdate", "password",
			"about_me", "username" };

	private final static String getQuery = "SELECT `role`, `firstname`, `lastname`, `gender`, `salutation`, `birthdate`, `username`, `about_me` "
			+ "FROM `roles`, `users`, `genders`, `salutations` WHERE `username` = ? and `role_id` = `roles`.`id` and "
			+ "`gender_id` = `genders`.`id` and `salutation_id` = `salutations`.`id`";
	private final static String[] getResult = { "role", "firstname", "lastname", "gender", "salutation", "birthdate",
			"username", "about_me" };

	public boolean create(User user) {
		boolean result = false;
		try {
			con = getConnection();
			ps = con.prepareStatement(createQuery);
			for (int i = 0, j = 1, k = createParams.length; i < k; i++, j++)
				ps.setString(j, user.getInfo(createParams[i]));
			ps.executeUpdate();
			result = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean edit(User user) {
		boolean result = false;
		try {
			con = getConnection();
			ps = con.prepareStatement(editQuery);
			for (int i = 0, j = 1, k = editParams.length; i < k; i++, j++)
				ps.setString(j, user.getInfo(editParams[i]));
			ps.executeUpdate();
			result = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public User getInfo(String username) {
		User user = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(getQuery);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				for (int i = 0, j = 1, k = getResult.length; i < k; i++, j++)
					user.setInfo(getResult[i], rs.getObject(j).toString());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) ps.close();
				if (rs != null) rs.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

}
