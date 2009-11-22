package setup;

public class NavEdge 
{
	private String id;
	private String from;
	private String to;
	
	
	public NavEdge(String id, String from, String to) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	
	
	public String makeInsertSQL()
	{
		return "insert into wps.nav_edges values ('" + id + "','" + from + "','" + to + "')";
	}
	
	
	
}
