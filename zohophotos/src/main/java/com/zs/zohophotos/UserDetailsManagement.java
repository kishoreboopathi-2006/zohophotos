package com.zs.zohophotos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDetailsManagement {
	Connection conn;

	UserDetailsManagement() {
		getConnection();
	}

	public void getConnection() {
		conn = DBConnector.getConnection();
		System.out.println("Connected successfully");
	}

	public int insertUserDetailsAndGetUserId(UserDetails details) {
		String sql = "insert into user_details values(null,?,?,?)";
		int userId = 0;
		try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, details.getName());
			ps.setString(2, details.getEmail());
			ps.setString(3, details.getPassword());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				userId = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
	}

	public boolean checkUserEmailExists(UserDetails details) {
		String sql = "select * from user_details where user_email=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, details.getEmail());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public int getUserId(UserDetails details) {
		int userId = 0;
		String sql = "select user_id from user_details where user_email=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, details.getEmail());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				userId = rs.getInt(1);
				return userId;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userId;
	}

	public String getUserName(UserDetails details) {
		String userName = "";
		String sql = "select user_name from user_details where user_email=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, details.getEmail());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				userName = rs.getString(1);
				return userName;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userName;
	}

	public String getPassword(UserDetails details) {
		String password = "";
		String sql = "select password from user_details where user_email=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, details.getEmail());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				password = rs.getString(1);
				return password;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return password;
	}
}
