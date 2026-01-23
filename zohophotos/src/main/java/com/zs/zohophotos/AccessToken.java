   
    package com.zs.zohophotos;

    import java.io.*;
    import java.net.*;
    import org.json.*;

    public class AccessToken {

        public static String getToken() throws Exception {

            String refreshToken = "1000.035537f99de9b44ad5e1a6f9a64a86da.505dd4356ee8be114573a96ff3ccbcc9";
            String clientId = "1000.6B8XKBZGYFR5LAVY48H1VVT0P8RFJL";
            String clientSecret = "07add9400d104b3bb197f368d0b0c4fe19269bc9a2";
            String urlStr =
                    "https://accounts.zoho.in/oauth/v2/token" +
                    "?refresh_token=" + refreshToken +
                    "&client_id=" + clientId +
                    "&client_secret=" + clientSecret +
                    "&grant_type=refresh_token";

                URL url = new URL(urlStr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");

                BufferedReader br =
                    new BufferedReader(new InputStreamReader(con.getInputStream()));

                String response = br.lines().reduce("", String::concat);
                JSONObject json = new JSONObject(response);

                return json.getString("access_token");
            }
        }
