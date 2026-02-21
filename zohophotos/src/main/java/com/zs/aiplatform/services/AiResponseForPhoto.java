package com.zs.aiplatform.services;

import java.io.*;
import java.time.LocalTime;

import com.zs.accesstoken.CsezAccessToken;

import okhttp3.*;

public class AiResponseForPhoto {
	String accessToken;
	static long start = System.currentTimeMillis();
	static {
		System.setProperty("http.proxyHost", "127.0.0.1");
		System.setProperty("http.proxyPort", "3128");
		System.setProperty("https.proxyHost", "127.0.0.1");
		System.setProperty("https.proxyPort", "3128");
	}

	AiResponseForPhoto(long current) {
		getAccessToken(current);

	}

	public void getAccessToken(long current) {
		if (current - start > 3600000 || accessToken == null) {
			System.out.println("getaccessToken");
			accessToken = CsezAccessToken.generateAccessToken();
		}

	}

	public String describeOnePhoto(String fileId) {
		String instruction="Describe this picture in simple, warm human-touch words, expressing natural emotions like happiness, excitement, peace, or wonder as if your heart is speaking. Use friendly, heartfelt language with suitable emojis (😊✨🌸❤️) and keep the description within maximum 2 lines.";
		String response = getResponse(fileId, instruction);
		return response;
	}

	public String categorizePhoto(String fileId) {
		String instruction="Analyze the uploaded image and extract all visible elements as simple visual tags, output only comma-separated tags with NO spaces after commas, each tag must be maximum 2 words, prefer 1-word tags when possible, use simple attribute + object format like 'red rose' or 'black shirt', remove extra descriptions and sentences, return output strictly like 'tag1,tag2,tag3'.";
		String response = getResponse(fileId, instruction);
		return response;
	}

	public String getAlbumCategory(String fileId) {
		String instruction = "Analyze the uploaded image and classify it into exactly one category from this list only: Diwali, Pongal, People, Office, Pets, Sports, Travel, Functions, Shopping, Friends; choose the most relevant category based on the image content and return only the selected category name with correct spelling exactly as written in the list, with no explanation or extra text.";
		String response = getResponse(fileId, instruction);
		System.err.print("============================================================="+response);
		return response;
	}

	public String getResponse(String fileId, String instruction) {
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
				.readTimeout(180, java.util.concurrent.TimeUnit.SECONDS)
				.writeTimeout(180, java.util.concurrent.TimeUnit.SECONDS)
				.callTimeout(240, java.util.concurrent.TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,
				"{\n" + "  \"model\": \"gpt-5-mini\",\n" + "  \"ai_vendor\": \"openai\",\n" + "  \"store\": true,\n"
						+ "  \"input\": [\n" + "    {\n" + "      \"role\": \"user\",\n" + "      \"content\": [\n"
						+ "        {\n" + "          \"type\": \"input_image\",\n" + "          \"file_id\": \""
						+ fileId + "\"\n" + "        },\n" + "        {\n" + "          \"type\": \"input_text\",\n"
						+ "          \"text\": \"" + instruction + "\"\n" + "        }\n" + "      ]\n" + "    }\n"
						+ "  ]\n" + "}");
		Request request = new Request.Builder()
				.url("https://platformai.csez.zohocorpin.com/internalapi/v2/ai/responses").method("POST", body)
				.addHeader("portal_id", "ZS").addHeader("zuid", "60042586466")
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Zoho-oauthtoken " + accessToken)
				.addHeader("Cookie",
						"JSESSIONID=655C8D170D55712083C2D014DEF7563A; paicsr=c399dd9c-2928-40b6-a447-330bab367a2c; zalb_24f5e264a3=f5216e024e521b4499f6a517ae2f7639")
				.build();
		Response response;
		try {
			response = client.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}