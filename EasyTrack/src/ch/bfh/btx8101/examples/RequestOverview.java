package ch.bfh.btx8101.examples;


import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RequestOverview {
	
	public static class KeyValue {
		private String key;
		private String value;
		
		public KeyValue(String key, String value) {
			this.key = key;
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public String getValue() {
			return value;
		}
	}
	
	public static String overview(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		StringBuilder sb = new StringBuilder();
		sb.append("getAuthType: " + request.getAuthType() + "\n");
		sb.append("getContentType: " + request.getContentType() + "\n");
		sb.append("getContextPath: " + request.getContextPath() + "\n");
		sb.append("getLocalAddr: " + request.getLocalAddr() + "\n");
		sb.append("getLocalName: " + request.getLocalName() + "\n");
		sb.append("getLocalPort: " + request.getLocalPort() + "\n");
		sb.append("getMethod: " + request.getMethod() + "\n");
		sb.append("getPathInfo: " + request.getPathInfo() + "\n");
		sb.append("getProtocol: " + request.getProtocol() + "\n");
		sb.append("getQueryString: " + request.getQueryString() + "\n");
		sb.append("getRemoteAddr: " + request.getRemoteAddr() + "\n");
		sb.append("getRemoteHost: " + request.getRemoteHost() + "\n");
		sb.append("getRemotePort: " + request.getRemotePort() + "\n");
		sb.append("getRemoteUser: " + request.getRemoteUser() + "\n");
		sb.append("getRequestedSessionId: " + request.getRequestedSessionId() + "\n");
		sb.append("getScheme: " + request.getScheme() + "\n");
		sb.append("getServerName: " + request.getServerName() + "\n");
		sb.append("getServerPort: " + request.getServerPort() + "\n");
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie:cookies) {
				sb.append("Cookie " + cookie.getName() + "\n\t");
				sb.append("getComment: " + cookie.getComment() + "\n\t");
				sb.append("getDomain: " + cookie.getDomain() + "\n\t");
				sb.append("getMaxAge: " + cookie.getMaxAge() + "\n\t");
				sb.append("getPath: " + cookie.getPath() + "\n\t");
				sb.append("getValue: " + cookie.getValue() + "\n\t");
				sb.append("getVersion: " + cookie.getVersion() + "\n\t");
				sb.append("getSecure: " + cookie.getSecure() + "\n");
			}
		}
		sb.append("Session:\n\t");
		if(session != null) {
			sb.append("getId: " + session.getId() + "\n\t");
			sb.append("getCreationTime: " + session.getCreationTime() + "\n\t");
			sb.append("getLastAccessedTime: " + session.getLastAccessedTime() + "\n\t");
			sb.append("getMaxInactiveInterval: " + session.getMaxInactiveInterval() + "\n\t");
			sb.append("Attributes:\n");
			Enumeration<String> attribs = session.getAttributeNames();
			while(attribs.hasMoreElements()) {
				String attrib = attribs.nextElement();
				sb.append(attrib + ":\n" + session.getAttribute(attrib) + "\n");
			}
		} else {
			sb.append("No session created");
		}
		
		
		return sb.toString();
	}
	
	public static ArrayList<KeyValue> requestOverview(HttpServletRequest request) {
		ArrayList<KeyValue> list = new ArrayList<KeyValue>();
		list.add(new KeyValue("getAuthType",request.getAuthType()));
		list.add(new KeyValue("getContentType",request.getContentType()));
		list.add(new KeyValue("getContextPath",request.getContextPath()));
		list.add(new KeyValue("getLocalAddr",request.getLocalAddr()));
		list.add(new KeyValue("getLocalName",request.getLocalName()));
		list.add(new KeyValue("getLocalPort",Integer.toString(request.getLocalPort())));
		list.add(new KeyValue("getMethod",request.getMethod()));
		list.add(new KeyValue("getPathInfo",request.getPathInfo()));
		list.add(new KeyValue("getProtocol",request.getProtocol()));
		list.add(new KeyValue("getQueryString",request.getQueryString()));
		list.add(new KeyValue("getRemoteAddr",request.getRemoteAddr()));
		list.add(new KeyValue("getRemoteHost",request.getRemoteHost()));
		list.add(new KeyValue("getRemotePort",Integer.toString(request.getRemotePort())));
		list.add(new KeyValue("getRemoteUser",request.getRemoteUser()));
		list.add(new KeyValue("getRequestedSessionId",request.getRequestedSessionId()));
		list.add(new KeyValue("getScheme",request.getScheme()));
		list.add(new KeyValue("getServerName",request.getServerName()));
		list.add(new KeyValue("getRequestURL",request.getRequestURL().toString())); 
		list.add(new KeyValue("getServerPort",Integer.toString(request.getServerPort())));
		Enumeration<String> attribs = request.getAttributeNames();
		while(attribs.hasMoreElements()) {
			String attrib = attribs.nextElement();
			list.add(new KeyValue("Attribute: " + attrib,request.getAttribute(attrib).toString()));
		}
		return list;
	}
	
	public static ArrayList<KeyValue> cookieOverview(HttpServletRequest request) {
		ArrayList<KeyValue> list = new ArrayList<KeyValue>();
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie:cookies) {
				list.add(new KeyValue(cookie.getName(), "{Comment: " + cookie.getComment() + ", Domain: " + cookie.getDomain() + ", MaxAge: " + cookie.getMaxAge() + ", Path: " + cookie.getPath() + ", Value: " + cookie.getValue() + ", Version: " + cookie.getVersion() + ", Secure: " + cookie.getSecure() + "}"));
			}
		}
		return list;
	}
	
	public static ArrayList<KeyValue> sessionOverview(HttpServletRequest request) {
		ArrayList<KeyValue> list = new ArrayList<KeyValue>();
		HttpSession session = request.getSession(false);
		if(session != null) {
			list.add(new KeyValue("getId",session.getId()));
			list.add(new KeyValue("getCreationTime",Long.toString(session.getCreationTime())));
			list.add(new KeyValue("getLastAccessedTime",Long.toString(session.getLastAccessedTime())));
			list.add(new KeyValue("getMaxInactiveInterval",Integer.toString(session.getMaxInactiveInterval())));
			Enumeration<String> attribs = session.getAttributeNames();
			while(attribs.hasMoreElements()) {
				String attrib = attribs.nextElement();
				list.add(new KeyValue("Attribute: " + attrib,session.getAttribute(attrib).toString()));
			}
		}
		return list;
	}
}
