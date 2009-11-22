package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import util.ReaderUtils;

import message.AccessPoint;
import message.Request;
import message.Response;

public class Client 
{
	
    public static void main(String[] args) throws IOException
    {
        try 
        {
        	//create connection to server
        	Socket echoSocket = new Socket("127.0.0.1", 6780);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                
	        //create a sample request
	        Request req = new Request();	        
			req.accessPoints().add( new AccessPoint("00:15:C7:AB:0C:20", 5) );
	        req.accessPoints().add( new AccessPoint("00:15:C7:AA:DC:C0", 5) );
	        req.accessPoints().add( new AccessPoint("00:15:C7:AB:04:A0", 5) );
	        req.accessPoints().add( new AccessPoint("00:18:74:49:4B:10", 5) );
	        req.accessPoints().add( new AccessPoint("00:15:C7:AA:D4:80", 5) );
	        req.accessPoints().add( new AccessPoint("00:15:C7:AB:8E:70", 5) );
	        			
			req.setRoomNumber("3305");
	        
			//send the request
	        String requestString = req.ToXML();        
	        out.print(requestString);
	        out.print('\0');        
	        out.flush();       
	        	    
		    //get message back from the server
		    String messageString = ReaderUtils.ReadTo(in, '\0');
		    Response resp = Response.FromXML(messageString);
		    
		    //print the message
		    System.out.println(resp.ToXML());
	
		    //close the connection
			out.close();
			in.close();
			echoSocket.close();
		
        }        
        catch (Exception e) 
        {
            System.err.println(e.toString());
            System.exit(1);
        }	    
	}

}
