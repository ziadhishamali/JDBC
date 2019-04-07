package eg.edu.alexu.csd.oop.jdbc;

public interface Iterator {
	
	/* Return boolean check if table has next node */
	 public boolean hasNext();
	 
	 /* Return The Next Node Object */ 
	 public Object next();
	 
	 /* Return boolean check if table has previous node */
	 public boolean hasPrevious();
	 
	 /* Return The Previous Node Object */ 
	 public Object previous();

}
