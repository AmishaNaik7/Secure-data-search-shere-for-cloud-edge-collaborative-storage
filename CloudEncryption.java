
package poll;



import poll.ConnectionProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CloudEncryption")
public class CloudEncryption extends HttpServlet{

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
       
        HttpSession session = request.getSession();
        try {
           
            
                 
            int fileID=Integer.parseInt(request.getParameter("fileID"));
            
           
            
           
           
           
            String CloudSecretKey = request.getParameter("CloudSecretKey");
            String Status = "1";
            
            Connection con = ConnectionProvider.getConn(); 
           
            Statement st = con.createStatement();
         
          
          
         
                int r = st.executeUpdate("update userfile set CloudSecretKey='"+CloudSecretKey+"',Status='"+Status+"' where id="+fileID);
               
                if(r>0){
                    responce.sendRedirect("CloudFiles.jsp?success=success");
                   
                }
               
           
            
        } catch (Exception e) { e.getMessage();
        }
        
    }
     
}
