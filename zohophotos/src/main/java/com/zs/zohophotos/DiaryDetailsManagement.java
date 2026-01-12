package com.zs.zohophotos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class DiaryDetailsManagement {
	Connection conn;

	DiaryDetailsManagement() {
		getConnection();
	}

	public boolean getConnection() {
		conn = DBConnector.getConnection();
		System.out.println("true");
		return true;
	}

	public ArrayList<DiaryHeaderDetails> getDiaryHeaderDetails(int userId) {
		ArrayList<DiaryHeaderDetails> arr = new ArrayList<>();
		String sql = "select date,title from diary_details d join user_id_and_diary_id u on u.diary_id=d.diary_id where u.user_id=? order by d.date";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("ds");
				arr.add(new DiaryHeaderDetails(rs.getDate(1), rs.getString(2)));
			}
			System.out.println(arr);
			return arr;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	public ArrayList<DiaryDetails> getDiaryDetails(int userId) {
		ArrayList<DiaryDetails> arr = new ArrayList<>();
		String sql = "select * from diary_details d join user_id_and_diary_id u on u.diary_id=d.diary_id where u.user_id=? order by d.date ,d.`time`";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;

	}

	public ArrayList<DiaryResponseDetails> getDiaryResponseDetails(int userId, String contextPath) {
		ArrayList<DiaryResponseDetails> arr = new ArrayList<>();
		String getDiaryDetailsSql = "select * from diary_details d join user_id_and_diary_id u on u.diary_id=d.diary_id where u.user_id=? order by d.date ,d.`time`";
		String getPhotoDetailsSql = "select photo_id from diary_photo_details where diary_id=?";
		try (PreparedStatement ps = conn.prepareStatement(getDiaryDetailsSql)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				DiaryResponseDetails diaryDetails = new DiaryResponseDetails(rs.getInt("diary_id"), rs.getDate("date"),
						rs.getTime("time"), rs.getString("title"), rs.getString("content"));
				System.out.println(diaryDetails.getContent());
				try (PreparedStatement ps1 = conn.prepareStatement(getPhotoDetailsSql)) {
					ArrayList<PhotoResponseDetails> photos = new ArrayList<>();
					ps1.setInt(1, diaryDetails.getDiaryId());
					ResultSet rs1 = ps1.executeQuery();
					while (rs1.next()) {
						int photoId = rs1.getInt("photo_id");
						photos.add(new PhotoResponseDetails(photoId, contextPath + "/photo?id=" +photoId));
					}
				diaryDetails.setPhotos(photos);
				arr.add(diaryDetails);
			}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

}
