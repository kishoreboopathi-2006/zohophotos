package com.zs.zohophotos.servlet;

import java.io.IOException;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.zs.zohodiary.model.InsertDiaryAndPhotosDetails1;
import com.zs.zohophotos.model.FavouritePhotoDetailsOperations;
import com.zs.zohophotos.records.FavouritePhotoDetails;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@MultipartConfig
@WebServlet("/selectFavouritePhoto")
public class SelectFavouritePhoto extends HttpServlet {
	FavouritePhotoDetails favouritePhoto;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession();
		int userId = (int) session.getAttribute("userId");
		String favourite=req.getParameter("add");
		String previewUrl = req.getParameter("previewUrl");
		String photoName = req.getParameter("photoName");
		System.out.println("previewUrl = " + favourite);
//		System.out.println("userId:"+userId);
		boolean execute=false;
		if(favourite.equals("true")) {
		favouritePhoto = new FavouritePhotoDetails(photoName, previewUrl, userId);
		FavouritePhotoDetailsOperations insert = new FavouritePhotoDetailsOperations(favouritePhoto);
		execute = insert.insertFavouritePhoto();
		}
		else {
			favouritePhoto = new FavouritePhotoDetails(photoName, previewUrl, userId);
			FavouritePhotoDetailsOperations delete = new FavouritePhotoDetailsOperations(favouritePhoto);
			execute = delete.deleteFavouritePhoto();
		}
		if (execute) {
			Gson gson = new Gson();
			String photoDetails = gson.toJson(favouritePhoto);
			res.setContentType("application/json");
			res.getWriter().write(photoDetails);
		} else {
			res.getWriter().write("fail");
		}
	}
}
