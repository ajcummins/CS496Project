package edu.ycp.cs496.eduapp.webapp.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.JSON;
import edu.ycp.cs496.eduapp.model.controllers.GetMyCourseList;

public class MyCourseListMobile extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4174367399316027618L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//pathinfo should contain "user"
		String username = req.getPathInfo();
		if (username.startsWith("/")) {
			username = username.substring(1);
		}
		
		//get the user courses from controller
		GetMyCourseList controller = new GetMyCourseList();
		List<Course> userCourses = controller.getMyCourseList(username);
		//System.out.println("MyCourseList: checking user:"+username+"...");
		
		//write courses 
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		JSON.getObjectMapper().writeValue(resp.getWriter(), userCourses);
		System.out.println("MyCourseList: 1st course: "+userCourses.get(0).getTitle());
		return;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}	
}
