package ch.bfh.btx8101.EasyTrack;

import java.io.IOException;
import java.sql.Connection;
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

import ch.bfh.btx8101.examples.Authorization;
import ch.bfh.btx8101.examples.Employee;
import ch.bfh.btx8101.examples.EnvironmentAuthorization;
import ch.bfh.btx8101.examples.LocalEmployee;

/**
 * Servlet implementation class Order
 */
@WebServlet("/Order")
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MSSQLDB");
			Connection conn = ds.getConnection();
			System.out.println(conn.isClosed());
			System.out.println("successfully connected to db");
		} catch (NamingException | SQLException e) {
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
		HttpSession session = request.getSession();
		if(session.isNew()) {
			session.setMaxInactiveInterval(10);
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
