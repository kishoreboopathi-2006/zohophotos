package com.zs.aiplatform.servlets;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.zs.aiplatform.records.CategorizePhotoDetails;
import com.zs.aiplatform.services.CategorizePhotoDetailsOperations;
import com.zs.zohophotos.DAO.WorkDrivePhotosAndFoldersDetailsManagement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/getCategorizePhotos")
public class getCategorizePhotos extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session=req.getSession();
		int userId=(int)session.getAttribute("userId");
		String folderId=WorkDrivePhotosAndFoldersDetailsManagement.getWorkdriveFolderId(userId);
		CategorizePhotoDetailsOperations photoDetails=new CategorizePhotoDetailsOperations();
		ArrayList<CategorizePhotoDetails> photoDetailsArray=photoDetails.getCategorizePhotoDetails(folderId);
		Gson gson=new Gson();
		String photoDetailsArrayResponse=gson.toJson(photoDetailsArray);
		System.out.println(photoDetailsArrayResponse);
		res.getWriter().write(photoDetailsArrayResponse);
	}
}
