package server.transaction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;

import util.ConnectionParameters;
import util.ReaderUtils;

import message.Request;
import message.Response;
import message.ResponseType;

public class HandelClientThread extends Thread  
{
	private Socket socket;
	private Transaction proto;

    public HandelClientThread(Socket socket, Transaction proto) 
    {
		super("ServerThread");
		this.socket = socket;
		this.proto = proto;
    }

    public void run() 
    {   
    	String clientName = socket.getLocalAddress().getHostAddress();    	
    	System.out.println("new client " + clientName);
    	
    	
    	
		try 
		{			
	    	//in and out tcp streams for the client
		    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
		    while(true)
		    {			    			    			    
			    //create a protocol object from the message
			    String messageString = ReaderUtils.ReadTo(in, '\0');
			    Request req = Request.FromXML(messageString);			    
			    System.out.println("request from " + clientName);
			    
			    //create response for the given message
			    Response resp = proto.processInput(req);
			    
			    //send response and end with null terminator
			    String responseString = resp.ToXML();
				out.print(responseString);
				out.print('\0');
				out.flush();
			    System.out.println("sent response, type " + resp.getType().toString() + " to " + clientName);
					
				//if response was OK then quit
				if (resp.getType() == ResponseType.OK){ break; }
		    }
		    
		    out.close();
		    in.close();
		    socket.close();		    

		    System.out.println("closed connection to " + clientName);
	
		}
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
		
    }

}
