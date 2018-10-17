package ch.bfh.btx8101.examples;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Environment
 */
@WebServlet("/Environment")
public class Environment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Environment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.isNew()) {
			session.setMaxInactiveInterval(60);
			session.setAttribute("User", new LocalEmployee(request.getRemoteUser()));
			if(request.getRemoteUser() == null)
				session.setAttribute("Authenticated", false);
			else
				session.setAttribute("Authenticated", true);
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
				rd = request.getRequestDispatcher("/WEB-INF/Environment.jsp");
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
