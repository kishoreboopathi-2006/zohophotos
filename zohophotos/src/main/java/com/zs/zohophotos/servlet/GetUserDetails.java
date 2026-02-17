package com.zs.zohophotos.servlet;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zs.zohophotos.records.UserDetails;
import com.zs.zohophotos.service.UserDetailsOperations;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/getUserDetails")
public class GetUserDetails extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession();
		int userId = (int) session.getAttribute("userId");
		String userName = (String) session.getAttribute("userName");
		String userEmail = (String) session.getAttribute("userEmail");
		UserDetails user = new UserDetails(userId, userName, userEmail);
		UserDetailsOperations details = new UserDetailsOperations();
		System.out.println(user);
		user = details.getUserDetails(user);
		Gson gson = new GsonBuilder().serializeNulls().create();
		String json = gson.toJson(user);
		System.out.println(json);
		res.getWriter().write(json);
	}

}
