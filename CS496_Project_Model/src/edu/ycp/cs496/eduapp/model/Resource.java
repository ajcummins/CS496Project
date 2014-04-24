package edu.ycp.cs496.eduapp.model;

public class Resource {
	private String id;
	private String link;
	
	public Resource(String inID, String inLink)
	{
		this.id = inID;
		this.link = inLink;
	}
	
	public void setLink(String inLink)
	{
		this.link = inLink;
	}
	
	public void setId(String inID)
	{
		this.id = inID;
	}
	
	public String getLink()
	{
		return link;
	}
	
	public String getId()
	{
		return id;
	}
}
