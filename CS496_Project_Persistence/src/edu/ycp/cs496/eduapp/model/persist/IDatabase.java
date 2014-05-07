package edu.ycp.cs496.eduapp.model.persist;

import java.util.List;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.User;

public interface IDatabase {

	User authenticateUser(String user, String pass);

	List<Course> getMyCourseList(String inUsername);

	List<Course> getMainCourseList();

	Course getCourseByCode(String courseCode);
	
	Course editCourse(Course course);
	Course addCourse(Course course);
	Course deleteCourse(Course course);

	boolean checkIfProf(String inProfPass);

	void createAccount(User inUser);

}
