package com.zs.zohodiary.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zs.zohodiary.DAO.DiaryDetailsManagement;
import com.zs.zohodiary.records.DiaryResponseDetails;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/diaryDetails")
public class RetriveDiaryDetails extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession();
		int userId = (int) session.getAttribute("userId");
		DiaryDetailsManagement management;
		try {
			management = new DiaryDetailsManagement();
			ArrayList<DiaryResponseDetails> diaryDetails = management.getDiaryResponseDetails(userId,
					req.getContextPath());
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/json");
			System.out.println(gson.toJson(diaryDetails));
			res.getWriter().println(gson.toJson(diaryDetails));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
