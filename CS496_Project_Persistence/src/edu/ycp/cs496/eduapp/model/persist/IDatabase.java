package edu.ycp.cs496.eduapp.model.persist;

import java.util.List;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.User;

public interface IDatabase {

	User authenticateUser(String user, String pass);

	User getUser(String user);
	
	void createAccount(User inUser,boolean isProf);

	List<Course> getMyCourseList(String inUsername);

	List<Course> getMainCourseList();

	Course getCourseByCode(String courseCode);

	boolean checkIfProf(String inProfPass);

}
