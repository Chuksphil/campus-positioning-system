package server.transaction;

import java.util.ArrayList;

import message.Request;
import message.Response;
import message.ResponseType;
import util.AccessPoint;
import util.DPoint;

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
		ArrayList<AccessPoint> aps = request.accessPoints();
		
		
		AssistantServerBank assistanceBank = TransactionServer.GetAssistantServerBank();
		
		
		
		
		boolean gotLocation = false;
		String roomNodeID = "";		
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
				roomNodeID = locationServer.GetRoomNodeID(roomTag);
				if (roomNodeID == "" || roomNodeID == null)
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
		String positionNodeID = null;		
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
				positionNodeID = positionServer.GetPositionNodeID(aps);				
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
				path = navigationServer.GetPath(positionNodeID, roomNodeID);
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
