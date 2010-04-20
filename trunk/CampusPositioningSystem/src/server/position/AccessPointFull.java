package server.position;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;

public class AccessPointFull {
	
	private String apname;
	private String building;
	private String room;
	private Coordinate coordinateWGS84;
	private String firstRadio;
	private String secondRadio;
	private String buildingNumber;
	private String roomNumber;
	
	private Point location;
	/**
	 * @return the apname
	 */
	public String getApname() {
		return apname;
	}
	/**
	 * @return the building
	 */
	public String getBuilding() {
		return building;
	}
	/**
	 * @return the room
	 */
	public String getRoom() {
		return room;
	}
	/**
	 * @return the coordinateWGS84
	 */
	public Coordinate getCoordinateWGS84() {
		return coordinateWGS84;
	}
	/**
	 * @return the location
	 */
	public Point getLocation() {
		return location;
	}
	/**
	 * @return the firstRadio
	 */
	public String getFirstRadio() {
		return firstRadio;
	}
	/**
	 * @return the secondRadio
	 */
	public String getSecondRadio() {
		return secondRadio;
	}
	/**
	 * @return the buildingNumber
	 */
	public String getBuildingNumber() {
		return buildingNumber;
	}
	/**
	 * @return the roomNumber
	 */
	public String getRoomNumber() {
		return roomNumber;
	}
	/**
	 * @param apname
	 * @param building
	 * @param room
	 * @param coordinateWGS84
	 * @param location
	 * @param firstRadio
	 * @param secondRadio
	 * @param buildingNumber
	 * @param roomNumber
	 */
	public AccessPointFull(String apname, String building, String room,
			Coordinate coordinateWGS84, Point location, String firstRadio,
			String secondRadio, String buildingNumber, String roomNumber) {
		super();
		this.apname = apname;
		this.building = building;
		this.room = room;
		this.coordinateWGS84 = coordinateWGS84;
		this.location = location;
		this.firstRadio = firstRadio;
		this.secondRadio = secondRadio;
		this.buildingNumber = buildingNumber;
		this.roomNumber = roomNumber;
	}

}
