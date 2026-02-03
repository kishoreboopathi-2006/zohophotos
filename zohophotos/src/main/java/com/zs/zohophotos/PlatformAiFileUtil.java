//package com.zs.zohophotos;
//
//import org.json.JSONObject;
//
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class PlatformAiFileUtil {
//
//    public static String createAIFile(String workdriveFileId) throws Exception {
//
//        String accessToken = CsezAccessToken.generateAccessToken();
//
//        OkHttpClient client = new OkHttpClient();
//
//        RequestBody body = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart(
//                        "vision",
//                        workdriveFileId   
//                )
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://platformai.csez.zohocorpin.com/internalapi/v2/ai/file?ai_vendor=openai")
//                .post(body)
//                .addHeader("zuid", "60042586463")
//                .addHeader("portal_id", "ZS")
//                .addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        String responseBody = response.body().string();
//
//        JSONObject json = new JSONObject(responseBody);
//
//        return json.getString("file_id"); 
//    }
//}
//
