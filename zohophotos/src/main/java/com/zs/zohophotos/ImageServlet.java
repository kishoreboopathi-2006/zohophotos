////package com;
////
////import java.io.IOException;
////import java.io.*;
////import java.net.HttpURLConnection;
////import java.net.URL;
////
////import jakarta.servlet.annotation.WebServlet;
////import jakarta.servlet.http.HttpServlet;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.servlet.http.HttpServletResponse;
////import okhttp3.OkHttpClient;
////import okhttp3.Request;
////import okhttp3.Response;
////
////@WebServlet("/image")
////public class ImageServlet extends HttpServlet {
////
////    private static final String DOWNLOAD_URL =
////        "https://workdrive.zoho.in/api/v1/files/";
////
////    @Override
////    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
////            throws IOException {
////
////        String fileId = req.getParameter("id");
////        if (fileId == null || fileId.isBlank()) {
////            resp.sendError(400, "Missing file id");
////            return;
////        }
////
////        String accessToken="";
////		try {
////			accessToken = AccessToken.getToken();
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////
////        String finalUrl = DOWNLOAD_URL + fileId + "/content";
////        System.out.println("IMAGE URL = " + finalUrl);
////
////        HttpURLConnection con =
////            (HttpURLConnection) new URL(finalUrl).openConnection();
////
////        con.setRequestMethod("GET");
////        con.setRequestProperty(
////            "Authorization",
////            "Zoho-oauthtoken " + accessToken
////        );
////        con.setRequestProperty("Accept", "*/*");
////
////        int status = con.getResponseCode();
////        System.out.println("ZOHO IMAGE STATUS = " + status);
////
////        if (status != 200) {
////            InputStream err = con.getErrorStream();
////            if (err != null) {
////                System.out.println(new String(err.readAllBytes()));
////            }
////            resp.sendError(500, "Zoho download failed");
////            return;
////        }
////
////        resp.setContentType(con.getContentType());
////        resp.setHeader("Cache-Control", "no-store");
////
////        try (InputStream in = con.getInputStream();
////             OutputStream out = resp.getOutputStream()) {
////
////            byte[] buffer = new byte[8192];
////            int len;
////            while ((len = in.read(buffer)) != -1) {
////                out.write(buffer, 0, len);
////            }
////        }
////    }
////}
//package com;
//
//import java.io.IOException;
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//@WebServlet("/image")
//public class ImageServlet extends HttpServlet {
//
//    private static final String DOWNLOAD_URL =
//        "https://www.zohoapis.in/api/v1/files/";
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws IOException {
//
//        String fileId = req.getParameter("id");
//        if (fileId == null || fileId.isBlank()) {
//            resp.sendError(400, "Missing file id");
//            return;
//        }
//
//        String accessToken="";
//		try {
//			accessToken = AccessToken.getToken();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//        String finalUrl = DOWNLOAD_URL + fileId;
//        System.out.println("IMAGE URL = " + finalUrl);
//
//        HttpURLConnection con =
//            (HttpURLConnection) new URL(finalUrl).openConnection();
//
//        con.setRequestMethod("GET");
//        con.setRequestProperty(
//            "Authorization",
//            "Zoho-oauthtoken " + accessToken
//        );
//        con.setRequestProperty("Accept", "*/*");
//
//        int status = con.getResponseCode();
//        System.out.println("ZOHO IMAGE STATUS = " + status);
//
//        if (status != 200) {
//            InputStream err = con.getErrorStream();
//            if (err != null) {
//                System.out.println(new String(err.readAllBytes()));
//            }
//            resp.sendError(500, "Zoho download failed");
//            return;
//        }
//
//        resp.setContentType(con.getContentType());
//        resp.setHeader("Cache-Control", "no-store");
//
//        try (InputStream in = con.getInputStream();
//             OutputStream out = resp.getOutputStream()) {
//
//            byte[] buffer = new byte[8192];
//            int len;
//            while ((len = in.read(buffer)) != -1) {
//                out.write(buffer, 0, len);
//            }
//        }
//    }
//}
