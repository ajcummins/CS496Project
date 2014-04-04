package edu.ycp.cs496.eduapp.model.persist;

import edu.ycp.cs496.eduapp.model.User;

public interface IDatabase {

	boolean authenticateUser(String user, String pass);

	boolean createAccount(User inUser,boolean isProf);

}
