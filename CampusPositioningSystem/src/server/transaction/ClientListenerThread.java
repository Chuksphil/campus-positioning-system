package server.transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import message.Request;
import message.Response;
import message.ResponseType;
import util.ConnectionParameters;
import util.ReaderUtils;

public class ClientListenerThread extends Thread  
{
	private int m_port;

    public ClientListenerThread(int port) 
    {    	
		super("ClientListenerThread");
		m_port = port;
    }

    public void run() 
    {   
    	ServerSocket serverSocket = null;
        Transaction proto = new Transaction();

        try 
        {
            serverSocket = new ServerSocket(m_port);
        }
        catch (IOException e) 
        {
            System.err.println("Could not listen on port:" + m_port);
            System.exit(-1);
        }

        while (true)
        {
        	try 
        	{
				new HandelClientThread(serverSocket.accept(), proto).start();
			}
        	catch (IOException e) 
			{
			}
        }
		
    }

}
