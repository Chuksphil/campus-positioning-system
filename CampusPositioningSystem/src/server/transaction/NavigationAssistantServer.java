package server.transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import message.NavigationRequest;
import message.NavigationResponse;
import message.PositionRequest;
import message.PositionResponse;
import message.ServerType;
import util.AccessPoint;
import util.DPoint;

public class NavigationAssistantServer extends AssistantServer {

	public NavigationAssistantServer(Socket socket, PrintWriter out, BufferedReader in) throws IOException
	{
		super(ServerType.Navigation, socket, out, in);
	}

	

	public synchronized String GetPath(String startNodeID, String roomNodeID) throws Exception
	{
		NavigationRequest navRequest = new NavigationRequest();
		navRequest.setStartNodeID(startNodeID);
		navRequest.setRoomNodeID(roomNodeID);
		
		String resp = this.Request(navRequest.ToXML());
		
		NavigationResponse navResp = NavigationResponse.FromXML(resp);
		
		
		return navResp.getPath();
	}
	
}
