package eg.edu.alexu.csd.oop.jdbc.cs61;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import eg.edu.alexu.csd.oop.db.Database;

public class Pool {
	private static Pool pool;
	private final int MAX = 10;
	private static int number = 0;
	private static ArrayList<Connection> connections;
	private static LinkedHashMap<Connection, String> outCon = new LinkedHashMap<>();
	private Database db;

	private Pool() {
		connections = new ArrayList<>();
	}
	
	public static Pool getInstance() {
		if(pool == null)
			pool = new Pool();
		return pool;
	}
	
	public Connection getConnection(String Path) throws SQLException {
		if (number >= MAX) {
			throw new SQLException();
		}
		if(connections.size() >= 1) {
			Connection currentConnection = connections.get(connections.size() - 1);
			connections.remove(connections.size() - 1);
			outCon.put(currentConnection, Path);
			return currentConnection;
		} else {
			number++;
			Connection connection = new IConnection(this.db);
			outCon.put(connection, Path);
			return connection;
		}
	}
	
	public static void returnConnection(Connection returnConnect) {
		number--;
		outCon.remove(returnConnect);
		connections.add(returnConnect);
	}
	
	public void setDB(Database database) {
		this.db = database;
	}
	
}
