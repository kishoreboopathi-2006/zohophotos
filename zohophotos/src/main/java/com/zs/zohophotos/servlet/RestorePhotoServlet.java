package com.zs.zohophotos.servlet;

import java.io.IOException;

import com.zs.zohophotos.service.DeletedPhotoDetailsOperations;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/restorePhoto")
@MultipartConfig
public class RestorePhotoServlet extends HttpServlet{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException {
		String url=(String) req.getParameter("url");
		System.out.println(url);
		DeletedPhotoDetailsOperations del=new DeletedPhotoDetailsOperations();
		boolean flag=del.restorePhoto(url);
		if(flag) {
		res.getWriter().write("success");
		}
		else {
			res.getWriter().write("fail");
		}
	}

}
