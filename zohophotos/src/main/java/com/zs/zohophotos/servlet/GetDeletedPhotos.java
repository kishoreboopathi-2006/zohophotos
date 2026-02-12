package com.zs.zohophotos.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.zs.zohophotos.records.DeletedPhotoDetails;
import com.zs.zohophotos.service.DeletedPhotoDetailsOperations;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/getDeletedPhotos")
public class GetDeletedPhotos extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session=req.getSession();
		int userId=(int)session.getAttribute("userId");
		DeletedPhotoDetailsOperations obj=new DeletedPhotoDetailsOperations();
		ArrayList<DeletedPhotoDetails> deletedArray=obj.getDeletedPhotoDetails(userId);
		Gson gson=new Gson();
		String json=gson.toJson(deletedArray);
		res.getWriter().write(json);
	}
}
