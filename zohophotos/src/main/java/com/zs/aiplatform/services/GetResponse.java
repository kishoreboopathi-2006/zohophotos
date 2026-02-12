package com.zs.aiplatform.services;

import okhttp3.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zs.accesstoken.CsezAccessToken;

public class GetResponse {

	public static String getResponse(String conversationId) throws Exception {

	    String accessToken = CsezAccessToken.generateAccessToken();
	    OkHttpClient client = new OkHttpClient.Builder()
	            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
	            .readTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
	            .writeTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
	            .callTimeout(180, java.util.concurrent.TimeUnit.SECONDS)
	            .retryOnConnectionFailure(true)
	            .build();

	    String url =
	        "https://platformai.csez.zohocorpin.com/internalapi/v2/ai/assistant/chat?conversation_id="
	        + conversationId;

	    for (int attempt = 1; attempt <= 30; attempt++) {

	        Request request = new Request.Builder()
	                .url(url)
	                .get()
	                .addHeader("zuid", "60042586466")
	                .addHeader("portal_id", "ZS")
	                .addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
	                .addHeader("Content-Type", "application/json")
	                .build();

	        Response response = client.newCall(request).execute();
	        String responseBody = response.body().string();

	        System.out.println("GET RESPONSE = " + responseBody);

	        JSONObject json = new JSONObject(responseBody);
	        JSONObject data = json.getJSONObject("data");

	        
	        if (!data.has("messages")) {
	            System.out.println("Attempt " + attempt + ": AI still processing...");
	            Thread.sleep(2000); 
	            continue;
	        }

//	        
//	        JSONArray messages = data.getJSONArray("messages");
//
//	        for (int i = 0; i < messages.length(); i++) {
//	            JSONArray content = messages.getJSONObject(i).getJSONArray("content");
//
//	            for (int j = 0; j < content.length(); j++) {
//	                JSONObject c = content.getJSONObject(j);
//	                if ("output_text".equals(c.getString("type"))) {
//	                    return c.getString("text");
//	                }
//	            }
//	        }
	        JSONArray messages = data.getJSONArray("messages");

	        for (int i = 0; i < messages.length(); i++) {
	            JSONObject message = messages.getJSONObject(i);

	            if (message.has("content")) {
	                return message.getString("content"); 
	            }
	        }

	    }

	    throw new RuntimeException("AI response not ready after waiting");
	}
}

