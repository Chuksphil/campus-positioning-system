package server.locations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;


public class Rooms 
{
	private Hashtable<String, Room> roomsByNumber = new Hashtable<String, Room>();
	private Hashtable<String, Room> roomsByTag = new Hashtable<String, Room>();
	private Hashtable<String, Room> roomsByID = new Hashtable<String, Room>();
	
	
	public void load(Connection conn) throws SQLException
	{
		
		PreparedStatement getRooms = conn.prepareStatement("SELECT * FROM wps.rooms;");
		ResultSet getRoomsResult  = getRooms.executeQuery();		
		while (getRoomsResult.next())
		{
			String id = getRoomsResult.getString("id");		
			String roomnumber = getRoomsResult.getString("number");
			String nodeId = getRoomsResult.getString("node");
			
			Room room = new Room(id, roomnumber, nodeId);
			roomsByID.put(id, room);
			roomsByNumber.put(roomnumber, room);
		}
				
		
		PreparedStatement getRoomTags= conn.prepareStatement("SELECT * FROM wps.room_tags;");
		ResultSet getRoomTagsResults  = getRoomTags.executeQuery();		
		while (getRoomTagsResults.next())
		{
			String tag = getRoomTagsResults.getString("tag");		
			String roomId = getRoomTagsResults.getString("room_id");			
			
			Room room = roomsByID.get(roomId);
			roomsByTag.put(tag, room);
		}
		
	}
	
	
	public Room getRoomByNumber(String roomNumber)
	{		
		return roomsByNumber.get(roomNumber);		
	}

	
	public Room getRoomByTag(String roomNumber)
	{
		return roomsByTag.get(roomNumber);		
	}
}
