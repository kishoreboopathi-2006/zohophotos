//package com;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import jakarta.servlet.http.Part;
//
//@WebServlet("/upload")
//@MultipartConfig
//public class Photojson extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse res)
//            throws ServletException, IOException {
//
//        res.setContentType("application/json");
//
//       
//        HttpSession session = req.getSession(false);
//        if (session == null || session.getAttribute("username") == null) {
//            res.getWriter().write("{\"error\":\"User not logged in\"}");
//            return;
//        }
//
//        Part filePart = req.getPart("file");
//        if (filePart == null || filePart.getSize() == 0) {
//            res.getWriter().write("{\"error\":\"No file selected\"}");
//            return;
//        }
//        
//        String fileName = filePart.getSubmittedFileName();
//        long fileSize = filePart.getSize();
//        String contentType = filePart.getContentType();
//
//       
//        InputStream fileStream = filePart.getInputStream();
//        byte[] fileBytes = fileStream.readAllBytes();
//
//       
//        String jsonResponse =
//                "{"
//              + "\"fileName\":\"" + fileName + "\","
//              + "\"fileSize\":" + fileSize + ","
//              + "\"contentType\":\"" + contentType + "\","
//              + "\"bytesLength\":" + fileBytes.length
//              + "}";
//
//        
//        res.getWriter().write(jsonResponse);
//    }
//}
