//package com.zs.aiplatform.services;
//
//import org.json.JSONObject;
//
//import okhttp3.*;
//
//public class GetAssistantIdForCategorize {
//
//	public static String getAssistantId() throws Exception {
//
//		String zuid = "60042586463";
//		String accessToken = com.zs.accesstoken.CsezAccessToken.generateAccessToken();
//
//		OkHttpClient client = new OkHttpClient();
//
//		JSONObject payload = new JSONObject();
//		payload.put("name", "image analyzer");
//		payload.put("model", "gpt-4o");
//		payload.put("description", "This is a bot used to describe images");
//		payload.put("instructions",
//				"You are an image analysis system. "
//				+ "Analyze the image and identify its content. "
//				+ "Return exactly 10 relevant categories, ordered from most accurate to least accurate. "
//				+ "The first category must be the single best and most specific match for the image and "
//				+ "should be suitable for album creation "
//				+ "(for example: onam, diwali, eid, christmas, wedding, birthday, nature, beach, mountain, city, food). "
//				+ "If the image shows a recognizable festival, celebration, or religious event, use its specific name. "
//				+ "Otherwise, use a general category. "
//				+ "Output format rules: return only lowercase words, separate categories using commas, "
//				+ "no spaces, no punctuation other than commas, no explanations, no extra text."
//		);
//		payload.put("ai_vendor", "openai");
//
//		RequestBody body = RequestBody.create(
//
//				MediaType.parse("application/json"), payload.toString());
//
//		Request request = new Request.Builder()
//				.url("https://platformai.csez.zohocorpin.com/internalapi/v2/ai/assistant").post(body)
//				.addHeader("zuid", zuid).addHeader("portal_id", "ZS")
//				.addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
//				// .addHeader("Content-Type", "application/json")
//				.build();
//
//		Response response = client.newCall(request).execute();
//		String responseBody = response.body().string();
//
//		JSONObject json = new JSONObject(responseBody);
//		System.out.print("Hello");
//		return json.getJSONObject("data").getString("assistant_id");
//	}
//}
