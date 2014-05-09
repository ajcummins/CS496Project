package edu.ycp.cs496.eduapp.model;

public class MeetingTime {
	private boolean[] days;
	private TimeOfDay startTime;
	private TimeOfDay endTime;
	private String location;
	private MeetingType type;
	
	public MeetingTime() {
		
	}
	
	public MeetingTime(TimeOfDay inStartTime, TimeOfDay inEndTime, String inLoc, MeetingType inType, boolean[] inDays)
	{
		setStartTime(inStartTime);
		setEndTime(inEndTime);
		setLocation(inLoc);
		setType(inType);
		setDays(inDays);
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

	public boolean[] getDays() {
		return days;
	}

	public void setDays(boolean[] days) {
		this.days = days;
	}
	
	
	
}
