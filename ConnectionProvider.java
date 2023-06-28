

package poll;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionProvider {
    private static Connection con=null;
    private static Connection con1=null;
    public static String path="";
	static{
		File file = new File("");
		path = file.getAbsolutePath();
	}
    public static Connection getConn(){
        try{
            
        if(con==null){
         Class.forName("com.mysql.jdbc.Driver");
       //con=DriverManager.getConnection("jdbc:mysql://node210181-env-9566638.j.layershift.co.uk/groupuserrevocation1","root"," HPXlez14461"); 
         con = DriverManager.getConnection("jdbc:mysql://localhost:3307/groupuserrevocation1", "root", "root");
        return con;
        }else{
           return con;  
        }
       
    }   catch(Exception e){
        e.printStackTrace();
    }
        return con;
    } 
    
       
}
