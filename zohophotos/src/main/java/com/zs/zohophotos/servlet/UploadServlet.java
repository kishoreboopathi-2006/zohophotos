
package com.zs.zohophotos.servlet;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zs.accesstoken.AccessTokenForWorkdrive;
import com.zs.aiplatform.records.FolderAndFileId;
import com.zs.aiplatform.services.AiResponseOperations;
import com.zs.zohophotos.DAO.WorkDrivePhotosAndFoldersDetailsManagement;
import com.zs.zohophotos.records.ProfilePhotoDetails;
import com.zs.zohophotos.records.WorkdrivePhotoDetails;
import com.zs.zohophotos.service.GetPreviewInformation;
import com.zs.zohophotos.service.ProfilePhotoDetailsOperation;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@WebServlet(value = "/upload", asyncSupported = true)
@MultipartConfig
public class UploadServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userName") == null) {
			System.out.println("K");
			res.sendRedirect("html/error.html");
			return;
		}

		String username = (String) session.getAttribute("userName");
		int userId = (int) session.getAttribute("userId");
		String previewUrl = null;

		String workdriveFolderId = com.zs.zohophotos.DAO.WorkDrivePhotosAndFoldersDetailsManagement
				.getWorkdriveFolderId(userId);
		if (workdriveFolderId == null) {
			System.out.println("get");
			res.sendRedirect("html/error.html");
			return;
		}
		String entry = req.getParameter("entry");
		if (entry != null) {
			if (entry.equals("profile")) {
				workdriveFolderId = "biz385cc35fd3e44747fe8786b3d557fc3029";
			}
		}
		System.out.println("UPLOAD FOLDER ID = " + workdriveFolderId);

		Part filePart = req.getPart("photo");
		if (filePart == null || filePart.getSize() == 0) {
			res.sendRedirect("html/error.html");
			return;
		}

		String fileName = filePart.getSubmittedFileName();
		byte[] fileBytes = filePart.getInputStream().readAllBytes();

		String accessToken = "";
		try {
			accessToken = AccessTokenForWorkdrive.getToken();
			System.out.println("ACCESS TOKEN = " + accessToken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		OkHttpClient client = new OkHttpClient.Builder()
			    .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)  // Connection timeout
			    .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)   // Time to send request
			    .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)    // Time to read response
			    .build();

		RequestBody fileBody = RequestBody.create(MediaType.parse(filePart.getContentType()), fileBytes);

		MultipartBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("parent_id", workdriveFolderId).addFormDataPart("content", fileName, fileBody).build();

		Request request = new Request.Builder().url("https://www.zohoapis.in/workdrive/api/v1/upload").post(requestBody)
				.addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
				// .addHeader("Accept", "application/vnd.api+json")
				.addHeader("Content-Type", "application/vnd.api+json").build();
		try (Response zohoResponse = client.newCall(request).execute()) {
			String responseBody = zohoResponse.body().string();
			System.out.println("UPLOAD RESPONSE = " + responseBody);
			JSONObject root = new JSONObject(responseBody);
			JSONArray array = root.getJSONArray("data");
			JSONObject firstObject = array.getJSONObject(0);
			JSONObject object = firstObject.getJSONObject("attributes");
			String photoName = object.getString("FileName");
			String folderId = object.getString("parent_id");
			String resourceId = object.getString("resource_id");
			GetPreviewInformation preview = new GetPreviewInformation();
			String url = preview.getPreviewUrl(resourceId);
			boolean flag = false;
			if (entry == null) {
				WorkdrivePhotoDetails photoDetails = new WorkdrivePhotoDetails(folderId, resourceId, fileName, url);
				flag = WorkDrivePhotosAndFoldersDetailsManagement.insertBasicPhotoDetails(photoDetails);
				if (!zohoResponse.isSuccessful()) {
					res.sendRedirect("html/error.html");
					return;
				}
			} else {
				ProfilePhotoDetailsOperation obj = new ProfilePhotoDetailsOperation();
				ProfilePhotoDetails photo = new ProfilePhotoDetails(userId, resourceId);
				previewUrl = obj.insertProfilePhoto(photo);
				res.getWriter().println(previewUrl);
				return;
			}
			if (flag) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							AiResponseOperations ai = new AiResponseOperations(
									new FolderAndFileId(folderId, resourceId));
							boolean execute = ai.insertAiresponse();
							if (execute) {
								System.out.println("success");
							} else {
								System.out.println("fail");
							}
						} finally {
							System.out.println("complete");
						}
					}
				}).start();
			}
			System.out.println("redirect");
			res.sendRedirect("/zohophotos/html/dashboard/dashboard.html");
		}
	}
}
