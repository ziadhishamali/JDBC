package eg.edu.alexu.csd.oop.jdbc.cs61;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import eg.edu.alexu.csd.oop.db.Database;

public class IResultSetMetaData implements ResultSetMetaData {
	private Database database;
	private Object[][] res;

	public IResultSetMetaData(Database database, Object[][] res) {
		this.database = database;
		this.res = res;
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
	public String getCatalogName(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getColumnClassName(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getColumnCount() throws SQLException { /* ************************************** */
		return res[0].length;
	}

	@Override
	public int getColumnDisplaySize(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getColumnLabel(int arg0) throws SQLException { /* *********************************** */
		return database.getMetaData().get(arg0 - 1);
	}

	@Override
	public String getColumnName(int arg0) throws SQLException { /* ********************************* */
		return database.getMetaData().get(arg0 - 1);
	}

	@Override
	public int getColumnType(int arg0) throws SQLException { /* ************************************** */
		if (res[0][--arg0] instanceof Integer) {
			return Types.INTEGER;
		} else {
			return Types.VARCHAR;
		}
	}

	@Override
	public String getColumnTypeName(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getPrecision(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getScale(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSchemaName(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTableName(int arg0) throws SQLException { /* ************************************ */
		return database.getTableName();
	}

	@Override
	public boolean isAutoIncrement(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCaseSensitive(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCurrency(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDefinitelyWritable(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int isNullable(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReadOnly(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSearchable(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSigned(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWritable(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

}
