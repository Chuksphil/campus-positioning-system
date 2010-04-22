package server.navigation;




import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.PriorityQueue;

import setup.path.Edge;
import setup.path.Node;



public class Graph 
{
	private Hashtable<String, Node> nodes = new Hashtable<String, Node>();
	
	public Collection<Node> getNodes()
	{
		return nodes.values();
	}
	
	public Node getNodeByID(String id)
	{
		return nodes.get(id);
	}

	public void load(Connection conn) throws FileNotFoundException, UnsupportedEncodingException, SQLException
	{				
	
		PreparedStatement getNodes = conn.prepareStatement("SELECT * FROM wps.nav_nodes;");
		ResultSet getNodesResult  = getNodes.executeQuery();		
		while (getNodesResult.next())
		{
			String id = getNodesResult.getString("id");		
			double latitude = getNodesResult.getDouble("lat");
			double longitude = getNodesResult.getDouble("long");
			
			Node newNode = new Node(latitude, longitude);
			nodes.put(id, newNode);
		}
		
		
		PreparedStatement getEdges = conn.prepareStatement("SELECT * FROM wps.nav_edges;");
		ResultSet getEdgesResult  = getEdges.executeQuery();		
		while (getEdgesResult.next())
		{			
			String from = getEdgesResult.getString("from");
			String to = getEdgesResult.getString("to");
			
			Node fromNode = nodes.get(from);
			Node fromTo= nodes.get(to);
			
			fromNode.getOutgoing().add(new Edge(fromNode, fromTo));
			fromTo.getOutgoing().add(new Edge(fromTo, fromNode));
		}
		
	}
	
	
	
	public Path dijkstra(Node from, Node to)
	{		
		Hashtable<Node, Path> paths = new Hashtable<Node, Path>();		
		NodeComparer comparer = new NodeComparer();		
		PriorityQueue<Node> q = new PriorityQueue<Node>(nodes.size(), comparer);		
		
		
		for(Node n : nodes.values())
		{			
			paths.put(n, new Path());
			
			comparer.setDistance(n, Double.POSITIVE_INFINITY);
			if (n == from)
			{
				comparer.setDistance(n, 0.0f);
			}
			
			q.add(n);
		}		
		
				
		while (q.size() > 0)
		{	
			Node u = q.remove();
			double distToU = comparer.getDistance(u);
			Path pathToU = paths.get(u);
			
			if (u == to)
			{
				return pathToU;
			}
			
			for(Edge e : u.getOutgoing())
			{
				Node v = e.getTo();
				
				double alt = distToU + e.getDistance();
				if (alt < comparer.getDistance(v))
				{
					comparer.setDistance(v, alt);
					
					//must add and remove v to put in the correct place in the queue
					q.remove(v);
					q.add(v);
					
					paths.put(v, pathToU.Plus(e));
				}
			}
		}
		
		
		return null;
	}
	
	
	
}
