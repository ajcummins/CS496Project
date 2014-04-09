package edu.ycp.cs496.eduapp.model.controllers;

import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.persist.DatabaseProvider;
import edu.ycp.cs496.eduapp.model.persist.IDatabase;

public class CreateAcctController {
	public void createAccount(User inUser,boolean isProf) {
		IDatabase db = DatabaseProvider.getInstance();
		db.createAccount(inUser, isProf);
	}
	
	public boolean checkIfProf(String inProfPass)
	{
		IDatabase db = DatabaseProvider.getInstance();
		return db.checkIfProf(inProfPass);
	}
}
