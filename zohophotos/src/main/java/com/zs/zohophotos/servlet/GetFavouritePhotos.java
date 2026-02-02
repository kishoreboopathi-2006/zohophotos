package com.zs.zohophotos.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.zs.zohophotos.model.FavouritePhotoDetailsOperations;
import com.zs.zohophotos.records.FavouritePhotoDetails;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/zohophotos/getFavouritePhotos")
public class GetFavouritePhotos extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session =req.getSession();
		int userId=(int) session.getAttribute("userId");
		ArrayList<FavouritePhotoDetails> photoDetails=new ArrayList<>();
		FavouritePhotoDetailsOperations operations=new FavouritePhotoDetailsOperations();
		photoDetails=operations.getFavouritePhotoDetails(userId);
		Gson gson=new Gson();
		String photoDetailsJson=gson.toJson(photoDetails);
		res.setContentType("application/json");
		res.getWriter().write(photoDetailsJson);
	}
	
}
