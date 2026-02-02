package com.zs.zohophotos.records;

public class FavouritePhotoDetails {
	private String previewUrl;
	private int userId;
	private String photoName;
	public FavouritePhotoDetails(String photoName,String previewUrl, int userId) {
		this.photoName=photoName;
		this.previewUrl = previewUrl;
		this.userId = userId;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
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
