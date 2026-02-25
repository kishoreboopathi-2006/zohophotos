package com.zs.accesstoken;

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import org.json.*;
import okhttp3.*;
import org.json.JSONObject;

public class AccessTokenForWorkdrive {

	private static final String REFRESH_TOKEN = "1000.02603e0668a8b3004d828d6805942ad5.c230a8cfc528a67f6df51629f6d7b390";
	private static final String CLIENT_ID = "1000.P9FZ2WG1DMT31GW6MO6ITRFYYH6JRU";
	private static final String CLIENT_SECRET = "1257f34da7ada1d587b7fe85953368a711dfb2de4c";


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
