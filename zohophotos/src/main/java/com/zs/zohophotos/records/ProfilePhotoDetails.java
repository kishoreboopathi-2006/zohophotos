package com.zs.zohophotos.records;

public class ProfilePhotoDetails {
	String previewUrl;
	int userId;
	String resourceId;

	public ProfilePhotoDetails(String previewUrl, int userId) {
		this.previewUrl = previewUrl;
		this.userId = userId;
	}

	public ProfilePhotoDetails(int userId, String resourceId) {
		this.userId = userId;
		this.resourceId = resourceId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
