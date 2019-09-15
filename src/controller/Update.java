package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import view.AlertBox;

/**
 * Update class
 * Handles update queries to database
 * @author fionn
 *
 */
public class Update {

	public Update() {}

	/**
	 * Updates grade in module table for existing record
	 * @param con database connection
	 * @param grade module grade
	 * @param email student email
	 * @return String message to be shown to user
	 */
	public String updateGrade(Connection con, int grade, String email) {
		String msg = "";
		try {
			String query = "UPDATE module SET grade = ? WHERE studentemail = ?";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, grade);
			pstmt.setString(2, email);

			int recordsInserted = pstmt.executeUpdate();
			if (recordsInserted == 0) {
				msg = "Record does not exist.";
			} else {
				msg = recordsInserted + " record updated in module table.\n";
			}
			pstmt.close();
			return msg;
		} catch (Exception ex) {
			AlertBox.displayError("Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			return msg;
		}
	}


	/**
	 * Updates degree in teacher table for existing record
	 * @param con database connection
	 * @param degree teacher degree level
	 * @param tname teacher name
	 * @return String message to be shown to user
	 */
	public String updateDegree(Connection con, int degree, String tname, String email) {
		String msg = "";
		try {
			String[] names = tname.split(" ");
			String query = "UPDATE teacher SET degreeLevel = ? WHERE lname = ? AND email = ?";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, degree);
			pstmt.setString(2, names[names.length-1]);
			pstmt.setString(3, email);

			int recordsInserted = pstmt.executeUpdate();
			if (recordsInserted == 0) {
				msg = "Record does not exist.";
			} else {
				msg = recordsInserted + " record updated in teacher table.\n";
			}
			pstmt.close();
			return msg;
		} catch (Exception ex) {
			AlertBox.displayError("Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			return msg;
		}
	}
}
