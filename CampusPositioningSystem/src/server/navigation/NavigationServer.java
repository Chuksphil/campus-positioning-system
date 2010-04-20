package server.navigation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import message.AssistanceRequest;
import message.NavigationRequest;
import message.NavigationResponse;
import message.ServerType;
import util.Config;
import util.ReaderUtils;

import com.vividsolutions.jts.io.ParseException;


public class NavigationServer 
{
	private static Navigation m_navigation;
	
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, ParseException 
	{
		String configFile = "config.xml";
		if (args.length > 0)
		{
			configFile = args[0];
		}		
		Config config = Config.FromFile(configFile);
		
		
		m_navigation = new Navigation(config.getConnection());
		
		
		//create connection to the main server
		Socket socket = new Socket(config.getMasterIP(), config.getAssistantListenPort());
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));   
		
		//create a request
        AssistanceRequest assistReq = new AssistanceRequest();
        assistReq.setServerType(ServerType.Navigation);
        		
		//send the request
        String requestString = assistReq.ToXML();        
        out.print(requestString);
        out.print('\0');        
        out.flush();       
        	 
        while (true)
        {        
		    //get message back from the server
		    String messageString = ReaderUtils.ReadTo(in, '\0');
		    NavigationRequest req = NavigationRequest.FromXML(messageString);
		    
		    String roomID = req.getRoomID();
		    double startLat = Double.parseDouble(req.getLatitude());
		    double startLong = Double.parseDouble(req.getLongitude());
		    
		    		    
		    Path path = m_navigation.getPath(startLong, startLat, roomID);
		    String pathString = path.GetPointsString();
		    
		    
		    NavigationResponse resp = new NavigationResponse();
		    resp.setPath(pathString);
		    

	        String responseString = resp.ToXML();        
	        out.print(responseString);
	        out.print('\0');        
	        out.flush();  
        }
	    
    }


}
