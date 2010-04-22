package setup.path;

import java.util.ArrayList;
import java.util.UUID;


public class Node 
{
	
	public Node(double latitude, double longitude) 
	{	
		this.id = UUID.randomUUID().toString();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	private String id; 
	private double longitude;
	private double latitude;
	
	private ArrayList<Edge> outgoing = new ArrayList<Edge>();
	
	public ArrayList<Edge> getOutgoing()
	{
		return outgoing;
	}
				
	
	public String getID() {
		return id;
	}
	public double getLongitude() {
		return longitude;
	}
	public double getLatitude() {
		return latitude;
	}
}
