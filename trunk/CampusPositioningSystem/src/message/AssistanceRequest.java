package message;

import java.util.ArrayList;

import util.AccessPoint;

import com.thoughtworks.xstream.XStream;



public class AssistanceRequest
{	
	private static XStream xstream;
	static
	{
		xstream = new XStream();
		xstream.alias("PositionRequest", AssistanceRequest.class);
		xstream.alias("accesspoint", AccessPoint.class);
	}

	public static AssistanceRequest FromXML(String xml)
	{			
		return (AssistanceRequest)xstream.fromXML(xml);		
	}	
	public String ToXML()
	{
		return xstream.toXML(this);	
	}
	
	

	private ServerType serverType;
	

	public ServerType getServerType()
	{
		return serverType;
	}
	public void setServerType(ServerType value)
	{
		serverType = value;
	}
	

}

