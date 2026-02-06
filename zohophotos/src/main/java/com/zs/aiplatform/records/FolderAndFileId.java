package com.zs.aiplatform.records;

public class FolderAndFileId {
	String folderId;
	String fileId;
	public FolderAndFileId(String folderId,String fileId) {
		this.fileId=fileId;
		this.folderId=folderId;
	}
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
}
