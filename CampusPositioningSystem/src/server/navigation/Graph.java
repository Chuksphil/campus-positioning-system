package server.navigation;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;


public class Graph 
{
	private ArrayList<Node> nodes = new ArrayList<Node>();
	
	public ArrayList<Node> getNodes() 
	{
		return nodes;
	}

	public void load(String file) throws FileNotFoundException, UnsupportedEncodingException, ParseException
	{		
		Hashtable<String, Node> nodesByLoc = new Hashtable<String, Node>();
		
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader in = new InputStreamReader(fis, "UTF-8");		
		WKTReader reader = new WKTReader();
		
		
		
		Geometry geo = reader.read(in);
		while (geo != null)
		{		
			Node prevNode = null;
			
			for(Coordinate cor : geo.getCoordinates())
			{				
				Double x = (double)cor.x;
				Double y = (double)cor.y;				
				
				String corString = x.toString() + "_" + y.toString();
				
				Node n;				
				if (nodesByLoc.containsKey(corString))
				{
					n = nodesByLoc.get(corString); 					
				}
				else
				{
					n = new Node(x, y);
					nodes.add(n);
					nodesByLoc.put(corString, n); 	
				}
				
				if (prevNode != null)
				{
					prevNode.getOutgoing().add(new Edge(prevNode, n));
					n.getOutgoing().add(new Edge(n, prevNode));
				}
				prevNode = n;				
			}
			
			
			geo = reader.read(in);
		}
		
		
		
		
	}
	
	public Path dijkstra(Node from, Node to)
	{		
		Hashtable<Node, Path> paths = new Hashtable<Node, Path>();		
		NodeComparer comparer = new NodeComparer();		
		PriorityQueue<Node> q = new PriorityQueue<Node>(nodes.size(), comparer);		
		
		
		for(Node n : nodes)
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
