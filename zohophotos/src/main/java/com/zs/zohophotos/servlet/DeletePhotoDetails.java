package com.zs.zohophotos.servlet;

import java.io.IOException;

import com.zs.zohophotos.records.DeletedPhotoDetails;
import com.zs.zohophotos.service.DeletedPhotoDetailsOperations;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@MultipartConfig
@WebServlet("/deletePhoto")
public class DeletePhotoDetails extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession();
		int userId = (int) session.getAttribute("userId");
		String resourceId = req.getParameter("resourceId");
		String url = req.getParameter("url");
		System.out.println(url+":"+resourceId);
		DeletedPhotoDetails details = new DeletedPhotoDetails(userId,resourceId,url);
		DeletedPhotoDetailsOperations photoService = new DeletedPhotoDetailsOperations();
		boolean flag = photoService.deletePhotoDetails(resourceId);
		flag = photoService.insertDeletedPhotoDetails(details);
		if (flag) {
			res.getWriter().write("success");
		} else {
			res.getWriter().write("fail");
		}
	}

}
