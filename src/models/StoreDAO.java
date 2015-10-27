package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDAO extends DAO {

	private String addItemQuery = "INSERT INTO `store_items` (`name`, `description`, `image`, `price`) VALUES (?, ?, ?, ?)";
	private String[] addItemParams = { "name", "description", "image", "price" };

	private String editItemQuery = "UPDATE `store_items` SET name = ?, description = ?, image = ?, price = ? WHERE `id` = ?";
	private String[] editItemParams = { "name", "description", "image", "price" };

	private String deleteItemQuery = "UPDATE `store_items` SET deleted = true WHERE `id` = ?";

	private String checkIfItemDeletedQuery = "SELECT EXISTS(SELECT 1 FROM `store_items` WHERE `id` = ? and `deleted` = true)"
			+ " as `result`";

	private String getItemListQuery = "SELECT `id`, `name`, `description`, `image`, `price`, `donation` FROM `store_items` WHERE `deleted` = false ORDER BY `donation` DESC, `price` ASC";
	private String[] getItemListResult = { "id", "name", "description", "image", "price", "donation" };

	private String getItem = "SELECT `id`, `name`, `description`, `image`, `price` FROM `store_items` WHERE `id` = ? LIMIT 1";
	private String[] getItemResult = { "id", "name", "description", "image", "price" };

	public void addItem(StoreItem item) {
		try {
			con = getConnection();
			ps = con.prepareStatement(addItemQuery);
			for (int i = 0, j = 1, k = addItemParams.length; i < k; i++, j++)
				ps.setString(j, item.getInfo(addItemParams[i]));
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

	public void editItem(StoreItem item) {
		try {
			con = getConnection();
			ps = con.prepareStatement(editItemQuery);
			int j = 1;
			for (int i = 0, k = editItemParams.length; i < k; i++, j++)
				ps.setString(j, item.getInfo(editItemParams[i]));
			ps.setInt(j, item.getId());
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

	public void deleteItem(StoreItem item) {
		try {
			con = getConnection();
			ps = con.prepareStatement(deleteItemQuery);
			ps.setInt(1, item.getId());
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

	public StoreItem getItem(int id) {
		StoreItem item = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(getItem);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				item = new StoreItem();
				for (int i = 0, j = 1, k = getItemResult.length; i < k; i++, j++)
					if (rs.getObject(j) != null) item.setInfo(getItemListResult[i], rs.getObject(j).toString());
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
		return item;
	}

	public boolean checkIfItemDeleted(int id) {
		boolean result = false;
		try {
			con = getConnection();
			ps = con.prepareStatement(checkIfItemDeletedQuery);
			ps.setInt(1, id);
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

	public List<StoreItem> getItemList() {
		List<StoreItem> items = new ArrayList<StoreItem>();
		StoreItem item = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(getItemListQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				item = new StoreItem();
				for (int i = 0, j = 1, k = getItemListResult.length; i < k; i++, j++)
					if (rs.getObject(j) != null) item.setInfo(getItemListResult[i], rs.getObject(j).toString());
				items.add(item);
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
		return items;
	}

	public static void main(String[] args) {
		StoreDAO dao = new StoreDAO();
		List<StoreItem> items = dao.getItemList();
		for (StoreItem item : items) {
			System.out.println(item.getInfo("name"));
		}
	}

}
