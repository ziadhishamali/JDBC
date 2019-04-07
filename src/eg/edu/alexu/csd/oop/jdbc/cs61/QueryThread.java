package eg.edu.alexu.csd.oop.jdbc.cs61;

public class QueryThread extends Thread{
	int runningtime;
    public QueryThread(int runningtime) {
        this.runningtime = runningtime;
    }
    
    public void run() {
        int loop = 1;
        long StartTime = System.currentTimeMillis() / 1000;
        for (int i = 0; i < loop; ++i) {
            loop++;
            if (runningtime < ((System.currentTimeMillis() / 1000) - StartTime)) {
            	System.out.println("xxxxxxxxxxxxxxxxxxxx");
                loop = 0;
            }
        }
    }
}
