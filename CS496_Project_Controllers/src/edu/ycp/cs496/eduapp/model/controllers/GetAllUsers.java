package edu.ycp.cs496.eduapp.model.controllers;

import java.util.List;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.persist.DatabaseProvider;
import edu.ycp.cs496.eduapp.model.persist.IDatabase;

public class GetAllUsers {
	public List<User> getAllUsers() {
		IDatabase db = DatabaseProvider.getInstance();
		return db.getAllUsers();
	}
}
