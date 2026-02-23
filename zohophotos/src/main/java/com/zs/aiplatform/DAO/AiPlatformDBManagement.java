package com.zs.aiplatform.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

	public HashMap<Integer, String> getCategory() {
		HashMap<Integer, String> map = new HashMap<>();
		String sql = "select * from  category_details";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				map.put(rs.getInt(1), rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

	public boolean insertAlbumDetails(AiResponseDetailsForPhoto photo) {
		System.out.println(photo.getTamilDescription());
		String sql = "insert into album_details values(null,?,?,?,?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, photo.getWorkdrive_file_id());
			ps.setString(2, photo.getAlbumCategory());
			ps.setString(3, photo.getDescription());
			ps.setString(4, photo.getTamilDescription());
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public int insertCategoryDetails(String category) {
		String sql = "insert ignore into category_details values(null,?)";
		try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, category);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	public boolean insertPhotoCategoryMap(AiResponseDetailsForPhoto photo, int categoryId) {
		String sql = "insert into photo_category_map values(null,?,?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(2, categoryId);
			ps.setString(1, photo.getWorkdrive_file_id());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;

	}

	public ArrayList<AiResponseDetailsForPhoto> getAiResponseForPhotos(String folderId) {
		ArrayList<AiResponseDetailsForPhoto> arr = new ArrayList<>();
		String sql = "select \n"
				+ " w.preview_url ,group_concat(c.category),a.album_category,a.description,a.tamil_description\n"
				+ "from \n" + "photo_category_map p join category_details c on c.category_id =p.category_id \n"
				+ "join workdrive_photo_details w on w.workdrive_file_id = p.workdrive_file_id \n"
				+ "join album_details a on w.workdrive_file_id=a.workdrive_file_id\n" + "where w.workdrive_folder_id=?"
				+ "group by w.preview_url ,a.album_category,a.description,a.tamil_description";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, folderId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String[] categories = rs.getString(2).split(",");
				arr.add(new AiResponseDetailsForPhoto(rs.getString(1), categories, rs.getString(3), rs.getString(4),
						rs.getString(5), folderId));
			}
			return arr;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getDescription(String fileId) {
		String sql = "select description from album_details where workdrive_file_id=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, fileId);
			ResultSet rs;
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<CategorizePhotoDetails> getCategoryForAlbum(String folderId) {
		ArrayList<CategorizePhotoDetails> arr = new ArrayList<>();
		String sql = "select a.album_category,group_concat(a.workdrive_file_id) from album_details a join workdrive_photo_details p on p.workdrive_file_id=a.workdrive_file_id where workdrive_folder_id=? group by a.album_category";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, folderId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String[] photoIds = rs.getString(2).split(",");
				arr.add(new CategorizePhotoDetails(rs.getString(1), photoIds));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return arr;
	}

}

//	public boolean insertIntoAiResponseTable(AiResponseDetailsForPhoto photoDetails) {
//		String sql = "insert into airesponse_table values(null,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//		try (PreparedStatement ps = conn.prepareStatement(sql)) {
//			ps.setString(1, photoDetails.getCategories()[0]);
//			ps.setString(2, photoDetails.getCategories()[1]);
//			ps.setString(3, photoDetails.getCategories()[2]);
//			ps.setString(4, photoDetails.getCategories()[3]);
//			ps.setString(5, photoDetails.getCategories()[4]);
//			ps.setString(6, photoDetails.getCategories()[5]);
//			ps.setString(7, photoDetails.getCategories()[6]);
//			ps.setString(8, photoDetails.getCategories()[7]);
//			ps.setString(9, photoDetails.getCategories()[8]);
//			ps.setString(10, photoDetails.getCategories()[9]);
//			ps.setString(11, photoDetails.getDescription());
//			ps.setString(12, photoDetails.getId());
//			ps.setString(13, photoDetails.getFolderId());
//			ps.executeUpdate();
//			return true;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;

//	public ArrayList<AiResponseDetailsForPhoto> getResponseDetails(String folderId) {
//		ArrayList<AiResponseDetailsForPhoto> arr = new ArrayList<>();
//		String sql = "select * from airesponse_table where workdrive_folder_id=?";
//		try (PreparedStatement ps = conn.prepareStatement(sql)) {
//			ps.setString(1, folderId);
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				String[] categories = new String[] { rs.getString("category1"), rs.getString("category2"),
//						rs.getString("category3"), rs.getString("category4"), rs.getString("category5"),
//						rs.getString("category6"), rs.getString("category7"), rs.getString("category8"),
//						rs.getString("category9"), rs.getString("category10") };
//				arr.add(new AiResponseDetailsForPhoto(rs.getString("workdrive_file_id"), categories,
//						rs.getString("description"), rs.getString("workdrive_folder_id")));
//			}
//			return arr;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public ArrayList<CategorizePhotoDetails> getCategorizePhotoDetails(String folderId) {
//		ArrayList<CategorizePhotoDetails> arr = new ArrayList<>();
//		String sql = "select category1 category,group_concat(workdrive_file_id) photo_ids from "
//				+ "airesponse_table where workdrive_folder_id=? group by category1 ";
//		try (PreparedStatement ps = conn.prepareStatement(sql)) {
//			ps.setString(1, folderId);
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				String category = rs.getString(1);
//				String[] photoIds = rs.getString(2).split(",");
//				arr.add(new CategorizePhotoDetails(category, photoIds));
//			}
//			return arr;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public String getDescribtion(String fileId) {
//		String sql = "select description from airesponse_table where workdrive_file_id =?";
//		try (PreparedStatement ps = conn.prepareStatement(sql)) {
//			ps.setString(1, fileId);
//			ResultSet rs = ps.executeQuery();
//			String descripe = null;
//			while (rs.next()) {
//				descripe = rs.getString(1);
//			}
//			return descripe;
//		} catch (SQLException e) {
//		}
//		return null;
//	}
