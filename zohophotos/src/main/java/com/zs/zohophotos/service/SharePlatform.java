package com.zs.zohophotos.service;

import java.io.IOException;

import com.zs.accesstoken.AccessTokenForCliq;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SharePlatform {

	public boolean sendViaCliq(String email, String url) {
		AccessTokenForCliq access = new AccessTokenForCliq();
		String accessToken = access.getAccessToken();
		System.out.println(accessToken);
		OkHttpClient client = new OkHttpClient().newBuilder()
			      .build();
			    MediaType mediaType = MediaType.parse("application/json");
			    RequestBody body = RequestBody.create(
			    	    mediaType,
			    	    "{\n" +
			    	    "   \"text\": \"Check out this image!\",\n" +
			    	    "   \"card\": {\n" +
			    	    "      \"title\": \"Image Title\",\n" +
			    	    "      \"thumbnail\": \"" + url + "\"\n" +
			    	    "   }\n" +
			    	    "}"
			    	);			    Request request = new Request.Builder()
			      .url("https://cliq.zoho.in/api/v2/buddies/"+email+"/message")
			      .method("POST", body)
			      .addHeader("Authorization", "Zoho-oauthtoken "+accessToken)
			      .addHeader("Content-Type", "application/json")
			      .addHeader("Cookie", "CT_CSRF_TOKEN=0ef54c2b-7d09-4f66-9293-c248aedb5c27; JSESSIONID=24A53FE9AE7FA1C2A9ACB20682784315; zalb_6152a3d6f2=d4367524e9b26a70cccf075d8dd51be8")
			      .build();
			    Response response;
				try {
					response = client.newCall(request).execute();
			    System.out.println(response.body().string());
				return true;
				}
			    catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return false;
	}
	 
	
}
