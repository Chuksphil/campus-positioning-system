package util;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.thoughtworks.xstream.XStream;



public class Config
{	
	private static XStream xstream;
	static
	{
		xstream = new XStream();
		xstream.alias("request", Config.class);
	}

	public static Config FromFile(String file)
	{			
		String xml;
		try {
			byte[] buffer = new byte[(int) new File(file).length()];
			BufferedInputStream f = new BufferedInputStream(new FileInputStream(file));
			f.read(buffer);
			xml = new String(buffer);
			return (Config)xstream.fromXML(xml);	
		} catch (Exception e) {
			return null;
		}		
	}
	public static Config FromXML(String xml)
	{			
		return (Config)xstream.fromXML(xml);		
	}	
	public String ToXML()
	{
		return xstream.toXML(this);	
	}
	
	
	private String myPort;
	private String masterPort;
	private String masterIP;
	
	
	
	public String getMyPort()
	{
		return myPort;
	}
	public void setMyPort(String value)
	{
		myPort = value;
	}
	
	public String getMasterPort()
	{
		return masterPort;
	}
	public void setMasterPort(String value)
	{
		masterPort = value;
	}

	public String getMasterIP()
	{
		return masterIP;
	}
	public void setMasterIP(String value)
	{
		masterIP = value;
	}

}

