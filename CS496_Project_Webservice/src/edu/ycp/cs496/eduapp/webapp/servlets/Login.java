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
		resp.setStatus(HttpServletResponse.SC_OK);
		req.getRequestDispatcher("/_view/Login.jsp").forward(req,resp);
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
				/*
				String action = req.getParameter("action");
				if(action != null) {
					req.setAttribute("action", action);
				}
				*/
				//  Redirect to My Course List... 
				resp.sendRedirect(req.getContextPath()+"/MyCourseList");
				
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
