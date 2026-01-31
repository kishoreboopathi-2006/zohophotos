package com.zs.zohodiary.model;

import java.sql.Connection;
import java.sql.SQLException;

import com.zs.zohodiary.DAO.DBConnector;
import com.zs.zohodiary.DAO.DiaryDetailsManagement;
import com.zs.zohodiary.records.DiaryDetails;

public class InsertDiaryAndPhotosDetails1 {
	Connection conn;
	DiaryDetails dd;
	DiaryDetailsManagement ddm = new DiaryDetailsManagement();

	public InsertDiaryAndPhotosDetails1(DiaryDetails dd) throws SQLException {
		this.dd = dd;
		System.out.println(dd);
		conn = DBConnector.getConnection();
	}

	public boolean insertDiaryDetails() {
		int diaryId = ddm.insertDiaryTextContent(dd);
		boolean flag = ddm.insertPhotoDetails(diaryId, dd);
		if (flag) {
			flag = ddm.mapUserIdAndDiaryId(diaryId, dd);
			if (flag) {
				return true;
			}
		}
		return false;
	}

	public boolean insertPhotoDetails(int diaryId) {
		boolean flag = ddm.insertPhotoDetails(diaryId, dd);
		return flag;
	}

}
