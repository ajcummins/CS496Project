package edu.ycp.cs496.eduapp.model.controllers;

import java.util.List;

import edu.ycp.cs496.eduapp.model.persist.DatabaseProvider;
import edu.ycp.cs496.eduapp.model.persist.IDatabase;

public class AddCourseRegEntry {

	public boolean addEntry(String courseCode,List<String> usernameList) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.addEntry(courseCode,usernameList);
	}

}
