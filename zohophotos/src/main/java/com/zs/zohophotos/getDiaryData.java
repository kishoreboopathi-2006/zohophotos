package com.zs.zohophotos;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig
public class getDiaryData extends HttpServlet {

	private static final long serialVersionUID = 1L;
	ArrayList<PhotoDetails> arr = new ArrayList<>();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int userId = 0;
		HttpSession session = req.getSession();
		userId = (int) session.getAttribute("userId");
		String date = req.getParameter("date");
		String content = req.getParameter("content");
		String title = req.getParameter("title");
		LocalTime entry_time = LocalTime.now();
		System.out.println("time" + entry_time);
		Collection<Part> images = req.getParts();
		for (Part p : images) {
			System.out.println(p.getName());
			if ("photos".equals(p.getName()) && p.getSize() > 0) {
				PhotoDetails pd = new PhotoDetails(p.getInputStream(), p.getSize(), p.getSubmittedFileName(),
						p.getContentType());
				arr.add(pd);
			}
		}
		DiaryDetails dd = new DiaryDetails(userId, 0, date, entry_time, title, content, arr);
		InsertDiaryAndPhotosDetails db = new InsertDiaryAndPhotosDetails(dd);
		res.setContentType("text/plain");
		res.getWriter().write("Saved Successfully");
	}
}
