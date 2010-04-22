package setup.fingerprinting;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import util.AccessPoint;


import com.thoughtworks.xstream.XStream;


public class FingerPrintFile
{	
	private static XStream xstream;
	static
	{
		xstream = new XStream();
		xstream.alias("FingerPrints", FingerPrintFile.class);
		xstream.alias("FingerPrint", FingerPrint.class);
		xstream.alias("AccessPoint", AccessPoint.class);
	}
	
	
	
	public static FingerPrintFile FromFile(String file)
	{			
		String xml;
		try {
			byte[] buffer = new byte[(int) new File(file).length()];
			BufferedInputStream f = new BufferedInputStream(new FileInputStream(file));
			f.read(buffer);
			xml = new String(buffer);
			return (FingerPrintFile)xstream.fromXML(xml);	
		} catch (Exception e) {
			return new FingerPrintFile();
		}		
	}
	public static FingerPrintFile FromXML(String xml)
	{			
		return (FingerPrintFile)xstream.fromXML(xml);		
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
	
	private ArrayList<FingerPrint> fingerPrints = new ArrayList<FingerPrint>();
	
	
	
	public ArrayList<FingerPrint> GetFingerPrints() {
		return fingerPrints;
	}
	
	
	public String GetPointsAsString()
	{
		String toRet = "";
		for(FingerPrint fp : fingerPrints)
		{
			toRet += "POINT (" + fp.getLatitude() + " " + fp.getLongitude() + ")";
		}
		return toRet;
	}
	
}
