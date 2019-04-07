package eg.edu.alexu.csd.oop.jdbc.cs61;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.jdbc.DataContaineer;
import eg.edu.alexu.csd.oop.jdbc.Iterator;

public class IResultSet implements ResultSet{
	private IResultSetMetaData metaData;
	private DataContaineer dataContaineer;
	private ArrayList<LinkedHashMap<String, String>> data;
	private ArrayList<String> columnNames;
	private Database dataBase;
	private int cursor;
	private boolean closedFalg;
	private Statement statement;
	private Object[][] res;
	
	public IResultSet(Object[][] res,Database database, IStatement iStatement) {
		
		this.statement = iStatement;
		this.dataBase = database;
		this.res = res;
		this.closedFalg = false;
		data = new ArrayList<>();
		columnNames = new ArrayList<>();
		cursor = 0;  //Before The First Row
		metaData = new IResultSetMetaData(this.dataBase, this.res);
		ArrayList<String> arr = database.getMetaData();
		for (int i = 0; i < arr.size(); i++) {
			columnNames.add(arr.get(i));
		}
		for (int i = 0; i < res.length; i++) {
			LinkedHashMap<String, String> temp = new LinkedHashMap<>();
			for (int j = 0; j < columnNames.size(); j++) {
				temp.put(columnNames.get(j),  String.valueOf(res[i][j]));
			}
			data.add(i, temp);
		}
		dataContaineer = new IIterator(data);
		
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
	public boolean absolute(int row) throws SQLException {   /* ************************************************************** */
		if (closedFalg) {
			throw new SQLException();
		} else if (row > data.size() || row <= 0) {
			return false; 
		} else {
			cursor = row;
			return true;
		}
	}

	@Override
	public void afterLast() throws SQLException {   /* ************************************************************** */
		if (closedFalg) {
			throw new SQLException();
		} else if (data.size() == 0) {
			
		} else {
			cursor = data.size() + 1;
		}
		
	}

	@Override
	public void beforeFirst() throws SQLException {   /* ************************************************************** */
		if (closedFalg) {
			throw new SQLException();
		} else if (data.size() == 0) {
			
		} else {
			cursor = 0;
		}
		
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void close() throws SQLException {   /* ************************************************************** */
		
		try {
			data = new ArrayList<>();
			closedFalg = true;	
			metaData = null;
		} catch (Exception e) {
			// TODO: handle exception
			throw new SQLException();
		}
	}

	@Override
	public void deleteRow() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {   /* ************************************************************** */
		
		if (closedFalg) {
			throw new SQLException();
		} else if (data.size() == 0) {
			throw new SQLException();
		} else {
			LinkedHashMap<String, String> temp = new LinkedHashMap<>();
			if (cursor == 0) {
				throw new SQLException();
			} else {
				temp = data.get(cursor - 1);
			}

			int index = 0;
			boolean flag = false;

			for (String key : temp.keySet()) {
				if (key.equals(columnLabel)) {
					flag = true;
					break;
				} else {
					index++;
				}
			}
			
			if (flag) {
				return index + 1;
			} else {
				throw new SQLException();
			}
		}
			
	}

	@Override
	public boolean first() throws SQLException {   /* ************************************************************** */
		
		if (closedFalg) {
			throw new SQLException();
		} else if (data.size() == 0) {
			return false;
		} else {
			cursor = 1;
			return true;
		}
		
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getConcurrency() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCursorName() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
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
	public float getFloat(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {   /* ************************************************************** */
		
		if (closedFalg) {
			throw new SQLException();
		} else if (data.size() == 0) {
			throw new SQLException();
		} else {
			
			LinkedHashMap<String, String> temp1 = new LinkedHashMap<>();
			LinkedHashMap<String, String> temp2 = new LinkedHashMap<>();
			
			if (cursor == 0) {
				throw new SQLException();
			} else {
				temp1 = data.get(cursor - 1);
			}
					
			if (columnIndex > temp1.size()) {
				throw new SQLException();
			}
			
			String index = String.valueOf(--columnIndex);
			
			int j = 0;
			for (String key : temp1.keySet()) {
				String tempIndex = Integer.toString(j++);
				temp2.put(tempIndex, temp1.get(key));
			}
			
			if (temp2.get(index) == null) {
				throw new SQLException();
			} else if (temp2.get(index).matches("([\\-]{0,1})([0-9]*)") ){
				
				return Integer.parseInt(temp2.get(index));
			} else {
				return 0;
			}
		}
		
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {   /* ************************************************************** */
		
		
		if (closedFalg) {
			throw new SQLException();
		} else if (data.size() == 0) {
			throw new SQLException();
		} else {
			LinkedHashMap<String, String> temp = new LinkedHashMap<>();
			
			if (cursor == 0) {
				throw new SQLException();
			} else {
				temp = data.get(cursor - 1);
			}			
			String value = "";
			boolean flag = false;

			for (String key : temp.keySet()) {
				if (key.equals(columnLabel)) {
					flag = true;
					value = temp.get(columnLabel);
					break;
				}
			}
			
			if (flag && value.matches("([\\-]{0,1})([0-9]*)")) {
				return Integer.parseInt(value);
			} else if (!flag) {
				throw new SQLException();
			} else {
				return 0;
			}
		}
		
		
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {   /* ************************************************************** */
		
		if (closedFalg) {
			throw new SQLException();
		} else {
			return this.metaData;
		}
		
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {   /* ************************************************************** */
		
		if (closedFalg) {
			throw new SQLException();
		} else if (data.size() == 0) {
			throw new SQLException();
		} else {
	
			LinkedHashMap<String, String> temp1 = new LinkedHashMap<>();
			LinkedHashMap<String, String> temp2 = new LinkedHashMap<>();

			if (cursor == 0) {
				throw new SQLException();
			} else {
				temp1 = data.get(cursor - 1);
			}
			String index = String.valueOf(columnIndex - 1);
			
			int size = temp1.size();
			
			if (columnIndex > size) {
				throw new SQLException();
			}
			
			/*int j = 0;
			for (String key : temp1.keySet()) {
				String tempIndex = Integer.toString(j++);
				temp2.put(tempIndex, temp1.get(key));
			}*/
			
			return (res[cursor - 1][columnIndex - 1]);
			
		}
		
		
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Statement getStatement() throws SQLException {
		if (closedFalg) {
			throw new SQLException();
		} else {
			return statement;
		}
	}

	@Override
	public String getString(int columnIndex) throws SQLException {   /* ************************************************************** */
		
		if (closedFalg) {
			throw new SQLException();
		} else if (data.size() == 0) {
			throw new SQLException();
		} else {
			LinkedHashMap<String, String> temp1 = new LinkedHashMap<>();
			LinkedHashMap<String, String> temp2 = new LinkedHashMap<>();
			

			if (cursor == 0) {
				throw new SQLException();
			} else {
				temp1 = data.get(cursor - 1);
			}
			
			String index = String.valueOf(--columnIndex);
			int size = temp1.size();
			
			if (columnIndex > size) {
				throw new SQLException();
			}
			
			int j = 0;
			for (String key : temp1.keySet()) {
				String tempIndex = Integer.toString(j++);
				temp2.put(tempIndex, temp1.get(key));
			}
			
			if (!temp2.get(index).matches("([\\-]{0,1})([0-9]*)")) {
				return (temp2.get(index));
			} else {
				return null;
			}
		}
		
		
	}

	@Override
	public String getString(String columnLabel) throws SQLException {   /* ************************************************************** */
		
		if (closedFalg) {
			throw new SQLException();
		} else if (data.size() == 0) {
			throw new SQLException();
		} else {
			LinkedHashMap<String, String> temp = new LinkedHashMap<>();

			if (cursor == 0) {
				throw new SQLException();
			} else {
				temp = data.get(cursor - 1);
			}			
			String value = "";
			boolean flag = false;

			for (String key : temp.keySet()) {
				if (key.equals(columnLabel)) {
					flag = true;
					value = temp.get(columnLabel);
					break;
				}
			}
			
			if (flag && !(value.matches("([\\-]{0,1})([0-9]*)"))) {
				return value;
			} else if (!flag) {
				return null;
			} 
			
		}
		return null;
		
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getType() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void insertRow() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public boolean isAfterLast() throws SQLException {   /* ************************************************************** */
		
		if (closedFalg) {
			throw new SQLException();
		} else {
			if (data.size() == 0) {
				return false;
			} else if (cursor == data.size() + 1) {
				return true;
			} else {
				return false;
			}
		}
		
		
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {   /* ************************************************************** */
		
		if (closedFalg) {
			throw new SQLException();
		} else {
			if (data.size() == 0) {
				return false;
			} else if (cursor == 0) {
				return true;
			} else {
				return false;
			}
		}
		
		
	}

	@Override
	public boolean isClosed() throws SQLException {   /* ************************************************************** */
		
		if (closedFalg) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isFirst() throws SQLException {   /* ************************************************************** */
		
		if (closedFalg) {
			throw new SQLException();
		} else {
			if (data.size() == 0) {
				return false;
			} else if (cursor == 1) {
				return true;
			} else {
				return false;
			}
		}

	}

	@Override
	public boolean isLast() throws SQLException {   /* ************************************************************** */
		
		if (closedFalg) {
			throw new SQLException();
		} else {
			if (data.size() == 0) {
				return false;
			} else if (cursor == data.size()) {
				return true;
			} else {
				return false;
			}
		}

	}

	@Override
	public boolean last() throws SQLException {   /* ************************************************************** */
		
		if (closedFalg) {
			throw new SQLException();
		} else {
			if (data.size() == 0) {
				return false;
			} else {
				cursor = data.size();
				return true;
			}
		}
		
		
		
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void moveToInsertRow() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public boolean next() throws SQLException {   /* ************************************************************** */
		
		/*int size = cursor + 1;
		System.out.println(size);
		if (size > data.size()) {
			
			cursor = data.size() + 1;
			return false;
		} else {
			cursor++;
			return true;
		}*/
		if (closedFalg) {
			throw new SQLException();
		} else if (data.size() == 0) {
			return false;
		} else {
			dataContaineer.setCursor(cursor);
			Iterator itr = dataContaineer.getIterator(); 
			cursor++;
			return itr.hasNext();
		}
		
		
	}

	@Override
	public boolean previous() throws SQLException {   /* ************************************************************** */
		
		/*int size = cursor - 1;
		if (size <= 0) {
			cursor = 0;
			return false;
		} else {
			cursor--;
			return true;
		}*/
		if (closedFalg) {
			throw new SQLException();
		} else if (data.size() == 0) {
			return false;
		} else {
			dataContaineer.setCursor(cursor);
			Iterator itr = dataContaineer.getIterator(); 
			cursor--;
			return itr.hasPrevious();
		}

	}

	@Override
	public void refreshRow() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public boolean relative(int rows) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowInserted() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateDate(int columnIndex, Date x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateDate(String columnLabel, Date x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNString(int columnIndex, String nString) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNString(String columnLabel, String nString) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNull(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateNull(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateRow() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateString(int columnIndex, String x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateString(String columnLabel, String x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public boolean wasNull() throws SQLException {
		throw new UnsupportedOperationException();
	}

}