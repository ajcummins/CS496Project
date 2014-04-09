package edu.ycp.cs496.eduapp.model.persist;

import java.util.List;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.User;

public interface IDatabase {

	boolean authenticateUser(String user, String pass);

	boolean createAccount(User inUser,boolean isProf);

	List<Course> getMyCourseList(List<String> courseIds);

	List<Course> getMainCourseList();

	Course getCourseByID(String courseID);

}
