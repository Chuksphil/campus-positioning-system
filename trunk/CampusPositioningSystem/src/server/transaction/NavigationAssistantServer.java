package server.transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import message.AccessPoint;
import message.NavigationRequest;
import message.NavigationResponse;
import message.PositionRequest;
import message.PositionResponse;
import message.ServerType;
import util.DPoint;

public class NavigationAssistantServer extends AssistantServer {

	public NavigationAssistantServer(Socket socket, PrintWriter out, BufferedReader in) throws IOException
	{
		super(ServerType.Navigation, socket, out, in);
	}

	

	public synchronized String GetPath(String roomID, DPoint location) throws Exception
	{
		NavigationRequest navRequest = new NavigationRequest();		
		navRequest.setRoomID(roomID);
		navRequest.setLatitude(((Double)location.getX()).toString());
		navRequest.setLongitude(((Double)location.getY()).toString());
		
		String resp = this.Request(navRequest.ToXML());
		
		NavigationResponse navResp = NavigationResponse.FromXML(resp);
		
		
		return navResp.getPath();
	}
	
}
