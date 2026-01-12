package com.zs.zohophotos;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ShowDiaryTitle extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		System.out.println("start");
		int userId = 0;
		HttpSession session = req.getSession();
		userId = (int) session.getAttribute("userId");
		DiaryDetailsManagement gdt = new DiaryDetailsManagement();
		ArrayList<DiaryHeaderDetails> details = gdt.getDiaryHeaderDetails(userId);
		Gson gson = new Gson();
		res.setContentType("application/json");
		res.getWriter().print(gson.toJson(details));

	}
}
