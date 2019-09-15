package view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Alert box class
 * Displays various alert box types
 * @author fionn
 *
 */
public class AlertBox {


	/**
	 * Display guide for manipulating database using student tab
	 */
	public static void studentTabHelp() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Help");
		alert.setContentText("To add a new student and their details, fill in all fields then click 'add'.\n"
				+ "To add a module, enter student name & email, module name & grade, then click 'add'.\n"
				+ "To edit a student's grade, enter student email, module name & grade, then click 'edit'\n"
				+ "To remove a student, enter student name & email, then click 'remove'.\n"
				+ "To remove a class and it's students, enter class group name then click 'remove'.\n"
				+ "To list student's in a class, enter a class group name then click 'list'.");
		alert.showAndWait();
	}


	/**
	 * Display guide for manipulating database using teacher tab
	 */
	public static void TeacherTabHelp() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Help");
		alert.setContentText("To add teacher details, fill in all fields then click 'add'.\n"
				+ "To edit a teacher's degree, enter teacher name, email & new degree level, then click 'edit'.\n"
				+ "To remove a teacher, enter teacher's name & email, then click 'remove'.\n"
				+ "To list all teachers, click 'list'.");
		alert.showAndWait();
	}


	/**
	 * Error alert box
	 * @param title title of alert box
	 * @param message contents of alert box
	 */
	public static void displayError(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
