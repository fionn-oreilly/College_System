package model;

import java.sql.Connection;
import java.sql.DriverManager;
import view.AlertBox;

/**
 * Connect to database
 * @author Fionn
 *
 */
public class ConnectDB {

	private static ConnectDB instance = new ConnectDB();
	private static Connection con = null;

	private ConnectDB() {}


    /**
     *
     * @return instance
     */
    public static ConnectDB getInstance() {
	   return instance;
    }


    /**
     *
     * @return Connection database connection
     */
    public static Connection getConnection() {
    	return con;
    }


    /**
     * Make connection to database if not already made
     * @return String message to user
     */
    public static String setConnection() {
    	if (con == null) {
    		try {
	  	    	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	  	        con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/schoolsystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
	  			String msg = "Database connection established";
	  			return msg;
	  		} catch (Exception ex) {
	  			AlertBox.displayError("Error", "Database connection failed: " + ex.getMessage());
	  			return "";
	  		}
    	} else {
    		AlertBox.displayError("Connection Error", "Database connection already established");
    		return "";
    	}
    }
}
