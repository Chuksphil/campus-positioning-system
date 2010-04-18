package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import util.ReaderUtils;

import message.AccessPoint;
import message.Request;
import message.Response;

public class Client 
{
	private ArrayList<AccessPoint> m_accessPoints = new ArrayList<AccessPoint>();	
	
	
	public String GetAccessPoints()
	{
		String toRet= "";
		
		for(AccessPoint accessPoint : m_accessPoints)
		{
			toRet += accessPoint.ToString() + ";";			
		}
		return toRet;
	}
	
	public void SetAccessPoints(String accessPoints)
	{
		m_accessPoints.clear();
		
		for( String apString : accessPoints.split(";"))
		{
			try{
				String[] apTokens = apString.split(",");
				String mac = apTokens[0];
				int strenght = Integer.parseInt(apTokens[1]);
				
				m_accessPoints.add(new AccessPoint(mac, strenght));
			}catch(Exception e){}
		}
	}
	
	public Response QueryRoom(String room) throws IOException
	{
		//create connection to server
		Socket socket = new Socket("127.0.0.1", 6780);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));   
		
		//create a request
        Request req = new Request();	 
        
        for(AccessPoint accessPoint : m_accessPoints)
        {
        	req.accessPoints().add( accessPoint );        
        }
        			
		req.setRoomNumber(room);
		
		//send the request
        String requestString = req.ToXML();        
        out.print(requestString);
        out.print('\0');        
        out.flush();       
        	    
	    //get message back from the server
	    String messageString = ReaderUtils.ReadTo(in, '\0');
	    Response resp = Response.FromXML(messageString);
	    
	    //close the connection
		out.close();
		in.close();		
		socket.close();
		
		return resp;
		
	}
	
	
    public Client() throws IOException
    {               
		
            //default ap data
            m_accessPoints.add( new AccessPoint("00:15:C7:AB:0C:20", 5) );
            m_accessPoints.add( new AccessPoint("00:15:C7:AA:DC:C0", 5) );
            m_accessPoints.add( new AccessPoint("00:15:C7:AB:04:A0", 5) );
            m_accessPoints.add( new AccessPoint("00:18:74:49:4B:10", 5) );
            m_accessPoints.add( new AccessPoint("00:15:C7:AA:D4:80", 5) );
            m_accessPoints.add( new AccessPoint("00:15:C7:AB:8E:70", 5) );
    	    
	}

}
