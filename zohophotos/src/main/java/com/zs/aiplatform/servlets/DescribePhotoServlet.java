package com.zs.aiplatform.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.zs.aiplatform.services.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/describePhoto")
public class DescribePhotoServlet extends HttpServlet {
	private final String ASSISTANT_ID="928000000234307";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        try {
            String body = new String(req.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(body);

            if (!json.has("file_id")) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                res.getWriter().write(
                    new JSONObject().put("error", "file_id missing").toString()
                );
                return;
            }

            String fileId = json.getString("file_id");

            byte[] imageBytes = WorkDriveUtil.downloadFile(fileId);
            
//            String assistantId = GetAssistantForDescribePhoto.getAssistantId();
            String aiFileId = PlatformAiFileUtil.createAIFile(imageBytes);
            String conversationId = CreateChat.createChat(ASSISTANT_ID, aiFileId);
            String description = GetResponse.getResponse(conversationId);
            JSONObject response = new JSONObject();
            response.put("content", description == null ? "" : description);
            response.put("status", description == null ? "processing" : "completed");

            res.getWriter().write(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write(
                new JSONObject().put("error", e.getMessage()).toString()
            );
        }
    }
}




































