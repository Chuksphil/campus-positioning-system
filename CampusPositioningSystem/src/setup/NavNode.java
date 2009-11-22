package setup;

public class NavNode 
{
	private String id;
	private String latitude;
	private String longitude;
	private String floor;
	
	
	public NavNode(String id, String latitude, String longitude, String floor) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.floor = floor;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}

	
	
	public String makeInsertSQL()
	{
		return "insert into wps.nav_nodes values ('" + id + "','" + latitude + "','" + longitude + "','" + floor + "')";
	}
	
	
	
}
