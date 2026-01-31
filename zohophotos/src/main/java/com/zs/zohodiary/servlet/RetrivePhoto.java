package com.zs.zohodiary.servlet;

import java.io.IOException;
import java.sql.SQLException;
import com.zs.zohodiary.DAO.DiaryDetailsManagement;
import com.zs.zohodiary.records.PhotoDetails;
import java.io.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/photo")
public class RetrivePhoto extends HttpServlet {
	DiaryDetailsManagement ddm;

	public void init() {
		try {
			ddm = new DiaryDetailsManagement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("hello");
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		int photoId = Integer.parseInt(req.getParameter("id"));
		PhotoDetails pd = ddm.getPhotoByPhotoId(photoId);
		if (pd == null) {
			res.sendError(404);
			return;
		}
		System.out.println(pd.contentType());
		res.setContentType(pd.contentType());
		try (InputStream in = pd.inputStream(); OutputStream out = res.getOutputStream()) {
			in.transferTo(out);
		}

	}
}
