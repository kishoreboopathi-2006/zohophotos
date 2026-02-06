package com.zs.aiplatform.services;

import org.json.JSONObject;

import com.zs.accesstoken.CsezAccessToken;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PlatformAiFileUtil {
	
	public static String createAIFile(byte[] imageBytes) throws Exception {

	    String accessToken = CsezAccessToken.generateAccessToken();

	    OkHttpClient client = new OkHttpClient.Builder()
	            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
	            .readTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
	            .writeTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
	            .callTimeout(180, java.util.concurrent.TimeUnit.SECONDS)
	            .retryOnConnectionFailure(true)
	            .build();

	    RequestBody fileBody = RequestBody.create(
	            MediaType.parse("image/jpeg"), imageBytes
	    );

	    MultipartBody requestBody = new MultipartBody.Builder()
	            .setType(MultipartBody.FORM)
	            .addFormDataPart("vision", "kaviya.jpg", fileBody)
	            .build();

	    Request request = new Request.Builder()
	            .url("https://platformai.csez.zohocorpin.com/internalapi/v2/ai/file?ai_vendor=openai")
	            .post(requestBody)
	            .addHeader("zuid", "60042586463")
	            .addHeader("portal_id", "ZS")
	            .addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
	            .addHeader("Accept", "application/json")
	            .build();

	    try (Response response = client.newCall(request).execute()) {

	        String responseBody = response.body().string();
	        JSONObject json = new JSONObject(responseBody);

	        if (json.has("error")) {
	            throw new RuntimeException(
	                "File upload failed: " +
	                json.getJSONObject("error").getString("message")
	            );
	        }

	        if (!json.has("data")) {
	            throw new RuntimeException("API response missing 'data': " + responseBody);
	        }

	        return json.getJSONObject("data")
	                   .getJSONArray("files")
	                   .getJSONObject(0)
	                   .getString("file_id");
	    }
	}

}

