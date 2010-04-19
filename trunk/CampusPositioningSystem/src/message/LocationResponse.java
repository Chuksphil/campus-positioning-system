package message;


import com.thoughtworks.xstream.XStream;


public class LocationResponse 
{	
	
	private static XStream xstream;
	static{
		xstream = new XStream();
		xstream.alias("LocationResponse", LocationResponse.class);	
	}
	
	public static LocationResponse FromXML(String xml)
	{		
		return (LocationResponse)xstream.fromXML(xml);		
	}	
	public String ToXML()
	{		
		return xstream.toXML(this);	
	}
	
	
	
	private String roomID;
	
	
	public String getRoomID()
	{
		return roomID;
	}
	public void setRoomID(String value)
	{
		roomID = value;
	}


}
