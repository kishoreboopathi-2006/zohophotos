package com.zs.zohophotos.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zs.loginpage.records.UserDetails;
import com.zs.zohodiary.DAO.DBConnector;
import com.zs.zohophotos.records.WorkdrivePhotoDetails;

public class WorkDrivePhotosAndFoldersDetailsManagement {

	public static String getWorkdriveFolderId(UserDetails ud) {

		String sql = "SELECT workdrive_folder_id FROM folder_details WHERE user_id = ?";

		try (Connection con = DBConnector.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, ud.getId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getString("folder_id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getWorkdriveFolderId(int userId) {

		String sql = "SELECT workdrive_folder_id FROM folder_details WHERE user_id = ?";

		try (Connection con = DBConnector.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getString("workdrive_folder_id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static int getFolderId(int userId) {

		String sql = "SELECT id FROM folder_details WHERE user_id = ?";

		try (Connection con = DBConnector.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static boolean saveFolderId(UserDetails ud, String folder_id) {

		String sql = "INSERT INTO folder_details values(null,?,?,?,default)";

		try (Connection con = DBConnector.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, folder_id);
			ps.setString(2, ud.getEmail());
			ps.setInt(3, ud.getId());
			ps.executeUpdate();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean saveWorkdrivePhotoDetails(WorkdrivePhotoDetails photoDetails) {
		String sql = "insert into workdrive_photo_details values(null,?,?,?,?,default)";
		try (Connection con = DBConnector.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, photoDetails.getImageName());
			ps.setString(2, photoDetails.getResourceId());
			ps.setString(3, photoDetails.getPreviewUrl());
			ps.setInt(4, photoDetails.getFolderId());
			System.out.println(ps);
			ps.executeUpdate();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ArrayList<WorkdrivePhotoDetails> getPhotoDetails(int folderId) {
		String sql = "select * from workdrive_photo_details where folder_id=?";
		ArrayList<WorkdrivePhotoDetails> arr = new ArrayList<>();
		try (Connection con = DBConnector.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, folderId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				arr.add(new WorkdrivePhotoDetails(rs.getInt(5), rs.getString(3),rs.getString(2), rs.getString(4),rs.getString(6),rs.getString(7)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}

}
