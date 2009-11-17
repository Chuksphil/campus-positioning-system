package server.navigation;

public class Edge 
{
	public Edge(Node from, Node to)
	{
		this.from = from;
		this.to = to;
	}
	
	private double distance;
	private String description; //for now North South East West	
	private Node from;
	private Node to;
	
	
	public double getDistance() {
		
		double lat1 = to.getLatitude();
		double lat2 = from.getLatitude();
		double long1 = to.getLongitude();
		double long2 = from.getLongitude();		
		return Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(long1 - long2, 2));		
	}
	public String getDescription() 
	{
		double lat1 = to.getLatitude();
		double lat2 = from.getLatitude();
		double long1 = to.getLongitude();
		double long2 = from.getLongitude();	
		
		Double angle = Math.atan2(lat1 - lat2, long1 - long2) * (180.0/Math.PI);
		while (angle < 0)
		{
			angle += 360;
		}
		
		if (angle > 22.5 + 45*0 && angle < 22.5 + 45*1)
		{
			return "North East";
		}
		else if (angle > 22.5 + 45*1 && angle < 22.5 + 45*2)
		{
			return "North";
		}
		else if (angle > 22.5 + 45*2 && angle < 22.5 + 45*3)
		{
			return "North West";
		}
		else if (angle > 22.5 + 45*3 && angle < 22.5 + 45*4)
		{
			return "West";
		}
		else if (angle > 22.5 + 45*4 && angle < 22.5 + 45*5)
		{
			return "South West";
		}
		else if (angle > 22.5 + 45*5 && angle < 22.5 + 45*6)
		{
			return "South";
		}
		else if (angle > 22.5 + 45*6 && angle < 22.5 + 45*7)
		{
			return "South East";
		}
		else
		{
			return "East";
		}
		
	}
	public Node getFrom() {
		return from;
	}
	public Node getTo() {
		return to;
	}
}
