package com.zs.zohophotos.service;

import java.sql.SQLException;

import com.zs.loginpage.DAO.UserDetailsManagement;
import com.zs.zohophotos.records.ProfilePhotoDetails;

public class ProfilePhotoDetailsOperation {

	public String insertProfilePhoto(ProfilePhotoDetails photo) {
		GetPreviewInformation preview = new GetPreviewInformation();
		String previewUrl = preview.getPreviewUrl(photo.getResourceId());
		photo.setPreviewUrl(previewUrl);
		changeProfilePhoto(photo);
		return previewUrl;
	}

	public boolean changeProfilePhoto(ProfilePhotoDetails photo) {
		try {
			UserDetailsManagement dbManager = new UserDetailsManagement();
			boolean flag = dbManager.changeProfilePhoto(photo);
			return flag;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

}
