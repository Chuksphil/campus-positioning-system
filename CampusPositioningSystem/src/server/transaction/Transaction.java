package server.transaction;

import java.io.FileNotFoundException;
import java.io.IOException;
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

public class Transaction 
{	
	public Transaction()
	{
	
	}

	/**
	 * process messages according to the protocol
	 * @param theInput the request from the client
	 * @return response to send back
	 * @throws Exception 
	 */
	public Response processInput(Request request) throws Exception 
	{			
		
		String roomTag = request.getRoomTag();
		String roomNumber = request.getRoomNumber();
		ArrayList<AccessPoint> aps = request.accessPoints();
		
		
		AssistantServerBank assistanceBank = TransactionServer.GetAssistantServerBank();
		
		
		
		
		boolean gotLocation = false;
		String roomID = "";		
		while (gotLocation == false)
		{
			LocationAssistantServer locationServer = assistanceBank.GetNextLocationAssistantServer();
			if (locationServer == null)
			{
				Response resp = new Response();
				resp.setType(ResponseType.ServerOutError);
				resp.setMessage("No locations servers responsing.");
				return resp;
			}
			try
			{						
				roomID = locationServer.GetRoomID(roomNumber, roomTag);
				if (roomID == "")
				{
					Response resp = new Response();
					resp.setType(ResponseType.RoomNotFound);
					resp.setMessage("No room found with that number.");
					return resp;					
				}
				gotLocation = true;
			}
			catch(Exception e)
			{
				assistanceBank.ReportBadServer(locationServer);
			}
		}
		
		
		
		
		boolean gotPosition = false;
		DPoint position = null;		
		while (gotPosition == false)
		{
			PositionAssistantServer positionServer = assistanceBank.GetNextPositionAssistantServer();
			if (positionServer == null)
			{
				Response resp = new Response();
				resp.setType(ResponseType.ServerOutError);
				resp.setMessage("No position servers responsing.");
				return resp;
			}
			try
			{						
				position = positionServer.GetPosition(aps);
				gotPosition = true;
			}
			catch(Exception e)
			{
				assistanceBank.ReportBadServer(positionServer);
			}
		}

		
		
		
		boolean gotNavigation = false;
		String path = "";		
		while (gotNavigation == false)
		{
			NavigationAssistantServer navigationServer = assistanceBank.GetNextNavigationAssistantServer();
			if (navigationServer == null)
			{
				Response resp = new Response();
				resp.setType(ResponseType.ServerOutError);
				resp.setMessage("No navigation servers responsing.");
				return resp;
			}
			try
			{						
				path = navigationServer.GetPath(roomID, position);
				gotNavigation = true;
			}
			catch(Exception e)
			{
				assistanceBank.ReportBadServer(navigationServer);
			}
		}				
		
				
		
		Response resp = new Response();
		resp.setType(ResponseType.OK);
		resp.setMessage(path);
		
		
		return resp;
	}
	
}
