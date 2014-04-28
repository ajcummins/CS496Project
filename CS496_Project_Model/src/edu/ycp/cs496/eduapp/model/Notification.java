package edu.ycp.cs496.eduapp.model;

import java.util.Date;

public class Notification {

		private Date noteDate;
		private String noteText;
		
		public Notification(){
			
		}
		
		public Notification(Date inDate, String inNoteText)
		{
			noteDate = inDate;
			noteText = inNoteText;
		}
		
		public void setDate(Date inDate)
		{
			noteDate = inDate;
		}
		
		public void setNoteText(String inText)
		{
			noteText = inText;
		}
		
		public Date getDate()
		{
			return noteDate;
		}
		
		public String getNoteText()
		{
			return noteText;
		}
}
