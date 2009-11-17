package server.navigation;

import java.util.ArrayList;

public class Node 
{
	
	public Node(double longitude, double latitude) 
	{	
		this.longitude = longitude;
		this.latitude = latitude;		
		this.roomID = -1;
	}
	
	public Node(double longitude, double latitude, int roomID) 
	{			
		this.longitude = longitude;
		this.latitude = latitude;
		this.roomID = roomID;
	}

	private double longitude;
	private double latitude;
	
	private int roomID;	
	private ArrayList<Edge> outgoing = new ArrayList<Edge>();
	
	
	
	public double getLongitude() {
		return longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public int getRoomID() {
		return roomID;
	}
	public ArrayList<Edge> getOutgoing() {
		return outgoing;
	}
}
