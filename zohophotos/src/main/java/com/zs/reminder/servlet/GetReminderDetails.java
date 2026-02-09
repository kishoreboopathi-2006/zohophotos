package com.zs.reminder.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.zs.reminder.records.ReminderDetails;
import com.zs.reminder.service.ReminderDetailsOperation;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/getReminderDetails")
public class GetReminderDetails extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession();
		int userId = (int)session.getAttribute("userId");
		System.out.println(userId);
		String userName = (String) req.getAttribute("userName");
		ReminderDetailsOperation obj = new ReminderDetailsOperation();
		ArrayList<ReminderDetails> details = obj.getReminderDetails(userId);
		Gson gson = new Gson();
		String response = gson.toJson(details);
		System.out.println(response);
		res.getWriter().write(response);
	}
}
