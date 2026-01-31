package com.zs.zohodiary.records;

import java.sql.Date;

public class DiaryHeaderDetails {
	String title;
	Date date;

	public DiaryHeaderDetails(Date date, String title) {
		this.date = date;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

}
