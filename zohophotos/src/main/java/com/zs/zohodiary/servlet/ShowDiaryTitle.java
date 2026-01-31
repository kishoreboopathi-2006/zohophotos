package com.zs.zohodiary.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.zs.zohodiary.DAO.DiaryDetailsManagement;
import com.zs.zohodiary.records.DiaryHeaderDetails;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/getDiaryTitles")
public class ShowDiaryTitle extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		System.out.println("start");
		int userId = 0;
		HttpSession session = req.getSession();
		userId = (int) session.getAttribute("userId");
		DiaryDetailsManagement gdt;
		try {
			gdt = new DiaryDetailsManagement();

			ArrayList<DiaryHeaderDetails> details = gdt.getDiaryHeaderDetails(userId);
			Gson gson = new Gson();
			res.setContentType("application/json");
			res.getWriter().print(gson.toJson(details));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
