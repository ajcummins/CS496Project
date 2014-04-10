package edu.ycp.cs496.eduapp.model;

public class TimeOfDay {
	private int hour;
	private int min;
	
	public TimeOfDay(int inHour, int inMin)
	{
		setHour(inHour);
		setMin(inMin);
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}
	
	
}
