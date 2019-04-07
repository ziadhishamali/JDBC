package eg.edu.alexu.csd.oop.jdbc.cs61;

import eg.edu.alexu.csd.oop.db.cs61.IDatabase;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainUI extends Application {

	private static TextArea textArea;

	public static void main(String[] args) {

		textArea = new TextArea();
		CustomAppender.setTextArea(textArea);
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Group root = new Group();
		Scene scene = new Scene(root, 1200, 850);

		primaryStage.setTitle("JDBC");
		primaryStage.setScene(scene);
		primaryStage.show();

		textArea.setLayoutX(5);
		textArea.setLayoutY(5);
		textArea.setFont(new Font("Berlin Sans FB", 20));
		textArea.setEditable(false);
		textArea.setPrefSize(1190, 775);

		TextField textField = new TextField();
		textField.setPromptText("Enter the SQL Query");
		textField.setLayoutX(5);
		textField.setLayoutY(790);
		textField.setFont(new Font("Berlin Sans FB", 20));
		textField.requestFocus();
		textField.setPrefSize(1190, 50);

		// initialize the controller
		Controller control = Controller.getInstance();
		control.setDriver(new IDriver(new IDatabase()), textArea);

		root.getChildren().addAll(textArea, textField);

		root.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				if (event.getCode().equals(KeyCode.ENTER)) {

					String query = textField.getText();
					control.sendQuery(query);

				} else if (event.getCode().equals(KeyCode.ESCAPE)) {

					textField.setText("");

				}

			}

		});

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				System.out.println("Stage is closing and i'm saving the files");
				control.close();
				primaryStage.close();
			}
		});

	}

}
