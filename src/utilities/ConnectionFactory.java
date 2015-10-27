package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private final static String driverClass = "com.mysql.jdbc.Driver";
	private final static String serverName = "localhost:3306";
	private final static String databaseName = "secudevcase1";
	private final static String connectionUrl = "jdbc:mysql://" + serverName + "/" + databaseName;
	private final static String username = "root";
	private final static String password = "";

	static {
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection connection = DriverManager.getConnection(connectionUrl, username, password);
		return connection;
	}

	public static String getDriverClass() {
		return driverClass;
	}

	public static String getConnectionUrl() {
		return connectionUrl;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public static String getDatabaseName() {
		return databaseName;
	}

	public static String getServerName() {
		return serverName;
	}

}
