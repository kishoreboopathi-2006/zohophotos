package com.zs.reminder.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

import com.zs.reminder.records.ReminderDetails;
import com.zs.zohodiary.DAO.DBConnector;

public class ReminderDBManagement {
	Connection conn;

	public ReminderDBManagement() {
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

	public Boolean insertReminderDetails(ReminderDetails reminderDetails) {
		String sql = "insert into reminder_details values(null,?,?,?,?,?,?,?,?,default)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, reminderDetails.getUserId());
			ps.setString(2, reminderDetails.getTitle());
			ps.setString(3, reminderDetails.getDate());
			ps.setTime(4, reminderDetails.getTime());
			ps.setString(5, reminderDetails.getCategory());
			ps.setString(6, reminderDetails.getMessage());
			ps.setString(7, reminderDetails.getPreviewUrl());
			ps.setString(8, reminderDetails.getFolderId());
			ps.executeUpdate();
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<ReminderDetails> getReminderDetails(int userId) {
		ArrayList<ReminderDetails> reminderDetailsArray = new ArrayList<>();
		String sql = "select * from reminder_details where user_id=? order by date";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				reminderDetailsArray.add(new ReminderDetails(rs.getInt(2), rs.getString(3), rs.getString(4),
						rs.getTime(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

		return reminderDetailsArray;
	}

	public boolean deleteData(String message) {
		String sql = "delete from reminder_details where message =?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, message);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
