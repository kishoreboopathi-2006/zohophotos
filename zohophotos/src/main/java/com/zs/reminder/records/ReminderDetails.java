package com.zs.reminder.records;

import java.sql.Time;
import java.time.LocalTime;

public class ReminderDetails {
	int userId;
	String title;
	String Date;
	Time time;
	String message;
	String previewUrl;
	String folderId;
	String category;

	public ReminderDetails(int userId, String title, String date, Time time, String category, String message,
			String previewUrl, String folderId) {
		this.userId = userId;
		this.title = title;
		this.Date = date;
		this.time = time;
		this.message = message;
		this.previewUrl = previewUrl;
		this.folderId = folderId;
		this.category = category;

	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

}
