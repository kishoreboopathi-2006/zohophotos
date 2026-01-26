package com.zs.zohophotos;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import okhttp3.*;

import java.io.IOException;

@WebServlet("/retrievePhotos")
public class RetrievePhotosServlet extends HttpServlet {

	static {
		System.setProperty("http.proxyHost", "127.0.0.1");
		System.setProperty("http.proxyPort", "3128");
		System.setProperty("https.proxyHost", "127.0.0.1");
		System.setProperty("https.proxyPort", "3128");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		System.out.println(session.getAttribute("userId"));
		int userId = (int) session.getAttribute("userId");
		System.out.println(userId);
		String folderId = WorkDrivePhotosAndFoldersDetailsManagement.getFolderId(userId);
		System.out.println("RETRIEVE FOLDER ID = " + folderId);

		String accessToken;
		try {
			accessToken = AccessToken.getToken();
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(500);
			return;
		}

		OkHttpClient client = new OkHttpClient();

		String url = "https://www.zohoapis.in/workdrive/api/v1/files/" + folderId + "/files";

		System.out.println("FINAL URL = " + url);

		Request request = new Request.Builder().url(url).get()
				.addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
				.addHeader("Accept", "application/vnd.api+json").build();

		try (Response response = client.newCall(request).execute()) {

			String body = response.body().string();

			System.out.println("ZOHO STATUS = " + response.code());
			System.out.println("RETRIEVE RESPONSE = " + body);

			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().write(body);
		}
	}
}
