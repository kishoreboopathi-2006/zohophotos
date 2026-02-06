package com.zs.aiplatform.services;

import java.util.ArrayList;

import com.zs.aiplatform.DAO.AiPlatformDBManagement;
import com.zs.aiplatform.records.CategorizePhotoDetails;

public class CategorizePhotoDetailsOperations {
	AiPlatformDBManagement dbManager;
	public ArrayList<CategorizePhotoDetails> getCategorizePhotoDetails(String folderId) {
		dbManager=new AiPlatformDBManagement();
		ArrayList<CategorizePhotoDetails> arr = dbManager.getCategorizePhotoDetails(folderId);
		return arr;
	}

}
