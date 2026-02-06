package com.zs.aiplatform.services;

import okhttp3.*;
import org.json.JSONObject;

import com.zs.accesstoken.AccessTokenForWorkdrive;
public class WorkDriveUtil {

    private static final OkHttpClient client = new OkHttpClient();

    public static byte[] downloadFile(String fileId) throws Exception {

        String accessToken = AccessTokenForWorkdrive.getToken();

      
        String metaUrl = "https://www.zohoapis.in/workdrive/api/v1/files/" + fileId;

        Request metaReq = new Request.Builder()
                .url(metaUrl)
                .addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
                .addHeader("Accept", "application/vnd.api+json")
                .build();

        Response metaRes = client.newCall(metaReq).execute();

        System.out.println("METADATA STATUS: " + metaRes.code());

        if (!metaRes.isSuccessful()) {
            throw new RuntimeException("Failed to fetch file metadata");
        }

        String metaBody = metaRes.body().string();
        JSONObject metaJson = new JSONObject(metaBody);

        String downloadUrl = metaJson
                .getJSONObject("data")
                .getJSONObject("attributes")
                .getString("download_url");

        System.out.println("DOWNLOAD URL: " + downloadUrl);

        Request downloadReq = new Request.Builder()
                .url(downloadUrl)
                .addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
                .build();

        Response downloadRes = client.newCall(downloadReq).execute();

        System.out.println("DOWNLOAD STATUS: " + downloadRes.code());

        if (!downloadRes.isSuccessful()) {
            throw new RuntimeException(
                "Image download failed with status " + downloadRes.code()
            );
        }

        return downloadRes.body().bytes();
    }
}
