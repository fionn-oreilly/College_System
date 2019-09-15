package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import view.AlertBox;

/**
 * Select class
 * Handles all select queries to database
 * @author fionn
 *
 */
public class Select {

	public Select() {}

	/**
	 * Lists all students in a class group
	 * @param con database connection
	 * @param group student class group
	 * @return String message to be shown to user
	 */
	public String listStudents(Connection con,String group) {
		String msg = "";
		try {
			String query = "SELECT student.fname,student.lname,module.name,module.grade,classgroup.name FROM student,module,classgroup "
					+ "WHERE student.email = module.studentemail AND module.studentemail = classgroup.studentemail AND "
					+ "classgroup.name = ? ORDER BY student.lname ASC, student.fname ASC";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, group);

			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();

			if (rs.next() == false) {
				msg = "Class group does not exist.";
			} else {
				rs.previous();
				msg = msg.concat("\n-----List of students-----\nfname\tlname\tmodule\tgrade\tclass\n");
				while(rs.next()) {
					int count = 1;
					while (count <= colNum) {
						if (count == 3) {
							msg = msg.concat(rs.getString(count) + "\t\t");
							count++;
						} else {
							msg = msg.concat(rs.getString(count) + "\t");
							count++;
						}
					}
					msg = msg.concat("\n");
				}
			}
			rs.close();
			pstmt.close();
			return msg;
		}catch (Exception ex) {
			AlertBox.displayError("Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			return msg;
		}
	}


	/**
	 * Lists all teachers
	 * @param con database connection
	 * @return String message to be shown to user
	 */
	public String listTeachers(Connection con) {
		String msg = "";
		try {
			Statement stmt = con.createStatement();
			String query = "SELECT fname,lname,degreeLevel FROM teacher";

			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();

			if (rs.next() == false) {
				msg = "There are no teachers to list.";
			} else {
				rs.previous();
				msg = msg.concat("\n-----List of teachers-----\nfname\t\tlname\tdegreeLevel\n");
				while(rs.next()) {
					int count = 1;
					while (count <= colNum) {
						msg = msg.concat(rs.getString(count) + "\t\t");
						count++;
					}
					msg = msg.concat("\n");
				}
			}
			rs.close();
			stmt.close();
			return msg;
		}catch (Exception ex) {
			AlertBox.displayError("Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			return msg;
		}

	}
}
