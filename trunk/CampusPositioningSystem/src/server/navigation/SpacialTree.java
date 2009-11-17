package server.navigation;

import java.util.Properties;
import java.util.Vector;

import com.infomatiq.jsi.IntProcedure;
import com.infomatiq.jsi.Point;
import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.rtree.RTree;

public class SpacialTree 
{
	private Vector<Node> nodes = new Vector<Node>();
	private RTree rtree = new RTree();
	
	public SpacialTree()
	{			
		rtree.init(new Properties());
	}
	
	public void add(Node n)
	{
		float latitude = (float)n.getLatitude();
		float longitude = (float)n.getLongitude();
		
		Rectangle rect = new Rectangle(longitude, latitude, longitude, latitude);		
		
		//get the id for the node to be added
		int nextID = nodes.size();		
		rtree.add(rect, nextID);
		
		//add node to vector of position nextID
		nodes.add(n);
	}
	
	public Node nearest(float longitude, float latitude)
	{
		Point point = new Point(longitude, latitude);
		
		GetOneProcedure getOne = new GetOneProcedure();		
		rtree.nearest(point, getOne, Float.MAX_VALUE);
		int nearestID = getOne.getId();
		
		return nodes.get(nearestID);
	}
	
	
	private class GetOneProcedure implements IntProcedure
	{
		private int id;
		public int getId() {
			return id;
		}		
		public boolean execute(int id) {
			this.id = id;
			//prevents further execution (we just want one)
			return false;
		}	
	}
	
	
}
