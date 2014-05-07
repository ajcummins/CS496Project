package edu.ycp.cs496.eduapp.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.controllers.CreateAcctController;
import edu.ycp.cs496.eduapp.model.controllers.LoginController;

public class CreateAccount extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6102460180220840523L;
	
	private String profPass;
	private String username;
	private String pass;
	private String confirmPass;
	private String firstName;
	private String lastName;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//resp.setContentType("application/json");
		resp.setStatus(HttpServletResponse.SC_OK);
		req.getRequestDispatcher("/_view/CreateAccount.jsp").forward(req,resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Create Account Post go..");
		
		// Obtain Account entries from the view
		//profPass = (String) req.getAttribute("ProPass");
		username = (String) req.getParameter("Username");
		pass = (String) req.getParameter("Password");
		confirmPass = (String) req.getParameter("ConfirmPassword");
		firstName = (String) req.getParameter("FirstName");
		lastName = (String) req.getParameter("LastName");
		
		System.out.println("username = " + username);
		System.out.println("pass = " + pass);
		System.out.println("confirmPass = " + confirmPass);
		System.out.println("firstName = " + firstName);
		System.out.println("lastName = " + lastName);
		
		// Check for empty fields EXCEPT FOR PROF PASSWORD
		if(noEmptyFields())
		{	
			// Check if Passwords match
			if(pass.equals(confirmPass))
			{
				System.out.println("Calling Controller");
				// Use CreateAcctController to check their credentials
				CreateAcctController controller = new CreateAcctController();
				
				// Fill User class with fields
				User newUser = new User(username,pass,firstName,lastName);
				
				// There is nothing in this field, so it must be a student
				controller.createAccount(newUser);
				// Add User to session
				HttpSession session = req.getSession();
				session.setAttribute("User", newUser);
				
				//  Redirect to My Course List... 
				resp.sendRedirect(req.getContextPath()+"/MyCourseList");
					
			}
			else
			{
				// Passwords didnt' match, send response to view
				req.setAttribute("result", "Passwords did not match");
				this.doGet(req, resp);
			}	
		}
		else
		{
			// Empty fields exist, send response to the view
			req.setAttribute("result", "Empty fields exsist, please fill all fields");
			this.doGet(req, resp);
		}
		
		
	}
	
	private boolean noEmptyFields()
	{
		if(username != "" && pass != "" && confirmPass != "" && firstName != "" && lastName != "")
		{
			if(username != null && pass != null && confirmPass != null && firstName != null && lastName != null)
			{				
				// no empty fields
				return true;
			}
		}
		
		return false;
		
	}
	
	
}
