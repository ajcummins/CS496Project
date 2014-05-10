package edu.ycp.cs496.eduapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course {
	private int courseID;
	private String code;
	//private User professor;
	private String title;
	private String description;
	private MeetingTime meetingTime;
	private List<Notification> noteList;	
	private List<Resource> resourceList; 
	
	public Course(){
		noteList = new ArrayList<Notification>();
		resourceList = new ArrayList<Resource>();
		
	}
	
	public Course(String code, String CourseTitle, String description, MeetingTime meetingTime,List<Notification> noteList, List<Resource> resourceList){
		this.code = code;
		this.title = CourseTitle;
		this.description = description;
		this.setMeetingTime(meetingTime);
		this.noteList = noteList;
		this.resourceList = resourceList;
	}
	
	public Course(String CourseTitle){
		//this.professor = Professor;
		this.title = CourseTitle;
		noteList = new ArrayList<Notification>();
		resourceList = new ArrayList<Resource>();
		
	}
	
	public Course(String code, String CourseTitle){
		setCode(code);
		//this.professor = Professor;
		this.title = CourseTitle;
		noteList = new ArrayList<Notification>();
		resourceList = new ArrayList<Resource>();
		
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
			if(resourceID.equals(resourceList.get(i).getId()))
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String inTitle) {
		this.title = inTitle;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MeetingTime getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(MeetingTime meetingTime) {
		this.meetingTime = meetingTime;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
}
