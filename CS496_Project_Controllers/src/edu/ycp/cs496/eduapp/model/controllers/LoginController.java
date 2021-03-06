package edu.ycp.cs496.eduapp.model.controllers;

import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.persist.DatabaseProvider;
import edu.ycp.cs496.eduapp.model.persist.IDatabase;

public class LoginController {
	public User authenticateUser(String user, String pass) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.authenticateUser(user,pass);
	}
}
