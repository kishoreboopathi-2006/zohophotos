package com.zs.aiplatform.records;

public class CategorizePhotoDetails {
	String category;
	String[] photosIds;

	public CategorizePhotoDetails(String category, String[] photosIds) {
		this.category = category;
		this.photosIds = photosIds;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String[] getPhotosIds() {
		return photosIds;
	}

	public void setPhotosIds(String[] photosIds) {
		this.photosIds = photosIds;
	}
}
