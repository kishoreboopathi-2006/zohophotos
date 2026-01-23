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
package com.zs.zohophotos;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String username = req.getParameter("username");

        if (username == null || username.trim().isEmpty()) {
            res.sendRedirect("html/error.html");
            return;
        }

        username = username.trim();

        try {
            String folderId = DBUtil.getFolderId(username);

            if (folderId == null) {
                folderId = FolderCreater.createFolder(username);
                DBUtil.saveFolderId(username, folderId);
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("username", username);

            res.sendRedirect("html/dashboard.html");

        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("html/error.html");
        }
    }
}
