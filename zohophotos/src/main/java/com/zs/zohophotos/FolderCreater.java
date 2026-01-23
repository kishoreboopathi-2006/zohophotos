
package com.zs.zohophotos;

import okhttp3.*;
import org.json.JSONObject;

public class FolderCreater {

    private static final String PARENT_ID = "ea2zn2f4d1a01b0454cdfa5ba711382392903";

    public static String createFolder(String folderName) throws Exception {

        String accessToken = AccessToken.getToken();
        
        

        OkHttpClient client = new OkHttpClient();

        JSONObject attributes = new JSONObject();
        attributes.put("name", folderName);
        attributes.put("parent_id", PARENT_ID);

        JSONObject data = new JSONObject();
        data.put("attributes", attributes);
        data.put("type", "files");

        JSONObject payload = new JSONObject();
        payload.put("data", data);

        RequestBody body = RequestBody.create(
                
                MediaType.parse("application/json"),payload.toString()
        );

        Request request = new Request.Builder()
                .url("https://www.zohoapis.in/workdrive/api/v1/files")
                .post(body)
                .addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
                .addHeader("Accept", "application/vnd.api+json")
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        String resp = response.body().string();

        if (response.code() == 201) {
            JSONObject json = new JSONObject(resp);
            
            return json.getJSONObject("data").getString("id"); 
        } else {
            throw new RuntimeException("Folder create failed: " + resp);
        }
    }
}
