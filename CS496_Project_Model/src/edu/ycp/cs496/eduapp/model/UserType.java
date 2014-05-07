package edu.ycp.cs496.eduapp.model;

public enum UserType {
	PROFESSOR(1), STUDENT(2), ADMIN(0);
	
	public final int typeID;
	
	private UserType(int id){
		typeID = id;
	}
}
