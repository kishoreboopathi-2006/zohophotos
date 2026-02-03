package com.zs.aiplatform.services;

import okhttp3.*;
import org.json.JSONObject;

import com.zs.accesstoken.CsezAccessToken;

import org.json.JSONArray;

public class CreateChat {

    public static String createChat(String assistantId, String fileId) throws Exception {

        OkHttpClient client = new OkHttpClient();

       
        JSONObject textObj = new JSONObject();
        textObj.put("type", "text");
        textObj.put("text",
            "\"Capture the feeling and facial expression and short.\"\n"
        );

        JSONObject imageInner = new JSONObject();
        imageInner.put("type", "file_id");
        imageInner.put("file_id", fileId);

        JSONObject imageObj = new JSONObject();
        imageObj.put("type", "image");
        imageObj.put("image", imageInner);

        JSONArray contents = new JSONArray();
        contents.put(textObj);
        contents.put(imageObj);

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("contents", contents);

        JSONArray messages = new JSONArray();
        messages.put(message);

        JSONObject payload = new JSONObject();
        payload.put("assistant_id", assistantId);
        payload.put("ai_vendor", "openai");
        payload.put("messages", messages);

        String jsonPayload = payload.toString();
        System.out.println("REQUEST JSON = " + jsonPayload);

        RequestBody body = RequestBody.create(
                
                MediaType.parse("application/json"),jsonPayload
        );

        Request request = new Request.Builder()
                .url("https://platformai.csez.zohocorpin.com/internalapi/v2/ai/assistant/chat")
                .post(body)
                .addHeader("portal_id", "ZS")
                .addHeader("zuid", "60042586463")
                .addHeader(
                    "Authorization",
                    "Zoho-oauthtoken " + CsezAccessToken.generateAccessToken()
                )
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {

            String responseBody = response.body().string();
            System.out.println("RESPONSE = " + responseBody);

            JSONObject json = new JSONObject(responseBody);

            if (json.has("error")) {
                throw new RuntimeException(
                        json.getJSONObject("error").getString("message")
                );
            }

            return json.getJSONObject("data")
                       .optString("conversation_id", "NO_CONVERSATION_ID");
        }
    }
}

