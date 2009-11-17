package server.communication;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import server.locations.RoomInfo;

import message.AccessPoint;
import message.Request;
import message.Response;
import message.ResponseType;

public class Protocol 
{

	/**
	 * process messages according to the protocol
	 * @param theInput the request from the client
	 * @return response to send back
	 * @throws SQLException 
	 */
	public Response processInput(Request request, Connection con) throws SQLException 
	{			
		Response toRet = new Response();
		
		String roomTag = request.getRoomTag();
		String roomNumber = request.getRoomNumber();
		String userID = request.getUserID();
		
		
		//no room number or room tag try to get room number from user id
		if (roomNumber == "" && roomTag == "")
		{
			//TODO: try to get room number
			toRet.setType(ResponseType.UnknownUser);
			toRet.setMessage("User not in system");
			return toRet;
		}
		
		//no room number was provided use the room tag to determine the location
		if (roomNumber == "")
		{				
			//search for the rooom number
			roomNumber = RoomInfo.findRoomNumber(roomTag, con);
			
			//no room was found
			if (roomNumber == "")
			{
				ArrayList<String> similarTags = RoomInfo.SeachTags(roomTag, con);				
				toRet.setType(ResponseType.TagNotFound);
				toRet.setMessage("Tag was not found see similar tags");
				toRet.setCloseTags(similarTags);
				return toRet;
			}			
		}
		
		for(AccessPoint ap : request.accessPoints())
		{
			
			
		}
		
		
		Response rep = new Response();
		rep.setType(ResponseType.OK);
		rep.setMessage("Test123");
		
		
		return rep;
	}
	
}
