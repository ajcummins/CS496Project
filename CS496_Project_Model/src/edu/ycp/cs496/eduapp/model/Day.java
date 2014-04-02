package edu.ycp.cs496.eduapp.model;

public enum Day {
	 SUNDAY(0),
	 MONDAY(1),
	 TUESDAY(2),
	 WEDNESDAY(3),
	 THURSDAY(4), 
	 FRIDAY(5), 
	 SATURDAY(6);
	 
	 private int numVal;
	 
	 Day(int inNumVal)
	 {
		 numVal = inNumVal;
	 }
	 
	 public int getNumVal() {
		 return numVal;
	 }
}
