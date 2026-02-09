package com.zs.StoryAi.service;

import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.zs.accesstoken.CsezAccessToken;

import okhttp3.*;

public class GetAssistantId {

    public static String getAssistantId() throws Exception {

        String zuid = "60042586463";
        String accessToken = CsezAccessToken.generateAccessToken();
      

        OkHttpClient client = new OkHttpClient.Builder()
        	    .connectTimeout(60, TimeUnit.SECONDS)
        	    .readTimeout(180, TimeUnit.SECONDS)
        	    .writeTimeout(60, TimeUnit.SECONDS)
        	    .build();

        JSONObject payload = new JSONObject();
        payload.put("name", "image analyzer");
        payload.put("model", "gpt-4o");
        payload.put("description", "This is a bot used to describe images");
        payload.put("instructions","Generate story in All images with english in very short with emojis.");
        payload.put("ai_vendor", "openai");

        RequestBody body = RequestBody.create(
                
                MediaType.parse("application/json"),payload.toString()
        );

        Request request = new Request.Builder()
                .url("https://platformai.csez.zohocorpin.com/internalapi/v2/ai/assistant")
                .post(body)
                .addHeader("zuid", zuid)
                .addHeader("portal_id", "ZS")
                .addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
                //.addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONObject json = new JSONObject(responseBody);
        System.out.print("Hello");
        return json.getJSONObject("data").getString("assistant_id");
    }
}
