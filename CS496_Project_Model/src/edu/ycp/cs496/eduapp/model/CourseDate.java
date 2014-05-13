package edu.ycp.cs496.eduapp.model;

public class CourseDate {
	private int month;
	private int day;
	private int year;
	
	public CourseDate()
	{
		
	}
	
	public CourseDate(int inMonth, int inDay, int inYear)
	{
		setMonth(inMonth);
		setDay(inDay);
		setYear(inYear);
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
}
