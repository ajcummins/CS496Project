package edu.ycp.cs496.eduapp.model.controllers;

import java.util.List;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.persist.DatabaseProvider;
import edu.ycp.cs496.eduapp.model.persist.IDatabase;

public class GetMainCourseList {
	public List<Course> getMainCourseList() {
		IDatabase db = DatabaseProvider.getInstance();
		return db.getMainCourseList();
	}
}
