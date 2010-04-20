package server.position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DPoint;


import com.vividsolutions.jts.algorithm.ConvexHull;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKBReader;


public class Position 
{
	
	public DPoint getPosition(Connection conn, ArrayList<String> macs, ArrayList<Integer> strenghts) throws Exception
	{
		//get the router locations at those mac addresses
		List<AccessPointFull> list = getAPs(conn, macs);
		
		
		//calcuale the wieghted average of all router locations
		double xsum = 0.0;
		double ysum = 0.0;
		double tot = 0.0;
		int on = 0;
		System.out.println("AP Points:");
		for(AccessPointFull ap: list)
		{				
			System.out.println(ap.getLocation().toString());
			
			int stren = strenghts.get(on);				
			xsum += (ap.getLocation().getX() * stren);
			ysum += (ap.getLocation().getY() * stren);
			tot += 1 * stren;
			on += 1;
		}
		double x = xsum / tot;
		double y = ysum / tot;
		
		
		return new DPoint(x, y);
	}

	

	
	/**
	 * given a connection and an array of mac addresses, this returns the corresponding Room objects
	 * @param conn
	 * @param macAddresses
	 * @return
	 * @throws Exception
	 */
	private List<AccessPointFull> getAPs(Connection conn, ArrayList<String> macs)throws Exception{
		WKBReader reader = new WKBReader();
//		String query = "select distinct on (the_geom) asbinary(the_geom) from navteq.streets where ST_Intersects(geomfromtext(\'"+polygon_wgs84.toString()+"\'),the_geom)";
		String query = "select asbinary(ap.the_geom), ap.apname, ap.building, ap.room, ap.longitude, ap.latitude, ap.first_radio, ap.second_radio,  ap.bld_num, ap.room_num from wps.access_points ap where ap.first_radio = ? or ap.second_radio = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		
		ArrayList<AccessPointFull> apList = new ArrayList<AccessPointFull>();
		
		for(String mac: macs){
			ps.setString(1, mac);
			ps.setString(2, mac);
			//AccessPoint(String apname, String building, String room, Coordinate coordinateWGS84, 
						//Point location, String firstRadio,String secondRadio, int buildingNumber, 
						//int roomNumber) {
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				apList.add(new AccessPointFull(
						rs.getString("apname"),
						rs.getString("building"),
						rs.getString("room"),
						new Coordinate(rs.getDouble("longitude"),rs.getDouble("latitude")),
						(Point)reader.read(rs.getBytes("asbinary")),
						rs.getString("first_radio"),
						rs.getString("second_radio"),
						rs.getString("bld_num"),
						rs.getString("room_num")
				));
			}
		}
		return apList;
	}

}
