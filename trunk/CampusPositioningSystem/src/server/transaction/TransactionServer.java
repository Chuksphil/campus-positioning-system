package server.transaction;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.SQLException;

import com.vividsolutions.jts.io.ParseException;

import server.ConnectionParameters;
import server.communication.Protocol;
import server.communication.ServerThread;


public class TransactionServer 
{
	private static Protocol proto;
	
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, ParseException 
	{
		Connection loadCon = ConnectionParameters.getConnection();
		proto = new Protocol(loadCon);
		loadCon.close();
		
		
        ServerSocket serverSocket = null;
        boolean listening = true;

        try 
        {
            serverSocket = new ServerSocket(6780);
        }
        catch (IOException e) 
        {
            System.err.println("Could not listen on port: 6780.");
            System.exit(-1);
        }

        while (listening)
        {
        	new ServerThread(serverSocket.accept(), proto).start();
        }

        serverSocket.close();
    }


}
