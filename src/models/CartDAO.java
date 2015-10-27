package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mysql.jdbc.PreparedStatement;

public class CartDAO extends DAO {

	private String getLatestCardIdQuery = "SELECT `store_cart`.`id` FROM `store_cart` WHERE `store_cart`.`user_id` = (SELECT `id` FROM `users` WHERE `username` = ? LIMIT 1) AND (`store_cart`.`store_cart_status_id` = 2 OR `store_cart`.`store_cart_status_id` = 3) LIMIT 1";

	private String editCartItemQuery = "INSERT INTO `store_cart_items` (`store_cart_id`,`store_item_id`,`quantity`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = ?";
	private String[] editCartItemParams = { "itemId", "quantity", "quantity" };

	private String deleteCartItemQuery = "DELETE FROM `store_cart_items` WHERE `store_cart_id` = ? AND `store_item_id` = ?";
	private String[] deleteCartItemParams = { "itemId" };

	private String getCartQuery = "SELECT `store_items`.`id`, `name`, `store_cart_items`.`quantity`, `store_items`.`price` as `price` FROM `store_cart_items`, `store_items` WHERE `store_cart_items`.`store_item_id` = `store_items`.`id` AND `store_cart_items`.`store_cart_id` = ? AND `store_items`.`deleted` = FALSE";
	private String[] getCartResult = { "itemId", "name", "quantity", "price" };

	private String createCart = "INSERT INTO `store_cart` (`id`, `user_id`, `datetime_updated`) VALUES (NULL, (SELECT `id` FROM `users` WHERE `username` = ? LIMIT 1), now())";

	private String generateUUIDQuery = "UPDATE `store_cart` SET `uuid` = ? WHERE `id` = ?";

	private String checkValidUUIDQuery = "SELECT EXISTS(SELECT 1 FROM `store_cart` WHERE `id` = ? AND `uuid` = ? LIMIT 1) as `result`";

	private String setCartStatusQuery = "UPDATE `store_cart` SET `store_cart_status_id` = (SELECT `id` FROM `store_cart_status` WHERE `status` = ? LIMIT 1), `datetime_updated` = NOW() WHERE `id` = ?";

	private String setPaymentDetailsQuery = "UPDATE `store_cart` SET `payment_details` = ? WHERE `uuid` = ? AND `user_id` = (SELECT `id` FROM `users` WHERE `username` = ? LIMIT 1)";

	private String getTotalDonationQuery = "SELECT SUM(`store_items`.`price`*`store_cart_consolidated`.`quantity`) as `total_donation` FROM `store_items`, (SELECT `store_item_id`, SUM(`quantity`) AS `quantity` FROM `store_cart_items` WHERE `store_cart_id` IN (SELECT `id` FROM `store_cart` WHERE `user_id` = (SELECT `id` FROM `users` WHERE `username` = ? LIMIT 1) AND `store_cart_status_id` = 1) AND `store_item_id` IN (SELECT `id` FROM `store_items` WHERE `donation` = true) GROUP BY `store_item_id`) as `store_cart_consolidated` WHERE `store_items`.`id` = `store_cart_consolidated`.`store_item_id`";

	private String getTotalPurchaseQuery = "SELECT SUM(`store_items`.`price`*`store_cart_consolidated`.`quantity`) as `total_donation` FROM `store_items`, (SELECT `store_item_id`, SUM(`quantity`) AS `quantity` FROM `store_cart_items` WHERE `store_cart_id` IN (SELECT `id` FROM `store_cart` WHERE `user_id` = (SELECT `id` FROM `users` WHERE `username` = ? LIMIT 1) AND `store_cart_status_id` = 1) AND `store_item_id` IN (SELECT `id` FROM `store_items` WHERE `donation` = false) GROUP BY `store_item_id`) as `store_cart_consolidated` WHERE `store_items`.`id` = `store_cart_consolidated`.`store_item_id`";

	public String generateUUID(String username) {
		String storeCartId = getLatestCartId(username);
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		try {
			con = getConnection();
			ps = con.prepareStatement(generateUUIDQuery);
			ps.setString(1, uuid);
			ps.setString(2, storeCartId);
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
		String storeCartId = getLatestCartId(username);
		boolean result = false;
		try {
			con = getConnection();
			ps = con.prepareStatement(checkValidUUIDQuery);
			ps.setString(1, storeCartId);
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

	private String createCart(String username) {
		String cartId = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(createCart, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, username);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) cartId = rs.getString(1);
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
		return cartId;
	}

	public String getLatestCartId(String username) {
		String cartId = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(getLatestCardIdQuery);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) cartId = rs.getString(1);
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
		if (cartId == null) cartId = createCart(username);
		return cartId;
	}

	public List<CartItem> getCart(String storeCartId) {
		List<CartItem> list = null;
		CartItem item = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(getCartQuery);
			ps.setString(1, storeCartId);
			rs = ps.executeQuery();
			list = new ArrayList<CartItem>();
			while (rs.next()) {
				item = new CartItem();
				for (int i = 0, j = 1, k = getCartResult.length; i < k; i++, j++)
					item.setInfo(getCartResult[i], rs.getObject(j).toString());
				list.add(item);
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
		return list;
	}

	public void editCartItem(String username, CartItem item) {
		String storeCartId = getLatestCartId(username);
		try {
			con = getConnection();
			ps = con.prepareStatement(editCartItemQuery);
			ps.setString(1, storeCartId);
			for (int i = 0, j = 2, k = editCartItemParams.length; i < k; i++, j++)
				ps.setString(j, item.getInfo(editCartItemParams[i]));
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

	public void deleteCartItem(String username, CartItem item) {
		String storeCartId = getLatestCartId(username);
		try {
			con = getConnection();
			ps = con.prepareStatement(deleteCartItemQuery);
			ps.setString(1, storeCartId);
			for (int i = 0, j = 2, k = deleteCartItemParams.length; i < k; i++, j++)
				ps.setString(j, item.getInfo(deleteCartItemParams[i]));
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

	public void setCartStatus(String username, String status) {
		String storeCartId = getLatestCartId(username);
		try {
			con = getConnection();
			ps = con.prepareStatement(setCartStatusQuery);
			ps.setString(1, status);
			ps.setString(2, storeCartId);
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

	public void setPaymentDetails(String username, String uuid, String details) {
		try {
			con = getConnection();
			ps = con.prepareStatement(setPaymentDetailsQuery);
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

	public String getTotalPurchase(String username) {
		String total = "0";
		try {
			con = getConnection();
			ps = con.prepareStatement(getTotalPurchaseQuery);
			ps.setString(1, username);
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
