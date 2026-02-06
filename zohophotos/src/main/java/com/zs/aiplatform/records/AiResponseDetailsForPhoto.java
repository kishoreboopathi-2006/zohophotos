package com.zs.aiplatform.records;

public class AiResponseDetailsForPhoto {

    private String workdrive_file_id;
    private String folderId;
    private String[] categories=new String[10];

    private String description;

    public AiResponseDetailsForPhoto() {
    }

    public AiResponseDetailsForPhoto(String id, String[] categories, String description,String folderId) {
        this.workdrive_file_id = id;
        this.categories = categories;
        this.description = description;
        this.folderId=folderId;
    }

    public String getWorkdrive_file_id() {
		return workdrive_file_id;
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
