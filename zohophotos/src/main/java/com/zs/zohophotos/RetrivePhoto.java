package com.zs.zohophotos;

import java.io.IOException;
import java.sql.SQLException;
import java.io.*;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
