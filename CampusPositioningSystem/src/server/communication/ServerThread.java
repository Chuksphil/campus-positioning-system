package server.communication;

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

public class ServerThread extends Thread  
{
	private Socket socket = null;
	private Protocol proto = new Protocol();

    public ServerThread(Socket socket) 
    {
		super("ServerThread");
		this.socket = socket;
    }

    public void run() 
    {   
    	String clientName = socket.getLocalAddress().getHostAddress();    	
    	System.out.println("new client " + clientName);
    	
    	
    	
		try 
		{
			//get a connection to the DB for the client
	    	Connection con = ConnectionParameters.getConnection();
			
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
			    Response resp = proto.processInput(req, con);
			    
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
		    con.close();		    

		    System.out.println("closed connection to " + clientName);
	
		}
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
		
    }

}
