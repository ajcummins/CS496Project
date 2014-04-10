package edu.ycp.cs496.eduapp.model;

public class MeetingTime {
	private boolean[] days;
	private TimeOfDay startTime;
	private TimeOfDay endTime;
	private String location;
	private MeetingType type;
	
	public MeetingTime(String inLoc, MeetingType inType)
	{
		setLocation(inLoc);
		setType(inType);
		days = new boolean[7];
	}
	
	public void setDaysOfMeeting(boolean sun, boolean mon, boolean tues, boolean wed, boolean thur, boolean fri, boolean sat)
	{
		days[Day.SUNDAY.getNumVal()] = sun;
		days[Day.MONDAY.getNumVal()] = mon;
		days[Day.TUESDAY.getNumVal()] = tues;
		days[Day.WEDNESDAY.getNumVal()] = wed;
		days[Day.THURSDAY.getNumVal()] = thur;
		days[Day.FRIDAY.getNumVal()] = fri;
		days[Day.SATURDAY.getNumVal()] = sat;
	}
	
	public void setDays(boolean[] inDays)
	{
		days = inDays;
	}
	
	public boolean[] getDays()
	{
		return days;
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
	
	
	
}
