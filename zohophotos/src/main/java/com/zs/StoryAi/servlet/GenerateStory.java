package com.zs.StoryAi.servlet;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zs.StoryAi.service.GetAssistantId;
import com.zs.StoryAi.service.GetConversationId;
import com.zs.StoryAi.service.GetFileId;
import com.zs.StoryAi.service.GetResponse;
import com.zs.aiplatform.services.WorkDriveUtil;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(value="/aiStory", asyncSupported=true)
public class GenerateStory extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        AsyncContext async = req.startAsync();
        async.setTimeout(300000);

        async.start(() -> {
            try {
                HttpServletRequest request =
                    (HttpServletRequest) async.getRequest();
                HttpServletResponse response =
                    (HttpServletResponse) async.getResponse();

                response.setContentType("application/json");

                JSONObject json = new JSONObject(
                    new String(request.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8)
                );

                JSONArray imageIds = json.getJSONArray("imageIds");

                StringBuilder story = new StringBuilder();
                String assistantId = GetAssistantId.getAssistantId();

                for (int i = 0; i < imageIds.length(); i++) {
                    byte[] img = WorkDriveUtil.downloadFile(
                        imageIds.getString(i)
                    );

                    String aiFile = GetFileId.createAIFile(img);
                    String conv =
                        GetConversationId.createChat(assistantId, aiFile);

                    story.append(
                        GetResponse.getResponse(conv)
                    ).append("\n\n");
                }
                response.getWriter().write(
                    new JSONObject().put("story", story.toString()).toString()
                );

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                async.complete();
            }
        });
    }
}


