package com.zs.zohophotos;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatWithAssistantUtil {

    public static String describeImage(String aiFileId) throws Exception {

        String accessToken = CsezAccessToken.generateAccessToken();
        String assistantId =GetAssistant.getAssistantId(); 
        //String fileId = PlatformAiFileUtil.createAIFile();

        OkHttpClient client = new OkHttpClient();

        JSONArray contents = new JSONArray()
            .put(new JSONObject()
                .put("type", "text")
                .put("text", "Describe this image in a friendly way"))
            .put(new JSONObject()
                .put("type", "image")
                .put("image", new JSONObject()
                    .put("type", "file_id")
                    .put("file_id", aiFileId)));

        JSONObject payload = new JSONObject()
            .put("assistant_id", assistantId)
            .put("ai_vendor", "openai")
            .put("messages", new JSONArray()
                .put(new JSONObject()
                    .put("role", "user")
                    .put("contents", contents)));

        RequestBody body = RequestBody.create(
                
                MediaType.parse("application/json"),payload.toString()
        );

        Request request = new Request.Builder()
                .url("https://platformai.csez.zohocorpin.com/internalapi/v2/ai/assistant/chat")
                .post(body)
                .addHeader("zuid", "60042586463")
                .addHeader("portal_id", "ZS")
                .addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
                .build();

        Response response = client.newCall(request).execute();
        JSONObject json = new JSONObject(response.body().string());

        return json
                .getJSONArray("messages")
                .getJSONObject(0)
                .getJSONArray("content")
                .getJSONObject(0)
                .getString("text");
    }
}
