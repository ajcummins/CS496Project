package edu.ycp.cs496.eduapp.model;


public class User {
	private int userID;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private UserType userType;

	public User()
	{
	}
	
	public User(final String inUser, final String inPass, final String inFName, final String inLName)
	{
		username = inUser;
		password = inPass;
		firstName = inFName;
		lastName = inLName;
		userType = UserType.STUDENT;
	}	
	
	// Setters
	public void setUsername(String inUsername)
	{
		username = inUsername;
	}
	public void setPassword(String inPassword)
	{
		password = inPassword;
	}
	public void setFName(String inFName)
	{
		firstName = inFName;
	}
	public void setLName(String inLName)
	{
		lastName = inLName;
	}
	public void setToProfessor()
	{
		userType = UserType.PROFESSOR;
	}
	public void setToStudent()
	{
		userType = UserType.STUDENT;
	}
	public void setToAdmin()
	{
		userType = UserType.ADMIN;
	}
	// Getters
	public String getUsername()
	{
		return username;
	}
	public String getPassword()
	{
		return password;
	}
	public String getFName()
	{
		return firstName;
	}
	public String getLName()
	{
		return lastName;
	}
	public UserType getUserType()
	{
		return userType;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}


}


