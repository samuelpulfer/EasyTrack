package ch.bfh.btx8101.EasyTrack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;

import ch.bfh.btx8101.EasyTrack.tracking.Tracking;
import ch.bfh.btx8101.EasyTrack.tracking.WhereAmI;
import ch.bfh.btx8101.examples.JSONHelper;

/**
 * Servlet implementation class Location
 */
@WebServlet("/Location")
public class Location extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// Database Connection
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MariaDB");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String carrier = request.getParameter("Carrier");
		String debug = request.getParameter("debug");
		if(carrier != null) {
			if(debug != null) {
				WhereAmI wmi = new WhereAmI(carrier);
				JSONObject jo = new JSONObject();
				jo.put("RawLocation", wmi.getRawLocation());
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.print(jo);
				out.flush();
			} else {
				
				JSONObject jo = new JSONObject();
				jo.put("SOMETHING", "John");
				/*
				//GAUCHS YOLO ECKE
				Random rand = new Random();
				rand.nextFloat();
				jo.put("x", rand.nextFloat() + "");
				jo.put("y", rand.nextFloat() + "");
				rand = null;
				/*GAUCHS YOLO ECKE ENDET HIER
				 */
				jo.put("x", "0.05");
				jo.put("y", "0.26");
				
				jo.put("image", "static/img/300.png");
				
				WhereAmI wmi = new WhereAmI(carrier);
				JSONObject joo = wmi.getLocation();
				joo.put("SOMETHING", "John");
				
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.print(joo);
				out.flush();
			}
			
		} else {
			StringBuilder sb = new StringBuilder();
			try {
				Connection conn = ds.getConnection();
				PreparedStatement prep = conn.prepareStatement("SELECT varia, created FROM location WHERE username = ? AND created > '2018-12-05 00:00:00' ORDER BY created");
				prep.setString(1, "carrier05");
				ResultSet rs = prep.executeQuery();
				while(rs.next()) {
					sb.append("New Entry " + rs.getTimestamp(2) + "\n");
					sb.append(Tracking.extract("bfh", new JSONObject(rs.getString(1))));
				}
				prep.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.getWriter().append(sb.toString());
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONHelper jh = new JSONHelper();
		JSONObject jo = JSONHelper.requestToJSON(request);
		System.out.println(jo.toString());
		JSONObject resp = new JSONObject();
	
		try {
			Connection conn = ds.getConnection();
			PreparedStatement prep = conn.prepareStatement("INSERT INTO `location` (username,varia,created,modified) VALUES (?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)");
			prep.setString(1, request.getRemoteUser());
			prep.setString(2, jo.toString());
			prep.executeUpdate();
			conn.commit();
			prep.close();
			conn.close();
			resp.put("request", jo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.put("Error", e.getMessage());
		}
		jh.defaultAnswer(resp);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jh);
		out.flush();
	}

}
