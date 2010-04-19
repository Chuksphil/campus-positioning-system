package server.transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import message.LocationRequest;
import message.LocationResponse;
import message.ServerType;

public class LocationAssistantServer extends AssistantServer {

	public LocationAssistantServer(Socket socket, PrintWriter out, BufferedReader in) throws IOException
	{
			
		super(ServerType.Location, socket, out, in);

	}
	
	
	public synchronized String GetRoomID(String number, String tag) throws Exception
	{
		LocationRequest locRequest = new LocationRequest();
		locRequest.setRoomNumber(number);
		locRequest.setRoomTag(tag);
		
		String resp = this.Request(locRequest.ToXML());
		
		LocationResponse locResp = LocationResponse.FromXML(resp);
		
		return locResp.getRoomID();
	}
	

}
