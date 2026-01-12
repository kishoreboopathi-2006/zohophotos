package com.zs.zohophotos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	private static Connection conn;

	private DBConnector() {
	}

	public static Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho_photos", "kishore",
						"kishore@2006");
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
