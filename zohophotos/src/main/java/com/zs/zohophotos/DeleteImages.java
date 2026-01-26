package com.zs.zohophotos;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteImages extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		BufferedReader bufferReader = req.getReader();
		StringBuilder sb = new StringBuilder();
		String line;
		ArrayList<Integer> photoIds = new ArrayList<>();
		while ((line = bufferReader.readLine()) != null) {
			sb.append(line);
		}
		Gson gson = new Gson();
		DeletePhotoIds photoIdsJson = gson.fromJson(sb.toString(), DeletePhotoIds.class);
		System.out.println(sb.toString());
		for (int id : photoIdsJson.getPhotoId()) {
			System.out.println(id);
			photoIds.add(id);
		}
		DiaryDetailsManagement diaryManagement;
		try {
			diaryManagement = new DiaryDetailsManagement();

			boolean execution = diaryManagement.deletePhotoDetails(photoIds);
			res.setContentType("text/plain");
			if (execution) {
				res.getWriter().println("success");
			} else {
				res.getWriter().println("fail");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
