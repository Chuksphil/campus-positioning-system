package server;

import message.Request;
import message.Response;
import message.ResponseType;

public class Protocol 
{

	/**
	 * process messages according to the protocol
	 * @param theInput the request from the client
	 * @return response to send back
	 */
	public Response processInput(Request request) 
	{		
		Response rep = new Response();
		rep.setType(ResponseType.OK);
		rep.setMessage("Test123");
		
		
		return rep;
	}
	
}
