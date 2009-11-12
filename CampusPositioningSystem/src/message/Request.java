package message;

import java.util.ArrayList;

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
	
	private String roomNumber;
	private String roomTag;
	private String userID;
	
	
	
	public ArrayList<AccessPoint> accessPoints()
	{
		return accessPoints;
	}
	
	
	public String getRoomNumber()
	{
		return roomNumber;
	}
	public void setRoomNumber(String value)
	{
		roomNumber = value;
	}
	
	public String getRoomTag()
	{
		return roomTag;
	}
	public void setRoomTag(String value)
	{
		roomTag = value;
	}

	public String getUserID()
	{
		return userID;
	}
	public void setUserID(String value)
	{
		userID = value;
	}
	
	
	/**
	 * Tests RoomInfo class functionality
	 * @param args
	 */
	public static void main(String[] args)
	{		
		Request req = new Request();
		AccessPoint a = new AccessPoint();
		a.setMacAdress("a_mac_addr");
		a.setSignalStrenght("6");
		req.accessPoints().add(a);
		req.setRoomNumber("123");
		
		
		
		System.out.println(req.ToXML());
		
	}
}

