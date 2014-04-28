package edu.ycp.cs496.eduapp.model;

public class MeetingTime {
	private Day day;
	private TimeOfDay startTime;
	private TimeOfDay endTime;
	private String location;
	private MeetingType type;
	
	public MeetingTime() {
		
	}
	
	public MeetingTime(TimeOfDay inStartTime, TimeOfDay inEndTime, Day inDay, String inLoc, MeetingType inType)
	{
		setStartTime(inStartTime);
		setEndTime(inEndTime);
		setLocation(inLoc);
		setType(inType);
		setDay(inDay);
	}
	

	public TimeOfDay getStartTime() {
		return startTime;
	}

	public void setStartTime(TimeOfDay startTime) {
		this.startTime = startTime;
	}

	public TimeOfDay getEndTime() {
		return endTime;
	}

	public void setEndTime(TimeOfDay endTime) {
		this.endTime = endTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public MeetingType getType() {
		return type;
	}

	public void setType(MeetingType type) {
		this.type = type;
	}


	public Day getDay() {
		return day;
	}


	public void setDay(Day day) {
		this.day = day;
	}
	
	
	
}
