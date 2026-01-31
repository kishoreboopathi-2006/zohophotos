package com.zs.zohophotos.records;

public class WorkdrivePhotoDetails {
	private String imageName;
	private String resourceId;
	private String previewUrl;
	private int folderId;
	private String category;
	private String describtion;

	public WorkdrivePhotoDetails(int folderId, String resourceId,String imageName,String previewUrl,String category ,String describtion) {
		this.imageName = imageName;
		this.resourceId = resourceId;
		this.folderId = folderId;
		this.previewUrl=previewUrl;
		this.describtion=describtion;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
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

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}
}
