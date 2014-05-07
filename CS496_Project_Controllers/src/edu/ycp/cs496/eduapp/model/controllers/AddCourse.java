package edu.ycp.cs496.eduapp.model.controllers;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.persist.DatabaseProvider;
import edu.ycp.cs496.eduapp.model.persist.IDatabase;

public class AddCourse {
	public Course addCourse(Course courseCode) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.addCourse(courseCode);
	}
}
