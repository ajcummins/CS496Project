package edu.ycp.cs496.eduapp.model;

import java.sql.Time;

public class Course {
	private String courseID;
	private User professor;
	private String resources;
	private String courseTitle;
	private String courseLocation;
	private CourseTime[] coursetimes;//Sunday - Saturday
	
	public Course(){
		coursetimes = new CourseTime[7];
		iniTimes();
	}
	
	public Course(User Professor, String CourseTitle){
		this.professor = Professor;
		this.courseTitle = CourseTitle;
		coursetimes = new CourseTime[7];
		iniTimes();
	}
	
	public Course(String inCourseID, User Professor, String CourseTitle, String CourseLocation, String res){
		courseID = inCourseID;
		this.professor = Professor;
		this.courseTitle = CourseTitle;
		coursetimes = new CourseTime[7];
		iniTimes();
		this.courseLocation = CourseLocation;
		this.resources = res;
	}
	
	public User getProfessor() {
		return professor;
	}

	public void setProfessor(User professor) {
		this.professor = professor;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCourseLocation() {
		return courseLocation;
	}

	public void setCourseLocation(String courseLocation) {
		this.courseLocation = courseLocation;
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
}
