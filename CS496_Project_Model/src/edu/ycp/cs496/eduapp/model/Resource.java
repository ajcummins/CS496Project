package edu.ycp.cs496.eduapp.model;

public class Resource {
	private String id;
	private String link;
	
	public Resource(String inID, String inLink)
	{
		id = inID;
		link = inLink;
	}
	
	public void setLink(String inLink)
	{
		link = inLink;
	}
	
	public void setID(String inID)
	{
		id = inID;
	}
	
	public String getLink()
	{
		return link;
	}
	
	public String getID()
	{
		return id;
	}
}
