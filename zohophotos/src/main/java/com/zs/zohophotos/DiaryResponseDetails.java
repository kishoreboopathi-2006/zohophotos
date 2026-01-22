package com.zs.zohophotos;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class DiaryResponseDetails {
	private int diaryId;
	private Date date;
	private Time time;
	private String title;
	private String content;
	private ArrayList<PhotoResponseDetails> photos;

	public ArrayList<PhotoResponseDetails> getPhotos() {
		return photos;
	}

	public void setPhotos(ArrayList<PhotoResponseDetails> photos) {
		this.photos = photos;
	}

	public DiaryResponseDetails(int diaryId, Date date, Time time, String title, String content) {
		this.diaryId = diaryId;
		this.date = date;
		this.time = time;
		this.title=title;
		this.content = content;
	}

	public int getDiaryId() {
		return diaryId;
	}

	public void setDiaryId(int diaryId) {
		this.diaryId = diaryId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
