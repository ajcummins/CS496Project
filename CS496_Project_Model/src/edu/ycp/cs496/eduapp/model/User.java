package edu.ycp.cs496.eduapp.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	//private int userID;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private UserType userType;
	// Get courseID's using course registry...
	//private List<String> courseIDList;

	public User()
	{
		//courseIDList = new ArrayList<String>();
	}
	
	public User(final String inUser, final String inPass, final String inFName, final String inLName)
	{
		username = inUser;
		password = inPass;
		firstName = inFName;
		lastName = inLName;
		userType = UserType.STUDENT;
		//courseIDList = new ArrayList<String>();
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
		username = inLName;
	}
	public void setToProfessor()
	{
		userType = UserType.PROFESSOR;
	}
	public void setToStudent()
	{
		userType = UserType.STUDENT;
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
	/*
	public List<String> getCourseIdList()
	{
		return courseIDList;
	}
	
	public boolean addToCourseList(String inCourseId)
	{
		return courseIDList.add(inCourseId);
		// this method returns true/false based on success vs failure
	}
	
	public boolean removeFromCourseList(String inCourseId)
	{
		String temp = null;
		for(int i = 0; i < courseIDList.size(); i++)
		{
			if(courseIDList.get(i).equals(inCourseId))
			{
				temp =  courseIDList.remove(i);
			}
		}
		
		if(temp != null)
		{
			return true;
		}
		return false;
	}
	*/

	/*
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	*/

}


