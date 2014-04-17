package edu.ycp.cs496.eduapp.model.controllers;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.persist.DatabaseProvider;
import edu.ycp.cs496.eduapp.model.persist.IDatabase;

public class GetCourseByID {
	public Course getCourseByCode(String courseCode) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.getCourseByCode(courseCode);
	}
}
