package com.zs.aiplatform.records;

public class AiResponseDetailsForPhoto {

	private String workdrive_file_id;
	private String folderId;
	private String[] categories;
	private String albumCategory;
	private String description;
	private String previewUrl;
	private String tamilDescription;

	public AiResponseDetailsForPhoto() {

	}

	public AiResponseDetailsForPhoto(String id, String[] categories, String description,String tamilDescription, String folderId) {
		this.workdrive_file_id = id;
		this.categories = categories;
		this.description = description;
		this.folderId = folderId;
		this.tamilDescription=tamilDescription;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public String getTamilDescription() {
		return tamilDescription;
	}

	public void setTamilDescription(String tamilDescribtion) {
		this.tamilDescription = tamilDescribtion;
	}

	public AiResponseDetailsForPhoto(String previewUrl, String[] categories, String albumCategory, String description,
			String tamilDescribtion, String folderId) {
		this.previewUrl = previewUrl;
		this.categories = categories;
		this.description = description;
		this.albumCategory = albumCategory;
		this.folderId = folderId;
		this.tamilDescription = tamilDescribtion;

	}

	public String getWorkdrive_file_id() {
		return workdrive_file_id;
	}

	public String getAlbumCategory() {
		return albumCategory;
	}

	public void setAlbumCategory(String albumCategory) {
		this.albumCategory = albumCategory;
	}

	public void setWorkdrive_file_id(String workdrive_file_id) {
		this.workdrive_file_id = workdrive_file_id;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public String getId() {
		return workdrive_file_id;
	}

	public void setId(String id) {
		this.workdrive_file_id = id;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategory1(String[] categories) {
		this.categories = categories;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
