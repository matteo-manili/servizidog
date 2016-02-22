package com.dogsitter.webapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;
/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class FBGraph {

	private String accessToken;

	public FBGraph(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getFBGraph() throws IOException {
		String graph = null;

			String g = "https://graph.facebook.com/me?fields=id,name,email,first_name,last_name,locale,gender,cover,picture&" + accessToken;
			
			URL u = new URL(g);
			URLConnection c = u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					c.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
			graph = b.toString();
			
			
			
			
			System.out.println(graph);

		return graph;
	}

	public JSONObject getGraphData(String fbGraph) throws Exception {
		JSONObject jsonObj = new JSONObject(fbGraph);
		return jsonObj;
	}

}
