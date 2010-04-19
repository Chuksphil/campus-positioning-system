package message;


import com.thoughtworks.xstream.XStream;



public class NavigationRequest
{	
	private static XStream xstream;
	static
	{
		xstream = new XStream();
		xstream.alias("NavigationRequest", NavigationRequest.class);
	}

	public static NavigationRequest FromXML(String xml)
	{			
		return (NavigationRequest)xstream.fromXML(xml);		
	}	
	public String ToXML()
	{
		return xstream.toXML(this);	
	}
	
	
	private String roomID;

	private String latitude;
	private String longitude;
		
	
	public String getRoomID()
	{
		return roomID;
	}
	public void setRoomID(String value)
	{
		roomID = value;
	}
	

	public String getLatitude()
	{
		return latitude;
	}
	public void setLatitude(String value)
	{
		latitude = value;
	}

	public String getLongitude()
	{
		return longitude;
	}
	public void setLongitude(String value)
	{
		longitude = value;
	}
	


}

