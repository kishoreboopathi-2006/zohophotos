//package com.zs.zohophotos;
//
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//import okhttp3.*;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//
//@WebServlet("/retrievePhotos")
//public class RetrievePhotosServlet extends HttpServlet {
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
//
//		HttpSession session = req.getSession(false);
//		if (session == null || session.getAttribute("userId") == null) {
//			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			return;
//		}
////		System.out.println(session.getAttribute("userId"));
//		int userId = (int) session.getAttribute("userId");
//		System.out.println(userId);
//		int folderId = WorkDrivePhotosAndFoldersDetailsManagement.getFolderId(userId);
//////		System.out.println("RETRIEVE FOLDER ID = " + folderId);
////		
////		String accessToken;
////		try {
////			accessToken = AccessToken.getToken();
////		} catch (Exception e) {
////			e.printStackTrace();
////			res.setStatus(500);
////			return;
////		}
////		
////		OkHttpClient client = new OkHttpClient();
////
////		String url = "https://www.zohoapis.in/workdrive/api/v1/files/" + folderId + "/files";
////
//////		System.out.println("FINAL URL = " + url);
////
////		Request request = new Request.Builder().url(url).get()
////				.addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
////				.addHeader("Accept", "application/vnd.api+json").build();
////
////		try (Response response = client.newCall(request).execute()) {
////
////			String body = response.body().string();
////
//////			System.out.println("ZOHO STATUS = " + response.code());
//////			System.out.println("RETRIEVE RESPONSE = " + body);
////
////			res.setContentType("application/json");
////			res.setCharacterEncoding("UTF-8");
////			res.getWriter().write(body);
////		}
//		ArrayList<WorkdrivePhotoDetails>  photoDetails=new ArrayList<>();
//		photoDetails =WorkDrivePhotosAndFoldersDetailsManagement.getPhotoDetails(folderId);
//		Gson gson=new Gson();
//		String json=gson.toJson(photoDetails);
//		System.out.println(json);
//		res.setContentType("application/json");
//		res.setCharacterEncoding("UTF-8");
//		res.getWriter().write(json);
//	}
//}
package com.zs.zohophotos.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.zs.zohophotos.DAO.WorkDrivePhotosAndFoldersDetailsManagement;
import com.zs.zohophotos.model.GetPreviewInformation;
import com.zs.zohophotos.model.GetWorkdrivePhotoDetails;

@WebServlet("/retrievePhotos")
public class RetrievePhotosServlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		ArrayList<String> arr = new ArrayList<>();
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		String username = (String) session.getAttribute("userName");
		int userId = (int) session.getAttribute("userId");
		GetWorkdrivePhotoDetails obj = new GetWorkdrivePhotoDetails(userId);
		String photoDetails=obj.getPhotoDetails();
		System.out.println(photoDetails);
		res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().write(photoDetails);
		}
	}
