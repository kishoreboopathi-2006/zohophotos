package com.zs.zohophotos.service;

import java.sql.SQLException;

import com.zs.loginpage.DAO.UserDetailsManagement;
import com.zs.zohophotos.DAO.WorkDrivePhotosAndFoldersDetailsManagement;
import com.zs.zohophotos.records.UserDetails;

public class UserDetailsOperations {
	UserDetailsManagement dbManager;

	public UserDetailsOperations() {
		try {
			dbManager = new UserDetailsManagement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public UserDetails getUserDetails(UserDetails user) {
		user=dbManager.getUserDetails(user);
		return user;
	}
}
