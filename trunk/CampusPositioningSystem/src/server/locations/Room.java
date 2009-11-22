package server.locations;

public class Room 
{
	private String id;
	private String roomnumber;
	private String nodeId;
	
	
	public Room(String id, String roomnumber, String nodeId) {
		super();
		this.id = id;
		this.roomnumber = roomnumber;
		this.nodeId = nodeId;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoomnumber() {
		return roomnumber;
	}
	public void setRoomnumber(String roomnumber) {
		this.roomnumber = roomnumber;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	
}
