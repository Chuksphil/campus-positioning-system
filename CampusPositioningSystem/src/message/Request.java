package message;

import java.util.ArrayList;

import util.AccessPoint;

import com.thoughtworks.xstream.XStream;



public class Request
{	
	private static XStream xstream;
	static
	{
		xstream = new XStream();
		xstream.alias("request", Request.class);
		xstream.alias("accesspoint", AccessPoint.class);
	}

	public static Request FromXML(String xml)
	{			
		return (Request)xstream.fromXML(xml);		
	}	
	public String ToXML()
	{
		return xstream.toXML(this);	
	}
	
	
	private ArrayList<AccessPoint> accessPoints = new ArrayList<AccessPoint>();
	private String roomTag;
			
	
	
	
	
	public ArrayList<AccessPoint> accessPoints()
	{
		return accessPoints;
	}
	
	
	public String getRoomTag()
	{
		return roomTag;
	}
	public void setRoomTag(String value)
	{
		roomTag = value;
	}

	
	

}

