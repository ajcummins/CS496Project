package edu.ycp.cs496.eduapp.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs496.eduapp.model.JSON;
import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.controllers.LoginController;
public class LoginMobile extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4174367399316027618L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//resp.setContentType("application/json");
		resp.setStatus(HttpServletResponse.SC_OK);
		//req.getRequestDispatcher("/_view/Login.jsp").forward(req,resp);
		
		//get the user and pass from path
		String pathInfo = req.getPathInfo();
		
		//pathinfo should contain "user/pass"
		if (pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}
		
		//get String of user and pass
		//pathInfo = "thon/test";
		int locationOfSlash = pathInfo.indexOf("/");
		String user = pathInfo.substring(0, locationOfSlash);
		String pass = pathInfo.substring(locationOfSlash+1);
		
		//get user
		//User authUser = DatabaseProvider.getInstance().authenticateUser(user, pass);
		// Use Login Controller to check their credentials
		LoginController controller = new LoginController();
		User authUser = controller.authenticateUser(user, pass);
		
		if (authUser != null){
			System.out.println("Attempt to log in, user=" + authUser.getUsername() + ", pass=" + authUser.getPassword() + ", lastname = "+ authUser.getLName());
		}
		//if not an user
		else {
			System.out.println("not valid user/pass");
		}
		//write user if exist else write null 
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		JSON.getObjectMapper().writeValue(resp.getWriter(), authUser);
		return;
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
	}
}
