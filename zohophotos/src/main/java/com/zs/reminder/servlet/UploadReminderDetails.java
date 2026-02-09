package com.zs.reminder.servlet;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zs.accesstoken.AccessTokenForWorkdrive;
import com.zs.reminder.records.ReminderDetails;
import com.zs.reminder.service.ReminderDetailsOperation;
import com.zs.zohophotos.DAO.WorkDrivePhotosAndFoldersDetailsManagement;
import com.zs.zohophotos.records.WorkdrivePhotoDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@WebServlet("/uploadReminderDetails")
@MultipartConfig
public class UploadReminderDetails extends HttpServlet {
	private final String workdriveFolderId = "za6tm108d9f72c6af4a5ca6c15f68f0d58056";

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ReminderDetailsOperation upload = new ReminderDetailsOperation();
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userName") == null) {
			System.out.println("username not found");
			res.sendRedirect("html/error.html");
			return;
		}

		String username = (String) session.getAttribute("userName");
		int userId = (int) session.getAttribute("userId");
		System.out.println(userId);
		String title = req.getParameter("title");
		String date = req.getParameter("date");
		String category = req.getParameter("type");
		String message = req.getParameter("message");
		Part filePart = req.getPart("photo");
		LocalTime time = LocalTime.now();
		Time sqlTime = Time.valueOf(time);
		if (filePart.getSize() < 0) {
			System.err.print("photo is not correct");
			return;
		}
		String previewUrl = upload.uploadPhoto(filePart);
		System.out.println(previewUrl);
		ReminderDetails reminderDetails = new ReminderDetails(userId, title, date, sqlTime, category, message,
				previewUrl, workdriveFolderId);
		boolean flag = upload.insertReminderDetails(reminderDetails);
		if (flag) {
			res.getWriter().write("success");
		}
	}
}
