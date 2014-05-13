package edu.ycp.cs496.eduapp.webapp.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.CourseDate;
import edu.ycp.cs496.eduapp.model.MeetingTime;
import edu.ycp.cs496.eduapp.model.MeetingType;
import edu.ycp.cs496.eduapp.model.Notification;
import edu.ycp.cs496.eduapp.model.Resource;
import edu.ycp.cs496.eduapp.model.TimeOfDay;
import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.controllers.AddCourse;
import edu.ycp.cs496.eduapp.model.controllers.AddCourseRegEntry;
import edu.ycp.cs496.eduapp.model.controllers.DeleteCourse;
import edu.ycp.cs496.eduapp.model.controllers.GetAllUsers;
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
		String action = req.getParameter("action");
		if (action != null) {
			req.setAttribute("action", action);
		}
		thisUser = (User) session.getAttribute("User");
		if(thisUser != null)
		{			
			//String permission = req.getParameter("permission");
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
					System.out.println("Edit yo");
					
					//GetCourseByID controller = new GetCourseByID();
					//Course course = controller.getCourseByCode(courseCode);
				}
				else if(action.trim().equals("delete"))
				{
					// DELETE
					System.out.println("delete yo");
					DeleteCourse controller = new DeleteCourse();
					
					String code = req.getParameter("Course.code");
					
					GetCourseByID tmp = new GetCourseByID();
					
					controller.deleteCourse(tmp.getCourseByCode(code));
					
				}
				else if(action.trim().equals("add"))
				{
					// ADD
					System.out.println("add yo");
					AddCourse controller = new AddCourse();
					
					// Obtain all of the data out of the form
					String code = req.getParameter("courseCode");
					String title = req.getParameter("courseTitle");
					String desc = req.getParameter("courseDesc");
					int startHr = Integer.parseInt(req.getParameter("startHr"));
					int startMin = Integer.parseInt(req.getParameter("startMin"));
					int endHr = Integer.parseInt(req.getParameter("endHr"));
					int endMin = Integer.parseInt(req.getParameter("endMin"));
					int startMon = Integer.parseInt(req.getParameter("startMon"));
					int startDay = Integer.parseInt(req.getParameter("startDay"));
					int startYr = Integer.parseInt(req.getParameter("startYr"));
					int endMon = Integer.parseInt(req.getParameter("endMon"));
					int endDay = Integer.parseInt(req.getParameter("endDay"));
					int endYr = Integer.parseInt(req.getParameter("endYr"));
					// handle all the chkboxes
					boolean sun = chkboxValue(req.getParameter("sunChk"));
					boolean mon = chkboxValue(req.getParameter("monChk"));
					boolean tue = chkboxValue(req.getParameter("tueChk"));
					boolean wed = chkboxValue(req.getParameter("wedChk"));
					boolean thu = chkboxValue(req.getParameter("thuChk"));
					boolean fri = chkboxValue(req.getParameter("friChk"));
					boolean sat = chkboxValue(req.getParameter("satChk"));
					boolean[] days = {sun,mon,tue,wed,thu,fri,sat};
					MeetingType type = loadType(req.getParameter("lecRad"),req.getParameter("labRad"));
					String loc = req.getParameter("loc");
					MeetingTime meetingTime = new MeetingTime((new TimeOfDay(startHr,startMin)),(new TimeOfDay(endHr,endMin)),loc,type,days);
					
					System.out.println("courseCode = " + code);
					System.out.println("startHr = " + startHr);
					System.out.println("endHr = " + endHr);
					System.out.println("sun = " + req.getParameter("sunChk"));
					System.out.println("loc = " + loc);
					
					
					// Construct the new course
					Course newCourse = new Course(code,title,desc, meetingTime,new ArrayList<Notification>(), new ArrayList<Resource>());
					newCourse.setStartDate(new CourseDate(startMon,startDay,startYr));
					newCourse.setEndDate(new CourseDate(endMon,endDay,endYr));
					boolean success = controller.addCourse(newCourse);
					if(success)
					{
						System.out.println("Add Successful");
						
						// Initialize course to have this user in it.						
						AddCourseRegEntry regController = new AddCourseRegEntry();
						List<String> tempUserList = new ArrayList<String>();
						tempUserList.add(thisUser.getUsername());
						boolean entrySuccess = regController.addEntry(newCourse.getCode(), tempUserList);
						
						if(entrySuccess)
						{
							System.out.println("Reg entry successful");
							req.setAttribute("action", "view");
						}
						else
						{
							System.out.println("Reg entry unsuccessful");
						}
						
					}
					else
					{
						System.out.println("Add UnSuccessful");
						req.setAttribute("result", "Add Course Falied");
					}
					
					
				}
				else if(action.trim().equals("register"))
				{
					// Register Users to the course...
					// Get Options from regList
					List<String> usernames = new ArrayList<String>();
					ResultSet userRegistry = (ResultSet) req.getAttribute("regList");
					try {
						int index = 1;
						while(userRegistry.next())
						{
							usernames.add("" + userRegistry.getString(index));
							index++;
						}
					} catch (SQLException e) {
						req.setAttribute("result", "Obtaining User's List Failed");
					}
					// Use controller and update the course registry...
					AddCourseRegEntry controller = new AddCourseRegEntry();
					boolean success = controller.addEntry(getCourseCode(req),usernames);
					
					if(success)
					{
						// Return to your course list
						req.setAttribute("action", "view");
						resp.sendRedirect(req.getContextPath() + "/MyCourseList/");
					}
					else
					{
						req.setAttribute("result", "Register Users Failed...");
					}
				}
				else
				{
					throw new ServletException("Unknown action: " + action);
				}

				showUI(req,resp,courseCode);
				
			}
			else
			{
				System.out.println("nothing yo");
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
			// MyCourseList
			
			 //Have to assume that User has already been obtained from the session... 
			 //There is a check at the beginning of the GET and POST
			 GetMyCourseList myCourseListController = new GetMyCourseList();
			 List<Course> mycourselist = myCourseListController.getMyCourseList(thisUser.getUsername());
			 if(mycourselist != null && mycourselist.size() != 0)
			 {
				 req.setAttribute("validcourse", "true");
				 req.setAttribute("MyCourseList",mycourselist);
				 //req.getRequestDispatcher("/_view/MyCourseList.jsp").forward(req, resp);
				 req.getRequestDispatcher("/_view/MyCourseList.jsp").forward(req, resp); 
			 }
			 else
			 {
				 req.setAttribute("validcourse", "false");
				 req.getRequestDispatcher("/_view/MyCourseList.jsp").forward(req, resp); 
			 }
			 
			
		}
		else if(courseCode.equals("NewCourse")){
			String action = "add";
			req.setAttribute("action", action);
			// Send all users list
			GetAllUsers controller = new GetAllUsers();
			List<User> allUsers = controller.getAllUsers();
			req.setAttribute("userlist", allUsers);
			req.getRequestDispatcher("/_view/Course.jsp").forward(req, resp);
		}
		else
		{
			// Course
			GetCourseByID controller = new GetCourseByID();
			Course course = controller.getCourseByCode(courseCode);
			req.setAttribute("Course", course);
			
			// Check all values make sure they are not null or size = 0
			List<Resource> resources = course.getResources();
			MeetingTime meetingTime = course.getMeetingTime();
			List<Notification> notes = course.getNotifications();
			
			if(resources != null && resources.size() > 0)
			{
				// There is something in resources
				req.setAttribute("validresources", "true");
				req.setAttribute("resourcelist", resources);
			}
			else
			{
				// There isn't anything in resources display a message instead
				req.setAttribute("validresources", "false");
				
			}
			
			if(notes != null && notes.size() > 0)
			{
				// There is something in notes
				req.setAttribute("validnotes", "true");
				req.setAttribute("notelist", notes);
			}
			else
			{
				// There isn't anything in notes display a message instead
				req.setAttribute("validnotes", "true");
			}
			
			// Meetingtime should always have something in it because it is set @ creation...
			
			req.setAttribute("MeetingTime", meetingTime);
			req.getRequestDispatcher("/_view/Course.jsp").forward(req, resp);
		}
		
	}
	
	
	private MeetingType loadType(String inLecValue,String inLabValue)
	{
		if(inLecValue.equals("checked"))
		{
			return MeetingType.LECTURE;
		}
		else
		{
			return MeetingType.LAB;
	
		}
	}
	
	private boolean chkboxValue(String inValue)
	{
		if(inValue == null)
		{
			return false;
		}
		if(inValue.equals("true"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
