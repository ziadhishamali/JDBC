package eg.edu.alexu.csd.oop.jdbc.cs61;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class Controller {

	private Driver driver;
	private Connection con;
	private Statement statement;
	private String query;
	private TextArea textArea;
	private static Controller control;

	private Controller() {

	}
	
	public static Controller getInstance() {
		if (control == null) {
			control = new Controller();
		}
		return control;
	}
	
	public void setDriver(Driver driver, TextArea textArea) {
		
		try {

			this.driver = driver;
			Properties info = new Properties();
			File dbDir = new File("myDatabase");
			info.put("path", dbDir.getAbsolutePath());
			this.con = this.driver.connect("jdbc:dbms://localhost", info);
			this.statement = con.createStatement();
			this.textArea = textArea;

		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	/**
	 * executes the query given to it using statement
	 * 
	 * @param query the query to be executed
	 */
	public void sendQuery(String query) {

		try {

			this.query = query;
			this.statement.execute(this.query);
			
			// it will show the table in case of a select query
			showTable();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	/**
	 * closes everything when the application is exited
	 */
	public void close() {
		// TODO Auto-generated method stub
		try {
			this.con.close();
			this.statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}
	}
	
	/**
	 * Shows the table if it's a select query
	 */
	private void showTable() {
		
		try {
			
			// trying to do the select query
			ResultSet res = this.statement.executeQuery(query);
			
			// make a table
			TableView<String[]> table = new TableView<>();
			table.setEditable(false);
			
			System.out.println("I'll fetch metadaa now isa !!!");
			
			// Getting the names of the columns from the meta data
			for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) {
				TableColumn tc = new TableColumn(res.getMetaData().getColumnName(i));
	            final int colNo = i - 1;
	            tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
	                    return new SimpleStringProperty((p.getValue()[colNo]));
	                }
	            });
	            tc.setPrefWidth(90);
	            table.getColumns().add(tc);
			}
			
			
			List<ArrayList<String>> arr = new ArrayList<>();

			// traverse the result set to get the rows of the table
			res.next();
			while (!res.isAfterLast()) {
				ArrayList<String> temp = new ArrayList<>();
				for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) {
					if (res.getMetaData().getColumnType(i) == Types.INTEGER) {
						temp.add(String.valueOf(res.getInt(i)));
					} else if (res.getMetaData().getColumnType(i) == Types.VARCHAR) {
						temp.add(res.getString(i));
					}
				}
				res.next();
				arr.add(temp);
			}
			
			
			String[][] cells = new String[arr.size()][res.getMetaData().getColumnCount()];
			
			// changing the format of the rows into 2d string array
			for (int i = 0; i < arr.size(); i++) {
				for (int j = 0; j < res.getMetaData().getColumnCount(); j++) {
					cells[i][j] = arr.get(i).get(j);
				}
			}
			
			
			ObservableList<String[]> data = FXCollections.observableArrayList();
			data.addAll(Arrays.asList(cells));
			
			// adding the rows values to the table
			table.setItems(data);

			// making an alert with the table in it
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("Selected table");
			dialog.setHeaderText(null);
			dialog.setContentText(null);
			GridPane gridPane = new GridPane();
			gridPane.getChildren().add(table);
			dialog.getDialogPane().setContent(gridPane);
			dialog.showAndWait();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
