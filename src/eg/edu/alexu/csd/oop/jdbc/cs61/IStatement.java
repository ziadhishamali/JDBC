package eg.edu.alexu.csd.oop.jdbc.cs61;

import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import eg.edu.alexu.csd.oop.db.Database;

public class IStatement implements Statement {
	private ArrayList<String> batch = new ArrayList<>();
	private Connection connection;
	private int[] counts;
	private int indexCount;
	private Database database;
	private ResultSet current;
	private boolean closedFalg;
	private Logger log;
	private int time = 0;

	public IStatement(Connection connection, Database db) {
		this.log = Logger.getLogger(IStatement.class.getName());
		PropertyConfigurator.configure("log4j.properties");

		log.info("Initializing the Statement ...");
		this.database = db;
		this.connection = connection;
		this.counts = new int[1];
		this.indexCount = 0;
		this.closedFalg = false;

	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addBatch(String arg0) throws SQLException {
		batch.add(arg0);
	}

	@Override
	public void cancel() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearBatch() throws SQLException {

		log.info("Clearing the batch ...");
		try {
			if (closedFalg) {
				throw new SQLException();
			}
			batch.clear();

		} catch (Exception e) {

			log.error(" Error !! Failed to clear the batch ...");

			throw new SQLException();
		}

	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() throws SQLException {
		
		try {
			log.info("Closing the Statement ...");
			this.database.save();
			current = null;
			closedFalg = true;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error !! Failed to close the statement ...");
			throw new SQLException();
		}
		
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(String arg0) throws SQLException {
		Thread thread = null;
		if (getQueryTimeout() > 0) {
			thread = new QueryThread(getQueryTimeout());
			System.out.println("hiii");
			thread.start();
		}

		try {
			if (closedFalg) {
				throw new SQLException();
			}
			String temp = arg0.trim().substring(0, 6);
			if (arg0.toLowerCase().contains("select") && arg0.trim().substring(0, 6).equalsIgnoreCase("select")) {

				log.info("Executing a select query ...");
				ResultSet test = executeQuery(arg0);
				if (test.next()) {
					if (getQueryTimeout() > 0 && thread.getState() != State.RUNNABLE) {
						log.error("Error !! Failed to execute the query TIMEOUT ...");
						throw new SQLException();
					}
					log.info("Query Executed Successfully ...");
					return true;
				}
				if (getQueryTimeout() > 0 && thread.getState() != State.RUNNABLE) {
					log.error("Error !! Failed to execute the query TIMEOUT ...");
					throw new SQLException();
				}
				log.info("Query Executed Successfully ...");
				return false;
			} else if ((arg0.toLowerCase().contains("drop") && arg0.trim().substring(0, 4).equalsIgnoreCase("drop"))
					|| (arg0.toLowerCase().contains("create")
							&& arg0.trim().substring(0, 6).equalsIgnoreCase("create"))) {

				log.info("Executing a struture query ...");
				if (getQueryTimeout() > 0 && thread.getState() != State.RUNNABLE) {
					log.error("Error !! Failed to execute the query TIMEOUT ...");
					throw new SQLException();
				}

				//log.info("Query Executed Successfully ...");
				return database.executeStructureQuery(arg0);
			} else {

				log.info("Executing an update query ...");
				counts[indexCount] = executeUpdate(arg0);
				if (counts[indexCount] == 0) {
					if (getQueryTimeout() > 0 && thread.getState() != State.RUNNABLE) {
						log.error("Error !! Failed to execute the query TIMEOUT ...");
						throw new SQLException();
					}
					log.info("Query Executed Successfully ...");
					return false;
				}
				if (getQueryTimeout() > 0 && thread.getState() != State.RUNNABLE) {
					log.error("Error !! Failed to execute the query TIMEOUT ...");
					throw new SQLException();
				}
				log.info("Query Executed Successfully ...");
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			// throw new SQLException();
			if (!(arg0.toLowerCase().contains("drop") && arg0.trim().substring(0, 4).equalsIgnoreCase("drop"))) {

				log.error("Error !! Failed to execute the query ...");
				throw new SQLException();
			}
		}
		if (getQueryTimeout() > 0 && thread.getState() != State.RUNNABLE) {
			throw new SQLException();
		}
		return false;
	}

	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int[] executeBatch() throws SQLException {

		log.info("Executing the batch ...");
		try {
			if (closedFalg) {
				throw new SQLException();
			}
			this.counts = new int[batch.size()];
			for (indexCount = 0; indexCount < batch.size(); indexCount++) {
				execute(batch.get(indexCount));
			}
			int[] res = this.counts;
			this.counts = new int[1];
			this.indexCount = 0;
			return res;
		} catch (Exception e) {

			log.info("Error !! Failed to execute the batch ...");
			throw new SQLException();
		}
	}

	@Override
	public ResultSet executeQuery(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		if (closedFalg) {
			throw new SQLException();
		}
		Thread thread = null;
		if (getQueryTimeout() > 0) {
			thread = new QueryThread(getQueryTimeout());
			thread.start();
		}
		try {
			Object[][] result = database.executeQuery(arg0);
			ResultSet res = new IResultSet(result, database, this);
			current = res;
			if (getQueryTimeout() > 0 && thread.getState() != State.RUNNABLE) {
				throw new SQLException();
			}
			return res;
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	@Override
	public int executeUpdate(String arg0) throws SQLException {
		try {
			if (closedFalg) {
				throw new SQLException();
			}
			Thread thread = null;
			if (getQueryTimeout() > 0) {
				thread = new QueryThread(getQueryTimeout());
				thread.start();
			}
			int updates = 0;
			try {
				updates = database.executeUpdateQuery(arg0);
			} catch (Exception e) {
				// TODO: handle exception
				throw new SQLException();
			}
			if (getQueryTimeout() > 0 && thread.getState() != State.RUNNABLE) {
				throw new SQLException();
			}
			
			log.info("Updated " + updates + " Row(s) Successfully ...");
			return updates;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error !! Couldn't update ...");
			throw new SQLException();
		}
	}

	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Connection getConnection() throws SQLException {
		return this.connection;
	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxRows() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		return time;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getResultSetType() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getUpdateCount() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCursorName(String arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMaxRows(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setQueryTimeout(int arg0) throws SQLException {
		if (arg0 <= 0) {
			throw new SQLException();
		} else {
			time = arg0;
		}
	}

}
