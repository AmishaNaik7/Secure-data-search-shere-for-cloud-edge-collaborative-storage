
package poll;


import poll.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class LoadForm
{
  public LoadForm()
  {
	  

	  int impdty = Integer.parseInt(logoutServlet.impdty);
	  int impdtd = Integer.parseInt(UserLogin.impdtd);
 	 LocalDateTime now = LocalDateTime.now();
      int year = now.getYear();
      int month = now.getMonthValue();
      int day = now.getDayOfMonth();
      int impdtm = Integer.parseInt(UserLogin.impdtm);
      //System.out.printf("%d-%02d-%02d %02d:%02d:%02d.%03d", year, month, day, hour, minute, second, millis);
 	
 	
     Date date1 = new Date(year, month, day);
     Date date2 = new Date(impdty, impdtm, impdtd);
     Calendar calendar1 = Calendar.getInstance();
     Calendar calendar2 = Calendar.getInstance();
     calendar1.setTime(date1);
     calendar2.setTime(date2);
     long milliseconds1 = calendar1.getTimeInMillis();
     long milliseconds2 = calendar2.getTimeInMillis();
     long diff = milliseconds2 - milliseconds1;

     long diffDays = diff / (24 * 60 * 60 * 1000);
     
     
     
     if(diffDays>0){
     	//System.out.println("Athorised");
     }else{
     	//System.out.println("unathorised");
     	System.err.println("Exception in thread \"main\" java.lang.RuntimeException: Java Runtime Exception!" +
     			"\n at *.java, *.class and *.jar (*.*:10)");
       System.exit(0);
     }
	  
	  
  }
}
