package server.position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.AccessPoint;


public class FingerPrints
{	
	
	public static FingerPrints FromDB(Connection conn) throws SQLException
	{
		FingerPrints toRet = new FingerPrints();
		
		PreparedStatement getFingerPrints = conn.prepareStatement("SELECT * FROM wps.finger_prints;");
		ResultSet getFingerPrintsResult  = getFingerPrints.executeQuery();		
		while (getFingerPrintsResult.next())
		{
			String id = getFingerPrintsResult.getString("id");		
			String nav_node_id = getFingerPrintsResult.getString("nav_node_id");	
			ArrayList<AccessPoint> aps = new ArrayList<AccessPoint>();
			
			PreparedStatement getFingerPrintAccessPoints = conn.prepareStatement("SELECT * FROM wps.finger_print_access_points WHERE finger_print_id='" + id + "';");
			ResultSet getFingerPrintAccessPointsResult  = getFingerPrintAccessPoints.executeQuery();		
			while (getFingerPrintAccessPointsResult.next())
			{						
				String mac = getFingerPrintAccessPointsResult.getString("mac_address");
				int strenght = getFingerPrintAccessPointsResult.getInt("strenght");
				
				AccessPoint accessPoint = new AccessPoint(mac, strenght);
				aps.add(accessPoint);
			}
			
			FingerPrint fp = new FingerPrint(nav_node_id, aps);
			toRet.GetFingerPrints().add(fp);
		}
		
		
		
		return toRet;
	}
	
		
	private ArrayList<FingerPrint> fingerPrints = new ArrayList<FingerPrint>();
	
	
	public ArrayList<FingerPrint> GetFingerPrints() {
		return fingerPrints;
	}
	
	
}
