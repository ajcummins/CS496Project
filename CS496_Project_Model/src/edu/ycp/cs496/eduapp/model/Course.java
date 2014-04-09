package edu.ycp.cs496.eduapp.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course {
	private String courseID;
	private User professor;
	private String title;
	private String location;
	private String description;
	private CourseTime[] coursetimes;//Sunday - Saturday
	private List<Notification> noteList;	
	private List<Resource> resourceList; 
	
	public Course(){
		coursetimes = new CourseTime[7];
		noteList = new ArrayList<Notification>();
		resourceList = new ArrayList<Resource>();
		iniTimes();
	}
	
	public Course(User Professor, String CourseTitle){
		this.professor = Professor;
		this.title = CourseTitle;
		coursetimes = new CourseTime[7];
		noteList = new ArrayList<Notification>();
		resourceList = new ArrayList<Resource>();
		iniTimes();
	}
	
	public Course(String inCourseID, User Professor, String CourseTitle, String CourseLocation){
		setCourseID(inCourseID);
		this.professor = Professor;
		this.title = CourseTitle;
		coursetimes = new CourseTime[7];
		noteList = new ArrayList<Notification>();
		resourceList = new ArrayList<Resource>();
		iniTimes();
		this.location = CourseLocation;
	}
	
	public User getProfessor() {
		return professor;
	}

	public void setProfessor(User professor) {
		this.professor = professor;
	}

	public List<Resource> getResources() {
		return resourceList;
	}

	public void addResource(Resource inResource) {
		resourceList.add(inResource);
	}
	
	public boolean removeResource(String resourceID)
	{
		Resource temp = null;
		for(int i = 0; i < resourceList.size(); i++)
		{
			if(resourceID.equals(resourceList.get(i).getID()))
			{
				temp = resourceList.remove(i);
			}
		}
		if(temp != null)
		{
			// The Resource was found in the list and removed sucessfully
			return true;
		}
		// The Resource was not found
		return false;
	}
	
	public List<Notification> getNotifications() {
		return noteList;
	}

	public void addNote(Notification inNote) {
		noteList.add(inNote);
	}
	
	public boolean removeNote(Date inDate)
	{
		Notification temp = null;
		for(int i = 0; i < noteList.size(); i++)
		{
			if(noteList.get(i).getDate().equals(inDate))
			{
				temp = noteList.remove(i);
			}
		}
		if(temp != null)
		{
			// The Resource was found in the list and removed sucessfully
			return true;
		}
		// The Resource was not found
		return false;
	}

	public String getCourseTitle() {
		return title;
	}

	public void setCourseTitle(String inTitle) {
		this.title = inTitle;
	}

	public String getCourseLocation() {
		return location;
	}

	public void setCourseLocation(String courseLocation) {
		this.location = courseLocation;
	}
	
	private void iniTimes(){
		for(int i=0; i<7;i++){
			coursetimes[i] = new CourseTime();
			coursetimes[i].setStartTime(new Time(0));
			coursetimes[i].setEndTime(new Time(0));
		}
	}
	
	public CourseTime getClassTime(Day inDay)
	{
		return coursetimes[inDay.getNumVal()];
	}
	
	public void setClassTime(Day inDay, CourseTime inCourseTime)
	{
		coursetimes[inDay.getNumVal()] = inCourseTime;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
}
