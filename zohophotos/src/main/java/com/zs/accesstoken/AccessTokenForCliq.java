package com.zs.accesstoken;

import java.io.*;

import org.json.JSONObject;

import okhttp3.*;

public class AccessTokenForCliq {

	public String getAccessToken() {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url(
				"https://accounts.zoho.in/oauth/v2/token?refresh_token=1000.2d8ee5cd480151b823809588346a3f7c.6c833c2b4de192518ddc6b0a7a961f10&grant_type=refresh_token&client_id=1000.P9FZ2WG1DMT31GW6MO6ITRFYYH6JRU&client_secret=1257f34da7ada1d587b7fe85953368a711dfb2de4c")
				.method("POST", body)
				.addHeader("Cookie",
						"iamcsr=190dc34a-7f60-462d-b1fa-482742a77f1e; zalb_6e73717622=680d8e643c8d4f4ecb79bf7c0a6012e8")
				.build();
		Response response;
		try {
			response = client.newCall(request).execute();

			JSONObject json = new JSONObject(response.body().string());
			String accessToken = json.getString("access_token");
			return accessToken;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
