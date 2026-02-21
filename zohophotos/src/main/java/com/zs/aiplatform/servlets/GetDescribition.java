package com.zs.aiplatform.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.zs.aiplatform.services.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/getDescriptionForPhoto")
public class GetDescribition extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");

		try {
			String body = new String(req.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
			JSONObject json = new JSONObject(body);

			if (!json.has("file_id")) {
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				res.getWriter().write(new JSONObject().put("error", "file_id missing").toString());
				return;
			}

			String fileId = json.getString("file_id");
			AiResponseOperations obj=new AiResponseOperations();
			String describtion=obj.getDescription(fileId);
			System.out.println("ggggggggggggg"+describtion);
			res.getWriter().write(describtion);
		} catch (Exception e) {
			
		}
	}
}
