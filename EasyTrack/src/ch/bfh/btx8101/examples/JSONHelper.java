package ch.bfh.btx8101.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public class JSONHelper extends JSONObject{

	private JSONObject meta = new JSONObject();
	private JSONObject data = new JSONObject();
	private int errorcode = 0;
	private String description = "Everything OK";

	public void defaultAnswer(JSONObject data) {
		this.data = data;
		buildAnswer();
	}

	public void defaultAnswer(int errorcode, String description) {
		this.errorcode = errorcode;
		this.description = description;
		buildAnswer();
	}

	public void defaultAnswer(int errorcode, String description, JSONObject data) {
		this.errorcode = errorcode;
		this.description = description;
		this.data = data;
		buildAnswer();
	}

	private void buildAnswer() {
		meta.put("error", errorcode);
		meta.put("description", description);
		super.put("meta", meta);
		super.put("data", data);
	}
	
	public static JSONObject requestToJSON(HttpServletRequest request) {
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			return null;
		}
		return new JSONObject(jb.toString());
		
	}
	private JSONObject post(HttpURLConnection conn) throws IOException {
		String payload = super.toString();
		byte[] postData = payload.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;
		
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json"); 
		conn.setRequestProperty("charset", "UTF-8");
		conn.setUseCaches(false);
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
		conn.getOutputStream().write(postData);
		
		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		StringBuilder sb = new StringBuilder();
		for(int c;(c=in.read())>=0;) {
			sb.append((char) c);
		}
		return new JSONObject(sb.toString());
		
	}
	public JSONObject post(String url) throws IOException {
		URL srvurl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) srvurl.openConnection();  
		return post(conn);	
	}
	public JSONObject post(String url, String userpass) throws IOException {
		URL srvurl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) srvurl.openConnection();
		String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userpass.getBytes());
		conn.setRequestProperty ("Authorization", basicAuth);
		return post(conn);
	}
	private JSONObject getHTTP(HttpURLConnection conn) throws IOException {
		//String payload = super.toString();
		//byte[] postData = payload.getBytes(StandardCharsets.UTF_8);
		//int postDataLength = postData.length;
		
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json"); 
		conn.setRequestProperty("charset", "UTF-8");
		conn.setUseCaches(false);
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		//conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
		//conn.getOutputStream().write(postData);
		
		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		StringBuilder sb = new StringBuilder();
		for(int c;(c=in.read())>=0;) {
			sb.append((char) c);
		}
		//System.out.println(sb.toString());
		return new JSONObject(sb.toString());
		
	}
	public JSONObject getHTTP(String url) throws IOException {
		URL srvurl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) srvurl.openConnection();  
		return getHTTP(conn);	
	}
	public JSONObject getHTTP(String url, String userpass) throws IOException {
		URL srvurl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) srvurl.openConnection();
		String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userpass.getBytes());
		conn.setRequestProperty ("Authorization", basicAuth);
		return getHTTP(conn);
	}
}
