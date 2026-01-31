//package com;
//
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import jakarta.servlet.http.Part;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//@WebServlet("/upload")
//@MultipartConfig
//public class UploadServlet extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse res)
//            throws ServletException, IOException {
//
//        
//        HttpSession session = req.getSession(false);
//        if (session == null || session.getAttribute("username") == null) {
//            res.sendRedirect("html/error.html");
//            return;
//        }
//
//        String username = (String) session.getAttribute("username");
//
//       
//        String folderId = DBUtil.getFolderId(username);
//        if (folderId == null) {
//            res.sendRedirect("html/error.html");
//            return;
//        }
//        System.out.println("UPLOAD FOLDER ID = " + folderId);
//
//
//        
//        Part filePart = req.getPart("photo");
//        if (filePart == null || filePart.getSize() == 0) {
//            res.sendRedirect("html/error.html");
//            return;
//        }
//
//        String fileName = filePart.getSubmittedFileName();
//        InputStream fileStream = filePart.getInputStream();
//        byte[] fileBytes = fileStream.readAllBytes();
//
//     
//        String accessToken="";
//		try {
//		//	accessToken = "1000.54998171c933a086eb889dec2b9cafa5.e4eb3a32b9ea97e2fc579b5d68febcbc";
//			accessToken = AccessToken.getToken();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//        OkHttpClient client = new OkHttpClient();
//
//        RequestBody fileBody = RequestBody.create(
//                
//                MediaType.parse(filePart.getContentType()),fileBytes
//        );
//
//        MultipartBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                /* https://workdrive.zoho.in/folder/ea2zn2f4d1a01b0454cdfa5ba711382392903 */
//                .addFormDataPart("parent_id", folderId)
//               // .addFormDataPart("parent_id", "ea2zn2f4d1a01b0454cdfa5ba711382392903")
//                .addFormDataPart("content", fileName, fileBody)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://www.zohoapis.in/workdrive/api/v1/upload")
//                .post(requestBody)
//                .addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
//                .addHeader("Content-Type", "application/octet-stream")
//                .build();
//
//      
//        try (Response response = client.newCall(request).execute()) {
//
//            if (!response.isSuccessful()) {
//                System.out.println("Upload failed: " + response.body().string());
//                res.sendRedirect("html/error.html");
//                return;
//            }
//        }
//
//        res.sendRedirect("html/dashboard.html");
//    }
//}
package com.zs.zohophotos.servlet;

//import java.io.IOException;
//import java.time.LocalTime;
//
//import com.google.gson.Gson;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import jakarta.servlet.http.Part;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//@WebServlet("/upload")
//@MultipartConfig
//public class UploadServlet extends HttpServlet {
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		HttpSession session = req.getSession(false);
////		System.out.println("Enter");
//		if (session == null || session.getAttribute("userName") == null) {
//			System.out.println("1");
//			res.sendRedirect("html/error.html");
//			return;
//		}
//
//		String username = (String) session.getAttribute("userName");
//		int userId = (int) session.getAttribute("userId");
////		System.out.println(userId);
//		String workdriveFolderId = WorkDrivePhotosAndFoldersDetailsManagement.getWorkdriveFolderId(userId);
////		System.out.println("work"+workdriveFolderId);
//		if (workdriveFolderId == null) {
//			System.out.println("folderis is not exist");
//			res.sendRedirect("html/error.html");
//			return;
//		}
//
////		System.out.println("UPLOAD FOLDER ID = " + workdriveFolderId);
//		Part filePart = req.getPart("photo");
//		if (filePart == null || filePart.getSize() == 0) {
//			res.sendRedirect("html/error.html");
//			System.out.println("filepart is not exists");
//			return;
//		}
//		String fileName = filePart.getSubmittedFileName();
//		byte[] fileBytes = filePart.getInputStream().readAllBytes();
//		String accessToken = "";
//		try {
//			accessToken = AccessToken.getToken();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		OkHttpClient client = new OkHttpClient();
//
//		RequestBody fileBody = RequestBody.create(MediaType.parse(filePart.getContentType()), fileBytes);
//
//		MultipartBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//				.addFormDataPart("parent_id", workdriveFolderId).addFormDataPart("content", fileName, fileBody).build();
//
//		Request request = new Request.Builder().url("https://www.zohoapis.in/workdrive/api/v1/upload").post(requestBody)
//				.addHeader("Authorization", "Zoho-oauthtoken " + accessToken).build();
//
//		try (Response zohoResponse = client.newCall(request).execute()) {
//			String responseBody = zohoResponse.body().string();
//			System.out.println("UPLOAD RESPONSE = " + responseBody);
//			Gson gson = new Gson();
//			UploadResponse api = gson.fromJson(responseBody, UploadResponse.class);
//			String resourceId = api.data.get(0).attributes.resource_id;
//			String photoName = api.data.get(0).attributes.photoName;
//			System.out.println("Resource_id  --------->" + resourceId);
//			String previewUrl = GetPreviewInformation.getPreviewUrl(resourceId);
//			System.out.println("2:"+previewUrl);
//			int folderId = WorkDrivePhotosAndFoldersDetailsManagement.getFolderId(userId);
//			WorkdrivePhotoDetails photoDetails = new WorkdrivePhotoDetails(folderId, resourceId, previewUrl,
//					photoName);
//			System.err.println(" Upload servlet ------------ > "+previewUrl);
//			boolean insert = WorkDrivePhotosAndFoldersDetailsManagement.saveWorkdrivePhotoDetails(photoDetails);
//			if (!zohoResponse.isSuccessful()) {
//				res.sendRedirect("html/error.html");
//				return;
//			}
//			if (insert) {
//				res.sendRedirect("html/dashboard.html");
//			} else {
//				System.out.println("insert failed");
//			}
//		}
//	}
//}



import java.io.IOException;

import com.zs.zohophotos.model.AccessToken.AccessTokenForWorkdrive;

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

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
        	System.out.println("K");
            res.sendRedirect("html/error.html");
            return;
        }

        String username = (String) session.getAttribute("userName");
		int userId = (int) session.getAttribute("userId");
		System.out.println(userId);
		String workdriveFolderId = com.zs.zohophotos.DAO.WorkDrivePhotosAndFoldersDetailsManagement.getWorkdriveFolderId(userId);        
        if (workdriveFolderId == null) {
        	System.out.println("get");
            res.sendRedirect("html/error.html");
            return;
        }

        System.out.println("UPLOAD FOLDER ID = " + workdriveFolderId);

        Part filePart = req.getPart("photo");
        if (filePart == null || filePart.getSize() == 0) {
            res.sendRedirect("html/error.html");
            return;
        }

        String fileName = filePart.getSubmittedFileName();
        byte[] fileBytes = filePart.getInputStream().readAllBytes();

        String accessToken="";
		try {
			accessToken = AccessTokenForWorkdrive.getToken();
		    System.out.println("ACCESS TOKEN = " + accessToken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        OkHttpClient client = new OkHttpClient();

        RequestBody fileBody = RequestBody.create(
                MediaType.parse(filePart.getContentType()),fileBytes
        );

        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("parent_id", workdriveFolderId)
                .addFormDataPart("content", fileName, fileBody)
                .build();

        Request request = new Request.Builder()
                .url("https://www.zohoapis.in/workdrive/api/v1/upload")
                .post(requestBody)
                .addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
              //  .addHeader("Accept", "application/vnd.api+json")
                .addHeader("Content-Type", "application/vnd.api+json")
                .build();
    

        try (Response zohoResponse = client.newCall(request).execute()) {

            String responseBody = zohoResponse.body().string();
            System.out.println("UPLOAD RESPONSE = " + responseBody);

            if (!zohoResponse.isSuccessful()) {
                res.sendRedirect("html/error.html");
                return;
            }
        }

        res.sendRedirect("html/dashboard.html");
    }
}


