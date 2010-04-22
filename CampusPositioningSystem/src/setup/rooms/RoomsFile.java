package setup.rooms;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import util.AccessPoint;


import com.thoughtworks.xstream.XStream;


public class RoomsFile
{	
	private static XStream xstream;
	static
	{
		xstream = new XStream();
		xstream.alias("RoomsFile", RoomsFile.class);
		xstream.alias("Room", Room.class);		
	}
	
	
	
	public static RoomsFile FromFile(String file)
	{			
		String xml;
		try {
			byte[] buffer = new byte[(int) new File(file).length()];
			BufferedInputStream f = new BufferedInputStream(new FileInputStream(file));
			f.read(buffer);
			xml = new String(buffer);
			return (RoomsFile)xstream.fromXML(xml);	
		} catch (Exception e) {
			return new RoomsFile();
		}		
	}
	public static RoomsFile FromXML(String xml)
	{			
		return (RoomsFile)xstream.fromXML(xml);		
	}
	
	
	public String ToXML()
	{
		return xstream.toXML(this);	
	}
	public void ToFile(String file)
	{			
		try {
			String xml = this.ToXML();
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(xml);    
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	

	private Hashtable<String,Room> rooms = new Hashtable<String,Room>();
	
	
	
	public Hashtable<String,Room> GetRooms() {
		return rooms;
	}
		
	
}
