package com.zs.aiplatform.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zs.aiplatform.DAO.AiPlatformDBManagement;
import com.zs.aiplatform.records.AiResponseDetailsForPhoto;
import com.zs.aiplatform.records.CategorizePhotoDetails;
import com.zs.aiplatform.records.FolderAndFileId;

public class AiResponseOperations {
	FolderAndFileId folder;
	boolean flag = false;
	AiResponseDetailsForPhoto photoDetails = new AiResponseDetailsForPhoto();
	AiPlatformDBManagement dbManager = new AiPlatformDBManagement();
	AiResponseForPhoto describe;
	static HashMap<Integer, String> map;

	public AiResponseOperations() {
	}

	public AiResponseOperations(FolderAndFileId folder) {
		this.folder = folder;
		describe = new AiResponseForPhoto(System.currentTimeMillis());
		map = dbManager.getCategory();

	}

	public boolean insertAiresponse() {
		flag = getAiResponseForDescripePhoto();
		if (flag) {
			flag = getAiResponseForCategorizePhoto();
			System.out.println("category" + flag);
		}
		if (flag) {
			flag = getAiResponseForAlbumCategory();
			System.out.println("album" + flag);
		}
		if (flag) {
			flag = insertCategoryTable();
			System.out.println("insert" + flag);
		}
		if (flag) {
			flag = insertAlbumCategoryTable();
			System.out.println("insertAlbum" + flag);

		}
		return flag;
	}

	private boolean insertAlbumCategoryTable() {
		boolean flag = dbManager.insertAlbumDetails(photoDetails);
		return flag;
	}

	private boolean insertCategoryTable() {
		boolean flag = false;
		for (String i : photoDetails.getCategories()) {
			System.out.println(i + map);
			if (!map.isEmpty()) {
				if (!map.containsValue(i)) {
					int key = dbManager.insertCategoryDetails(i);
					map.put(key, i);
					System.out.println(key);
					flag = dbManager.insertPhotoCategoryMap(photoDetails, key);
				} else {
					for (int j : map.keySet()) {
						if (map.get(j).equals(i)) {
							flag = dbManager.insertPhotoCategoryMap(photoDetails, j);
						}
					}
				}
			}
		}
		return flag;
	}

	public boolean getAiResponseForDescripePhoto() {
		try {
			byte[] imageBytes = WorkDriveUtil.downloadFile(folder.getFileId());
			String aiFileId = PlatformAiFileUtil.createAIFile(imageBytes);
			String JsonResponse = describe.describeOnePhoto(aiFileId);
			JSONObject json = new JSONObject(JsonResponse);
			JSONArray jsonArray = json.getJSONArray("output");
			JSONObject firstJson = jsonArray.getJSONObject(1);
			JSONArray content = firstJson.getJSONArray("content");
			String description = content.getJSONObject(0).getString("text");
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
			String aiFileId = PlatformAiFileUtil.createAIFile(imageBytes);
			String JsonResponse = describe.categorizePhoto(aiFileId);
			JSONObject json = new JSONObject(JsonResponse);
			JSONArray jsonArray = json.getJSONArray("output");
			JSONObject firstJson = jsonArray.getJSONObject(1);
			JSONArray content = firstJson.getJSONArray("content");
			String categorize = content.getJSONObject(0).getString("text");
			String[] arr = categorize.split(",");
			System.out.println("key" + Arrays.toString(arr));
			photoDetails = new AiResponseDetailsForPhoto(folder.getFileId(), arr, photoDetails.getDescription(),
					folder.getFolderId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean getAiResponseForAlbumCategory() {
		try {
			byte[] imageBytes = WorkDriveUtil.downloadFile(folder.getFileId());
			String aiFileId = PlatformAiFileUtil.createAIFile(imageBytes);
			String JsonResponse = describe.getAlbumCategory(aiFileId);
			JSONObject json = new JSONObject(JsonResponse);
			JSONArray jsonArray = json.getJSONArray("output");
			JSONObject firstJson = jsonArray.getJSONObject(1);
			JSONArray content = firstJson.getJSONArray("content");
			String albumCategory = content.getJSONObject(0).getString("text");
			photoDetails.setAlbumCategory(albumCategory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}

	public ArrayList<AiResponseDetailsForPhoto> getAiResponseForPhotos(String folderId) {
		ArrayList<AiResponseDetailsForPhoto> responseArray=dbManager.getAiResponseForPhotos( folderId);
		return responseArray;
	}

	public String getDescription(String fileId) {
		String description =dbManager.getDescription(fileId);
		return description;
	}

	public ArrayList<CategorizePhotoDetails> getCategorizePhotoDetails(String folderId) {
		ArrayList<CategorizePhotoDetails> responseArray=dbManager.getCategoryForAlbum(folderId);
		return null;
	}

//	public ArrayList<AiResponseDetailsForPhoto> getAiResponseForPhotos(String folderId) {
//		ArrayList<AiResponseDetailsForPhoto> arr = dbManager.getResponseDetails(folderId);
//		return arr;
//	}
//
//	public String getDescribtion(String fileId) {
//		String describtion = dbManager.getDescribtion(fileId);
//		return describtion;
//	}

}
