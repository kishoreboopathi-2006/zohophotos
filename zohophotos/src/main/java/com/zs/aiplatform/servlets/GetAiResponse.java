package com.zs.aiplatform.servlets;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.zs.aiplatform.records.AiResponseDetailsForPhoto;
import com.zs.aiplatform.records.FolderAndFileId;
import com.zs.aiplatform.services.AiResponseOperations;
import com.zs.zohophotos.DAO.WorkDrivePhotosAndFoldersDetailsManagement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/getAiResponse")
public class GetAiResponse extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession();
		int userId = (int) session.getAttribute("userId");
		String folderId = WorkDrivePhotosAndFoldersDetailsManagement.getWorkdriveFolderId(userId);
		AiResponseOperations operation = new AiResponseOperations();
		ArrayList<AiResponseDetailsForPhoto> responseDetailsArray = operation.getAiResponseForPhotos(folderId);
		Gson gson = new Gson();
		String response = gson.toJson(responseDetailsArray);
		System.out.println(response);
		res.getWriter().write(response);
	}
}
