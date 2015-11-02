package models;

import java.sql.SQLException;

import utilities.UUIDGenerator;

public class DonationDAO extends DAO {

	private String deleteExpiredQuery = "DELETE FROM `donations` WHERE `donation_details` IS NULL AND `datetime_created` < DATE_SUB(NOW(), INTERVAL 10 MINUTE)";

	private String createDonationQuery = "INSERT INTO `donations`(`user_id`, `datetime_created`, `uuid`, `amount`) values ((SELECT `id` FROM `users` WHERE `username` = ? LIMIT 1), now(), ?, ?)";

	private String checkValidUUIDQuery = "SELECT EXISTS(SELECT 1 FROM `donations` WHERE `user_id` = (SELECT `id` FROM `users` WHERE `username` = ? LIMIT 1) AND `uuid` = ? ORDER BY `datetime_created` desc LIMIT 1) as `result`";

	private String successfulDonationQuery = "UPDATE `donations` SET `donation_details` = ? WHERE `uuid` = ? AND `user_id` = (SELECT `id` FROM `users` WHERE `username` = ? LIMIT 1)";

	private String getTotalDonationQuery = "SELECT SUM(`total_donation`) as `total_donation` FROM ((SELECT SUM(`store_items`.`price`*`store_cart_consolidated`.`quantity`) as `total_donation` FROM `store_items`, (SELECT `store_item_id`, SUM(`quantity`) AS `quantity` FROM `store_cart_items` WHERE `store_cart_id` IN (SELECT `id` FROM `store_cart` WHERE `user_id` = (SELECT `id` FROM `users` WHERE `username` = ? LIMIT 1) AND `store_cart_status_id` = 1) AND `store_item_id` IN (SELECT `id` FROM `store_items` WHERE `donation` = true) GROUP BY `store_item_id`) as `store_cart_consolidated` WHERE `store_items`.`id` = `store_cart_consolidated`.`store_item_id`) UNION ALL (SELECT SUM(`amount`) as `total_donation` FROM `donations` WHERE `donation_details` IS NOT NULL AND `user_id` = (SELECT `id` FROM `users` WHERE `username` = ? LIMIT 1))) as `total`";

	public String createDonation(String username, DonationPack pack) {
		String uuid = UUIDGenerator.generateUUID();
		try {
			con = getConnection();
			ps = con.prepareStatement(createDonationQuery);
			ps.setString(1, username);
			ps.setString(2, uuid);
			ps.setInt(3, pack.getAmount());
			ps.executeUpdate();
			ps = con.prepareStatement(deleteExpiredQuery);
			ps.executeUpdate();
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
		return uuid;
	}

	public boolean checkUUID(String username, String uuid) {
		boolean result = false;
		try {
			con = getConnection();
			ps = con.prepareStatement(checkValidUUIDQuery);
			ps.setString(1, username);
			ps.setString(2, uuid);
			rs = ps.executeQuery();
			if (rs.next()) if (rs.getInt(1) == 1) result = true;
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
		return result;
	}

	public void setDonationDetails(String username, String uuid, String details) {
		try {
			con = getConnection();
			ps = con.prepareStatement(successfulDonationQuery);
			ps.setString(1, details);
			ps.setString(2, uuid);
			ps.setString(3, username);
			ps.executeUpdate();
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
	}

	public String getTotalDonation(String username) {
		String total = "0";
		try {
			con = getConnection();
			ps = con.prepareStatement(getTotalDonationQuery);
			ps.setString(1, username);
			ps.setString(2, username);
			rs = ps.executeQuery();
			if (rs.next()) total = rs.getString(1);
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
		return total;
	}

}
