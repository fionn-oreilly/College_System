package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import view.AlertBox;

/**
 * Contains functions to check for duplicate entries in database tables
 * @author fionn
 *
 */
public class CheckRecordExists {

	public CheckRecordExists() {}


	/**
	 * Checks if record to be inserted already exists in student table
	 * @param con database connection
	 * @param sname student name
	 * @param email student email
	 * @return boolean if record exists
	 */
	public boolean checkStudent(Connection con, String sname, String email) {
		try {
			String[] names = sname.split(" ");
			String query = "SELECT fname,lname,email FROM student WHERE fname = ? AND lname = ? AND email = ?";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, names[0]);
			pstmt.setString(2, names[names.length-1]);
			pstmt.setString(3, email);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			AlertBox.displayError("Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details." + ex.getMessage());
			return true;
		}
	}


	/**
	 * Checks if record to be inserted already exists in module table
	 * @param con database connection
	 * @param email student email
	 * @param modname module name
	 * @return boolean if record exists
	 */
	public boolean checkModule(Connection con, String email, String modname) {
		try {
			String query = "SELECT * FROM module WHERE name = ? AND studentemail = ?";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, modname);
			pstmt.setString(2, email);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			AlertBox.displayError("Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			return true;
		}
	}


	/**
	 * Checks if record to be inserted already exists in teacher table
	 * @param con database connection
	 * @param tname teacher name
	 * @param email teacher email
	 * @return boolean if record exists
	 */
	public boolean checkTeacher(Connection con, String tname, String email) {
		try {
			String[] names = tname.split(" ");
			String query = "SELECT fname,lname,email FROM teacher WHERE fname = ? AND lname = ? AND email = ?";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, names[0]);
			pstmt.setString(2, names[names.length-1]);
			pstmt.setString(3, email);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			AlertBox.displayError("Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			return true;
		}
	}
}
