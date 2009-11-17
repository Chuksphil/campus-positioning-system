package server.navigation;

import java.util.ArrayList;

public class Path 
{
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	
	public ArrayList<Edge> getEdges() {
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
	
}
