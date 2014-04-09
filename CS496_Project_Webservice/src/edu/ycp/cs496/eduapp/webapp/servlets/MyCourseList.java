package edu.ycp.cs496.eduapp.webapp.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.controllers.GetCourseByID;
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
		
		//FIXME : Session.User != null
		if(true)
		{
			String action = req.getParameter("action");
			//String permission = req.getParameter("permission");
			if(action != null) {
				req.setAttribute("action", action);
			}
			String courseID = getCourseID(req);
			showUI(req, resp, courseID);
		}
		else
		{
			// No User data in session, redirect to Login...
		}
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//FIXME : Session.User != null
		if(true)
		{
			// Post response
			String courseID = getCourseID(req);
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
				showUI(req,resp,courseID);
				return;
			}
		}
		else
		{
			// No User data in session : Redirect to Login
		}
	}
	
	private String getCourseID(HttpServletRequest req) {
		String courseID = null;
		String pathInfo = req.getPathInfo();
		if(pathInfo != null && !pathInfo.equals("") && !pathInfo.equals("/")){
			courseID = pathInfo;
			if(courseID.startsWith("/")) {
				courseID = courseID.substring(1);
			}
		}
		return courseID;
	}
	
	private void showUI(HttpServletRequest req, HttpServletResponse resp, String courseID) throws ServletException, IOException {
		if(courseID == null)
		{
			// Adapt this code to use a controller to get the SPECIFIC Courses from the main course list using User.courseListIDs
			/*
			 	GetInventory controller = new GetInventory();
				List<Item> inventory = controller.getInventory();
				req.setAttribute("Inventory", inventory);
				req.getRequestDispatcher("/_view/inventory.jsp").forward(req, resp);
			 */
			
			/*
			 * Have to assume that User has already been obtained from the session... 
			 * There is a check at the beginning of the GET and POST
			 * GetMyCourseList controller = new GetMyCourseList();
			 * List<Course> myCourseList = controller.getMyCourseList(thisUser.getCourseIdList());
			 * req.setAttribute("MyCourseList",myCourseList);
			 * req.setRequestDispatcher("/_view/webApp/MyCourseList.jsp").forward(req, resp);
			 */
			
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
			GetCourseByID controller = new GetCourseByID();
			Course course = controller.getCourseByID(courseID);
			req.setAttribute("Course", course);
			req.getRequestDispatcher("/_view/course.jsp").forward(req, resp);
		}
		
	}

	
}
