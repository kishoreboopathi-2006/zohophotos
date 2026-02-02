package com.zs.zohophotos.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.zs.zohophotos.DAO.WorkDrivePhotosAndFoldersDetailsManagement;
import com.zs.zohophotos.model.AccessToken.AccessTokenForWorkdrive;
import com.zs.zohophotos.records.WorkdrivePhotoDetails;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetWorkdrivePhotoDetails {
	int userId;
	String accessToken;

	public GetWorkdrivePhotoDetails(int userId) {
		this.userId = userId;
		getAccessToken();
	}

	public void getAccessToken() {
		try {
			accessToken = AccessTokenForWorkdrive.getToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getPhotoDetails() {
		ArrayList<WorkdrivePhotoDetails> photoDetailsArray = new ArrayList<>();
		String workdriveFolderId = WorkDrivePhotosAndFoldersDetailsManagement.getWorkdriveFolderId(userId);
		int folderId = WorkDrivePhotosAndFoldersDetailsManagement.getFolderId(userId);
//		System.out.println("RETRIEVE FOLDER ID = " + workdriveFolderId);
		String response = getResponse(workdriveFolderId);
		JSONObject json = new JSONObject(response);
		JSONArray dataArray = json.getJSONArray("data");
		for (int i = 0; i < dataArray.length(); i++) {
			JSONObject fileObj = dataArray.getJSONObject(i);
			String resourceId = fileObj.getString("id");
			String name = fileObj.getJSONObject("attributes").getString("name");
			WorkdrivePhotoDetails photoDetails = new WorkdrivePhotoDetails(workdriveFolderId, resourceId, name);
//			System.out.println("Resource ID: " + resourceId + "name:" + name);
			photoDetailsArray.add(photoDetails);
		}
		Gson gson = new Gson();
		photoDetailsArray = GetPreviewInformation.getPreviewUrl(photoDetailsArray);
		String previewDetails = gson.toJson(photoDetailsArray);
//		System.out.println(previewDetails);
		return previewDetails;

	}

	public String getResponse(String workdriveFolderId) {
		OkHttpClient client = new OkHttpClient();
		String url = "https://www.zohoapis.in/workdrive/api/v1/files/" + workdriveFolderId + "/files";
//		System.out.println("FINAL URL = " + url);
		Request request = new Request.Builder().url(url).get()
				.addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
				.addHeader("Accept", "application/vnd.api+json").build();
		try (Response response = client.newCall(request).execute()) {
			String body = response.body().string();
//			System.out.println("ZOHO STATUS = " + response.code());
//			System.out.println("RETRIEVE RESPONSE = " + body);
			return body;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
