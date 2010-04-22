package setup.dbpopulate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import setup.fingerprinting.FingerPrint;
import setup.fingerprinting.FingerPrintFile;
import setup.path.Edge;
import setup.path.Node;
import setup.path.PathsFile;
import setup.rooms.Room;
import setup.rooms.RoomsFile;
import util.AccessPoint;

public class PopulateDatabase {

	public static void Populate(Connection conn, FingerPrintFile fingerPrintFile, PathsFile pathsFile, RoomsFile roomsFile)
	{
		try {
			conn.prepareStatement("DELETE FROM wps.rooms").execute();
			conn.prepareStatement("DELETE FROM wps.finger_print_access_points").execute();
			conn.prepareStatement("DELETE FROM wps.finger_prints").execute();
			conn.prepareStatement("DELETE FROM wps.nav_edges").execute();
			conn.prepareStatement("DELETE FROM wps.nav_nodes").execute();
			
			
			
			for(Node node : pathsFile.GetNodes())
			{
				String insertNode = "INSERT INTO wps.nav_nodes VALUES ('" + node.getID() + "'," + node.getLatitude() + "," + node.getLongitude() + ")";
				conn.prepareStatement(insertNode).execute();			
			}
			

			for(Edge edge : pathsFile.GetEdges())
			{	
				String insertEdge = "INSERT INTO wps.nav_edges VALUES ('" + UUID.randomUUID().toString() + "','" + edge.getFrom().getID() + "','" + edge.getTo().getID() + "')";
				conn.prepareStatement(insertEdge).execute();			
			}
			
			
			for(FingerPrint fp : fingerPrintFile.GetFingerPrints())
			{
				Node nearestNode = null;
				double nearestDist = Double.MAX_VALUE;
				for(Node node : pathsFile.GetNodes())
				{
					double dist = Math.sqrt(Math.pow(node.getLatitude() - fp.getLatitude(), 2.0) + Math.pow(node.getLongitude() - fp.getLongitude(), 2.0));
					if (dist < nearestDist)
					{
						nearestDist = dist;
						nearestNode = node;
					}
				}
				
				
				String fpId = UUID.randomUUID().toString();
				String insertFingerPrint = "INSERT INTO wps.finger_prints VALUES ('" + fpId + "','" + nearestNode.getID() + "')";
				conn.prepareStatement(insertFingerPrint).execute();	
				
				for(AccessPoint ap : fp.getAps())
				{
					String insertFingerPrintAccessPoints = "INSERT INTO wps.finger_print_access_points VALUES ('" + UUID.randomUUID().toString() + "','" + fpId + "','" + ap.getMacAdress() + "'," + ap.getSignalStrenght() +")";
					conn.prepareStatement(insertFingerPrintAccessPoints).execute();
				}			
			}
			
			for(String roomNodeID : roomsFile.GetRooms().keySet())
			{
				Room room = roomsFile.GetRooms().get(roomNodeID);
				if (room.getTags() != "" && room.getTags() != null)
				{
					for (String roomTag : room.getTags().split(","))
					{
						String insertFingerPrint = "INSERT INTO wps.rooms VALUES ('" + UUID.randomUUID().toString() + "','" + roomNodeID + "','" + roomTag + "')";
						conn.prepareStatement(insertFingerPrint).execute();	
					}
				}
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
}
