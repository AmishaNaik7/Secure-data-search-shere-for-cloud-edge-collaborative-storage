
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
import java.util.ArrayList;
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

@WebServlet("/AuthorisedUser")
public class AuthorisedUser extends HttpServlet{

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
       
        HttpSession session = request.getSession();
        try {
           
        	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();            
            String Addeddate=dateFormat.format(date);
            GlobalFunction GF = new GlobalFunction();
           
            Connection con = ConnectionProvider.getConn(); 
            
            Statement st = con.createStatement();
            
            Statement sts = con.createStatement();
            
            int fileID=Integer.parseInt(request.getParameter("fileID"));
            
           
           
            String AccessLevel=request.getParameter("AccessLevel");
            String UserSecretKey = request.getParameter("UserSecretKey");
            String fileSecKey = request.getParameter("fileSecKey");
            
            
            char [] namec = fileSecKey.toCharArray();
           
            
          
            String AuthorisedGroupId = request.getParameter("GroupId");
            String Status = "0";
            
            String NumberOfUserInGroup = GF.getNumberOfUserInGroup(AuthorisedGroupId);
            
            
            
    		ArrayList<String> groupKey = new ArrayList<String>();
    		int group = Integer.parseInt(NumberOfUserInGroup);
    		try {
    			char namc[] = fileSecKey.toCharArray();
    			int partition = namc.length / group;
    			int j = 0;
    			for (int g = 1; g <= group; g++) {
    				String key="";
    				if (g != group) {
    					for (int i = j; i < partition * g; i++, j++) {
    						//System.out.print(namc[i]);
    						key=key+namc[i]+"";
    					}
    				} else {
    					for (int i = j; i < namc.length; i++) {
    						//System.out.print(namc[i]);
    						key=key+namc[i]+"";
    					}
    				}
    				groupKey.add(key);
    				//System.out.println("");
    			}
    		} catch (Exception e) {
    			System.out.println("dont give 0");
    		}
    		System.out.println(groupKey);
            
            
           
    		
    		ResultSet rss = sts.executeQuery("select * from user where GroupID='"+AuthorisedGroupId+"'");
    		int sequence=1;
    		int keySqnc = 0;
    		while(rss.next()){
    			
    			String SKey = groupKey.get(keySqnc);
    			 String msg ="New file shared with you in group..<br> Your Details.<br>"+
                         
                             "File Id : "+rss.getInt("id") +"<br>"+
                             "Date : "+ Addeddate + " "+"<br>"+
                             "Secret Key :"+ SKey;
                     
                     String sub = "New Group Key";
                     
                    
    			
    			GF.setShareFileHistory(fileID, rss.getInt("id"), rss.getString("email"), Integer.parseInt(AuthorisedGroupId), SKey, Addeddate,sequence);
    			sequence++;
    			keySqnc++;
    		}
            
          
          
            	
                int r = st.executeUpdate("update userfile set AuthorisedGroup='"+AuthorisedGroupId+"',AccessLevel='"+AccessLevel+"',UserSecretKey='"+UserSecretKey+"',Status='"+Status+"' where id="+fileID);
               
                if(r>0){
                    responce.sendRedirect("DataOwnerFiles.jsp?assigned=success");
                   
                }
               
           
            
        } catch (Exception e) { e.getMessage();
        }
        
    }
     
}
