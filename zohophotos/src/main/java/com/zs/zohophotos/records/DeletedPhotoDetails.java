package com.zs.zohophotos.records;

public class DeletedPhotoDetails {

	String url;
	int userId;
	String resourceId;

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public DeletedPhotoDetails(int userId, String resourceId, String url) {
		this.url = url;
		this.userId = userId;
		this.resourceId = resourceId;
	}

}
