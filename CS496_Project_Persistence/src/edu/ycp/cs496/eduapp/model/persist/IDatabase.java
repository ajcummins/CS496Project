package edu.ycp.cs496.eduapp.model.persist;

import java.util.List;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.User;

public interface IDatabase {

	User authenticateUser(String user, String pass);

	Course getCourseByCode(String courseCode);
	
	boolean editCourse(Course course);
	boolean addCourse(Course course);
	boolean deleteCourse(Course course);

	boolean checkIfProf(String inProfPass);

	User createAccount(User inUser);

	void createAccount(User inUser, boolean isProf);

	List<User> getAllUsers();

	boolean addEntry(String courseCode, List<String> usernameList);

	List<Course> getMyCourseList(User inUser);

	List<Course> getMyCourseList(String inUsername);

}
