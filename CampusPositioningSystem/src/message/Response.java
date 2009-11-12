package message;

import com.thoughtworks.xstream.XStream;


public class Response 
{	
	
	private static XStream xstream;
	static{
		xstream = new XStream();
		xstream.alias("response", Response.class);	
	}
	
	public static Response FromXML(String xml)
	{		
		return (Response)xstream.fromXML(xml);		
	}	
	public String ToXML()
	{		
		return xstream.toXML(this);	
	}
	
	
	private ResponseType type;
	private String message;
	
	public ResponseType getType()
	{
		return type;
	}
	public void setType(ResponseType value)
	{
		type = value;
	}
	
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String value)
	{
		message = value;
	}

}
