package com.zs.zohophotos.model;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.zs.accesstoken.AccessTokenForWorkdrive;
import com.zs.zohophotos.records.WorkdrivePhotoDetails;

import okhttp3.*;

public class GetPreviewInformation {

	public static ArrayList<WorkdrivePhotoDetails> getPreviewUrl(ArrayList<WorkdrivePhotoDetails> photoDetails) {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		String accessToken = "";
		for (int i = 0; i < photoDetails.size(); i++) {
			try {
				accessToken = AccessTokenForWorkdrive.getToken();
				RequestBody body = RequestBody.create(mediaType, "");
				Request request = new Request.Builder()
						.url("https://www.zohoapis.in/workdrive/api/v1/files/" + photoDetails.get(i).getResourceId()
								+ "/previewinfo")
						.get().addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
						.addHeader("Accept", "application/vnd.api+json").build();

				Response response;
				response = client.newCall(request).execute();
				Gson gson = new Gson();
				String jsonResponse = response.body().string();
				JSONObject json = new JSONObject(jsonResponse);
				String image = json.getJSONObject("data").getJSONObject("attributes").getString("preview_data_url");
//				System.out.println(image);
				photoDetails.get(i).setPreviewUrl(image);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return photoDetails;
	}
}
//package com.zs.zohophotos;
//import java.io.IOException;
//
//import org.json.JSONObject;
//
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//@WebServlet("/preview/*")
//public class PreviewServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse res)
//            throws IOException {
//
//        String fileId = req.getPathInfo().substring(1);
//        String accessToken="";
//		try {
//			accessToken = AccessToken.getToken();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//        OkHttpClient client = new OkHttpClient();
//
//        
//        Request infoReq = new Request.Builder()
//            .url("https://www.zohoapis.in/workdrive/api/v1/files/"
//                 + fileId + "/previewinfo")
//            .addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
//            .addHeader("Accept", "application/vnd.api+json")
//            .build();
//        Response infoRes = client.newCall(infoReq).execute();
//        String json = infoRes.body().string();
//
//        System.out.println("Preview info response: " + json); 
//
//        if (json == null || json.isEmpty() || !json.trim().startsWith("{")) {
//            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid response from Zoho");
//            return;
//        }
//
//        JSONObject obj = new JSONObject(json);
//
//        String imageUrl =
//            obj.getJSONObject("data")
//               .getJSONObject("attributes")
//               .getString("preview_data_url");
//
//       
//        Request imgReq = new Request.Builder()
//            .url(imageUrl)
//            .build();
//
//        Response imgRes = client.newCall(imgReq).execute();
//        System.out.println("Preview JSON: "+imageUrl);
//
//        res.setContentType(imgRes.header("Content-Type", "application/json"));
//        res.getWriter().println(imageUrl);
////        imgRes.body().byteStream()
////              .transferTo(res.getOutputStream());
//    }
//}
