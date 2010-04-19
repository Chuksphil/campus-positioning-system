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
		Response toRet = new Response();
		
		String roomTag = request.getRoomTag();
		String roomNumber = request.getRoomNumber();
		ArrayList<AccessPoint> aps = request.accessPoints();
		
		
		AssistantServerBank assistanceBank = TransactionServer.GetAssistantServerBank();
		
		
		
		
		boolean gotLocation = false;
		String roomID = "";		
		while (gotLocation == false)
		{
			LocationAssistantServer locationServer = assistanceBank.GetNextLocationAssistantServer();
			try
			{						
				roomID = locationServer.GetRoomID(roomNumber, roomTag);
				gotLocation = true;
			}
			catch(IOException e)
			{
				assistanceBank.ReportBadServer(locationServer);
			}
		}
		
		
		
		
		boolean gotPosition = false;
		DPoint position = null;		
		while (gotPosition == false)
		{
			PositionAssistantServer positionServer = assistanceBank.GetNextPositionAssistantServer();
			try
			{						
				position = positionServer.GetPosition(aps);
				gotPosition = true;
			}
			catch(IOException e)
			{
				assistanceBank.ReportBadServer(positionServer);
			}
		}

		
		
		
		boolean gotNavigation = false;
		String path = "";		
		while (gotPosition == false)
		{
			NavigationAssistantServer navigationServer = assistanceBank.GetNextNavigationAssistantServer();
			try
			{						
				path = navigationServer.GetPath(roomID, position);
				gotNavigation = true;
			}
			catch(IOException e)
			{
				assistanceBank.ReportBadServer(navigationServer);
			}
		}				
		
				
		
		Response rep = new Response();
		rep.setType(ResponseType.OK);
		rep.setMessage(path);
		
		
		return rep;
	}
	
}
