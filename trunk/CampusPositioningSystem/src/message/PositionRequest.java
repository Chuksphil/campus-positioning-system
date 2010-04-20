package message;

import java.util.ArrayList;

import util.AccessPoint;

import com.thoughtworks.xstream.XStream;



public class PositionRequest
{	
	private static XStream xstream;
	static
	{
		xstream = new XStream();
		xstream.alias("PositionRequest", PositionRequest.class);
		xstream.alias("accesspoint", AccessPoint.class);
	}

	public static PositionRequest FromXML(String xml)
	{			
		return (PositionRequest)xstream.fromXML(xml);		
	}	
	public String ToXML()
	{
		return xstream.toXML(this);	
	}
	
	
	private ArrayList<AccessPoint> accessPoints = new ArrayList<AccessPoint>();
	
	
	public ArrayList<AccessPoint> accessPoints()
	{
		return accessPoints;
	}
	

}

