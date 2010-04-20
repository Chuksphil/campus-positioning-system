package server.transaction;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.SQLException;

import com.vividsolutions.jts.io.ParseException;

import util.Config;


public class TransactionServer 
{	
	private static AssistantServerBank serverBank = new AssistantServerBank();
	
	
	public static AssistantServerBank GetAssistantServerBank()
	{
		return serverBank;		
	}
	
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, ParseException 
	{		
		String configFile = "config.xml";
		if (args.length > 0)
		{
			configFile = args[0];
		}		
		Config config = Config.FromFile(configFile);
		
		int assistantListenerPort = config.getAssistantListenPort();
		int clientListenerPort = config.getClientListenPort(); 
		
		AssistantListenerThread assitantListener = new AssistantListenerThread(assistantListenerPort);
		ClientListenerThread clientListener = new ClientListenerThread(clientListenerPort);
		
		assitantListener.start();
		clientListener.start();
		
		//delay forever
		while(true){}
    }


}
