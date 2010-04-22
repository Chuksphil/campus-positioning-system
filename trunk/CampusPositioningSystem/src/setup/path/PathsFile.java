package setup.path;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import util.AccessPoint;


import com.thoughtworks.xstream.XStream;


public class PathsFile
{	
	private static XStream xstream;
	static
	{
		xstream = new XStream();
		xstream.alias("PathsFile", PathsFile.class);
		xstream.alias("Node", Node.class);
		xstream.alias("Edge", Edge.class);
	}
	
	
	
	public static PathsFile FromFile(String file)
	{			
		String xml;
		try {
			byte[] buffer = new byte[(int) new File(file).length()];
			BufferedInputStream f = new BufferedInputStream(new FileInputStream(file));
			f.read(buffer);
			xml = new String(buffer);
			return (PathsFile)xstream.fromXML(xml);	
		} catch (Exception e) {
			return new PathsFile();
		}		
	}
	public static PathsFile FromXML(String xml)
	{			
		return (PathsFile)xstream.fromXML(xml);		
	}
	
	
	public String ToXML()
	{
		return xstream.toXML(this);	
	}
	public void ToFile(String file)
	{			
		try {
			String xml = this.ToXML();
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(xml);    
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	
	
	
	public ArrayList<Node> GetNodes() {
		return nodes;
	}

	public ArrayList<Edge> GetEdges() {
		return edges;
	}
	
	
	public String GetWKT()
	{
		String toRet = "";
		for(Node node : nodes)
		{
			toRet += "POINT (" + node.getLatitude() + " " + node.getLongitude() + ")";
		}
		for(Edge edge : edges)
		{
			toRet += "LINESTRING (" + edge.getTo().getLatitude() + " " + edge.getTo().getLongitude() + " , " + edge.getFrom().getLatitude() + " " + edge.getFrom().getLongitude() + ")";
		}
		return toRet;
	}
	
	public Node NearestNode(double latitude, double longitude)
	{
		double nearest = Double.MAX_VALUE;
		Node toRet = null; 
		for(Node node : nodes)
		{
			double dist = Math.sqrt(Math.pow(latitude - node.getLatitude(), 2.0) + Math.pow(longitude - node.getLongitude(), 2.0));
			if (dist < nearest)
			{
				nearest = dist;
				toRet = node;
			}
		}
		
		return toRet;
	}
}
