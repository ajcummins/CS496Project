package edu.ycp.cs496.eduapp.model.controllers;

import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.persist.DatabaseProvider;
import edu.ycp.cs496.eduapp.model.persist.IDatabase;

public class CreateAcctController {
	public User createAccount(User inUser) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.createAccount(inUser);
	}
	
	public boolean checkIfProf(String inProfPass)
	{
		IDatabase db = DatabaseProvider.getInstance();
		return db.checkIfProf(inProfPass);
	}
}
