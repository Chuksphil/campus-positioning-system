package server.navigation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.vividsolutions.jts.io.ParseException;

public class Navigation {

	private static Graph graph = new Graph();
	private static SpacialTree rTree = new SpacialTree();
			
	public Navigation(String wktPathFile) throws FileNotFoundException, UnsupportedEncodingException, ParseException
	{		
		graph.load(wktPathFile);
		
		for (Node n : graph.getNodes())
		{
			rTree.add(n);
		}
	}
	
	public Path getPath(double longStart, double latStart, double longEnd, double latEnd)
	{
		Node n1 = rTree.nearest((float)longStart, (float)latStart);
		Node n2 = rTree.nearest((float)longEnd, (float)latEnd);
		return graph.dijkstra(n1, n2);
	}
	
	public static void main(String[] args) throws IOException, ParseException
    {		
				
		Navigation nav = new Navigation("/home/innominate/Documents/School/DatabaseTechnologies/proj/nav_graphs/floor3graph.wkt");		
		Path p = nav.getPath(2226855.784041974, 1373824.0661956766, 2226810.203628682, 1374297.2038930908);		
		
		Edge le = null;
						
		for(Edge e : p.getEdges())
		{
			le = e;
			
			double x = e.getFrom().getLongitude();
			double y = e.getFrom().getLatitude();
			
			//System.out.println(e.getDescription());
			
			System.out.print(x + " " + y + ", ");
		}
		
		double x2 = le.getTo().getLongitude();
		double y2 = le.getTo().getLatitude();				
		System.out.print(x2 + " " + y2 + ", ");
		
				
    }
	
}
