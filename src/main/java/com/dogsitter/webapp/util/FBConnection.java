package com.dogsitter.webapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.dogsitter.Constants;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class FBConnection {
	

	static String accessToken = "";
	
	
	public String getUrlRedirects() throws UnsupportedEncodingException{
		return URLEncoder.encode(Constants.FACEBOOK_REDIRECT_URI, "UTF-8");
	}
	
	public String getAppId(){
		return Constants.FACEBOOK_APP_ID;
	}
	

	public String getFBAuthUrl() {
		String fbLoginUrl = "";
		try {
			 fbLoginUrl = "http://www.facebook.com/dialog/oauth?client_id="+Constants.FACEBOOK_APP_ID+"&redirect_uri=" + URLEncoder.encode(Constants.FACEBOOK_REDIRECT_URI, "UTF-8");
			
			//System.out.println(fbLoginUrl);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fbLoginUrl;
	}

	public String getFBGraphUrl(String code) {
		String fbGraphUrl = "";
		try {
			fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
					+ "client_id=" + Constants.FACEBOOK_APP_ID 
					+ "&redirect_uri="+ URLEncoder.encode(Constants.FACEBOOK_REDIRECT_URI, "UTF-8")
					+ "&client_secret="+ Constants.FACEBOOK_APP_SECRET + "&code="+code;
			
			System.out.println("fbGraphUrl= "+fbGraphUrl);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbGraphUrl;
	}

	public String getAccessToken(String code) {
		if ("".equals(accessToken)) {
			URL fbGraphURL;
			try {
				fbGraphURL = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				fbConnection = fbGraphURL.openConnection();
				BufferedReader in;
				in = new BufferedReader( new InputStreamReader( fbConnection.getInputStream() ) );
				String inputLine;
				b = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
					b.append(inputLine + "\n");
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Unable to connect with Facebook "
						+ e);
			}

			accessToken = b.toString();
			if (accessToken.startsWith("{")) {
				throw new RuntimeException("ERROR: Access Token Invalid: "
						+ accessToken);
			}
		}
		return accessToken;
	}

}
