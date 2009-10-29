package earlyScratch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * 
 * @author Matt Weber
 * Just a class to make it easy to change the database server params easily
 *
 */
public class ConnectionParameters {
	
	/**
	 * Returns a connection to a postgis database running on localhost
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getPostgisConnection() throws SQLException, ClassNotFoundException{
		Class.forName("org.postgresql.Driver"); 
	    String url = "jdbc:postgresql://localhost:5432/postgis";
	    
	    return DriverManager.getConnection(url, "user", "pass");
	}
	public static void main(String[] args){
		try{
			Connection conn = getPostgisConnection();
			System.out.println(conn.getCatalog());
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
