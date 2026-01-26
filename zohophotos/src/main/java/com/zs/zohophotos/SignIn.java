package com.zs.zohophotos;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@MultipartConfig
public class SignIn extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		UserDetails ud = new UserDetails(email, password);
		UserDetailsManagement udm;
		try {
			udm = new UserDetailsManagement();
			if (udm.checkUserEmailExists(ud)) {
				if (udm.getPassword(ud).equals(password)) {
					int userId = udm.getUserId(ud);
					String userName = udm.getUserName(ud);
					ud.setId(userId);
					ud.setName(userName);
					System.out.println(ud.getId() + ud.getName());
					System.out.println(ud.getEmail());
					HttpSession session = req.getSession();
					session.setAttribute("userId", userId);
					System.out.println(session.getAttribute("userId"));
					session.setAttribute("userEmail", ud.getEmail());
					session.setAttribute("userName", ud.getName());
					res.getWriter().println("success");
				}
			} else {
				res.getWriter().println("fail");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
