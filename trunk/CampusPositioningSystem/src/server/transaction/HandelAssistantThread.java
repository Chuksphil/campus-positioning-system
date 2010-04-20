package server.transaction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;

import util.ReaderUtils;

import message.AssistanceRequest;
import message.Request;
import message.Response;
import message.ResponseType;
import message.ServerType;

public class HandelAssistantThread extends Thread  
{
	private Socket socket;

    public HandelAssistantThread(Socket socket) 
    {
		super("ServerThread");		
		this.socket = socket;	
    }

    public void run() 
    {   
		try 
		{
			String assistantName = socket.getLocalAddress().getHostAddress();
			
	    	//in and out tcp streams for the assistant
		    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
		    			    			    			    
			//create a protocol object from the message
			String messageString = ReaderUtils.ReadTo(in, '\0');
			AssistanceRequest req = AssistanceRequest.FromXML(messageString);			    
			System.out.println(req.getServerType() + " assistance from " + assistantName);
			    
			ServerType serverType = req.getServerType();
			    
			AssistantServer assistant = AssistantServer.MakeServer(serverType, socket, out, in);
			    
			TransactionServer.GetAssistantServerBank().AddAssistantServer(assistant);
	
		}
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
		
    }

}
