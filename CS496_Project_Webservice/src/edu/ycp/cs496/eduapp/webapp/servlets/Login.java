package edu.ycp.cs496.eduapp.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs496.eduapp.model.controllers.LoginController;

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
			boolean success = controller.authenticateUser(userString, passString);
			
			if(success)
			{
				// Login Successful, redirect to home page... 
				// (Shouldn't the controller return their User Data??)
				req.setAttribute("result", "Login Successful, Implement the rest");
				this.doGet(req, resp);
				
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
