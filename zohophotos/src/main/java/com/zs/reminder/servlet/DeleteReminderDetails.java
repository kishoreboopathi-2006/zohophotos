package com.zs.reminder.servlet;

import java.io.IOException;

import com.zs.reminder.service.ReminderDetailsOperation;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteReminderDetails")
@MultipartConfig
public class DeleteReminderDetails extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String message = req.getParameter("message");
		System.out.println("ghjk");
		ReminderDetailsOperation obj = new ReminderDetailsOperation();
		boolean flag = obj.deleteData(message);
		System.out.println(message);
		if (flag) {
			res.getWriter().println("success");
		}
	}
}
