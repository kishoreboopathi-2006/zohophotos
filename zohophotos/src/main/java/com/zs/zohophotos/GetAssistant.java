//package com.zs.zohophotos;
//
//import org.json.JSONObject;
//
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class GetAssistant {
////	 static{
////	        System.setProperty("http.proxyHost", "127.0.0.1");
////	        System.setProperty("http.proxyPort", "3128");
////	        System.setProperty("https.proxyHost", "127.0.0.1");
////	        System.setProperty("https.proxyPort", "3128");
////	    }
//    public static String getAssistantId() throws Exception {
//    	
//
//        String zuid = "60042586463";
//        String accessToken = CsezAccessToken.generateAccessToken();
//
//        OkHttpClient client = new OkHttpClient();
//
//        
//        JSONObject payload = new JSONObject();
//        payload.put("name", "image analyzer");
//        payload.put("model", "gpt-4o");
//        payload.put("description", "This is a bot used to describe images");
//        payload.put("instructions",
//                "Please analyze the image I upload and describe everything visible in clear detail, "
//                        + "including people, objects, setting, colors, expressions, actions, and background elements. "
//                        + "Explain it in a friendly, funny, and engaging way—like a smart friend chatting—while keeping all information accurate. "
//                        + "Add light humor where it fits, and include helpful context without making unsafe assumptions.");
//        payload.put("ai_vendor", "openai");
//
//        RequestBody body = RequestBody.create(
//               
//                MediaType.parse("application/json"), payload.toString()
//        );
//
//        Request request = new Request.Builder()
//                .url("https://platformai.csez.zohocorpin.com/internalapi/v2/ai/assistant")
//                .post(body)
//                .addHeader("zuid", zuid)
//                .addHeader("portal_id", "ZS")
//                .addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
//                .addHeader("Content-Type", "application/json")
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//        String responseBody = response.body().string();
//        JSONObject json = new JSONObject(responseBody);
//
//        return json.getString("assistant_id");
//    }
//}
