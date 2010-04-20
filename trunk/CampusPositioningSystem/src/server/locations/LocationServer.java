package server.locations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import message.AssistanceRequest;
import message.LocationRequest;
import message.LocationResponse;
import message.Request;
import message.Response;
import message.ServerType;

import com.vividsolutions.jts.io.ParseException;

import server.transaction.Transaction;
import server.transaction.HandelClientThread;
import util.AccessPoint;
import util.Config;
import util.ReaderUtils;


public class LocationServer 
{
	private static Locations m_locations;
	
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, ParseException 
	{
		String configFile = "config.xml";
		if (args.length > 0)
		{
			configFile = args[0];
		}		
		Config config = Config.FromFile(configFile);
		
		
		m_locations = new Locations(config.getConnection());
		
		
		//create connection to the main server
		Socket socket = new Socket(config.getMasterIP(), config.getAssistantListenPort());
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
		    	if (m_locations.IsValidRoomNumber(roomNumber))
		    	{
		    		roomID = m_locations.GetNodeIDByNumber(roomNumber);
		    	}
		    }
		    else if (roomTag != "")
		    {
		    	if (m_locations.IsValidRoomTag(roomTag))
		    	{
		    		roomID = m_locations.GetNodeIDByTag(roomTag);
		    	}
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
