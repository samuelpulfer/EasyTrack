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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.JSONObject;

import ch.bfh.btx8101.EasyTrack.Auth.AuthHelper;
import ch.bfh.btx8101.examples.Employee;

/**
 * Servlet implementation class Tasks
 */
@WebServlet("/Tasks")
public class Tasks extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DataSource ds = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Tasks() {
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
		HttpSession session = AuthHelper.checkSession(request);
		JSONObject jo = new JSONObject();
		Employee emp = (Employee) session.getAttribute("User");
		
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT o.fid, dest_start.name AS deststart, dest_end.name AS destend, o.ts_end, o.`mode`, o.`isolation`, o.emergency, o.message, o.state\n" + 
					"FROM `order` AS o\n" + 
					"LEFT JOIN `destinations` AS dest_start\n" + 
					"ON (o.destination_start = dest_start.id)\n" + 
					"LEFT JOIN `destinations` AS dest_end\n" + 
					"ON (o.destination_end = dest_end.id)\n" + 
					"WHERE o.`carrier`=? AND o.delivered IS NULL AND o.deleted IS NULL\n" + 
					"ORDER BY o.ts_end ASC\n" +
					"LIMIT 1");
			ps.setString(1, emp.getUsername());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				StringBuilder sb = new StringBuilder();
				sb.append("FID: " + rs.getString("fid") + "\n");
				sb.append("Source: " + rs.getString("deststart") + "\n");
				sb.append("Destination: " + rs.getString("destend") + "\n");
				sb.append("Arrival: " + rs.getTimestamp("ts_end") + "\n");
				sb.append("Transport: " + rs.getString("mode") + "\n");
				sb.append("Isolation: " + rs.getBoolean("isolation") + "\n");
				sb.append("Emergency: " + rs.getBoolean("emergency") + "\n");
				sb.append("Message: " + rs.getString("message") + "\n");
				jo.put("tasks", sb.toString());
				int state = rs.getInt("state");
				if(state == 0)
					jo.put("button", "Accept");
				else if(state == 1)
					jo.put("button", "Picked Up");
				else
					jo.put("button", "Delivered");
				
			} else {
				jo.put("tasks", "Nothing todo...");
				jo.put("button", "nothing");
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			jo.put("Error", e.getMessage());
		}

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jo);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = AuthHelper.checkSession(request);
		Employee emp = (Employee) session.getAttribute("User");
		System.out.println("There was a post...");
		
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT id, state FROM `order` WHERE `carrier`=? AND delivered IS NULL AND deleted IS NULL ORDER BY ts_end ASC");
			ps.setString(1, emp.getUsername());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int state = rs.getInt("state");
				if(state == 0) {
					PreparedStatement psu = conn.prepareStatement("UPDATE `order` SET accepted = CURRENT_TIMESTAMP, state=1 WHERE id=?");
					psu.setInt(1, rs.getInt("id"));
					psu.executeUpdate();
					conn.commit();
					psu.close();
				} else if(state == 1) {
					PreparedStatement psu = conn.prepareStatement("UPDATE `order` SET pickedup = CURRENT_TIMESTAMP, state=2 WHERE id=?");
					psu.setInt(1, rs.getInt("id"));
					psu.executeUpdate();
					conn.commit();
					psu.close();
				} else if(state == 2) {
					PreparedStatement psu = conn.prepareStatement("UPDATE `order` SET delivered = CURRENT_TIMESTAMP, state=3 WHERE id=?");
					psu.setInt(1, rs.getInt("id"));
					psu.executeUpdate();
					conn.commit();
					psu.close();
				}
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		doGet(request, response);
	}

}
