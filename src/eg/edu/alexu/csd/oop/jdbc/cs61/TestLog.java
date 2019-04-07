package eg.edu.alexu.csd.oop.jdbc.cs61;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


import java.io.*;
import java.util.*;

public class TestLog {

	/* Get actual class name to be printed on */
	static Logger log = Logger.getLogger(TestLog.class.getName());

	/*public static void main(String[] args) throws IOException, SQLException {

		PropertyConfigurator.configure("log4j.properties");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		log.info(timeStamp + " - " + "Starting the JDBC Program ...");

		Driver driver = new IDriver();

		Properties info = new Properties();
		File dbDir = new File("data1");
		info.put("path", dbDir.getAbsolutePath());
		Connection conn = driver.connect("jdbc:xmldb://localhost", info);
		Statement statement = conn.createStatement();
		
		statement.addBatch("Create Database data5");
		statement.addBatch("Create Table table1 (id int, name varchar, age int)");
		statement.addBatch("Insert into table1 values (1, 'Ziad', 20)");
		statement.addBatch("Create Database data5");
		statement.executeBatch();
		
	}*/

}
