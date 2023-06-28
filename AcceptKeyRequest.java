/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

@WebServlet("/AcceptKeyRequest")
public class AcceptKeyRequest extends HttpServlet{

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
       
        HttpSession session = request.getSession();
        try {
            GlobalFunction GF = new GlobalFunction();
            int Userid=Integer.parseInt(request.getParameter("Userid"));
            int ReqTID=Integer.parseInt(request.getParameter("ReqTID"));
            int RequestFrom=Integer.parseInt(request.getParameter("RequestFrom"));
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();            
            String Addeddate=dateFormat.format(date);
            String FullName = GF.getFullName(Userid);
            
            Connection con = ConnectionProvider.getConn();
            
            Statement st = null;
            
            String query ="select * from filerequest where id="+ReqTID;
           
           st = con.createStatement();
            ResultSet res=st.executeQuery(query);
            if(res.next())
            {
                //int fileRequestID = res.getInt("id");
                int Fileid = res.getInt("fileID");
                String SharedKey = GF.getSharedKey(Userid, Fileid);
                
               
                
                	PreparedStatement stmt = con.prepareStatement("update filerequest SET Status='"+SharedKey+"' where id="+ReqTID);              
                    
                    int status= stmt.executeUpdate();
                    String msg1 = "You have new request Fileid : "+Fileid+" for file : "+Fileid+".";
             
                    if(status==1){ 
                        //SendMail.EmailSending(ToUserEmail, "New File Request", msg1);                           
                       }
                	
        } 
        
    }   catch (SQLException ex) {
            Logger.getLogger(AcceptKeyRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
     

