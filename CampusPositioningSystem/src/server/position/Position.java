package server.position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import server.ConnectionParameters;


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
		List<AccessPoint> list = getAPs(conn, macs);
		
		
		//calcuale the wieghted average of all router locations
		double xsum = 0.0;
		double ysum = 0.0;
		double tot = 0.0;
		int on = 0;
		for(AccessPoint ap: list)
		{				
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

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		try{
//			//define an array of mac addresses that you might read from a device in the Klaus building
//			String[] macs = new String[]{
//					"00:15:C7:AB:0C:20",
//					"00:15:C7:AA:DC:C0",
//					"00:15:C7:AB:04:A0",
//					"00:18:74:49:4B:10",
//					"00:15:C7:AA:D4:80",
//					"00:15:C7:AB:8E:70"
//			};
//			
//			int[] strenght = new int[]{
//				1,
//				100,
//				1,
//				1,
//				1,
//				1
//			};
//			
//			
//			
//			//connect to the db and populate the AccessPoint objects using the mac addresses
//			Connection conn = ConnectionParameters.getConnection();
//			PostGIS self = new PostGIS();
//			List<AccessPoint> list = self.getAPs(conn, macs);
//			for(AccessPoint ap: list){
//				//this outputs Well Known Text (WKT) which can be pasted directly into OpenJUMP for a visual representation
//				System.out.println(ap.getLocation().toString());
//			}
//			
//			//get the convex hull of the APs
//			Geometry convex = self.getConvexHull(list);
//			
//			//the convex hull could be used in multiple ways to estimate a location
//			//a naive way would simply be to take the centroid like this
//			Point centroid = convex.getCentroid();
//			
//			System.out.println("------------------");			
//			System.out.println(centroid.toString());
//			System.out.println("------------------");
//			
//			double xsum = 0.0;
//			double ysum = 0.0;
//			double tot = 0.0;
//			int on = 0;
//			for(AccessPoint ap: list)
//			{				
//				int stren = strenght[on];				
//				xsum += (ap.getLocation().getX() * stren);
//				ysum += (ap.getLocation().getY() * stren);
//				tot += 1 * stren;
//				on += 1;
//			}
//			Double x = xsum / tot;
//			Double y = ysum / tot;
//			System.out.println("POINT (" + x.toString() + " " + y.toString() + ")");
//			
//			
//					
//			
//			
//			//a smarter way to estimate location might start with a list of rooms that intersect the convex hull
//			List<Room> rooms = self.getIntersectingRooms(conn, (Polygon)convex);
//			System.out.println(convex.toString());
//			System.out.println(convex.getCentroid().toString());
//			
//			System.out.println("---------ALL ROOMS------------------");
//			for(Room r: rooms){
////				System.out.println(r.getBuildingNumber()+" - "+r.getRoom());
//				System.out.println(r.getBoundary().toText());
//			}
//			System.out.println("---------------------------");
//			
////			//then you might want to clip the room polygons to the convex hull for fun or something
////			self.clipRooms(rooms, convex);//WARNING: this changes the Room objects
////			for(Room r: rooms){
////				System.out.println(r.getBoundary().toText());
////			}
//			
//			conn.close();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	

	
	/**
	 * given a connection and an array of mac addresses, this returns the corresponding Room objects
	 * @param conn
	 * @param macAddresses
	 * @return
	 * @throws Exception
	 */
	public List<AccessPoint> getAPs(Connection conn, ArrayList<String> macs)throws Exception{
		WKBReader reader = new WKBReader();
//		String query = "select distinct on (the_geom) asbinary(the_geom) from navteq.streets where ST_Intersects(geomfromtext(\'"+polygon_wgs84.toString()+"\'),the_geom)";
		String query = "select asbinary(ap.the_geom), ap.apname, ap.building, ap.room, ap.longitude, ap.latitude, ap.first_radio, ap.second_radio,  ap.bld_num, ap.room_num from wps.access_points ap where ap.first_radio = ? or ap.second_radio = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		
		ArrayList<AccessPoint> apList = new ArrayList<AccessPoint>();
		
		for(String mac: macs){
			ps.setString(1, mac);
			ps.setString(2, mac);
			//AccessPoint(String apname, String building, String room, Coordinate coordinateWGS84, 
						//Point location, String firstRadio,String secondRadio, int buildingNumber, 
						//int roomNumber) {
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				apList.add(new AccessPoint(
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
