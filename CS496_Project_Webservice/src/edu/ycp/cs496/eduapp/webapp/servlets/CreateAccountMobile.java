package edu.ycp.cs496.eduapp.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.controllers.CreateAcctController;
import edu.ycp.cs496.eduapp.model.JSON;

public class CreateAccountMobile extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6102460180220840523L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//resp.setContentType("application/json");
		resp.setStatus(HttpServletResponse.SC_OK);
		req.getRequestDispatcher("/_view/CreateAccount.jsp").forward(req,resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//get User from JSON
		User newUser = JSON.getObjectMapper().readValue(req.getReader(), User.class);
		System.out.println("Receive User from JSON");
		// Use CreateAcctController to check their credentials
		CreateAcctController controller = new CreateAcctController();
		//create student account
		controller.createAccount(newUser);
		System.out.println("create account user:"+newUser.getUsername()+" pass:"+newUser.getPassword()+" name:"+newUser.getFName()+" "+newUser.getLName());

	}
}
