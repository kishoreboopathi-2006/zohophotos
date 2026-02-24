package com.zs.zohophotos.servlet;

import java.io.IOException;

import com.zs.zohophotos.service.SharePlatform;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
@WebServlet("/shareViaCliq")
@MultipartConfig
public class ShareInCliq extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String email = req.getParameter("email");
		String url = req.getParameter("url");
		System.out.println(email);
		System.out.println(url);
		SharePlatform share = new SharePlatform();
		boolean send = share.sendViaCliq(email, url);
		if (send) {
			try {
				res.getWriter().write("success");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		res.getWriter().write("fail");
	}
}
