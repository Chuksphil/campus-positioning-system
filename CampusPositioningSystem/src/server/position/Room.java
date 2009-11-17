package server.position;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;

public class Room {

	private Geometry boundary;
	private String buildingNumber;
	private String room;
	private String floor;
	/**
	 * @param boundary
	 * @param buildingNumber
	 * @param room
	 * @param floor
	 */
	public Room(Geometry boundary, String buildingNumber, String room,
			String floor) {
		super();
		this.boundary = boundary;
		this.buildingNumber = buildingNumber;
		this.room = room;
		this.floor = floor;
	}
	/**
	 * @return the boundary
	 */
	public Geometry getBoundary() {
		return boundary;
	}
	/**
	 * @param boundary the boundary to set
	 */
	public void setBoundary(Geometry boundary) {
		this.boundary = boundary;
	}
	/**
	 * @return the buildingNumber
	 */
	public String getBuildingNumber() {
		return buildingNumber;
	}
	/**
	 * @return the room
	 */
	public String getRoom() {
		return room;
	}
	/**
	 * @return the floor
	 */
	public String getFloor() {
		return floor;
	}
	/**
	 * @param room the room to set
	 */
	public void setRoom(String room) {
		this.room = room;
	}
}
