package ch.bfh.btx8101.EasyTrack.Auth;


import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ch.bfh.btx8101.examples.Authorization;
import ch.bfh.btx8101.examples.DatabaseAuth;
import ch.bfh.btx8101.examples.Employee;
import ch.bfh.btx8101.examples.LocalEmployee;

public class AuthHelper {
	
	public static HttpSession checkSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("User") == null) {
			DatabaseAuth dba = new DatabaseAuth();
			String remoteUser = request.getRemoteUser();
			Employee emp = null;
			if(remoteUser != null && !remoteUser.equals("")) {
				System.out.println("there is a remote user: " + remoteUser);
				emp = dba.getEmployee(remoteUser);
			}
			if(emp != null) {
				session.setAttribute("Authenticated", true);
			} else {
				System.out.println("authenticate as guest");
				emp = dba.getEmployee("guest");
				session.setAttribute("Authenticated", false);
			}
			session.setMaxInactiveInterval(300);
			session.setAttribute("User", emp);
			System.out.println(emp);
		}
		//System.out.println("Session is new: " + session.isNew());
		//System.out.println("RemoteUser: " + request.getRemoteUser());
		//System.out.println("Authenticated: " + session.getAttribute("Authenticated"));
		return session;
	}
	

	public static RequestDispatcher checkAuthGET(HttpServletRequest request, Authorization authorization) {
		HttpSession session = request.getSession();
		if (session.isNew()) {
			session.setMaxInactiveInterval(300);
			session.setAttribute("User", new LocalEmployee(request.getRemoteUser()));
			session.setAttribute("Authenticated", false);
			session.setAttribute("Authorized", false);
		}
		// Logout
		String logout = request.getParameter("logout");
		if (logout != null && logout.equals("true")) {
			session.setAttribute("Authenticated", false);
			session.setAttribute("User", new LocalEmployee(null));
		}
		//BasicAuth
		session.setAttribute("Authenticated", basicAuth(request));
		RequestDispatcher rd;
		if ((Boolean) session.getAttribute("Authenticated") == false)
			rd = request.getRequestDispatcher("/WEB-INF/Login.jsp");
		else {
			// Authorization
			if (authorization.authorize((Employee) session.getAttribute("User"))) {
				session.removeAttribute("customLoginMessage");
				rd = null;
			} else {
				session.setAttribute("customLoginMessage", "You are not allowed to access this site. Please login with an authorized user.");
				rd = request.getRequestDispatcher("/WEB-INF/Login.jsp");
			}
		}
		return rd;
	}
	
	private static boolean basicAuth(HttpServletRequest request) {
		String authHeader = request.getHeader("authorization");
		if(authHeader != null && authHeader != "") {
			String encodedValue = authHeader.split(" ")[1];
			System.out.println("Base64-encoded Authorization Value: <em>" + encodedValue);
			try {
				String decodedValue = new String(Base64.getDecoder().decode(encodedValue.getBytes("UTF-8")));
				System.out.println("hallo");
				System.out.println(decodedValue);
				HttpSession session = request.getSession();
				session.setAttribute("User", new LocalEmployee(decodedValue.split(":")[0]));
				
				return true;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return false;
		
		}
}
