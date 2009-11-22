package server.locations;

import java.sql.Connection;
import java.sql.SQLException;

public class Locations 
{
	private Rooms rooms;
	
	public Locations(Connection conn) throws SQLException
	{
		rooms = new Rooms();
		rooms.load(conn);		
	}
	
	public boolean IsValidRoomNumber(String roomNumber)
	{
		return (rooms.getRoomByNumber(roomNumber) != null);
	}
	
	public boolean IsValidRoomTag(String roomTag)
	{
		return (rooms.getRoomByTag(roomTag) != null);
	}
	
	
	
	public String GetRoomNumer(String roomTag)
	{
		return rooms.getRoomByTag(roomTag).getRoomnumber();
	}

	public String GetNodeIDByTag(String roomTag)
	{
		return rooms.getRoomByTag(roomTag).getNodeId();
	}

	public String GetNodeIDByNumber(String roomNumber)
	{
		return rooms.getRoomByNumber(roomNumber).getNodeId();
	}
}
