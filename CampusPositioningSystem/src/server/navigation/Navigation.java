package server.navigation;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;


import com.vividsolutions.jts.io.ParseException;

public class Navigation {

	private static Graph graph = new Graph();
	private static SpacialTree rTree = new SpacialTree();
			
	public Navigation(Connection conn) throws FileNotFoundException, UnsupportedEncodingException, ParseException, SQLException
	{		
		graph.load(conn);
		
		for (Node n : graph.getNodes())
		{
			rTree.add(n);
		}
	}
	
	public Path getPath(double longStart, double latStart, String endNodeID)
	{
		Node n1 = rTree.nearest((float)longStart, (float)latStart);		
		Node n2 = graph.getNodeByID(endNodeID);
		return graph.dijkstra(n1, n2);
	}
		
	
}
