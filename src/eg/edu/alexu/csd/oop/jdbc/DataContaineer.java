package eg.edu.alexu.csd.oop.jdbc;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface DataContaineer {
	
	/* return Iterator Object */
	public Iterator getIterator();
	
	/* Set The Cursor State */
	public void setCursor(int cursor);


}
