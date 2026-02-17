package com.zs.zohophotos.records;

public class UserDetails {
	int userId;
	String userName;
	String userEmail;
	String password;
	String previewUrl;

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public UserDetails(int userId, String userName, String userEmail) {
		this.userEmail = userEmail;
		this.userId = userId;
		this.userName = userName;
	}

	public UserDetails(int userId, String userName, String userEmail, String password) {
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDetails [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail + ", password="
				+ password + ", previewUrl=" + previewUrl + "]";
	}

}
