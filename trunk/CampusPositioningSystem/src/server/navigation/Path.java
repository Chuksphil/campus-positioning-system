package server.navigation;

import java.util.ArrayList;

import setup.path.Edge;

public class Path 
{
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	
	public ArrayList<Edge> getEdges() 
	{
		return edges;
	}

	public Path Plus(Edge e)
	{
		Path ret = new Path();
		
		for(Edge curentEdge : edges)
		{
			ret.edges.add(curentEdge);
		}		
		ret.edges.add(e);
		
		return ret;
	}
	
	
	public String GetPointsString()
	{
		String ret = "LINESTRING (";
		
		Edge le = null;
		
		for(Edge e : edges)
		{	
			le = e;
			
			double x = e.getFrom().getLongitude();
			double y = e.getFrom().getLatitude();
			
			ret += (y + " " + x + ", ");
		}
		
		double x = le.getTo().getLongitude();
		double y = le.getTo().getLatitude();
		ret += (y + " " + x + ")");
		
		return ret;
	}
	
}
