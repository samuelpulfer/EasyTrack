package ch.bfh.btx8101.EasyTrack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
import ch.bfh.btx8101.examples.JSONHelper;

/**
 * Servlet implementation class Carrier
 */
@WebServlet("/Carrier")
public class Carrier extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Carrier() {
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
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = AuthHelper.checkSession(request);
		RequestDispatcher rd;
		rd = request.getRequestDispatcher("/WEB-INF/Carrier.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONHelper jh = new JSONHelper();
		JSONObject jo = JSONHelper.requestToJSON(request);
		System.out.println(jo.toString());
		JSONObject resp = new JSONObject();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			PreparedStatement prep = conn.prepareStatement("INSERT INTO `location` (username,varia,created,modified) VALUES (?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)");
			prep.setString(1, request.getRemoteUser());
			prep.setString(2, jo.toString());
			prep.executeUpdate();
			conn.commit();
			prep.close();
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
