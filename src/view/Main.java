package view;

import java.sql.Connection;
import controller.Delete;
import controller.Insert;
import controller.Select;
import controller.Update;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ConnectDB;

/**
 * Main class
 * @author Fionn
 *
 */
public class Main extends Application {

	Stage window;

	public static void main(String[] args) {launch(args);}

	/**
	 * start function
	 * Create GUI
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

//Connect to database//
		ConnectDB.getInstance();
		String msg = ConnectDB.setConnection();
		Connection con  = ConnectDB.getConnection();


//Create tabs//
		window = primaryStage;
		TabPane TP = new TabPane();
		Tab studentTab = new Tab();
		studentTab.setText("Student");
		studentTab.setClosable(false);
		Tab teacherTab = new Tab();
		teacherTab.setText("Teacher");
		teacherTab.setClosable(false);


//Student tab labels and text fields//
		Label snamelabel = new Label("Enter student name (middle name optional)");
		TextField snamefield = new TextField();
		snamefield.setPromptText("E.g: Fionn Michael O'Reilly");
		snamelabel.setStyle("-fx-font-weight: bold");
		snamefield.setMaxWidth(270);

		Label numberlabel = new Label("Enter student phone number");
		TextField numberfield = new TextField();
		numberfield.setPromptText("E.g: 0871234567");
		numberlabel.setStyle("-fx-font-weight: bold");
		numberfield.setMaxWidth(270);

		Label semaillabel = new Label("Enter student email");
		TextField semailfield = new TextField();
		semailfield.setPromptText("E.g: fionn@gmail.com");
		semaillabel.setStyle("-fx-font-weight: bold");
		semailfield.setMaxWidth(270);

		Label DOBlabel = new Label("Enter student DOB (YYYY-MM-DD)");
		TextField DOBfield = new TextField();
		DOBfield.setPromptText("E.g: 1995-03-01");
		DOBlabel.setStyle("-fx-font-weight: bold");
		DOBfield.setMaxWidth(270);

		Label modulelabel = new Label("Enter module name");
		TextField modulefield = new TextField();
		modulefield.setPromptText("E.g: OOP");
		modulelabel.setStyle("-fx-font-weight: bold");
		modulefield.setMaxWidth(270);

		Label gradelabel = new Label("Enter module grade (optional)");
		TextField gradefield = new TextField();
		gradefield.setPromptText("E.g: 75");
		gradelabel.setStyle("-fx-font-weight: bold");
		gradefield.setMaxWidth(270);

		Label grouplabel = new Label("Enter student's class group");
		TextField groupfield = new TextField();
		groupfield.setPromptText("E.g: SDH2C");
		grouplabel.setStyle("-fx-font-weight: bold");
		groupfield.setMaxWidth(270);


//Teacher tab labels and text fields//
		Label tnamelabel = new Label("Enter teacher name (middle name optional)");
		TextField tnamefield = new TextField();
		tnamefield.setPromptText("E.g: Elliot Tuthill");
		tnamelabel.setStyle("-fx-font-weight: bold");
		tnamefield.setMaxWidth(270);

		Label tnumberlabel = new Label("Enter teacher phone number");
		TextField tnumberfield = new TextField();
		tnumberfield.setPromptText("E.g: 0867654321");
		tnumberlabel.setStyle("-fx-font-weight: bold");
		tnumberfield.setMaxWidth(270);

		Label temaillabel = new Label("Enter teacher email");
		TextField temailfield = new TextField();
		temailfield.setPromptText("E.g: elliot@gmail.com");
		temaillabel.setStyle("-fx-font-weight: bold");
		temailfield.setMaxWidth(270);

		Label degreelabel = new Label("Enter teacher degree level");
		TextField degreefield = new TextField();
		degreefield.setPromptText("E.g: 9");
		degreelabel.setStyle("-fx-font-weight: bold");
		degreefield.setMaxWidth(270);


//Student tab text area//
		TextArea TA = new TextArea();
		TA.setText(msg);
		TA.setStyle("-fx-font-weight: bold");
		TA.setEditable(false);
		TA.setMaxWidth(260);
		TA.setStyle("-fx-border-color: black;");
		TA.setPrefSize(600, 200);

//Teacher tab text area//
		TextArea TA2 = new TextArea();
		TA2.setEditable(false);
		TA2.setMaxWidth(260);
		TA2.setStyle("-fx-border-color: black;");
		TA2.setPrefSize(600, 200);


//Student tab buttons//
		Button addStudent = new Button("Add");
		addStudent.setStyle("-fx-font-weight: bold");
		addStudent.setOnAction(e -> {
			Insert insert = new Insert();
			try {
				if (gradefield.getText().isEmpty()) {gradefield.setText("0");}
				if (numberfield.getText().isEmpty() == false && DOBfield.getText().isEmpty() == false) {
					TA.setText(insert.insertStudent(con,snamefield.getText(),numberfield.getText(),semailfield.getText(),DOBfield.getText(),
							modulefield.getText(),Integer.parseInt(gradefield.getText()),groupfield.getText()));
				} else {
					TA.setText(insert.insertModule(con, snamefield.getText(), semailfield.getText(), modulefield.getText(), Integer.parseInt(gradefield.getText())));
				}
				clearStudentFields(snamefield,numberfield,semailfield,DOBfield,modulefield,gradefield,groupfield);
			} catch (Exception ex) {
				AlertBox.displayError("Insert Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details." + ex.getMessage());
			}
		});

		Button editStudent = new Button("Edit");
		editStudent.setStyle("-fx-font-weight: bold");
		editStudent.setOnAction(e -> {
			try {
				if (gradefield.getText().isEmpty()) {gradefield.setText("0");}
				Update update = new Update();
				TA.setText(update.updateGrade(con, Integer.parseInt(gradefield.getText()), semailfield.getText()));
				clearStudentFields(snamefield,numberfield,semailfield,DOBfield,modulefield,gradefield,groupfield);
			} catch (Exception ex) {
				AlertBox.displayError("Update Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			}
		});

		Button removeStudent = new Button("Remove");
		removeStudent.setStyle("-fx-font-weight: bold");
		removeStudent.setOnAction(e -> {
			try {
				Delete delete = new Delete();
				if (groupfield.getText().isEmpty() == true && semailfield.getText().isEmpty() == false && modulefield.getText().isEmpty() == false) {
					TA.setText(delete.deleteModule(con,semailfield.getText(),modulefield.getText()));
				} else if (groupfield.getText().isEmpty() == false && modulefield.getText().isEmpty() == true) {
					TA.setText(delete.deleteGroup(con,groupfield.getText()));
				} else if (snamefield.getText().isEmpty() == false && semailfield.getText().isEmpty() == false && modulefield.getText().isEmpty() == true) {
					TA.setText(delete.deleteStudent(con, snamefield.getText(), semailfield.getText()));
				} else {
					AlertBox.displayError("Update Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
				}
				clearStudentFields(snamefield,numberfield,semailfield,DOBfield,modulefield,gradefield,groupfield);
			} catch (Exception ex) {
				AlertBox.displayError("Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			}
		});

		Button listStudents = new Button("List");
		listStudents.setStyle("-fx-font-weight: bold");
		listStudents.setOnAction(e -> {
			Select select = new Select();
			TA.setText(select.listStudents(con,groupfield.getText()));
			clearStudentFields(snamefield,numberfield,semailfield,DOBfield,modulefield,gradefield,groupfield);
		});

		Button closeStudent = new Button("Close");
		closeStudent.setStyle("-fx-font-weight: bold");
		closeStudent.setOnAction(e -> System.exit(0));

		Button studentTabHelp = new Button("Help");
		studentTabHelp.setStyle("-fx-font-weight: bold");
		studentTabHelp.setOnAction(e -> {
			AlertBox.studentTabHelp();
		});


//Teacher tab buttons//
		Button addTeacher = new Button("Add");
		addTeacher.setStyle("-fx-font-weight: bold");
		addTeacher.setOnAction(e -> {
			try {
				if (tnamefield.getText().isEmpty() == false && temailfield.getText().isEmpty() == false) {
					Insert insert = new Insert();
					TA2.setText(insert.insertTeacher(con, tnamefield.getText(), tnumberfield.getText(), temailfield.getText(), Integer.parseInt(degreefield.getText())));
					clearTeacherFields(tnamefield,tnumberfield,temailfield,degreefield);
				} else {
					AlertBox.displayError("Insert Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
				}
			} catch (Exception ex) {
				AlertBox.displayError("Insert Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			}
		});

		Button editTeacher = new Button("Edit");
		editTeacher.setStyle("-fx-font-weight: bold");
		editTeacher.setOnAction(e -> {
			try {
				if (degreefield.getText().isEmpty() == false && tnamefield.getText().isEmpty() == false && temailfield.getText().isEmpty() == false) {
					Update update = new Update();
					TA2.setText(update.updateDegree(con, Integer.parseInt(degreefield.getText()), tnamefield.getText(), temailfield.getText()));
					clearTeacherFields(tnamefield,tnumberfield,temailfield,degreefield);
				} else {
					AlertBox.displayError("Update Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
				}
			} catch (Exception ex) {
				AlertBox.displayError("Update Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			}
		});

		Button removeTeacher = new Button("Remove");
		removeTeacher.setStyle("-fx-font-weight: bold");
		removeTeacher.setOnAction(e -> {
			try {
				if (tnamefield.getText().isEmpty() == false && temailfield.getText().isEmpty() == false) {
					Delete delete = new Delete();
					TA2.setText(delete.deleteTeacher(con, tnamefield.getText(), temailfield.getText()));
					clearTeacherFields(tnamefield,tnumberfield,temailfield,degreefield);
				} else {
					AlertBox.displayError("Delete Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
				}
			} catch (Exception ex) {
				AlertBox.displayError("Delete Error", "Please ensure all fields are filled correctly\nClick the 'help' button for details.");
			}
		});

		Button listTeachers = new Button("List");
		listTeachers.setStyle("-fx-font-weight: bold");
		listTeachers.setOnAction(e -> {
			Select select = new Select();
			TA2.setText(select.listTeachers(con));
		});

		Button closeTeacher = new Button("Close");
		closeTeacher.setStyle("-fx-font-weight: bold");
		closeTeacher.setOnAction(e -> System.exit(0));

		Button teacherTabHelp = new Button("Help");
		teacherTabHelp.setStyle("-fx-font-weight: bold");
		teacherTabHelp.setOnAction(e -> {
			AlertBox.TeacherTabHelp();
		});


//Add layouts to tabs
		VBox studentlayout = new VBox();
		studentlayout.setAlignment(Pos.CENTER);
		HBox studentbuttons = new HBox(addStudent, editStudent,removeStudent,listStudents,closeStudent,studentTabHelp);
		studentbuttons.setPadding(new Insets(10, 10, 10, 10));
		studentbuttons.setAlignment(Pos.CENTER);
		studentlayout.getChildren().addAll(snamelabel,snamefield,numberlabel,numberfield,semaillabel,semailfield,DOBlabel,DOBfield,modulelabel,modulefield,gradelabel,gradefield,grouplabel,groupfield,studentbuttons,TA);
		studentTab.setContent(studentlayout);

		VBox teacherlayout = new VBox();
		teacherlayout.setAlignment(Pos.TOP_CENTER);
		HBox teacherbuttons = new HBox(addTeacher, editTeacher,removeTeacher,listTeachers,closeTeacher,teacherTabHelp);
		teacherbuttons.setPadding(new Insets(10, 10, 10, 10));
		teacherbuttons.setAlignment(Pos.CENTER);
		teacherlayout.getChildren().addAll(tnamelabel,tnamefield,tnumberlabel,tnumberfield,temaillabel,temailfield,degreelabel,degreefield,teacherbuttons,TA2);
		teacherTab.setContent(teacherlayout);


		TP.getTabs().addAll(studentTab,teacherTab);
		Scene scene = new Scene(TP,300,600);
		window.setScene(scene);
		window.show();

	}


	/**
	 * Clear text fields in student tab
	 * @param tf1 student name text field
	 * @param tf2 student phone number text field
	 * @param tf3 student email text field
	 * @param tf4 student DOB text field
	 * @param tf5 student module text field
	 * @param tf6 student grade text field
	 * @param tf7 student class group text field
	 */
	public void clearStudentFields(TextField tf1,TextField tf2,TextField tf3,TextField tf4,TextField tf5,TextField tf6,TextField tf7) {
		tf1.setText("");
		tf2.setText("");
		tf3.setText("");
		tf4.setText("");
		tf5.setText("");
		tf6.setText("");
		tf7.setText("");
	}


	/**
	 * Clear text fields in teacher tab
	 * @param tf1 teacher name text field
	 * @param tf2 teacher phone number text field
	 * @param tf3 teacher email text field
	 * @param tf4 teacher degree level text field
	 */
	public void clearTeacherFields(TextField tf1,TextField tf2,TextField tf3,TextField tf4) {
		tf1.setText("");
		tf2.setText("");
		tf3.setText("");
		tf4.setText("");
	}
}
