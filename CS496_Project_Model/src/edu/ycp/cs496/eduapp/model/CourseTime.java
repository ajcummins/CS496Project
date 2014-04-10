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
	public CourseTime(float inStart, float inEnd)
	{
		startTime = floatToTime(inStart);
		endTime = floatToTime(inEnd);
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
	
	public Time floatToTime(float inFloat){
		float hrs = (inFloat / 1);
		float min = (inFloat % 1) * 10;
		int hrsInMS = (int) (hrs*60*60*1000);
		int minInMS = (int) (min*60*1000);
		Time thisTime = new Time(hrsInMS + minInMS);
		return thisTime;
		/*
		 * How it works:
		 * Split up float from 13.30 to 13 and 0.3
		 * 13 is hrs 0.3 is min but need to mult by 10 to get 30 minutes
		 * do converstions from min and hrs respectively to ms
		*/
	}
}
//http://tutorials.jenkov.com/java-date-time/java-util-calendar.html