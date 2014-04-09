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
		private List<String> profPasses;
		
		public FakeDatabase() {
			// Initialize Lists
			// Mobile / User Lists
			userCourses = new ArrayList<String>();
			// Main Database Lists
			allCourses = new ArrayList<Course>();
			allUsers = new ArrayList<User>();
			profPasses = new ArrayList<String>();
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
			Course CS496 = new Course("CS496",dhove,"Web and Mobile App Development","KEC124");
			CS496.setClassTime(Day.TUESDAY, new CourseTime(11.00f,12.15f));
			CS496.setClassTime(Day.THURSDAY, new CourseTime(11.00f,12.15f));
			Course CS456 = new Course("CS456",dbab,"Social and Prfessional Issues in Computing","KEC117");
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
		public List<Course> getMyCourseList(List<String> courseIds) {
			List<Course> temp = new ArrayList<Course>();
			for(int i = 0; i < courseIds.size(); i ++)
			{
				// Remove a string from the id list to compare to
				String currentId = courseIds.remove(i);
				// Iterate through main list looking for the courseID
				for(int j = 0; j < allCourses.size(); j++)
				{
					// If the course id is found
					if(currentId.equals(allCourses.get(j).getCourseID()))
					{
						// Add the course to the temp list to be returned
						temp.add(allCourses.get(j));
						// There SHOULDN'T / WILL NOT BE another element with that id
						// so don't iterate through the rest of the loop for no reason : break out of loop
						j = allCourses.size();
					}
				}
			}
			return temp;
		}

		@Override
		public List<Course> getMainCourseList() {
			return allCourses;
		}

		@Override
		public Course getCourseByID(String courseID) {
			
			Course temp = null;
			for(int i = 0; i < allCourses.size(); i ++)
			{
				if(courseID.equals(allCourses.get(i).getCourseID()))
				{
					temp = allCourses.get(i);
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
}

