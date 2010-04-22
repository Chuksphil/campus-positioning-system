package server.locations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import setup.rooms.Room;


public class RoomsInfo 
{
	private Hashtable<String, String> tagToNodeID = new Hashtable<String, String>();
	
	
	public void load(Connection conn) throws SQLException
	{
		
		PreparedStatement getRooms = conn.prepareStatement("SELECT * FROM wps.rooms;");
		ResultSet getRoomsResult  = getRooms.executeQuery();		
		while (getRoomsResult.next())
		{
			String navNodeId = getRoomsResult.getString("nav_nodes_id");		
			String tag = getRoomsResult.getString("tag");			
			
			tagToNodeID.put(tag, navNodeId);
		}
				
		
		
	}
	
	
	public String getNodeID(String roomTag)
	{	
		if (tagToNodeID.containsKey(roomTag) == false)
		{
			return "";
		}		
		return tagToNodeID.get(roomTag);		
	}

}
