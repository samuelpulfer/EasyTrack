package ch.bfh.btx8101.examples;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;


/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
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
			session.setMaxInactiveInterval(300);
			session.setAttribute("User", new LocalEmployee(request.getRemoteUser()));
			session.setAttribute("Authenticated", false);
			session.setAttribute("Authorized", false);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONHelper jh = new JSONHelper();
		JSONObject jo = JSONHelper.requestToJSON(request);
		String username = jo.getString("username");
		String passwd = jo.getString("password");
		
		System.out.println(username);
		System.out.println(passwd);
		
		if(username == null || passwd == null)
			jh.defaultAnswer(1, "Something went wrong");
		else {
			Authentication auth = new InternalAuth();
			Employee emp = auth.authenticate(username, passwd);
			if(emp == null)
				jh.defaultAnswer(1, "Wrong username or password");
			else {
				jh.defaultAnswer(0, "Success");
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(300);
				session.setAttribute("User", emp);
				session.setAttribute("Authenticated", true);
				session.setAttribute("Authorized", false);
				session.removeAttribute("customLoginMessage");
			}
		}
		System.out.println(jh.toString());
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jh);
		out.flush();
	}

}
