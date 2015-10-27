package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilities.ConnectionFactory;

public class DAO {

	protected Connection con;
	protected PreparedStatement ps;
	protected ResultSet rs;

	protected static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection con = ConnectionFactory.getConnection();
		return con;
	}

}
