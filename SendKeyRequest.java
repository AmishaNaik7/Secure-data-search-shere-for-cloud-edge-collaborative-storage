
package poll;

import poll.ConnectionProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SendKeyRequest")
public class SendKeyRequest extends HttpServlet{

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
       
        HttpSession session = request.getSession();
        try {
            GlobalFunction GF = new GlobalFunction();
            int Userid=Integer.parseInt(request.getParameter("Userid"));
            int Fileid=Integer.parseInt(request.getParameter("Fileid"));
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();            
            String Addeddate=dateFormat.format(date);
            String FullName = GF.getFullName(Userid);
            
            Connection con = ConnectionProvider.getConn();
            int fromUser = Integer.parseInt((String)session.getAttribute("userId"));
            Statement st = null;
            
            String query ="select * from userfile where id="+Fileid;
           
           st = con.createStatement();
            ResultSet res=st.executeQuery(query);
            while(res.next())
            {
                int uploadUserID = res.getInt("uploadedBy");
                
                
                String UploadedOwnerEmail = GF.getUserEmail(uploadUserID);
                String FileName = GF.getFileName(Fileid);
                
                Statement stz= con.createStatement();
                ResultSet rsz = stz.executeQuery("select * from fileshared where fileId="+Fileid+"");
                while(rsz.next()){
                	
                	
                	int Userto = Integer.parseInt(rsz.getString("UserId"));
                	String ToUserEmail = rsz.getString("userEmail");
                	PreparedStatement stmt = con.prepareStatement("insert into filerequest (fromUser,Userto,fileID,req_date) values("+fromUser+","+Userto+","+Fileid+",'"+Addeddate+"')");              
                    
                    int status= stmt.executeUpdate();
                    String msg1 = "You have new request Fileid : "+Fileid+" for file : "+Fileid+".";
             
                    if(status==1){ 
                                                   
                       }
                	
                }
                
                
                
                
                
                
           
        } 
        
    }   catch (SQLException ex) {
            Logger.getLogger(SendKeyRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
     

