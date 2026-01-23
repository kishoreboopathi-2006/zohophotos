package com.zs.zohophotos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

    
    private static final String URL =
            "jdbc:mysql://localhost:3306/WorkdriveUploadZoho";
    private static final String USER = "Kaviya";
    private static final String PASSWORD = "Kaviyaa@2007";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

  
    public static String getFolderId(String username) {

        String sql = "SELECT folder_id FROM user_workdrive_folder WHERE username = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("folder_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    
    public static void saveFolderId(String username, String folderId) {

        String sql = "INSERT INTO user_workdrive_folder (username, folder_id) VALUES (?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, folderId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

