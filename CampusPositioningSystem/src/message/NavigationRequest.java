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
	
	
	private String roomNodeID;
	private String startNodeID;

	public String getRoomNodeID() {
		return roomNodeID;
	}
	public void setRoomNodeID(String roomNodeID) {
		this.roomNodeID = roomNodeID;
	}
	public String getStartNodeID() {
		return startNodeID;
	}
	public void setStartNodeID(String startNodeID) {
		this.startNodeID = startNodeID;
	}
		
	
	
	


}

