package com.zs.aiplatform.services;

import java.util.ArrayList;

import com.zs.aiplatform.DAO.AiPlatformDBManagement;
import com.zs.aiplatform.records.AiResponseDetailsForPhoto;
import com.zs.aiplatform.records.FolderAndFileId;

public class AiResponseOperations {
	FolderAndFileId folder;
	boolean flag = false;
	private final String ASSISTANT_ID_FOR_DESCRIBEPHOTO = "928000000234307";
	private final String ASSISTANT_ID_FOR_CATEGORIZEPHOTO = "928000000232861";
	AiResponseDetailsForPhoto photoDetails = new AiResponseDetailsForPhoto();
	AiPlatformDBManagement dbManager = new AiPlatformDBManagement();

	public AiResponseOperations(FolderAndFileId folder) {
		// TODO Auto-generated constructor stub
		this.folder=folder;
	}
	public AiResponseOperations() {
	}

	public boolean insertAiresponse() {
		flag = getAiResponseForDescripePhoto();
		if (flag) {
			flag = getAiResponseForCategorizePhoto();
		}
		if (flag) {
			flag = dbManager.insertIntoAiResponseTable(photoDetails);
			return true;
		}
		return false;
	}

	public boolean getAiResponseForDescripePhoto() {
		try {
			byte[] imageBytes = WorkDriveUtil.downloadFile(folder.getFileId());

//    String assistantId = GetAssistantForDescribePhoto.getAssistantId();
			String aiFileId = PlatformAiFileUtil.createAIFile(imageBytes);
			String conversationId;
			conversationId = CreateChat.createChat(ASSISTANT_ID_FOR_DESCRIBEPHOTO, aiFileId);
			String description = GetResponse.getResponse(conversationId);
			photoDetails.setDescription(description);
			System.out.println("d" + description);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean getAiResponseForCategorizePhoto() {
		try {
			byte[] imageBytes = WorkDriveUtil.downloadFile(folder.getFileId());
//    String assistantId = GetAssistantForDescribePhoto.getAssistantId();
			String aiFileId = PlatformAiFileUtil.createAIFile(imageBytes);
			String conversationId;
			conversationId = CreateChat.createChat(ASSISTANT_ID_FOR_CATEGORIZEPHOTO, aiFileId);
			String Categorize = GetResponse.getResponse(conversationId);
			String[] arr = Categorize.split(",");
			System.out.println("key" + arr.toString());
			photoDetails = new AiResponseDetailsForPhoto(folder.getFileId(), arr, photoDetails.getDescription(),folder.getFolderId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	public ArrayList<AiResponseDetailsForPhoto> getAiResponseForPhotos(String folderId) {
		ArrayList<AiResponseDetailsForPhoto> arr=dbManager.getResponseDetails(folderId);
		return arr;
	}

}
