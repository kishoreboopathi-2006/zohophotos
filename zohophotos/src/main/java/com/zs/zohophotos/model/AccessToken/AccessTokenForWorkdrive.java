package com.zs.zohophotos.model.AccessToken;

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import org.json.*;
import okhttp3.*;
import org.json.JSONObject;

public class AccessTokenForWorkdrive {

	private static final String REFRESH_TOKEN = "1000.f4e553f95beb0de20c58df1454fa1737.d947d4e8004d66fc70bbd941a6f36c46";
	private static final String CLIENT_ID = "1000.6B8XKBZGYFR5LAVY48H1VVT0P8RFJL";
	private static final String CLIENT_SECRET = "07add9400d104b3bb197f368d0b0c4fe19269bc9a2";

	private static final OkHttpClient client = new OkHttpClient();

	private static String cachedToken = null;
	private static long expiryTime = 0;

	public static synchronized String getToken() throws Exception {

		if (cachedToken != null && System.currentTimeMillis() < expiryTime) {
			return cachedToken;
		}
		String url = "https://accounts.zoho.in/oauth/v2/token" + "?refresh_token=" + REFRESH_TOKEN + "&client_id="
				+ CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&grant_type=refresh_token";

		RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "");

		Request request = new Request.Builder().url(url).post(body).build();

		try (Response response = client.newCall(request).execute()) {

			String responseBody = response.body().string();
			JSONObject json = new JSONObject(responseBody);

			if (!json.has("access_token")) {
				throw new Exception("Zoho error: " + responseBody);
			}

			cachedToken = json.getString("access_token");
			expiryTime = System.currentTimeMillis() + (3500 * 1000);
			System.out.println(cachedToken);
			return cachedToken;
		}
	}
}
