package edu.ycp.cs496.eduapp.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		// Obtain Account entries from the view
		profPass = (String) req.getAttribute("ProPass");
		username = (String) req.getAttribute("Username");
		pass = (String) req.getAttribute("Password");
		confirmPass = (String) req.getAttribute("ConfirmPassword");
		firstName = (String) req.getAttribute("FirstName");
		lastName = (String) req.getAttribute("LastName");
		
		// Check for empty fields EXCEPT FOR PROF PASSWORD
		if(noEmptyFields())
		{	
			// Check if Passwords match
			if(pass.equals(confirmPass))
			{
				// Use CreateAcctController to check their credentials
				CreateAcctController controller = new CreateAcctController();
				
				// Fill User class with fields
				User newUser = new User(username,pass,firstName,lastName);
				// Set whether user is prof or student
				boolean success;
				if(!profPass.equals("") && profPass != null)
				{
					// There is something in the field we must check it
					// FIXME: Implement this
					// boolean temp = controller.checkIfProf(profPass);
					boolean temp = false;
					success = controller.createAccount(newUser, temp);
				}
				else
				{
					// There is nothing in this field, so it must be a student
					success = controller.createAccount(newUser, false);
				}
				
				if(success)
				{
					// Account creation was successful redirect to home.. again shouldn't it give back a User class??
					req.setAttribute("result", "Account Creation was Successful! Implement the rest!");
					this.doGet(req, resp);
				}
				else
				{
					// Account creation failed...
					req.setAttribute("result", "Account Creation failed");
					this.doGet(req, resp);
				}
				
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
