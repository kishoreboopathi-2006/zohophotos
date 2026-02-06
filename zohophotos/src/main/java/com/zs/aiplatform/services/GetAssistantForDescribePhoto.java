package com.zs.aiplatform.services;

import org.json.JSONObject;

import com.zs.accesstoken.CsezAccessToken;

import okhttp3.*;

public class GetAssistantForDescribePhoto{

    public static String getAssistantId() throws Exception {

        String zuid = "60042586463";
        String accessToken = CsezAccessToken.generateAccessToken();
      

        OkHttpClient client = new OkHttpClient();

        JSONObject payload = new JSONObject();
        payload.put("name", "image analyzer");
        payload.put("model", "gpt-4o");
        payload.put("description", "This is a bot used to describe images");
        payload.put("instructions",
                "Please analyze the image and describe it clearly.");
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
