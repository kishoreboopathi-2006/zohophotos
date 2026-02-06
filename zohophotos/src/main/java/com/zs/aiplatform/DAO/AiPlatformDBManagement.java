package com.zs.aiplatform.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zs.aiplatform.records.AiResponseDetailsForPhoto;
import com.zs.aiplatform.records.CategorizePhotoDetails;
import com.zs.zohodiary.DAO.DBConnector;

public class AiPlatformDBManagement {
	static Connection conn;

	public AiPlatformDBManagement() {
		getConnection();
	}

	public void getConnection() {
		try {
			conn = DBConnector.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean insertIntoAiResponseTable(AiResponseDetailsForPhoto photoDetails) {
		String sql = "insert into airesponse_table values(null,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, photoDetails.getCategories()[0]);
			ps.setString(2, photoDetails.getCategories()[1]);
			ps.setString(3, photoDetails.getCategories()[2]);
			ps.setString(4, photoDetails.getCategories()[3]);
			ps.setString(5, photoDetails.getCategories()[4]);
			ps.setString(6, photoDetails.getCategories()[5]);
			ps.setString(7, photoDetails.getCategories()[6]);
			ps.setString(8, photoDetails.getCategories()[7]);
			ps.setString(9, photoDetails.getCategories()[8]);
			ps.setString(10, photoDetails.getCategories()[9]);
			ps.setString(11, photoDetails.getDescription());
			ps.setString(12, photoDetails.getId());
			ps.setString(13, photoDetails.getFolderId());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<AiResponseDetailsForPhoto> getResponseDetails(String folderId) {
		ArrayList<AiResponseDetailsForPhoto> arr = new ArrayList<>();
		String sql = "select * from airesponse_table where workdrive_folder_id=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, folderId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String[] categories = new String[] { rs.getString("category1"), rs.getString("category2"),
						rs.getString("category3"), rs.getString("category4"), rs.getString("category5"),
						rs.getString("category6"), rs.getString("category7"), rs.getString("category8"),
						rs.getString("category9"), rs.getString("category10") };
				arr.add(new AiResponseDetailsForPhoto(rs.getString("workdrive_file_id"), categories,
						rs.getString("description"), rs.getString("workdrive_folder_id")));
			}
			return arr;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<CategorizePhotoDetails> getCategorizePhotoDetails(String folderId) {
		ArrayList<CategorizePhotoDetails> arr = new ArrayList<>();
		String sql="select category1 category,group_concat(workdrive_file_id) photo_ids from "
				+ "airesponse_table where workdrive_folder_id=? group by category1 ";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, folderId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String category=rs.getString(1);
				String[] photoIds=rs.getString(2).split(",");
				arr.add(new CategorizePhotoDetails(category,photoIds));
			}
			return arr;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
