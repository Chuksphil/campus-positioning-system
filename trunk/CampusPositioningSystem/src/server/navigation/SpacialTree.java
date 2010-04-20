package server.navigation;

import java.util.Properties;
import java.util.Vector;

import org.deegree.io.rtree.HyperBoundingBox;
import org.deegree.io.rtree.HyperPoint;
import org.deegree.io.rtree.RTree;
import org.deegree.io.rtree.RTreeException;



public class SpacialTree 
{
	private Vector<Node> nodes = new Vector<Node>();
	private RTree rtree;
	
	public SpacialTree()
	{				
		try 
		{
			rtree = new RTree(2, 50);
		}
		catch (RTreeException e)
		{
		}		
	}
	
	public void add(Node n)
	{
		float latitude = (float)n.getLatitude();
		float longitude = (float)n.getLongitude();
		
		HyperPoint point = new HyperPoint(new double[]{latitude, longitude});
		HyperBoundingBox rect = new HyperBoundingBox(point, point);
		
				
		//get the id for the node to be added
		int nextID = nodes.size();
		
		try 
		{
			rtree.insert(nextID, rect);
		} catch (RTreeException e) {}
		
		//add node to vector of position nextID
		nodes.add(n);
	}
	
	public Node nearest(float longitude, float latitude)
	{
		HyperPoint point = new HyperPoint(new double[]{latitude, longitude});
						
		double[] nearestResults = null;
		try 
		{
			nearestResults = rtree.nearestNeighbour(point);
		} catch (RTreeException e) {}
		
		
		int nearestID = (int)nearestResults[1];
		
		return nodes.get(nearestID);
	}
	
		
	
}
