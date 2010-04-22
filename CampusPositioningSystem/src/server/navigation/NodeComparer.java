package server.navigation;

import java.util.Comparator;
import java.util.Hashtable;

import setup.path.Node;

public class NodeComparer implements Comparator<Node> 
{
	private Hashtable<Node, Double> distances = new Hashtable<Node, Double>();
	
	public void setDistance(Node node, double dist)
	{
		distances.put(node, dist);
	}
	
	public double getDistance(Node node)
	{
		return distances.get(node);
	}
	
	public int compare(Node o1, Node o2) 
	{
		double n1Dist = 	distances.get(o1);
		double n2Dist = 	distances.get(o2);
		
		if (n1Dist < n2Dist)
		{
			return -1;
		}
		else if (n1Dist > n2Dist)
		{
			return 1;
		}
		else 
		{
			return 0;
		}
	}

}
