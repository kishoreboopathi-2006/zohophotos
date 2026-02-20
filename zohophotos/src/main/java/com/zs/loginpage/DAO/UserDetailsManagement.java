package com.zs.loginpage.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zs.loginpage.records.UserDetails;
import com.zs.zohodiary.DAO.DBConnector;
import com.zs.zohophotos.records.ProfilePhotoDetails;

public class UserDetailsManagement {
	Connection conn;

	public UserDetailsManagement() throws SQLException {
		getConnection();
	}

	public void getConnection() throws SQLException {
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
			e.printStackTrace();
		}
		return password;
	}

	public com.zs.zohophotos.records.UserDetails getUserDetails(com.zs.zohophotos.records.UserDetails user) {
		String sql = "select preview_url from user_photo where user_id=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, user.getUserId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String previewUrl = rs.getString(1);
				user.setPreviewUrl(previewUrl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public boolean changeProfilePhoto(ProfilePhotoDetails photo) {
		Boolean flag = checkProfilePhoto(photo);
		if (flag) {
			String sql = "update user_photo set preview_url=? where user_id=?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, photo.getPreviewUrl());
				ps.setInt(2, photo.getUserId());
				ps.executeUpdate();
				System.out.println("update");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		flag = insertProfilePhoto(photo);
		if (flag) {
			return true;
		}
		return false;
	}

	private Boolean insertProfilePhoto(ProfilePhotoDetails photo) {
		String sql = "insert into user_photo value(?,?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, photo.getPreviewUrl());
			ps.setInt(2, photo.getUserId());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Boolean checkProfilePhoto(ProfilePhotoDetails photo) {
		String sql = "select * from user_photo where user_id=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, photo.getUserId());
			System.err.println("===================================================="+ps+"======================================================================");
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

}
