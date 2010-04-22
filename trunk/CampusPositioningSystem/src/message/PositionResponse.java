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
	
	
	
	private String startNodeID;
	
	
	public String getStartNodeID()
	{
		return startNodeID;
	}
	public void setStartNodeID(String value)
	{
		startNodeID = value;
	}



}
