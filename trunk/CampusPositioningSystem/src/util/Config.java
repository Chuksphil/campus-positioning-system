package util;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.thoughtworks.xstream.XStream;



public class Config
{	
	private static XStream xstream;
	static
	{
		xstream = new XStream();
		xstream.alias("Config", Config.class);
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
	
	
	private String dbPass;
	private String dbUser;
	private String dbURL;
	
	private String masterIP;	
	private int clientListenPort;
	private int assistantListenPort;
	
	
	public Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(dbURL, dbUser, dbPass);
	}
	
	
	public int getClientListenPort()
	{
		return clientListenPort;
	}
	public void setClientListenPort(int value)
	{
		clientListenPort = value;
	}
	
	public int getAssistantListenPort()
	{
		return assistantListenPort;
	}
	public void setAssistantListenPort(int value)
	{
		assistantListenPort = value;
	}
	

	public String getDBPass()
	{
		return dbPass;
	}
	public void setDBPass(String value)
	{
		dbPass = value;
	}
	
	public String getDBUser()
	{
		return dbUser;
	}
	public void setDBUser(String value)
	{
		dbUser = value;
	}
	
	public String getDBConnString()
	{
		return dbURL;
	}
	public void setDBConnString(String value)
	{
		dbURL = value;
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

