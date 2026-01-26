package com.zs.zohophotos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkDrivePhotosAndFoldersDetailsManagement {

//    private static final String URL =
//            "jdbc:mysql://localhost:3306/WorkdriveUploadZoho";
//    private static final String USER = "Kaviya";
//    private static final String PASSWORD = "Kaviyaa@2007";
//
//    private static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }

	public static String getFolderId(UserDetails ud) {

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

	public static String getFolderId(int userId) {

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

}
//package com;
//
//import java.util.concurrent.ConcurrentHashMap;
//
//public class DBUtil {
//
//    private static ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
//
//    public static void saveFolderId(int userId, String folderId) {
//        map.put(userId, folderId);
//    }
//
//    public static String getFolderId(int userId) {
//        return map.get(userId);
//    }
//}
