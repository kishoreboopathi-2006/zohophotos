
package com.zs.zohophotos;

//import java.io.IOException;
//
//import org.json.JSONObject;
//
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class CsezAccessToken {
//	public static void main(String[] args) throws IOException {
//		getCsezToken();
//	}
//
//    public static String getCsezToken() throws IOException {
//
//        String refreshToken = "1000.9c5a6212c91d5e101abdf0f243d67e20.90f0cf4e6f39c136208724fb687ff681";
//        String clientId = "1000.5YXKWPOYRPJ2744RQLLUCB8J0R3DOV";
//        String clientSecret = "acbc5753ad2f1eb82ac9d6bf2e62354de4d22ae1cb";
//
//        OkHttpClient client = new OkHttpClient();
//
//        RequestBody body = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("refresh_token", refreshToken)
//                .addFormDataPart("client_id", clientId)
//                .addFormDataPart("client_secret", clientSecret)
//                .addFormDataPart("grant_type", "refresh_token")
//                .build();
//
//        Request request = new Request.Builder()
//                .url("Accounts.csez.zohocorpin.com/oauth/v2/token")
//                .post(body)
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//        String responseBody = response.body().string();
//        JSONObject json = new JSONObject(responseBody);
//
//        return json.getString("access_token");
//    }
//}

//$Id$


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
* Utility class for generating OAuth access tokens for CSEZ (Zoho's internal environment).
* 
* This class handles OAuth token refresh flow to obtain access tokens for CSEZ environment.
* 
* TODO: Move hardcoded credentials to configuration/environment variables for production use.
* 
*/
public class CsezAccessToken {
	
	private static final Logger LOGGER = Logger.getLogger(CsezAccessToken.class.getName());
	
	// OAuth endpoint for CSEZ environment
	private static final String CSEZ_OAUTH_TOKEN_URL = "https://accounts.csez.zohocorpin.com/oauth/v2/token";
	
	// OAuth grant type
	private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
	
	// TODO: Move these to configuration/environment variables
	// Hardcoded credentials (temporary - for development/testing only)
	private static final String REFRESH_TOKEN = "1000.9c5a6212c91d5e101abdf0f243d67e20.90f0cf4e6f39c136208724fb687ff681";
	private static final String CLIENT_ID = "1000.5YXKWPOYRPJ2744RQLLUCB8J0R3DOV";
	private static final String CLIENT_SECRET = "acbc5753ad2f1eb82ac9d6bf2e62354de4d22ae1cb";
	
	// HTTP client (reusable)
	private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
	
	/**
	 * Main method for testing purposes only.
	 * 
	 * @param args Command line arguments (not used)
	 */
	public static void main(String[] args) {
		String accessToken = generateAccessToken();
		LOGGER.log(Level.INFO, "Generated Access Token: {0}", accessToken);
		if (accessToken != null) {
			LOGGER.log(Level.INFO, "Successfully generated access token for CSEZ");
		} else {
			LOGGER.log(Level.WARNING, "Failed to generate access token for CSEZ");
		}
	}
	
	/**
	 * Generates an OAuth access token for CSEZ environment using refresh token flow.
	 * 
	 * This method:
	 * 1. Builds OAuth token refresh request with credentials
	 * 2. Makes HTTP POST request to CSEZ OAuth endpoint
	 * 3. Parses and returns the access token from response
	 * 
	 * @return Access token string if successful, null otherwise
	 */
	public static String generateAccessToken() {
		// Build form data for POST request
		String formData = buildFormData();
		
		// Create HTTP request
		HttpRequest request = createTokenRequest(formData);
		
		// Execute request and parse response
		return executeTokenRequest(request);
	}
	
	/**
	 * Builds form data string for OAuth token request.
	 * 
	 * @return URL-encoded form data string
	 */
	private static String buildFormData() {
		StringBuilder formData = new StringBuilder();
		formData.append("refresh_token=").append(REFRESH_TOKEN);
		formData.append("&client_id=").append(CLIENT_ID);
		formData.append("&client_secret=").append(CLIENT_SECRET);
		formData.append("&grant_type=").append(GRANT_TYPE_REFRESH_TOKEN);
		return formData.toString();
	}
	
	/**
	 * Creates HTTP request for OAuth token endpoint.
	 * 
	 * @param formData URL-encoded form data
	 * @return HttpRequest object
	 */
	private static HttpRequest createTokenRequest(String formData) {
		return HttpRequest.newBuilder()
			.uri(URI.create(CSEZ_OAUTH_TOKEN_URL))
			.header("Content-Type", "application/x-www-form-urlencoded")
			.POST(HttpRequest.BodyPublishers.ofString(formData, StandardCharsets.UTF_8))
			.build();
	}
	
	/**
	 * Executes OAuth token request and parses the response.
	 * 
	 * @param request HTTP request to execute
	 * @return Access token if successful, null otherwise
	 */
	private static String executeTokenRequest(HttpRequest request) {
		try {
			HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
			
			// Log response for debugging
			LOGGER.log(Level.INFO, "OAuth token response status: {0}, body: {1}", 
				new Object[]{response.statusCode(), sanitizeResponse(response.body())});
			
			// Check HTTP status
			if (response.statusCode() != 200) {
				LOGGER.log(Level.SEVERE, "OAuth token request failed with status {0}. Response: {1}", 
					new Object[]{response.statusCode(), sanitizeResponse(response.body())});
				return null;
			}
			
			// Parse JSON response
			JSONObject jsonResponse = new JSONObject(response.body());
			
			// Check for error in response
			if (jsonResponse.has("error")) {
				String error = jsonResponse.getString("error");
				String errorDescription = jsonResponse.optString("error_description", "");
				LOGGER.log(Level.SEVERE, "OAuth token request failed. Error: {0}, Description: {1}", 
					new Object[]{error, errorDescription});
				return null;
			}
			
			// Extract access token
			if (!jsonResponse.has("access_token")) {
				LOGGER.log(Level.SEVERE, "OAuth token response missing access_token field. Response: {0}", 
					sanitizeResponse(response.body()));
				return null;
			}
			
			String accessToken = jsonResponse.getString("access_token");
			LOGGER.log(Level.INFO, "Successfully generated CSEZ access token");
			return accessToken;
			
		} catch (java.net.http.HttpTimeoutException e) {
			LOGGER.log(Level.SEVERE, "Timeout while generating CSEZ access token", e);
			return null;
		} catch (java.io.IOException e) {
			LOGGER.log(Level.SEVERE, "IO error while generating CSEZ access token", e);
			return null;
		} catch (org.json.JSONException e) {
			LOGGER.log(Level.SEVERE, "Error parsing OAuth token response JSON", e);
			return null;
		} catch (InterruptedException e) {
			LOGGER.log(Level.SEVERE, "Interrupted while generating CSEZ access token", e);
			Thread.currentThread().interrupt();
			return null;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unexpected error while generating CSEZ access token: {0}", e.getMessage());
			LOGGER.log(Level.FINE, "Stack trace:", e);
			return null;
		}
	}
	
	/**
	 * Sanitizes response body for logging (removes sensitive information).
	 * 
	 * @param responseBody Original response body
	 * @return Sanitized response body
	 */
	private static String sanitizeResponse(String responseBody) {
		if (responseBody == null || responseBody.isEmpty()) {
			return "";
		}
		
		try {
			JSONObject json = new JSONObject(responseBody);
			// Remove sensitive fields if present
			if (json.has("access_token")) {
				json.put("access_token", "***REDACTED***");
			}
			if (json.has("refresh_token")) {
				json.put("refresh_token", "***REDACTED***");
			}
			return json.toString();
		} catch (Exception e) {
			// If not JSON, return as-is (but limit length)
			return responseBody.length() > 500 ? responseBody.substring(0, 500) + "..." : responseBody;
		}
	}
}
