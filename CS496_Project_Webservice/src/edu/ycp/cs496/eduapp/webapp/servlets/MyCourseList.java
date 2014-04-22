package edu.ycp.cs496.eduapp.webapp.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.controllers.GetCourseByID;
import edu.ycp.cs496.eduapp.model.controllers.GetMyCourseList;
import edu.ycp.cs496.eduapp.model.controllers.LoginController;

public class MyCourseList extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4174367399316027618L;
	private User thisUser;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//Retrieve the User object from the session and make sure it isn't empty...
		HttpSession session = req.getSession();
		thisUser = (User) session.getAttribute("User");
		if(thisUser != null)
		{			
			String action = req.getParameter("action");
			//String permission = req.getParameter("permission");
			if(action != null) {
				req.setAttribute("action", action);
			}
			String courseCode = getCourseCode(req);
			showUI(req, resp, courseCode);
		}
		else
		{
			// No User data in session, redirect to Login...
			req.setAttribute("result", "You are not logged in, Please log in");
			resp.sendRedirect(req.getContextPath() + "/Login");
		}
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//Retrieve the User object from the session and make sure it isn't empty...
		HttpSession session = req.getSession();
		thisUser = (User) session.getAttribute("User");
		if(thisUser != null)
		{
			// Post response
			String courseCode = getCourseCode(req);
			String action = req.getParameter("action");
			//String permission = req.getParameter("permission");
			if (action != null && !action.trim().equals(""))
			{
				//action is not empty, which action is it?
				if(action.trim().equals("edit"))
				{
					// EDIT
				}
				else if(action.trim().equals("delete"))
				{
					// DELETE
				}
				else if(action.trim().equals("add"))
				{
					// ADD
					
					// use GetMainCourseList controller to fill an object to use in the view like inventory / myCourseList
				}
				else
				{
					throw new ServletException("Unknown action: " + action);
				}
			}
			else
			{
				//action is empty
				showUI(req,resp,courseCode);
				return;
			}
		}
		else
		{
			// No User data in session, redirect to Login...
			req.setAttribute("result", "You are not logged in, Please log in");
			resp.sendRedirect(req.getContextPath() + "/Login");
		}
	}
	
	private String getCourseCode(HttpServletRequest req) {
		String courseCode = null;
		String pathInfo = req.getPathInfo();
		if(pathInfo != null && !pathInfo.equals("") && !pathInfo.equals("/")){
			courseCode = pathInfo;
			if(courseCode.startsWith("/")) {
				courseCode = courseCode.substring(1);
			}
		}
		return courseCode;
	}
	
	private void showUI(HttpServletRequest req, HttpServletResponse resp, String courseCode) throws ServletException, IOException {
		if(courseCode == null)
		{
			 //Have to assume that User has already been obtained from the session... 
			 //There is a check at the beginning of the GET and POST
			 GetMyCourseList myCourseListController = new GetMyCourseList();
			 List<Course> myCourseList = myCourseListController.getMyCourseList(thisUser.getUsername());
			 System.out.println("OUTPUT COURSE LIST");
			 for(int i = 0; i < myCourseList.size(); i++)
			 {
				 System.out.println("Course : "  + myCourseList.get(i).getCourseTitle());
			 }
			 req.setAttribute("mycourselist",myCourseList);
			 req.getRequestDispatcher("/_view/MyCourseList.jsp");
			
		}
		else
		{
			// Adapt this code to use a controller to get a SPECIFIC Course from the main course list
			/*
				GetItemByName controller = new GetItemByName();
				Item item = controller.getItem(itemName);
				req.setAttribute("Item", item);
				req.getRequestDispatcher("/_view/item.jsp").forward(req, resp);
			*/
			System.out.println("PRINT OUT COURSE");
			GetCourseByID controller = new GetCourseByID();
			Course course = controller.getCourseByCode(courseCode);
			req.setAttribute("Course", course);
			req.getRequestDispatcher("/_view/eduapp/Course.jsp");
		}
		
	}

	
}
