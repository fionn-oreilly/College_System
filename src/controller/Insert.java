package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import view.AlertBox;

/**
 * Insert class
 * Handles all insert statements for database
 * @author fionn
 *
 */
public class Insert {

	public Insert() {}


	/**
	 * Inserts new record into student, module, and classgroup tables.
	 * @param con database connection
	 * @param sname student name
	 * @param number student phone number
	 * @param email student email
	 * @param DOB student DOB
	 * @param modname module name
	 * @param grade module grade
	 * @param classgroup student class group
	 * @return String message to be shown to user
	 */
	public String insertStudent(Connection con, String sname, String number, String email, String DOB, String modname, int grade, String classgroup) {
		String msg = "";
		try {
			CheckRecordExists check = new CheckRecordExists();
			if (sname.isEmpty() || number.isEmpty() || email.isEmpty() || DOB.isEmpty() || modname.isEmpty() || classgroup.isEmpty()) {
				AlertBox.displayError("Insert Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			} else if (check.checkStudent(con, sname, email) == true || check.checkModule(con, sname, modname) == true) {
				msg = "Record already exists.";
			}else {
				int recordsInserted = 0;
				String[] names = sname.split(" ");

		//Insert into student table
				String query2 = "INSERT INTO student VALUES(?,?,?,?,?,?)";
				PreparedStatement pstmt2 = con.prepareStatement(query2);
				pstmt2.setString(1, names[0]);
				if (names.length == 3) {
					pstmt2.setString(2, names[1]);
					pstmt2.setString(3, names[2]);
				} else {
					pstmt2.setString(2, null);
					pstmt2.setString(3, names[1]);
				}
				pstmt2.setString(4, number);
				pstmt2.setString(5, email);
				pstmt2.setString(6, DOB);

				recordsInserted = pstmt2.executeUpdate();
				msg = msg.concat(recordsInserted + " record inserted student table.\n");
				pstmt2.close();
				
				
		//Insert into module table
				String query = "INSERT INTO module VALUES(?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, modname);
				pstmt.setInt(2, grade);
				pstmt.setString(3, email);

				recordsInserted = pstmt.executeUpdate();
				msg = msg.concat(recordsInserted + " record inserted into module table.\n");
				pstmt.close();


		//Insert into classgroup table
				String query3 = "INSERT INTO classgroup VALUES(?,?)";
				PreparedStatement pstmt3 = con.prepareStatement(query3);
				pstmt3.setString(1, classgroup);
				pstmt3.setString(2, email);

				recordsInserted = pstmt3.executeUpdate();
				msg = msg.concat(recordsInserted + " record inserted classgroup table.\n");
				pstmt3.close();
			}
			return msg;
		}catch (Exception ex) {
			AlertBox.displayError("Insert Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			return msg;
		}
	}


	/**
	 * Inserts new record into module table
	 * @param con database connection
	 * @param sname student name
	 * @param email student email
	 * @param modname module name
	 * @param grade module grade
	 * @return String message to be shown to user
	 */
	public String insertModule(Connection con,String sname, String email, String modname, int grade) {
		String msg = "";
		try {
			CheckRecordExists check = new CheckRecordExists();
			if (check.checkModule(con, email, modname) == true) {
				msg = "Record already exists.";
			} else if (email.isEmpty() || modname.isEmpty()) {
				AlertBox.displayError("Insert Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			} else if (check.checkStudent(con, sname, email) == false) {
				AlertBox.displayError("Student does not exist", " Cannot add module for this student as student does not eisxt.");
			} else {
				String query = "INSERT INTO module VALUES(?,?,?)";

				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, modname);
				pstmt.setInt(2, grade);
				pstmt.setString(3, email);

				int recordsInserted = pstmt.executeUpdate();
				msg = recordsInserted + " record inserted into module table.\n";
				pstmt.close();
			}
			return msg;
		}catch (Exception ex) {
			AlertBox.displayError("Insert Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details." + ex.getMessage());
			return msg;
		}
	}


	/**
	 * Inserts new record into teacher table
	 * @param con database connection
	 * @param tname teacher name
	 * @param number teacher phone number
	 * @param email teacher email
	 * @param degree teacher degree level
	 * @return String message to be shown to user
	 */
	public String insertTeacher(Connection con, String tname, String number, String email, int degree) {
		String msg = "";
		try {
			CheckRecordExists check = new CheckRecordExists();
			if (check.checkTeacher(con, tname, email) == true) {
				msg = "Record already exists.";
			} else if (tname.isEmpty() || number.isEmpty() || email.isEmpty() || degree == 0) {
				AlertBox.displayError("Insert Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			} else {
				String[] names = tname.split(" ");
				String query = "INSERT INTO teacher VALUES(?,?,?,?,?,?)";

				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, names[0]);
				if (names.length == 3) {
					pstmt.setString(2, names[1]);
					pstmt.setString(3, names[2]);
				} else {
					pstmt.setString(2, null);
					pstmt.setString(3, names[1]);
				}
				pstmt.setString(4, number);
				pstmt.setString(5, email);
				pstmt.setInt(6, degree);

				int recordsInserted = pstmt.executeUpdate();
				msg = recordsInserted + " record inserted into teacher table.\n";
				pstmt.close();
			}
			return msg;
		} catch (Exception ex) {
			AlertBox.displayError("Insert Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			return msg;
		}
	}
}
