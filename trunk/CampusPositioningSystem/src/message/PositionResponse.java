package message;


import com.thoughtworks.xstream.XStream;


public class PositionResponse 
{	
	
	private static XStream xstream;
	static{
		xstream = new XStream();
		xstream.alias("PositionResponse", PositionResponse.class);	
	}
	
	public static PositionResponse FromXML(String xml)
	{		
		return (PositionResponse)xstream.fromXML(xml);		
	}	
	public String ToXML()
	{		
		return xstream.toXML(this);	
	}
	
	
	
	private String latitude;
	private String longitude;
	
	
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
