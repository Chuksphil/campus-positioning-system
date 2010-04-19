package server.transaction;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.vividsolutions.jts.io.ParseException;

import server.locations.Locations;
import server.navigation.Navigation;
import server.navigation.Path;
import server.position.Position;
import util.DPoint;

import message.AccessPoint;
import message.Request;
import message.Response;
import message.ResponseType;

public class CopyOfProtocol 
{
	private Locations locationDB;
	private Navigation navigationDB;
	private Position positionDB;
	
	public CopyOfProtocol()
	{
	
	}
	
	public CopyOfProtocol(Connection conn) throws SQLException, FileNotFoundException, UnsupportedEncodingException, ParseException
	{
		locationDB = new Locations(conn);
		navigationDB = new Navigation(conn);
		positionDB = new Position();
	}

	/**
	 * process messages according to the protocol
	 * @param theInput the request from the client
	 * @return response to send back
	 * @throws Exception 
	 */
	public Response processInput(Request request, Connection conn) throws Exception 
	{			
		Response toRet = new Response();
		
		String roomTag = request.getRoomTag();
		String roomNumber = request.getRoomNumber();
		String userID = request.getUserID();
		String destinationNodeID;
		
		
		
		if (roomNumber == "" && roomTag == "")
		{ //no room number or room tag try to get destinationNodeID from user id
			
			//TODO: try to get room number
			toRet.setType(ResponseType.UnknownUser);
			toRet.setMessage("User not in system");
			return toRet;
		}
		else if (roomNumber == "")
		{ //no room number was provided try to get destinationNodeID from tag
			
			//search for the dest by tag
			if (locationDB.IsValidRoomTag(roomTag))
			{
				destinationNodeID = locationDB.GetNodeIDByTag(roomTag);				
			}
			else
			{
				toRet.setType(ResponseType.TagNotFound);
				toRet.setMessage("Tag not found");
				return toRet;
			}						
		}
		else
		{ //no room number was provided
			
			//search for the dest by room num
			if (locationDB.IsValidRoomNumber(roomNumber))
			{
				destinationNodeID = locationDB.GetNodeIDByNumber(roomNumber);
			}
			else
			{				
				toRet.setType(ResponseType.RoomNotFound);
				toRet.setMessage("Room not found");
				return toRet;
			}			
		}
		
		
		ArrayList<String> ap_macs = new ArrayList<String>();
		ArrayList<Integer> ap_strenght = new ArrayList<Integer>();
		for(AccessPoint ap : request.accessPoints())
		{
			ap_macs.add(ap.getMacAdress());
			ap_strenght.add(ap.getSignalStrenght());			
		}		
		DPoint pos = positionDB.getPosition(conn, ap_macs, ap_strenght);
		
		
		Path path = navigationDB.getPath(pos.getY(), pos.getX(), destinationNodeID);
				
		
		Response rep = new Response();
		rep.setType(ResponseType.OK);
		rep.setMessage(path.GetPointsString());
		
		
		return rep;
	}
	
}
