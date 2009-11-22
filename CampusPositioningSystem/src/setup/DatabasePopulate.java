package setup;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.ConnectionParameters;
import server.navigation.Edge;


import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;


public class DatabasePopulate 
{

	public static void populate(String pathsFile, String roomsFile) throws SAXException, IOException, ParserConfigurationException, ParseException, SQLException, ClassNotFoundException
	{
		String floor = "3";
		
		Hashtable<String, NavNode> nodesByLoc = new Hashtable<String, NavNode>();
		ArrayList<NavEdge> edges = new ArrayList<NavEdge>();
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		FileInputStream fis = new FileInputStream(pathsFile);
		InputStreamReader in = new InputStreamReader(fis, "UTF-8");		
		WKTReader reader = new WKTReader();
		
		Geometry geo = reader.read(in);
		while (geo != null)
		{		
			NavNode prevNode = null;
			
			for(Coordinate cor : geo.getCoordinates())
			{				
				Double x = (double)cor.x;
				Double y = (double)cor.y;				
				
				String corString = x.toString() + "_" + y.toString();
				
				NavNode n;				
				if (nodesByLoc.containsKey(corString))
				{
					n = nodesByLoc.get(corString); 					
				}
				else
				{
					n = new NavNode(UUID.randomUUID().toString(), x.toString(), y.toString(), floor);					
					nodesByLoc.put(corString, n); 	
				}
				
				if (prevNode != null)
				{	
					edges.add( new NavEdge(UUID.randomUUID().toString(), prevNode.getId(), n.getId()) );
					edges.add( new NavEdge(UUID.randomUUID().toString(), n.getId(), prevNode.getId()) );					
				}
				prevNode = n;				
			}
			
			
			geo = reader.read(in);
		}
		
		
		
		
		
		
		
		
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(roomsFile);
		
		
		NodeList features = doc.getElementsByTagName("feature");
		for (int i = 0; i < features.getLength(); i++) 
		{
			Element feature = (Element)features.item(i);
			
			Node cordNode = feature.getElementsByTagName("gml:coordinates").item(0);
			Node roomNode = feature.getElementsByTagName("property").item(5);
			
			String room = roomNode.getChildNodes().item(0).getNodeValue().trim();
			String cord = cordNode.getChildNodes().item(0).getNodeValue().trim();									
			cord = "POLYGON ((" + cord.replace(',', ' ').replace('\n', ',') + "))";
			
			if (room.length() != 0)
			{
								
				//System.out.println( "'" + room + "'");
				//System.out.println( "'" + cord + "'");
				
				Geometry roomPoly = reader.read(cord);
				
				
				NavNode roomNavNode = null;				
				
				for(NavNode node : nodesByLoc.values())
				{
					String nodePointString = "POINT (" + node.getLatitude() + " " + node.getLongitude() + ")";					
					Geometry roomPoint = reader.read(nodePointString);
					
					if (roomPoly.intersects(roomPoint))
					{						
						roomNavNode = node;
						break;
					}					
				}
				
				if (roomNavNode != null)
				{
					rooms.add(new Room(UUID.randomUUID().toString(), room, roomNavNode.getId()));					
				}
							
			}			
			
			
		}
		
		
		Connection conn = ConnectionParameters.getConnection();
		
		conn.prepareStatement("DELETE FROM wps.rooms;").execute();
		conn.prepareStatement("DELETE FROM wps.nav_edges;").execute();
		conn.prepareStatement("DELETE FROM wps.nav_nodes;").execute();
		
		for (NavNode n : nodesByLoc.values())
		{
			String query = n.makeInsertSQL();
			conn.prepareStatement(query).execute();			
		}
		for (NavEdge e : edges)
		{
			String query = e.makeInsertSQL();
			conn.prepareStatement(query).execute();			
		}
		for (Room r : rooms)
		{
			String query = r.makeInsertSQL();
			conn.prepareStatement(query).execute();			
		}
		
		conn.commit();
		conn.close();
		
		
		
		
		
		
		
		
	}
	
	public static void main(String[] args) throws IOException, ParseException, SAXException, ParserConfigurationException, SQLException, ClassNotFoundException
    {	
		String paths = "/home/innominate/Documents/School/DatabaseTechnologies/proj/nav_graphs/floor3graph.wkt";
		String rooms = "/home/innominate/Desktop/153_3_Building_polygons.jml";
		
		populate(paths, rooms);
		
    }
	
}
