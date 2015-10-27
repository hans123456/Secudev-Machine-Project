package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagementDAO extends DAO {

	private String getListQuery = "SELECT `store_cart_with_user`.`user_id`, `store_cart_with_user`.`username`, `store_cart_with_user`.`firstname`, `store_cart_with_user`.`lastname`, `store_cart_with_user`.`store_cart_id`, `store_cart_with_user`.`status`, IFNULL(`store_cart_items_multiplied`.`price`, 0) AS `price`, `store_cart_with_user`.`datetime_updated` FROM (SELECT `users`.`id` as `user_id`, `users`.`username`, `users`.`firstname`, `users`.`lastname`, `store_cart`.`id` as `store_cart_id`, `store_cart_status`.`status`, `store_cart`.`datetime_updated` FROM `users`, `store_cart`, `store_cart_status` WHERE `users`.`id` = `store_cart`.`user_id` AND `store_cart`.`store_cart_status_id` = `store_cart_status`.`id` ) AS `store_cart_with_user` LEFT JOIN (SELECT `store_cart_items`.`store_cart_id`, SUM(`store_cart_items`.`quantity`*`store_items`.`price`) as `price` FROM `store_cart_items`, `store_items` WHERE `store_cart_items`.`store_item_id` = `store_items`.`id` GROUP BY `store_cart_items`.`store_cart_id`) AS `store_cart_items_multiplied` ON `store_cart_with_user`.`store_cart_id`=`store_cart_items_multiplied`.`store_cart_id` AND `store_cart_items_multiplied`.`price`>0 ORDER BY `datetime_updated` DESC";
	private String[] getListResult = { "user_id", "username", "firstname", "lastname", "store_cart_id", "status",
			"price", "datetime_updated" };

	public List<CartTransaction> getList(List<ManagementQuery> queries) {
		List<CartTransaction> transactions = new ArrayList<CartTransaction>();
		CartTransaction transaction = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(getListQuery);
			// int pi = 1;
			// for (ManagementQuery q : queries)
			// for (String p : q.getQueryParams())
			// ps.setString(pi++, p);
			rs = ps.executeQuery();
			while (rs.next()) {
				transaction = new CartTransaction();
				for (int i = 0, j = 1, k = getListResult.length; i < k; i++, j++)
					if (rs.getObject(j) != null) transaction.setInfo(getListResult[i], rs.getObject(j).toString());
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
		return transactions;
	}

}
