package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.Regexp;

import com.vividsolutions.jts.io.ParseException;

public class Wireless {

	
	public static ArrayList<AccessPoint> GetVisibleAccesspoints()
	{
		ArrayList<AccessPoint> toRet = new ArrayList<AccessPoint>();
		
		String wirelessData = "";
		
		try 
		{				
			//Process p = Runtime.getRuntime().exec("iwlist ra0 scanning");
			Process p = Runtime.getRuntime().exec("cat wireless.txt");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = input.readLine();
			while (line != null) 
			{
				wirelessData += (line + "\n");
				line = input.readLine();
			}
			input.close();			
		}
		catch (IOException e) 
		{			
		}
		
		

		
		Pattern addressPattern = Pattern.compile("Address: (.*)$", Pattern.MULTILINE);
		Matcher addressMatcher = addressPattern.matcher(wirelessData);
		
		Pattern signalPattern = Pattern.compile("Quality:(.*)/100  Signal level:", Pattern.MULTILINE);
		Matcher signalMatcher = signalPattern.matcher(wirelessData);
		
				
		
		boolean haveMatch = addressMatcher.find() && signalMatcher.find();
		
		while(haveMatch)
		{
			String curAddress = addressMatcher.group(1);
			String curSignal = signalMatcher.group(1);
			int curSignalInt = Integer.parseInt(curSignal);
			
			AccessPoint ap = new AccessPoint(curAddress, curSignalInt);
			toRet.add(ap);
			
			haveMatch = addressMatcher.find() && signalMatcher.find();
		}

		
		return toRet;
	}
	
	public static void main(String[] args) 
	{
		GetVisibleAccesspoints();
	}
	
}
