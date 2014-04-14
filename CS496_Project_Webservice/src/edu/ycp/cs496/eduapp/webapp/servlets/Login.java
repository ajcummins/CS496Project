package edu.ycp.cs496.eduapp.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs496.eduapp.model.JSON;
import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.controllers.LoginController;
import edu.ycp.cs496.eduapp.model.persist.DatabaseProvider;
public class Login extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4174367399316027618L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//resp.setContentType("application/json");
		resp.setStatus(HttpServletResponse.SC_OK);
		req.getRequestDispatcher("/_view/Login.jsp").forward(req,resp);
		
		//
		String pathInfo = req.getPathInfo();
		String user = null;//make it equal to login/user
		String pass = null;//make it equal to login/user/pass
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
			//get users
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			JSON.getObjectMapper().writeValue(resp.getWriter(), DatabaseProvider.getInstance().authenticateUser(user, pass));
			return;
		}
		
		// Get the item name
		if (pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Obtain User & Password entry from the view
		String userString = (String) req.getParameter("userName");
		String passString = (String) req.getParameter("password");
		
		// Check for empty fields
		if(userString != null && passString !=null && !userString.equals("") && !passString.equals(""))
		{		
			// Use Login Controller to check their credentials
			LoginController controller = new LoginController();
			User thisUser = controller.authenticateUser(userString, passString);
			
			if(thisUser != null)
			{
				// Login Successful
				// Store User in Session
				HttpSession session = req.getSession();
				session.setAttribute("User", thisUser);
				
				//  Redirect to My Course List... 
				req.getRequestDispatcher("/_view/MyCourseList.jsp").forward(req, resp);
				
			}
			else
			{
				// Login Unsuccessful... 
				req.setAttribute("result", "Login Unsuccessful, Please try agian");
				this.doGet(req, resp);
			}
		}
		else
		{
			// Empty fields exist, send response to the view
			req.setAttribute("result", "Empty fields exist, please fill all fields");
			this.doGet(req, resp);
		}
		
	}
}
