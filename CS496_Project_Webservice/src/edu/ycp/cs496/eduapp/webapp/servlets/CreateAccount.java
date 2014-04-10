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
				
				if(!profPass.equals("") && profPass != null)
				{
					// There is something in the field we must check it
					// Check to see if the password was correct
					boolean success = controller.checkIfProf(profPass);
					if(success)
					{
						// Prof Pass was correct, create new account as Prof!
						controller.createAccount(newUser, success);
						// Add User to Session
						HttpSession session = req.getSession();
						session.setAttribute("User", newUser);
						
						//  Redirect to My Course List... 
						req.getRequestDispatcher("/_view/MyCourseList.jsp").forward(req, resp);
						
					}
					else
					{
						// Prof Pass was incorrect, but there was a value in the field meaning they could have mis-typed
						req.setAttribute("result", "Professor Password was incorrect, Please try agian or delete Professor Password field");
						this.doGet(req, resp);
					}
					
				}
				else
				{
					// There is nothing in this field, so it must be a student
					controller.createAccount(newUser, false);
					// Add User to session
					HttpSession session = req.getSession();
					session.setAttribute("User", newUser);
					
					//  Redirect to My Course List... 
					req.getRequestDispatcher("/_view/MyCourseList.jsp").forward(req, resp);
					
				}
				
				/* FIXME: The check after the controller runs to see if it worked will be replaced when an actual database is implemented
				 * It will be replaced with try/catch blocks in which we can output an error in the catch block...
				 */
				
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
