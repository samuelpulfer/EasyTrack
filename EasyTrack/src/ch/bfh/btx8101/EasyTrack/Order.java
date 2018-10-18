package ch.bfh.btx8101.EasyTrack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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

import ch.bfh.btx8101.examples.Authorization;
import ch.bfh.btx8101.examples.Employee;
import ch.bfh.btx8101.examples.EnvironmentAuthorization;
import ch.bfh.btx8101.examples.JSONHelper;
import ch.bfh.btx8101.examples.LocalEmployee;

/**
 * Servlet implementation class Order
 */
@WebServlet("/Order")
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection conn;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Order() {
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
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MariaDB");
			conn = ds.getConnection();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.isNew()) {
			session.setMaxInactiveInterval(300);
			session.setAttribute("User", new LocalEmployee(request.getRemoteUser()));
			session.setAttribute("Authenticated", false);
			session.setAttribute("Authorized", false);
		}
		// Logout
				String logout = request.getParameter("logout");
				if(logout != null && logout.equals("true")) {
					session.setAttribute("Authenticated", false);
					session.setAttribute("User", new LocalEmployee(null));
				}
				RequestDispatcher rd;
				if((Boolean) session.getAttribute("Authenticated") == false)
					rd = request.getRequestDispatcher("/WEB-INF/Login.jsp");
				else {
					// Authorization
					Authorization authorization = new EnvironmentAuthorization();
					if(authorization.authorize((Employee) session.getAttribute("User"))) {
						session.removeAttribute("customLoginMessage");
						rd = request.getRequestDispatcher("/WEB-INF/Order.jsp");
					} else {
						session.setAttribute("customLoginMessage", "You are not allowed to access this site. Please Login with an authorized user.");
						rd = request.getRequestDispatcher("/WEB-INF/Login.jsp");
					}
				}
					
				rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder errormessage = new StringBuilder();
		int dest_start = 0;
		int dest_end = 0;
		Timestamp startzeit = null;
		Timestamp endzeit = null;
		
		HttpSession session = request.getSession();
		if(session.isNew()) {
			session.setMaxInactiveInterval(300);
			session.setAttribute("User", new LocalEmployee(request.getRemoteUser()));
			session.setAttribute("Authenticated", false);
			session.setAttribute("Authorized", false);
			errormessage.append("Ihre Session ist abgelaufen, bitte aktualisieren sie die Seite");
		}
		LocalEmployee emp = (LocalEmployee) session.getAttribute("User");
		JSONHelper jh = new JSONHelper();
		JSONObject jo = JSONHelper.requestToJSON(request);
		System.out.println(jo.toString());
		
		if(jo.getString("fallnummer").equals(""))
			errormessage.append("Fallnummer darf nicht leer sein\n");
		if(jo.getString("von").equals(""))
			errormessage.append("\"Von\" darf nicht leer sein\n");
		if(jo.getString("nach").equals(""))
			errormessage.append("\"Nach\" darf nicht leer sein\n");
		if(jo.getString("startzeit").equals("") && jo.getString("endzeit").equals(""))
			errormessage.append("Start- oder Endzeit muss ausgewaehlt werden\n");
		if(jo.getString("transportart").equals(""))
			errormessage.append("Transportart darf nicht leer sein\n");
		if(errormessage.length() > 0) {
			jh.defaultAnswer(1, errormessage.toString());
		} else {
			dest_start = getDestinationId(jo.getString("von"));
			dest_end = getDestinationId(jo.getString("nach"));
			if(dest_start == 0)
				errormessage.append("\"Von\" ist keine gueltige Station\n");
			if(dest_end == 0)
				errormessage.append("\"Nach\" ist keine gueltige Station\n");
			System.out.println(jo.getString("startzeit").length());
			if(jo.getString("startzeit").length() == 19) {
				try {
					startzeit = Timestamp.valueOf(jo.getString("startzeit"));
				} catch (IllegalArgumentException e) {
					errormessage.append("Zeitpunkt ist nicht gueltig\n");
				}
			} else if(jo.getString("endzeit").length() == 19) {
				try {
					endzeit = Timestamp.valueOf(jo.getString("endzeit"));
				} catch (IllegalArgumentException e) {
					errormessage.append("Zeitpunkt ist nicht gueltig\n");
				}
			} else if(!jo.getString("startzeit").equalsIgnoreCase("asap"))
				errormessage.append("Zeitpunkt ist nicht gueltig\n");
			if(errormessage.length() <= 0) {
				try {
					Timestamp now = Timestamp.valueOf(java.time.LocalDateTime.now());
					PreparedStatement prep = conn.prepareStatement("INSERT INTO `order` (fid,destination_start,destination_end,ts_start,ts_end,mode,isolation,emergency,message,creator,created,modified) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
					prep.setString(1, jo.getString("fallnummer"));
					prep.setInt(2, dest_start);
					prep.setInt(3, dest_end);
					if(startzeit == null && endzeit == null)
						prep.setTimestamp(4, now);
					else if (startzeit != null)
						prep.setTimestamp(4, startzeit);
					else
						prep.setNull(4, java.sql.Types.TIMESTAMP);
					if(endzeit == null)
						prep.setNull(5, java.sql.Types.TIMESTAMP);
					else
						prep.setTimestamp(5, endzeit);
					prep.setString(6, jo.getString("transportart"));
					prep.setBoolean(7, jo.getBoolean("isolation"));
					prep.setBoolean(8, jo.getBoolean("notfall"));
					if(jo.getString("message").equals(""))
						prep.setNull(9, java.sql.Types.VARCHAR);
					else
						prep.setString(9, jo.getString("message"));
					prep.setString(10, emp.getUsername());
					prep.setTimestamp(11, now);
					prep.setTimestamp(12, now);
					prep.execute();
					conn.commit();
					prep.close();
					jh.defaultAnswer(new JSONObject());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					errormessage.append(e.getMessage());
					jh.defaultAnswer(1, errormessage.toString());
				}
			} else {
				jh.defaultAnswer(1, errormessage.toString());
			}
			
		}
		System.out.println(jh.toString());
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jh);
		out.flush();
	}
	
	private int getDestinationId(String stationname) {
		int resultId = 0;
		try {
			PreparedStatement prep = conn.prepareStatement("SELECT id FROM destinations WHERE deleted IS NULL AND name=?");
			prep.setString(1, stationname);
			ResultSet rs = prep.executeQuery();
			if (rs.next()) {
				resultId = rs.getInt(1);
			}
			rs.close();
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultId;
	}

}
