package server.position;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;

import message.AssistanceRequest;
import message.PositionRequest;
import message.PositionResponse;
import message.ServerType;
import util.AccessPoint;
import util.Config;
import util.DPoint;
import util.ReaderUtils;


public class PositionServer 
{
	private static FingerPrintSearch m_fpSearch;
	private static Connection m_conn;
	
	public static void main(String[] args) throws Exception 
	{	
		String configFile = "config.xml";
		if (args.length > 0)
		{
			configFile = args[0];
		}		
		Config config = Config.FromFile(configFile);
		
		
		m_conn = config.getConnection();
		m_fpSearch = new FingerPrintSearch();
		m_fpSearch.load(m_conn);
		
		
		
		//create connection to the main server
		Socket socket = new Socket(config.getMasterIP(), config.getAssistantListenPort());
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
//		    ArrayList<String> apMacs = new ArrayList<String>();
//		    ArrayList<Integer> apStrenghts = new ArrayList<Integer>();
//		    for(AccessPoint ap : aps)
//		    {
//		    	apMacs.add(ap.getMacAdress());
//		    	apStrenghts.add(ap.getSignalStrenght());
//		    }
//		    
//		    DPoint pos = m_position.getPosition(m_conn,apMacs,apStrenghts);
		    
		    String fingerPrintNodeID = m_fpSearch.ClosestFingerPrintNodeID(aps);
		    		    
		    
		    PositionResponse resp = new PositionResponse();
		    resp.setStartNodeID(fingerPrintNodeID);
		    

	        String responseString = resp.ToXML();        
	        out.print(responseString);
	        out.print('\0');        
	        out.flush();  
        }
	    
    }


}
