package setup.fingerprinting;

import java.util.ArrayList;


import util.AccessPoint;

public class FingerPrint 
{
	private double latitude;
	private double longitude;
	private ArrayList<AccessPoint> aps;
	
	
	public FingerPrint(double latitude, double longitude, ArrayList<AccessPoint> aps)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.aps = aps;		
	}
			
	public double getLatitude() 
	{
		return latitude;
	}	
	public double getLongitude() 
	{
		return longitude;
	}
	public ArrayList<AccessPoint> getAps() 
	{
		return aps;
	}

	
	
	
}
