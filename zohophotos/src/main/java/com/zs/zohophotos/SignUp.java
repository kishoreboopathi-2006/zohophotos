package com.zs.zohophotos;

import java.io.IOException;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@MultipartConfig

public class SignUp extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String name = req.getParameter("name");
		UserDetails ud = new UserDetails(name, email, password);
		UserDetailsManagement udm = new UserDetailsManagement();
		if (udm.checkUserEmailExists(ud)) {
			res.getWriter().println("fail");
		} else {
			System.out.println("success");
			int userId = udm.insertUserDetailsAndGetUserId(ud);
			ud.setId(userId);
			HttpSession session = req.getSession();
			session.setAttribute("userId", userId);
			session.setAttribute("userName", ud.getName());
			res.getWriter().println("success");
		}
	}
}
