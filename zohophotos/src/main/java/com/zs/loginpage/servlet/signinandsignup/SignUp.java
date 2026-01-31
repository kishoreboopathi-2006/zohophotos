package com.zs.loginpage.servlet.signinandsignup;

import java.io.IOException;
import java.sql.SQLException;

import com.zs.loginpage.DAO.UserDetailsManagement;
import com.zs.loginpage.records.UserDetails;
import com.zs.zohophotos.model.WorkDriveFolderManager;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/signUp")
@MultipartConfig

public class SignUp extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String name = req.getParameter("name");
		UserDetails ud = new UserDetails(name, email, password);
		UserDetailsManagement udm;
		try {
			udm = new UserDetailsManagement();

			if (udm.checkUserEmailExists(ud)) {
				res.getWriter().println("fail");
				System.out.println("fail");
			} else {
				System.out.println("success");
				int userId = udm.insertUserDetailsAndGetUserId(ud);
				ud.setId(userId);
				HttpSession session = req.getSession();
				session.setAttribute("userId", userId);
				session.setAttribute("userName", ud.getName());
				session.setAttribute("userEmail", ud.getEmail());
				WorkDriveFolderManager folder = new WorkDriveFolderManager();
				try {
					if (folder.createFolder(ud)) {
						res.getWriter().println("success");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
