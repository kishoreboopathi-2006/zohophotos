package com.zs.zohophotos.records;

public class WorkdrivePhotoDetails {
	private String imageName;
	private String resourceId;
	private String previewUrl;
	private String folderId;
	private String date;

	public WorkdrivePhotoDetails(int photoId, String folderId, String resourceId, String imageName, String previewUrl) {
		this.imageName = imageName;
		this.resourceId = resourceId;
		this.folderId = folderId;
		this.previewUrl = previewUrl;

	}

	public WorkdrivePhotoDetails(String folderId, String resourceId, String imageName, String previewUrl, String date) {
		this.imageName = imageName;
		this.resourceId = resourceId;
		this.folderId = folderId;
		this.previewUrl = previewUrl;
		this.date = date;
	}

	public WorkdrivePhotoDetails(String folderId, String resourceId, String imageName, String previewUrl) {
		this.imageName = imageName;
		this.resourceId = resourceId;
		this.folderId = folderId;
		this.previewUrl = previewUrl;
	}

	public WorkdrivePhotoDetails(String folderId, String resourceId, String imageName) {
		this.imageName = imageName;
		this.resourceId = resourceId;
		this.folderId = folderId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
}
