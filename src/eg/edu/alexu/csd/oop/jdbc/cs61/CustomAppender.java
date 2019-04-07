package eg.edu.alexu.csd.oop.jdbc.cs61;


import org.apache.log4j.Level;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

import javafx.scene.control.TextArea;

public class CustomAppender extends WriterAppender {

    private static TextArea logTextArea;

    @Override
    public void append(LoggingEvent loggingEvent){

    	try {
    		System.out.println(logTextArea);
            final String logMessage = this.layout.format( loggingEvent );
            
            if (loggingEvent.getLevel().equals(Level.ERROR)) {
            	logTextArea.setStyle("-fx-text-fill: red;");
            } else {
            	logTextArea.setStyle("-fx-text-fill: black;");
            }
            
            logTextArea.appendText(logMessage);
    	} catch (Exception e) {
			
		}
        
    }
    
    public static void setTextArea(TextArea textArea) {
    	logTextArea = textArea;
    }

}
