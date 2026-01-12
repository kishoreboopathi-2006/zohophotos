package com.zs.zohophotos;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RetriveDiaryDetails {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession();
		int userId = (int) session.getAttribute("userId");
		DiaryDetailsManagement management = new DiaryDetailsManagement();
		ArrayList<DiaryResponseDetails> diaryDetails = management.getDiaryResponseDetails(userId, req.getContextPath());
		Gson gson = new Gson();
		res.setContentType("application/json");
		res.getWriter().println(gson.toJson(diaryDetails));
	}

}
