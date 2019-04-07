package eg.edu.alexu.csd.oop.jdbc.cs61;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs61.IDatabase;

public class IDriver implements Driver {

	private Database db;
	private static Logger log;

	public IDriver(Database db) {
		this.log = Logger.getLogger(IDriver.class.getName());
		PropertyConfigurator.configure("log4j.properties");

		log.info("Initializing the Driver ...");
		try {
			this.db = db;
		} catch (Exception e) {
			// TODO: handle exception

			log.error("Error !! Failed to initialize the Driver ...");
		}
	}

	public IDriver() {
		this.log = Logger.getLogger(IDriver.class.getName());
		PropertyConfigurator.configure("log4j.properties");

		log.info("Initializing the Driver ...");
		try {
			this.db = new IDatabase();
		} catch (Exception e) {
			// TODO: handle exception

			log.error("Error !! Failed to initialize the Driver ...");
		}
	}

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		if (url.equals("jdbc:dbms://localhost"))
			return true;
		return false;
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {

		System.out.println(info.get("path"));
		log.info("Connecting to " + info.get("path") + " ...");
		try {

			File dir = new File(info.get("path").toString());
			String path = dir.getAbsolutePath();
			this.db.setInside(true);
			this.db.createDatabase(path, false);
			Pool.getInstance().setDB(this.db);
			return Pool.getInstance().getConnection(path);

		} catch (Exception e) {

			log.error("Error !! Failed to Connect with " + info.get("path") + " ...");

			throw new SQLException();
		}

	}

	@Override
	public int getMajorVersion() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMinorVersion() {
		throw new UnsupportedOperationException();
	}

	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		try {
			DriverPropertyInfo[] i = new DriverPropertyInfo[info.size()];
			int j = 0;
			for (Object key : info.keySet()) {
				i[j] = (DriverPropertyInfo) key;
			}
			return i;
		} catch (Exception e) {
			// TODO: handle exception
			throw new SQLException();
		}
	}

	@Override
	public boolean jdbcCompliant() {
		throw new UnsupportedOperationException();
	}

}
