package server.position;

import java.util.ArrayList;


import util.AccessPoint;

public class FingerPrint 
{
	private String nodeID;
	private ArrayList<AccessPoint> aps;
	
	
	public FingerPrint(String nodeID, ArrayList<AccessPoint> aps)
	{		
		this.nodeID = nodeID;
		this.aps = aps;		
	}
			
	public String getNodeID() 
	{
		return nodeID;
	}	
	public ArrayList<AccessPoint> getAps() 
	{
		return aps;
	}

	
	
	
}
