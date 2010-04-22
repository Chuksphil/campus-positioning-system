package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import util.AccessPoint;
import util.ReaderUtils;
import util.Wireless;

import message.Request;
import message.Response;

public class Client 
{
	private Socket m_socket;
	private PrintWriter m_out;
	private BufferedReader m_in;  
	
	
	
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
	
	public Response Query(String tag) throws IOException
	{		
		//create a request
        Request req = new Request();	 
        
        for(AccessPoint accessPoint : m_accessPoints)
        {
        	req.accessPoints().add( accessPoint );        
        }
                        
		req.setRoomTag(tag);
		
		//send the request
        String requestString = req.ToXML();        
        m_out.print(requestString);
        m_out.print('\0');        
        m_out.flush();       
        	    
	    //get message back from the server
	    String messageString = ReaderUtils.ReadTo(m_in, '\0');
	    Response resp = Response.FromXML(messageString);
	    
		
		return resp;
		
	}
	
	public void Close() throws IOException
	{
	    //close the connection
		m_out.close();
		m_in.close();		
		m_socket.close();
	}
	
	
    public Client() throws IOException
    {              
		
        
    	m_accessPoints.addAll(Wireless.GetVisibleAccesspoints());

		//create connection to server
		m_socket = new Socket("127.0.0.1", 6780);
		m_out = new PrintWriter(m_socket.getOutputStream(), true);
		m_in = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));  
	}

}
