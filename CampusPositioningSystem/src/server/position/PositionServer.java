package server.position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import message.AccessPoint;
import message.AssistanceRequest;
import message.LocationRequest;
import message.LocationResponse;
import message.PositionRequest;
import message.PositionResponse;
import message.Request;
import message.Response;
import message.ServerType;

import com.vividsolutions.jts.io.ParseException;

import server.transaction.Transaction;
import server.transaction.HandelClientThread;
import util.ConnectionParameters;
import util.DPoint;
import util.ReaderUtils;


public class PositionServer 
{
	private static Position m_position;
	private static Connection m_conn;
	
	public static void main(String[] args) throws Exception 
	{
		m_conn = ConnectionParameters.getConnection();
		m_position = new Position();
		
		
		//create connection to the main server
		Socket socket = new Socket("127.0.0.1", 6779);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));   
		
		//create a request
        AssistanceRequest assistReq = new AssistanceRequest();
        assistReq.setServerType(ServerType.Position);
        		
		//send the request
        String requestString = assistReq.ToXML();        
        out.print(requestString);
        out.print('\0');        
        out.flush();       
        	 
        while (true)
        {        
		    //get message back from the server
		    String messageString = ReaderUtils.ReadTo(in, '\0');
		    PositionRequest req = PositionRequest.FromXML(messageString);
		    
		    ArrayList<AccessPoint> aps = req.accessPoints();
		    ArrayList<String> apMacs = new ArrayList<String>();
		    ArrayList<Integer> apStrenghts = new ArrayList<Integer>();
		    for(AccessPoint ap : aps)
		    {
		    	apMacs.add(ap.getMacAdress());
		    	apStrenghts.add(ap.getSignalStrenght());
		    }
		    
		    DPoint pos = m_position.getPosition(m_conn,apMacs,apStrenghts);
		    		    
		    
		    PositionResponse resp = new PositionResponse();
		    resp.setLatitude(((Double)pos.getX()).toString());
		    resp.setLongitude(((Double)pos.getY()).toString());
		    

	        String responseString = resp.ToXML();        
	        out.print(responseString);
	        out.print('\0');        
	        out.flush();  
        }
	    
    }


}
