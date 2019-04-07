package eg.edu.alexu.csd.oop.jdbc.cs61;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import eg.edu.alexu.csd.oop.jdbc.DataContaineer;
import eg.edu.alexu.csd.oop.jdbc.Iterator;

public class IIterator implements DataContaineer {

	private ArrayList<LinkedHashMap<String, String>> data;
	private int cursor = 0;

	public IIterator(ArrayList<LinkedHashMap<String, String>> data) {
		this.data = data;
	}
	
	@Override
	public Iterator getIterator() {
		// TODO Auto-generated method stub
		return new NameIterator();
	}

	@Override
	public void setCursor(int cursor) {
		// TODO Auto-generated method stub
		System.out.println(cursor);
		this.cursor = cursor;

	}

	private class NameIterator implements Iterator {

		
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			int size = cursor + 1;
			if (size > data.size()) {
				cursor = data.size() + 1;
				return false;
			} else {
				cursor++;
				return true;

			}
		}

		@Override
		public Object next() {
			// TODO Auto-generated method stub
			if (this.hasNext()) {
				return data.get(cursor - 1);
			} else {
				return null;
			}
		}

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			int size = cursor - 1;
			if (size <= 0) {
				cursor = 0;
				return false;
			} else {
				cursor--;
				return true;
			}
		}

		@Override
		public Object previous() {
			// TODO Auto-generated method stub
			if (this.hasPrevious()) {
				return data.get(cursor - 1);
			} else {
				return null;
			}		
		}

	}

}
