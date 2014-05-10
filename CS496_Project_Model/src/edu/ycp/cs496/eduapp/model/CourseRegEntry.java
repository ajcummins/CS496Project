package edu.ycp.cs496.eduapp.model;

public class CourseRegEntry {
	private int entryID;
	private int userID;
	private int courseID;
	private boolean isInstructor;
	
	public CourseRegEntry(){
		
	}
	
	public CourseRegEntry(int inUserID, int inCourseID, boolean inIsInstructor)
	{
		userID = inUserID;
		courseID = inCourseID;
		setInstructor(inIsInstructor);
	}
	
	public CourseRegEntry(int inUserID, int inCourseID)
	{
		userID = inUserID;
		courseID = inCourseID;
		isInstructor = false;
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public boolean isInstructor() {
		return isInstructor;
	}

	public void setInstructor(boolean isInstructor) {
		this.isInstructor = isInstructor;
	}

	public int getEntryID() {
		return entryID;
	}

	public void setEntryID(int entryID) {
		this.entryID = entryID;
	}
}
