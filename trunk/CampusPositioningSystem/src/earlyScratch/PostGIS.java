package earlyScratch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import type.AccessPoint;
import type.Room;

import com.vividsolutions.jts.algorithm.ConvexHull;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKBReader;

public class PostGIS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			//define an array of mac addresses that you might read from a device in the Klaus building
			String[] macs = new String[]{
					"00:15:C7:AB:0C:20",
					"00:15:C7:AA:DC:C0",
					"00:15:C7:AB:04:A0",
					"00:18:74:49:4B:10",
					"00:15:C7:AA:D4:80",
					"00:15:C7:AB:8E:70"
			};
			
			//connect to the db and populate the AccessPoint objects using the mac addresses
			Connection conn = ConnectionParameters.getPostgisConnection();
			PostGIS self = new PostGIS();
			List<AccessPoint> list = self.getAPs(conn, macs);
			for(AccessPoint ap: list){
				//this outputs Well Known Text (WKT) which can be pasted directly into OpenJUMP for a visual representation
				System.out.println(ap.getLocation().toString());
			}
			
			//get the convex hull of the APs
			Geometry convex = self.getConvexHull(list);
			
			//the convex hull could be used in multiple ways to estimate a location
			//a naive way would simply be to take the centroid like this
			Point centroid = convex.getCentroid();
			
			//a smarter way to estimate location might start with a list of rooms that intersect the convex hull
			List<Room> rooms = self.getIntersectingRooms(conn, (Polygon)convex);
			System.out.println(convex.toString());
			System.out.println(convex.getCentroid().toString());
			for(Room r: rooms){
//				System.out.println(r.getBuildingNumber()+" - "+r.getRoom());
				System.out.println(r.getBoundary().toText());
			}
			System.out.println("---------------------------");
			
			//then you might want to clip the room polygons to the convex hull for fun or something
			self.clipRooms(rooms, convex);//WARNING: this changes the Room objects
			for(Room r: rooms){
				System.out.println(r.getBoundary().toText());
			}
			
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * finds the intersection of each room with the given region and then modifies the room's polygon accordingly
	 * @param rooms
	 * @param region
	 */
	public void clipRooms(List<Room> rooms, Geometry region){
		for(int i = 0; i < rooms.size(); i++){
			rooms.get(i).setBoundary(rooms.get(i).getBoundary().intersection(region));
		}
	}
	
	/**
	 * Given a db connection and a region, returns a list of rooms that intersect the region
	 * @param conn
	 * @param region
	 * @return
	 * @throws Exception
	 */
	public List<Room> getIntersectingRooms(Connection conn, Geometry region)throws Exception{
		ArrayList<Room> rooms = new ArrayList<Room>();
		WKBReader reader = new WKBReader();
		String query = "select asbinary(r.the_geom), r.bldg_num, r.room, r.floor from wps.rooms r where not r.room = '' and ST_Intersects(r.the_geom,geomfromtext('"+region.toText()+"'))";
		ResultSet rs = conn.createStatement().executeQuery(query);
		//Room(Polygon boundary, String buildingNumber, String room, String floor)
		while(rs.next()){
			rooms.add(new Room(
					reader.read(rs.getBytes("asbinary")),
					rs.getString("bldg_num"),
					rs.getString("room"),
					rs.getString("floor")
					
			));
		}
		return rooms;
	}
	public Coordinate estimateLocation(Connection conn, String[] APs)throws Exception{
		List<AccessPoint> list = getAPs(conn,APs);
		Point p = getConvexHull(list).getCentroid();
		return p.getCoordinate();
	}
	
	/**
	 * turn a list of AccessPoints into a single polygon that is the convex hull of the AP points
	 * @param APs
	 * @return Geometry object that should be of Polygon type
	 */
	public Geometry getConvexHull(List<AccessPoint> APs){
		GeometryFactory factory = new GeometryFactory();
		Coordinate[] c = new Coordinate[APs.size()];
		
		for(int i = 0; i < APs.size(); i++){
			c[i] = APs.get(i).getLocation().getCoordinate();
		}
		ConvexHull hull = new ConvexHull(c, factory);
		return hull.getConvexHull();
	}
	
	/**
	 * given a connection and an array of mac addresses, this returns the corresponding Room objects
	 * @param conn
	 * @param macAddresses
	 * @return
	 * @throws Exception
	 */
	public List<AccessPoint> getAPs(Connection conn, String[] macAddresses)throws Exception{
		WKBReader reader = new WKBReader();
//		String query = "select distinct on (the_geom) asbinary(the_geom) from navteq.streets where ST_Intersects(geomfromtext(\'"+polygon_wgs84.toString()+"\'),the_geom)";
		String query = "select asbinary(ap.the_geom), ap.apname, ap.building, ap.room, ap.longitude, ap.latitude, ap.first_radio, ap.second_radio,  ap.bld_num, ap.room_num from wps.access_points ap where ap.first_radio = ? or ap.second_radio = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		
		ArrayList<AccessPoint> apList = new ArrayList<AccessPoint>();
		
		for(String mac: macAddresses){
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
