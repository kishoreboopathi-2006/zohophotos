package com.zs.reminder.service;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zs.accesstoken.AccessTokenForWorkdrive;
import com.zs.reminder.DAO.ReminderDBManagement;
import com.zs.reminder.records.ReminderDetails;
import com.zs.zohophotos.DAO.WorkDrivePhotosAndFoldersDetailsManagement;
import com.zs.zohophotos.records.WorkdrivePhotoDetails;

import jakarta.servlet.http.Part;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReminderDetailsOperation {
	private final String workdriveFolderId = "za6tm108d9f72c6af4a5ca6c15f68f0d58056";
	ReminderDBManagement dbManager = new ReminderDBManagement();

	public String uploadPhoto(Part filePart) throws IOException {
		String resourceId = "";
		try {
			String fileName = filePart.getSubmittedFileName();
			byte[] fileBytes = filePart.getInputStream().readAllBytes();
			String accessToken = "";
			accessToken = AccessTokenForWorkdrive.getToken();
			System.out.println("ACCESS TOKEN = " + accessToken);

			OkHttpClient client = new OkHttpClient();

			RequestBody fileBody = RequestBody.create(MediaType.parse(filePart.getContentType()), fileBytes);

			MultipartBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("parent_id", workdriveFolderId).addFormDataPart("content", fileName, fileBody)
					.build();

			Request request = new Request.Builder().url("https://www.zohoapis.in/workdrive/api/v1/upload")
					.post(requestBody).addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
					// .addHeader("Accept", "application/vnd.api+json")
					.addHeader("Content-Type", "application/vnd.api+json").build();
			try (Response zohoResponse = client.newCall(request).execute()) {
				String responseBody = zohoResponse.body().string();
				System.out.println("UPLOAD RESPONSE = " + responseBody);
				JSONObject root = new JSONObject(responseBody);
				JSONArray array = root.getJSONArray("data");
				JSONObject firstObject = array.getJSONObject(0);
				JSONObject object = firstObject.getJSONObject("attributes");
				resourceId = object.getString("resource_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resourceId;
	}

	public boolean insertReminderDetails(ReminderDetails reminderDetails) {
		Boolean flag = dbManager.insertReminderDetails(reminderDetails);
		return flag;
		// TODO Auto-generated method stub

	}

	public ArrayList<ReminderDetails> getReminderDetails(int userId) {
		ArrayList<ReminderDetails> details = dbManager.getReminderDetails(userId);
		GetPreviewInforMationForReminder obj = new GetPreviewInforMationForReminder();
		details = obj.getPreviewUrl(details);
		// TODO Auto-generated method stub
		return details;
	}

	public boolean deleteData(String message) {
		boolean flag=dbManager.deleteData(message);
		return flag;
	}
}
