package locationdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.ConnectionParameters;

public class RoomInfo 
{
	/**
	 * Returns a list of tags similar to the one passed
	 * @param tag tag to search for
	 * @param conn connection to use
	 * @return list of similar tags, could be empty
	 * @throws SQLException
	 */
	public static ArrayList<String> SeachTags(String tag, Connection conn) throws SQLException
	{
		ArrayList<String> toRet = new ArrayList<String>();
		
		String query = "select ri.room_tag from wps.room_info ri where ri.room_tag LIKE ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, "%" + tag + "%");
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			toRet.add(rs.getString("room_tag"));
		}		
		return toRet;		
	}
	
	/**
	 * Returns room number for the tag passed
	 * @param tag tag to search for
	 * @param conn connection to use
	 * @return room number or "" if none found
	 * @throws SQLException
	 */
	public static String findRoomNumber(String tag, Connection conn) throws SQLException
	{
		String query = "select ri.room_number from wps.room_info ri where ri.room_tag = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, tag);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			return rs.getString("room_number");
		}		
		return "";		
	}
	
	
	/**
	 * Tests RoomInfo class functionality
	 * @param args
	 */
	public static void main(String[] args){
		try{
			Connection conn = ConnectionParameters.getPostgisConnection();
			
			String roomNum = findRoomNumber("Lab", conn);			
			System.out.println("FindRoomNumber Results: " + roomNum);
			
			ArrayList<String> searchTags = SeachTags("La", conn);
			System.out.print("SearchTags Results: ");
			for(String tag : searchTags)
			{
				System.out.print(tag + ", ");	
			}
			System.out.println();
			
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
