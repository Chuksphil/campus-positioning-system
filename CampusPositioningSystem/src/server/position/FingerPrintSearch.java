package server.position;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import util.AccessPoint;
import util.DPoint;


public class FingerPrintSearch 
{
	private ArrayList<FingerPrintHyperPoint> fingerPrints = new ArrayList<FingerPrintHyperPoint>();	
	private Hashtable<String, Integer> apDimensions = new Hashtable<String, Integer>();
	private int apNum;
	
	public void load(Connection conn) throws SQLException
	{
		FingerPrints fps = FingerPrints.FromDB(conn);
		
		apNum = 0;
		
		for(FingerPrint fp : fps.GetFingerPrints())
		{
			for(AccessPoint ap : fp.getAps())
			{
				String apMac = ap.getMacAdress();
				
				if (apDimensions.containsKey(apMac) == false)
				{
					apNum += 1;
					apDimensions.put(apMac, apNum);
				}
			}
		}
							
		
		for(FingerPrint fp : fps.GetFingerPrints())
		{
			double[] hyperPoint = new double[apNum];
			for(int i=0; i<apNum; i++)
			{
				hyperPoint[i] = 0;
			}
						
			for(AccessPoint ap : fp.getAps())
			{
				int apDimNum = apDimensions.get(ap.getMacAdress());
				hyperPoint[apDimNum-1] = ap.getSignalStrenght();
			}
		
			
			FingerPrintHyperPoint fpHp = new FingerPrintHyperPoint(fp, hyperPoint);
			fingerPrints.add(fpHp);						
		}
		
	}
	
	
	public String ClosestFingerPrintNodeID(ArrayList<AccessPoint> aps) throws Exception
	{
		double[] hyperPoint = new double[apNum];
		for(int i=0; i<apNum; i++)
		{
			hyperPoint[i] = 0;
		}
					
		for(AccessPoint ap : aps)
		{
			if (apDimensions.containsKey(ap.getMacAdress()))
			{			
				int apDimNum = apDimensions.get(ap.getMacAdress());
				hyperPoint[apDimNum-1] = ap.getSignalStrenght();
			}
		}
		
		double closestSoFar = Double.MAX_VALUE;
		FingerPrint fp = null;
		
		for(FingerPrintHyperPoint fpHp : fingerPrints)
		{
			double dist = fpHp.GetDistance(hyperPoint);
			if (dist < closestSoFar)
			{
				closestSoFar = dist;
				fp = fpHp.getFp();
			}
		}
			
						
		return fp.getNodeID();
			
	}
	
	
	
}
