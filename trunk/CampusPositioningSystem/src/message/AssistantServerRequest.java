package message;


import com.thoughtworks.xstream.XStream;



public class AssistantServerRequest
{	
	private static XStream xstream;
	static
	{
		xstream = new XStream();
		xstream.alias("AssistantServerRequest", AssistantServerRequest.class);
	}

	public static AssistantServerRequest FromXML(String xml)
	{			
		return (AssistantServerRequest)xstream.fromXML(xml);		
	}	
	public String ToXML()
	{
		return xstream.toXML(this);	
	}
	
	
	private ServerType type;
		
		
	
	public ServerType getServerType()
	{
		return type;
	}
	public void setServerType(ServerType value)
	{
		type = value;
	}
	


}

