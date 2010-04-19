package message;


import com.thoughtworks.xstream.XStream;



public class LocationRequest
{	
	private static XStream xstream;
	static
	{
		xstream = new XStream();
		xstream.alias("LocationRequest", LocationRequest.class);
	}

	public static LocationRequest FromXML(String xml)
	{			
		return (LocationRequest)xstream.fromXML(xml);		
	}	
	public String ToXML()
	{
		return xstream.toXML(this);	
	}
	
	
	private String roomNumber;
	private String roomTag;	
		
	
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


}

