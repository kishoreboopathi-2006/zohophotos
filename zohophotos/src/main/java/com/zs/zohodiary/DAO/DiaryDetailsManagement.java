package com.zs.zohodiary.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;

import com.zs.zohodiary.model.InsertDiaryAndPhotosDetails1;
import com.zs.zohodiary.records.DiaryDetails;
import com.zs.zohodiary.records.DiaryHeaderDetails;
import com.zs.zohodiary.records.DiaryResponseDetails;
import com.zs.zohodiary.records.PhotoDetails;
import com.zs.zohodiary.records.PhotoResponseDetails;

public class DiaryDetailsManagement {
	Connection conn;

	public DiaryDetailsManagement() throws SQLException {
		getConnection();
	}

	public boolean getConnection() throws SQLException {
		conn = DBConnector.getConnection();
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
				System.out.println(diaryDetails.getDate());
				try (PreparedStatement ps1 = conn.prepareStatement(getPhotoDetailsSql)) {
					ArrayList<PhotoResponseDetails> photos = new ArrayList<>();
					ps1.setInt(1, diaryDetails.getDiaryId());
					ResultSet rs1 = ps1.executeQuery();
					while (rs1.next()) {
						int photoId = rs1.getInt("photo_id");
						photos.add(new PhotoResponseDetails(photoId, contextPath + "/photo?id=" + photoId));
					}
					diaryDetails.setPhotos(photos);
					arr.add(diaryDetails);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	public boolean updateDiaryDetails(DiaryDetails dd) {
		int id = getDiaryId(dd);
		System.out.println(id);
		String sql = "update diary_details set title=?, content=?, time =? where diary_id=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, dd.title());
			ps.setString(2, dd.content());
			ps.setTime(3, Time.valueOf(dd.time()));
			ps.setInt(4, id);
			System.out.println(ps);
			ps.executeUpdate();
			InsertDiaryAndPhotosDetails1 insert = new InsertDiaryAndPhotosDetails1(dd);
			boolean flag = insert.insertPhotoDetails(id);
			if (flag) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean deletePhotoDetails(ArrayList<Integer> photoIds) {

		String sql = "delete from diary_photo_details where photo_id=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			for (int i = 0; i < photoIds.size(); i++) {
				ps.setInt(1, photoIds.get(i));
				ps.addBatch();
			}

			ps.executeBatch();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public int getDiaryId(DiaryDetails dd) {
		System.out.println(dd.date());
		String sql = "select d.diary_id from diary_details d join user_id_and_diary_id u on u.diary_id=d.diary_id where d.date=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, dd.date());
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public PhotoDetails getPhotoByPhotoId(int photoId) {
		String sql = "select * from diary_photo_details where photo_id=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			System.out.println("hell");
			ps.setInt(1, photoId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return new PhotoDetails(rs.getBinaryStream("photo"), 0, rs.getString("photo_name"),
						rs.getString("content_type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int insertDiaryTextContent(DiaryDetails dd) {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diaryId;
	}
	public boolean insertPhotoDetails(int diaryId,DiaryDetails dd) {
		String sql = "insert into diary_photo_details values (null,?,?,?,?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			for (PhotoDetails photos : dd.photos()) {
				System.out.println("photo:" + photos);
				ps.setBinaryStream(1, photos.inputStream(), (int) photos.size());
				ps.setString(2, photos.contentType());
				ps.setInt(3, diaryId);
				ps.setString(4, photos.name());
				ps.addBatch();
				System.out.println(ps);
			}
			ps.executeBatch();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean mapUserIdAndDiaryId(int diaryId,DiaryDetails dd) {

		String sql = "insert into user_id_and_diary_id values(?,?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, diaryId);
			ps.setInt(2, dd.userId());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

}
