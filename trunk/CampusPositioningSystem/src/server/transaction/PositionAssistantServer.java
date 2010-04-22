package server.transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import util.AccessPoint;
import util.DPoint;

import message.LocationRequest;
import message.LocationResponse;
import message.PositionRequest;
import message.PositionResponse;
import message.ServerType;

public class PositionAssistantServer extends AssistantServer {

	public PositionAssistantServer(Socket socket, PrintWriter out, BufferedReader in) throws IOException
	{
			
		super(ServerType.Position, socket, out, in);

	}
	
	
	public synchronized String GetPositionNodeID(ArrayList<AccessPoint> aps) throws Exception
	{
		PositionRequest posRequest = new PositionRequest();		
		posRequest.accessPoints().addAll(aps);
		
		String resp = this.Request(posRequest.ToXML());
		
		PositionResponse posResp = PositionResponse.FromXML(resp);
				
		
		return posResp.getStartNodeID();
	}
	

}
