package setup;

public class Room 
{
	private String id;
	private String number;
	private String node;
	
	
	public Room(String id, String number, String node) {
		super();
		this.id = id;
		this.number = number;
		this.node = node;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	

	
	public String makeInsertSQL()
	{
		return "insert into wps.rooms values ('" + id + "','" + number + "','" + node + "')";
	}	
	
	
	
	
	
}
