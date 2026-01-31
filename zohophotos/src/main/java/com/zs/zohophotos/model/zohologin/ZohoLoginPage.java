package com.zs.zohophotos.zohologin;

import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({ "/zohologin", "/zohoredirect" })
public class ZohoLoginPage extends HttpServlet {
	private static final String CLIENT_ID = "1000.5GOHB7E49SQ0F1B121PM1NG3Y0XDDE";
	private static final String CLIENT_SECRET = "f19eec3091e099b316f31053573da73dcf6015de7e";
	private static final String REDIRECT_URI = "http://localhost:8080/zohophotos/zohoredirect";
	private static final String SCOPE = "email,profile";

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String path = req.getRequestURI();
		if (path.equals("/zohologin")) {
			loginViaOauth(res);
		} else if (path.equals("/zohoredirect")) {
			String code = exchangeCodeForToken(req, res);
			Decrypter.decrypt(code);
		}
	}

	private void loginViaOauth(HttpServletResponse response) throws IOException {

		System.out.println("Entered SignIn:redirectOauth !");
		String authUrl = "https://accounts.zoho.in/oauth/v2/auth" + "?response_type=code" + "&client_id=" + CLIENT_ID
				+ "&scope=" + URLEncoder.encode(SCOPE, StandardCharsets.UTF_8) + "&access_type=offline"
				+ "&prompt=consent" + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8);
		System.out.println("Leaving SignIn:redirectOauth !");
		response.sendRedirect(authUrl);
	}

	private String exchangeCodeForToken(HttpServletRequest req, HttpServletResponse resp) {

		System.out.println("Entered SignIn:exchangeCodeForToken !");
		String code = req.getParameter("code");
		String body = "grant_type=authorization_code" + "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET
				+ "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8) + "&code="
				+ URLEncoder.encode(code, StandardCharsets.UTF_8);

		String uri = "https://accounts.zoho.in/oauth/v2/token";
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri))
				.headers("Content-Type", "application/x-www-form-urlencoded")
				.POST(HttpRequest.BodyPublishers.ofString(body)).build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
			return response.body();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Leaving SignIn:ExchangeCodeForToken !");
		return null;
	}

}
