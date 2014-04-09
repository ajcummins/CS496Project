package edu.ycp.cs496.eduapp.model.persist;

import java.util.List;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.User;

public interface IDatabase {

	User authenticateUser(String user, String pass);

	void createAccount(User inUser,boolean isProf);

	List<Course> getMyCourseList(List<String> courseIds);

	List<Course> getMainCourseList();

	Course getCourseByID(String courseID);

	boolean checkIfProf(String inProfPass);

}
