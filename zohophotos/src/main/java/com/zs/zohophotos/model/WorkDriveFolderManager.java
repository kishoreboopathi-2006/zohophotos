//package com;
//
//import java.io.IOException;
//
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//@WebServlet("/login")
//public class LoginServlet extends HttpServlet {
//
//    protected void doPost(HttpServletRequest req, HttpServletResponse res)
//            throws IOException {
//
//        String username = req.getParameter("username");
//
//       
//        if (username == null || username.trim().isEmpty()) {
//            res.sendRedirect("html/error.html");
//            return;
//        }
//
//        username = username.trim();
//
//        try {
//            System.out.println("Login request for user: " + username);
//
//            String folderId = DBUtil.getFolderId(username);
//            System.out.println("Existing folderId: " + folderId);
//
//            if (folderId == null) {
//                folderId = FolderCreater.createFolder(username);
//                System.out.println("Created folderId: " + folderId);
//
//                if (folderId == null) {
//                    throw new RuntimeException("Folder creation returned null");
//                }
//
//                DBUtil.saveFolderId(username, folderId);
//            }
//            
//            HttpSession session = req.getSession();
//            session.setAttribute("username", username);
//
//            res.sendRedirect("html/dashboard.html");
//
//        } catch (Exception e) {
//            e.printStackTrace();  
//            res.sendRedirect("html/error.html");
//        }
//    }
//}
package com.zs.zohophotos.model;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import com.zs.loginpage.records.UserDetails;
import com.zs.zohophotos.DAO.WorkDrivePhotosAndFoldersDetailsManagement;
import com.zs.zohophotos.records.FolderCreater;

public class WorkDriveFolderManager {
	public boolean createFolder(UserDetails ud) throws Exception {
		String folderId = FolderCreater.createFolder(ud.getEmail());
//		System.out.println(folderId);
		boolean flag = WorkDrivePhotosAndFoldersDetailsManagement.saveFolderId(ud, folderId);
		if (flag) {
			return true;
		}
		return false;

	}

	public boolean getFolder(UserDetails ud) {
		String folderId = WorkDrivePhotosAndFoldersDetailsManagement.getWorkdriveFolderId(ud);
		return true;
	}
}
