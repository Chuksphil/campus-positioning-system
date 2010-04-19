package server.transaction;

import java.io.IOException;
import java.net.ServerSocket;

public class AssistantListenerThread  extends Thread  
{

	private int m_port;

    public AssistantListenerThread(int port) 
    {    	
		super("AssistantListenerThread");
		m_port = port;
    }

    public void run() 
    {   
    	ServerSocket serverSocket = null;        

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
				new HandelAssistantThread(serverSocket.accept());
			}
        	catch (IOException e) 
			{
			}
        }
		
    }
	
}
