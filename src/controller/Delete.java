package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import view.AlertBox;

/**
 * Delete class
 * Handles delete statements to database
 * @author fionn
 *
 */
public class Delete {

	public Delete() {}

	/**
	 * Deletes records from module table
	 * @param con database connection
	 * @param email student email
	 * @param module module name
	 * @return String message to be shown to user
	 */
	public  String deleteModule(Connection con,String email,String module) {
		String msg = "";
		try {
			String query = "DELETE FROM module WHERE studentemail = ? AND name=?";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, module);

			int res = pstmt.executeUpdate();
			if (res == 0) {
				msg = "Record does not exist";
			} else {
				msg = res + " records deleted from module table.";
			}
			pstmt.close();
			return msg;
		} catch (Exception ex) {
			AlertBox.displayError("Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			return msg;
		}
	}


	/**
	 * Deletes records from classgroup table
	 * @param con database connection
	 * @param group student class group
	 * @return String message to be shown to user
	 */
	public  String deleteGroup(Connection con,String group) {
		String msg = "";
		try {
			String query = "DELETE module, student, classgroup FROM module JOIN student ON"
					+ " module.studentemail = student.email JOIN classgroup ON"
					+ " classgroup.studentemail = module.studentemail WHERE classgroup.name = ?";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, group);

			int res = pstmt.executeUpdate();
			if (res == 0) {
				msg = "Class group does not exist.";
			} else {
				msg = res + " record(s) deleted from database.\n";
			}
			pstmt.close();
			return msg;
		} catch (Exception ex) {
			AlertBox.displayError("Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			return msg;
		}
	}


	/**
	 * Deletes records from student, module, and class group table
	 * @param con database connection
	 * @param sname student name
	 * @param email student email
	 * @return String message to be shown to user
	 */
	public String deleteStudent(Connection con,String sname, String email) {
		String msg = "";
		try {
			String[] names = sname.split(" ");
			String query = "DELETE module, student, classgroup FROM module JOIN student ON"
					+ " module.studentemail = student.email JOIN classgroup ON"
					+ " classgroup.studentemail = module.studentemail WHERE student.lname = ? AND student.email = ?";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, names[names.length-1]);
			pstmt.setString(2, email);

			int res = pstmt.executeUpdate();
			if (res == 0) {
				msg = "Student does not exist.";
			} else {
				msg = res + " record(s) deleted from database.\n";
			}
			pstmt.close();
			return msg;
		} catch (Exception ex) {
			AlertBox.displayError("Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			return msg;
		}
	}


	/**
	 * Deletes record from teacher table
	 * @param con database connection
	 * @param tname teacher name
	 * @return String message to be shown to user
	 */
	public String deleteTeacher(Connection con,String tname, String email) {
		String msg = "";
		try {
			String[] names = tname.split(" ");
			String query = "DELETE FROM teacher WHERE fname = ? AND lname = ? AND email = ?";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, names[0]);
			pstmt.setString(2, names[names.length-1]);
			pstmt.setString(3, email);

			int records = pstmt.executeUpdate();
			if (records == 0) {
				msg = "Teacher does not exist.";
			} else {
				msg = records + " record deleted from teacher table.\n";
			}
			pstmt.close();
			return msg;
		} catch (Exception ex) {
			AlertBox.displayError("Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			return msg;
		}
	}
}
