//package com.zs.zohodiary.DAO;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.Time;
//
//public class InsertDiaryAndPhotosDetails {
//	Connection conn;
//	DiaryDetails dd;
//
//	InsertDiaryAndPhotosDetails(DiaryDetails dd) throws SQLException {
//		this.dd = dd;
//		System.out.println(dd);
//		conn = DBConnector.getConnection();
//	}
//
//	public boolean mapUserIdAndDiaryId(int diaryId) {
//
//		String sql = "insert into user_id_and_diary_id values(?,?)";
//		try (PreparedStatement ps = conn.prepareStatement(sql)) {
//			ps.setLong(1, diaryId);
//			ps.setInt(2, dd.userId());
//			ps.executeUpdate();
//			return true;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//
//	}
//
//	public boolean insertDiaryDetails() {
//		String sql = "insert into diary_details values(null,?,?,?,?)";
//		int diaryId = 0;
//		try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//			ps.setString(1, dd.date());
//			ps.setString(2, dd.title());
//			ps.setString(3, dd.content());
//			ps.setTime(4, Time.valueOf(dd.time()));
//			System.out.println(ps);
//			ps.executeUpdate();
//			ResultSet rs = ps.getGeneratedKeys();
//			if (rs.next()) {
//				diaryId = rs.getInt(1);
//			}
//			boolean flag = insertPhotoDetails(diaryId);
//			if (flag) {
//				flag = mapUserIdAndDiaryId(diaryId);
//				if (flag) {
//					return true;
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	public boolean insertPhotoDetails(int diaryId) {
//		String sql = "insert into diary_photo_details values (null,?,?,?,?)";
//		try (PreparedStatement ps = conn.prepareStatement(sql)) {
//			for (PhotoDetails photos : dd.photos()) {
//				System.out.println("photo:" + photos);
//				ps.setBinaryStream(1, photos.inputStream(), (int) photos.size());
//				ps.setString(2, photos.contentType());
//				ps.setInt(3, diaryId);
//				ps.setString(4, photos.name());
//				ps.addBatch();
//				System.out.println(ps);
//			}
//			ps.executeBatch();
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//}
