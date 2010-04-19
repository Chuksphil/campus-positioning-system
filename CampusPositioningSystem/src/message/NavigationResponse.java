package message;


import com.thoughtworks.xstream.XStream;


public class NavigationResponse 
{	
	
	private static XStream xstream;
	static{
		xstream = new XStream();
		xstream.alias("LocationResponse", NavigationResponse.class);	
	}
	
	public static NavigationResponse FromXML(String xml)
	{		
		return (NavigationResponse)xstream.fromXML(xml);		
	}	
	public String ToXML()
	{		
		return xstream.toXML(this);	
	}
	
	
	
	private String path;
	
	
	public String getPath()
	{
		return path;
	}
	public void setPath(String value)
	{
		path = value;
	}


}
