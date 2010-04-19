package server.locations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import message.AccessPoint;
import message.AssistanceRequest;
import message.LocationRequest;
import message.LocationResponse;
import message.Request;
import message.Response;
import message.ServerType;

import com.vividsolutions.jts.io.ParseException;

import server.transaction.Transaction;
import server.transaction.HandelClientThread;
import util.ConnectionParameters;
import util.ReaderUtils;


public class LocationServer 
{
	private static Locations m_locations;
	
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, ParseException 
	{
		m_locations = new Locations(ConnectionParameters.getConnection());
		
		
		//create connection to the main server
		Socket socket = new Socket("127.0.0.1", 6779);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));   
		
		//create a request
        AssistanceRequest assistReq = new AssistanceRequest();
        assistReq.setServerType(ServerType.Location);
        		
		//send the request
        String requestString = assistReq.ToXML();        
        out.print(requestString);
        out.print('\0');        
        out.flush();       
        	 
        while (true)
        {        
		    //get message back from the server
		    String messageString = ReaderUtils.ReadTo(in, '\0');
		    LocationRequest req = LocationRequest.FromXML(messageString);
		    
		    String roomNumber = req.getRoomNumber();
		    String roomTag = req.getRoomTag();
		    
		    String roomID = "";
		    
		    if (roomNumber != "")
		    {
		    	roomID = m_locations.GetNodeIDByNumber(roomNumber);	    	
		    }
		    else if (roomTag != "")
		    {
		    	roomID = m_locations.GetNodeIDByTag(roomTag);		    	
		    }
		    
		    
		    LocationResponse resp = new LocationResponse();
		    resp.setRoomID(roomID);
		    

	        String responseString = resp.ToXML();        
	        out.print(responseString);
	        out.print('\0');        
	        out.flush();  
        }
	    
    }


}
