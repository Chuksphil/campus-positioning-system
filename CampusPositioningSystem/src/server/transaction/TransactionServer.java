package server.transaction;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.SQLException;

import com.vividsolutions.jts.io.ParseException;

import util.ConnectionParameters;


public class TransactionServer 
{	
	private static AssistantServerBank serverBank = new AssistantServerBank();
	
	
	public static AssistantServerBank GetAssistantServerBank()
	{
		return serverBank;		
	}
	
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, ParseException 
	{
		
		AssistantListenerThread assitantListener = new AssistantListenerThread(6779);
		ClientListenerThread clientListener = new ClientListenerThread(6780);
		
		assitantListener.start();
		clientListener.start();
		
		//delay forever
		while(true){}
    }


}
