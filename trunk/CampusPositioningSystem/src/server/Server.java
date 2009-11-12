package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server 
{
	public static void main(String[] args) throws IOException 
	{
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
        	new ServerThread(serverSocket.accept()).start();
        }

        serverSocket.close();
    }


}
