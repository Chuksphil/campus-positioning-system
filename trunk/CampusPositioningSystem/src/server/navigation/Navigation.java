package server.navigation;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

import setup.path.Node;


import com.vividsolutions.jts.io.ParseException;

public class Navigation {

	private static Graph graph = new Graph();
			
	public Navigation(Connection conn) throws FileNotFoundException, UnsupportedEncodingException, ParseException, SQLException
	{		
		graph.load(conn);		
	}
	
	public Path getPath(String startNodeID, String endNodeID)
	{
		Node n1 = graph.getNodeByID(startNodeID);		
		Node n2 = graph.getNodeByID(endNodeID);
		return graph.dijkstra(n1, n2);
	}
		
	
}
