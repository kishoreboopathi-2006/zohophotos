package com.zs.loginpage.zohologin;

import java.io.IOException;
import java.util.Base64;

import com.google.gson.Gson;

public class Decrypter {
	public static void decrypt(String idToken) throws IOException {
		
		String payload = idToken.split("\\.")[1];
		byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
		String decodedString = new String(decodedBytes);
		System.out.println(decodedString);
		
		
	}

}
