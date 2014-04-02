package edu.ycp.cs496.eduapp.model.persist;

public class DatabaseProvider {
	private static IDatabase theInstance;
	
	public static void setInstance(IDatabase db) {
		theInstance = db;
	}
	
	public static IDatabase getInstance() {
		return theInstance;
	}
}
