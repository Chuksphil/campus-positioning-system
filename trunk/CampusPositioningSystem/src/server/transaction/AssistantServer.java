package server.transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import message.Response;
import message.ServerType;
import util.ReaderUtils;


public abstract class AssistantServer {
	
	
	private Socket m_socket;
	private PrintWriter m_out;
	private BufferedReader m_in; 
		
	
	private ServerType m_type;
	
	public ServerType GetType()
	{
		return m_type;		
	}
	
	public static AssistantServer MakeServer(ServerType type, Socket socket, PrintWriter out, BufferedReader in) throws IOException
	{
		if (type == ServerType.Location)
		{
			return new LocationAssistantServer(socket, out, in);
		}
		else if (type == ServerType.Position)
		{
			return new PositionAssistantServer(socket, out, in);
		}
		else if (type == ServerType.Navigation)
		{
			return new NavigationAssistantServer(socket, out, in);
		}
		return null;
	}
	
	public void Close() throws IOException
	{
	    m_out.close();
	    m_in.close();
	    m_socket.close();
	}
	
	
	public AssistantServer(ServerType type, Socket socket, PrintWriter out, BufferedReader in) throws IOException
	{
		m_socket = socket;
		m_out = out;
		m_in = in;
		m_type = type;		
	}
	
	
	protected String Request(String request) throws Exception
	{
		//send the request                
        m_out.print(request);
        m_out.print('\0');        
        m_out.flush();       
        	    
	    //get message back from the assitant
	    String messageString = ReaderUtils.ReadTo(m_in, '\0');
	    
		
		return messageString;
	}
	
	
	
	
}
