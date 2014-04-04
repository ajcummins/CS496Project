package edu.ycp.cs496.eduapp.model.persist;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.CourseTime;
import edu.ycp.cs496.eduapp.model.Day;
import edu.ycp.cs496.eduapp.model.User;

public class FakeDatabase implements IDatabase {
		private List<String> userCourses;
		private List<Course> allCourses;
		private List<User> allUsers;
		
		public FakeDatabase() {
			// Initialize Lists
			// Mobile / User Lists
			userCourses = new ArrayList<String>();
			// Main Database Lists
			allCourses = new ArrayList<Course>();
			allUsers = new ArrayList<User>();
			// Populate Lists
			
			// Create Users
			User dhove = new User("dhove","test","David","Hovemeyer");
			User dbab = new User("dbab","test","David","Babcock");
			dbab.setToProfessor();
			dhove.setToProfessor();
			User ajcummins = new User("ajcummins","test","A. Joshua","Cummins");
			User thon = new User("thon","test","Thomas","Hon");
			User ao = new User("ao","test","Anthony","O");
			// Create Courses
			Course CS496 = new Course("CS496",dhove,"Web and Mobile App Development","KEC124","http://ycpcs.github.io/cs496-spring2014/");
			CS496.setClassTime(Day.TUESDAY, new CourseTime(11.00f,12.15f));
			CS496.setClassTime(Day.THURSDAY, new CourseTime(11.00f,12.15f));
			Course CS456 = new Course("CS456",dbab,"Social and Prfessional Issues in Computing","KEC117","http://faculty.ycp.edu/~dbabcock/cs456/index.html");
			CS456.setClassTime(Day.TUESDAY, new CourseTime(9.30f, 10.45f));
			CS456.setClassTime(Day.THURSDAY, new CourseTime(9.30f, 10.45f));
			
			
			// Add to respective lists
			userCourses.add("CS496");
			
			allCourses.add(CS496);
			allCourses.add(CS456);
			
			allUsers.add(dhove);
			allUsers.add(dbab);
			allUsers.add(ajcummins);
			allUsers.add(thon);
			allUsers.add(ao);
			
		}

		@Override
		public boolean authenticateUser(String user, String pass) {
			for(int i = 0; i < allUsers.size(); i++)
			{
				if(allUsers.get(i).getUsername().equals(user))
				{
					if(allUsers.get(i).getPassword().equals(pass))
					{
						return true;
					}
				}
			}
			return false;
		}

		@Override
		public boolean createAccount(User inUser,boolean isProf) {
			
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
				// Add was successful
				return true;
			}
			else
			{
				// Add User failed
				return false;
			}
		}
}
