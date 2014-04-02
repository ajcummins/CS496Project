package edu.ycp.cs496.eduapp.model.persist;

public interface IDatabase {

	boolean authenticateUser(String user, String pass);

}
