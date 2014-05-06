package edu.ycp.cs496.eduapp.model.persist;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.CourseRegEntry;
import edu.ycp.cs496.eduapp.model.Day;
import edu.ycp.cs496.eduapp.model.MeetingTime;
import edu.ycp.cs496.eduapp.model.MeetingType;
import edu.ycp.cs496.eduapp.model.Notification;
import edu.ycp.cs496.eduapp.model.Resource;
import edu.ycp.cs496.eduapp.model.TimeOfDay;
import edu.ycp.cs496.eduapp.model.User;

public class FakeDatabase implements IDatabase {
		private List<Course> allCourses;
		private List<User> allUsers;
		private List<CourseRegEntry> courseReg;
		private List<String> profPasses;
		
		public FakeDatabase() {
			// Initialize Lists
			// Main Database Lists
			allCourses = new ArrayList<Course>();
			allUsers = new ArrayList<User>();
			profPasses = new ArrayList<String>();
			courseReg = new ArrayList<CourseRegEntry>();
			
			// Populate Lists
			
			// Create Users and set USER IDS They're user id is their number in the list
			User ajcummins = new User("ajcummins","test","A. Joshua","Cummins");
			User thon = new User("thon","test","Thomas","Hon");
			User ao = new User("ao","test","Anthony","O");
			User dhove = new User("dhove","test","David","Hovemeyer");
			User dbab = new User("dbab","test","David","Babcock");
			
			// Set Professors
			dbab.setToProfessor();
			dhove.setToProfessor();
			// Add them to allUsers list
			allUsers.add(dbab);
			allUsers.add(dhove);
			allUsers.add(ajcummins);
			allUsers.add(ao);
			allUsers.add(thon);
			
			
			// Create Courses add to allCourses List
			Course cs496 = new Course("CS496","Web and Mobile App Development");
			cs496.setDescription("Learn about how to utilize the android programming language in conjunction with with web services");
			cs496.addNote(new Notification(new Date(98547), "web and mobile test notification"));
			cs496.addResource(new Resource("CS496 Course Home Page","http://ycpcs.github.io/cs496-spring2014/"));
			cs496.addMeetingTime(new MeetingTime(new TimeOfDay(11,00),new TimeOfDay(12,15), Day.TUESDAY,"KEC119 and KEC127", MeetingType.LECTURE));
			cs496.addMeetingTime(new MeetingTime(new TimeOfDay(11,00),new TimeOfDay(12,15), Day.THURSDAY,"KEC119 and KEC127", MeetingType.LECTURE));
			
			Course cs456 = new Course("CS456","Social and Professional Issues in Computing");	
			cs456.setDescription("Discuss and learn about how technology impacts society, not every issue has an easy answer");
			cs456.addNote(new Notification(new Date(857823), "social and professional issues test notification"));
			cs456.addResource(new Resource("CS456 Course Home Page","http://faculty.ycp.edu/~dbabcock/cs456/index.html"));
			cs456.addMeetingTime(new MeetingTime(new TimeOfDay(9,30),new TimeOfDay(10,45), Day.TUESDAY,"KEC117", MeetingType.LECTURE));
			cs456.addMeetingTime(new MeetingTime(new TimeOfDay(9,30),new TimeOfDay(10,45), Day.THURSDAY,"KEC117", MeetingType.LECTURE));
			
			allCourses.add(cs496);
			allCourses.add(cs456);
			
			
			
			// Create Course registry entries and add to course registry
			// ENTRY IS (UserID,CourseID,boolean isProf) or (UserID,CourseID) -> isProf = false
			// add babcock to prof of cs456
			courseReg.add(new CourseRegEntry(findUserIndex(dbab),findCourseIndex(cs456),true));	
			// add hove to prof of cs496
			courseReg.add(new CourseRegEntry(findUserIndex(dhove),findCourseIndex(cs496),true));	
			// add all of us to cs496
			courseReg.add(new CourseRegEntry(findUserIndex(ao),findCourseIndex(cs496)));			
			courseReg.add(new CourseRegEntry(findUserIndex(ajcummins),findCourseIndex(cs496)));		
			courseReg.add(new CourseRegEntry(findUserIndex(thon),findCourseIndex(cs496)));		
			// add ao to cs456
			courseReg.add(new CourseRegEntry(findUserIndex(ao),findCourseIndex(cs456)));	
		
			// Populate prof Passes
			profPasses.add("ihatemondays");
			
		}

		@Override
		public User authenticateUser(String user, String pass) {
			User temp = null;
			for(int i = 0; i < allUsers.size(); i++)
			{
				if(allUsers.get(i).getUsername().equals(user))
				{
					if(allUsers.get(i).getPassword().equals(pass))
					{
						temp =  allUsers.get(i);
					}
				}
			}
			return temp;
			// returns the User or returns null
		}
		
		//get User
		@Override
		public User getUser(String user){
			User temp = null;
			for(int i = 0; i < allUsers.size(); i++)
			{
				if(allUsers.get(i).getUsername().equals(user))
				{
					temp =  allUsers.get(i);	
				}
			}
			return temp;
			// returns the User or returns null
		}
		
		@Override
		public void createAccount(User inUser,boolean isProf) {
			
			if(inUser != null)
			{
				// Set the User to Professor or Student based off boolean input
				if(isProf)
				{
					inUser.setToProfessor();
				}
				else
				{
					inUser.setToStudent();
				}
				allUsers.add(inUser);
			}
		}

		@Override
		public List<Course> getMyCourseList(String inUsername) {
			// Using the Course registry
			// 1st using the username get the user's ID
			int userID = -1;
			List<Course> temp = new ArrayList<Course>();
			for(int i = 0; i < allUsers.size(); i++)
			{
				if(inUsername.equals((allUsers.get(i).getUsername())))
				{
					userID = i;
				}
			}
			// Obtain all the courses that correspond with the user's ID
			if(userID != -1)
			{
				for(int j = 0; j < courseReg.size(); j++)
				{
					if(courseReg.get(j).getUserID() == userID)
					{
						// We have a match add the respective course to the temp course list
						temp.add(allCourses.get(courseReg.get(j).getCourseID()));
					}
				}
				return temp;
			}
			return null;
			
		}

		@Override
		public List<Course> getMainCourseList() {
			return allCourses;
		}

		@Override
		public Course getCourseByCode(String courseCode) {
			
			Course temp = null;
			for(int i = 0; i < allCourses.size(); i ++)
			{
				if(courseCode.equals(allCourses.get(i).getCode()))
				{
					temp = allCourses.get(i);
					// Don't want to keep iterating, course codes should be unique...
					i = allCourses.size();
				}
			}
			return temp;
			// if temp != null then we know the course was found
		}

		@Override
		public boolean checkIfProf(String inProfPass) {
			boolean key = false;
			for(int i = 0; i < profPasses.size(); i++)
			{
				if(inProfPass.equals(profPasses.get(i)))
				{
					key = true;
					// Skip to end, we got what we needed, no point in iterating further
					i = profPasses.size();
				}
			}
			
			return key;
			
		}
		
		public int findCourseIndex(Course inCourse)
		{
			for(int i = 0; i < allCourses.size(); i++)
			{
				if(inCourse.getCode().equals(allCourses.get(i).getCode()))
				{
					return i;
				}
			}
			return -1;
		}
		
		public int findUserIndex(User inUser)
		{
			for(int i = 0; i < allUsers.size(); i++)
			{
				if(inUser.getUsername().equals(allUsers.get(i).getUsername()))
				{
					return i;
				}
			}
			return -1;
		}

		@Override
		public Course editCourse(Course courseToEdit) {
			/*int index = findCourseIndex(courseToEdit);
			
			allCourses.get(index).setCode(courseToEdit.getCode());
			allCourses.get(index).setDescription(courseToEdit.getDescription());
			allCourses.get(index).setTitle(courseToEdit.getTitle());
			//allCourses.get(index).addNote(courseToEdit.get);
			
			//notifications add
			//recorse add/remove
			//location & */
			return null;
			
		}

		@Override
		public Course addCourse(Course course) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Course deleteCourse(Course course) {
			// TODO Auto-generated method stub
			return null;
		}
}

