package com.zs.zohophotos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public class InsertDiaryAndPhotosDetails {
	Connection conn;
	DiaryDetails dd;

	InsertDiaryAndPhotosDetails(DiaryDetails dd) {
		this.dd = dd;
		System.out.println(dd);
		conn = DBConnector.getConnection();
		insertDiaryDetails();
	}

	public void mapUserIdAndDiaryId(int diaryId) {

		String sql = "insert into user_id_and_diary_id values(?,?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, diaryId);
			ps.setInt(2, dd.userId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertDiaryDetails() {
		String sql = "insert into diary_details values(null,?,?,?,?)";
		int diaryId = 0;
		try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, dd.date());
			ps.setString(2, dd.title());
			ps.setString(3, dd.content());
			ps.setTime(4, Time.valueOf(dd.time()));
			System.out.println(ps);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				diaryId = rs.getInt(1);
			}

			insertPhotoDetails(diaryId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertPhotoDetails(int diaryId) {
		String sql = "insert into diary_photo_details values (null,?,?,?,?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			for (PhotoDetails photos : dd.photos()) {
				System.out.println(photos);
				ps.setBinaryStream(1, photos.inputStream(), (int) photos.size());
				ps.setString(2, photos.contentType());
				ps.setInt(3, diaryId);
				ps.setString(4, photos.name());
				ps.addBatch();
			}
			ps.executeBatch();
			mapUserIdAndDiaryId(diaryId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
