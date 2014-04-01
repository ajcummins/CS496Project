package edu.ycp.cs496.eduapp.model;

import java.sql.Time;

public class CourseTime {
	private Time startTime, endTime;
	
	public CourseTime(){
		startTime = new Time(0);
		endTime = new Time(0);
		
	}
	public CourseTime(Time Start, Time End){
		startTime = Start;
		endTime = End;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
}
//http://tutorials.jenkov.com/java-date-time/java-util-calendar.html