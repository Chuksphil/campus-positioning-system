package util;

import java.io.IOException;
import java.io.Reader;

public class ReaderUtils 
{
	public static String ReadTo(Reader in, char endchr) throws IOException
	{
		StringBuilder messageBuilder = new StringBuilder();		    
	    while(true) //loop until null term to build a message
	    {	    	
	    	int chr = in.read();
	    	if (chr == -1){ throw new IOException("End of Stream"); }
	    	if (chr == endchr){ break; } //end of the message		    	
	    	messageBuilder.append((char)chr); //add to message string	    	
	    }
	    
	    return  messageBuilder.toString();
		
	}
}
