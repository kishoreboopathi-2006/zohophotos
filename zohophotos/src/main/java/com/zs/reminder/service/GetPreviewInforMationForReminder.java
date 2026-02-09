package com.zs.reminder.service;

import java.util.ArrayList;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.zs.accesstoken.AccessTokenForWorkdrive;
import com.zs.reminder.records.ReminderDetails;
import com.zs.zohophotos.records.WorkdrivePhotoDetails;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetPreviewInforMationForReminder {
	public  ArrayList<ReminderDetails> getPreviewUrl(ArrayList<ReminderDetails> photoDetails) {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		String accessToken = "";
		for (int i = 0; i < photoDetails.size(); i++) {
			try {
				accessToken = AccessTokenForWorkdrive.getToken();
				RequestBody body = RequestBody.create(mediaType, "");
				Request request = new Request.Builder()
						.url("https://www.zohoapis.in/workdrive/api/v1/files/" + photoDetails.get(i).getPreviewUrl()
								+ "/previewinfo")
						.get().addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
						.addHeader("Accept", "application/vnd.api+json").build();

				Response response;
				response = client.newCall(request).execute();
				Gson gson = new Gson();
				String jsonResponse = response.body().string();
				JSONObject json = new JSONObject(jsonResponse);
				System.out.println(json.toString());
				String image = json.getJSONObject("data").getJSONObject("attributes").getString("preview_data_url");
//				System.out.println(image);
				photoDetails.get(i).setPreviewUrl(image);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return photoDetails;
	}
}
