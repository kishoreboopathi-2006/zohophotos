package com.zs.zohophotos;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetResponse {
	public static String getResponse() throws Exception{
		String accessToken = CsezAccessToken.generateAccessToken();
	OkHttpClient client = new OkHttpClient().newBuilder()
			  .build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, "");
			Request request = new Request.Builder()
			  .url("https://platformai.csez.zohocorpin.com/internalapi/v2/ai/assistant/chat?conversation_id=928000000229039")
			  .method("GET", body)
			  .addHeader("zuid", "60042586463")
			  .addHeader("portal_id", "ZS")
			  .addHeader("Content-Type", "application/json")
			  .addHeader("Authorization", "Zoho-oauthtoken "+accessToken)
			  .addHeader("Cookie", "_zcsr_tmp=00626b60-8afb-44e9-b662-d9b178b89452; paicsr=00626b60-8afb-44e9-b662-d9b178b89452; zalb_24f5e264a3=f5216e024e521b4499f6a517ae2f7639")
			  .build();
			Response response = client.newCall(request).execute();
			JSONObject json = new JSONObject(response);
			return json.getString("response");
}
}
