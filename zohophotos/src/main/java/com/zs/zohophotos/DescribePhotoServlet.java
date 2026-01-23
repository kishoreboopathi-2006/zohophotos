package com.zs.zohophotos;
import java.io.IOException;

import org.json.JSONObject;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/describePhoto")
public class DescribePhotoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String workdriveFileId = req.getParameter("fileId");

        if (workdriveFileId == null) {
            res.setStatus(400);
            return;
        }

        try {
            
            String aiFileId = PlatformAiFileUtil.createAIFile(workdriveFileId);

            String description = ChatWithAssistantUtil.describeImage(aiFileId);

            res.setContentType("application/json");
            res.getWriter().write(
                new JSONObject()
                    .put("description", description)
                    .toString()
            );

        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(500);
        }
    }
}
