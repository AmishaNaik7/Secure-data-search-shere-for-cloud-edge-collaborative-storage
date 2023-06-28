
package poll;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;






import poll.ConnectionProvider;


@WebServlet("/GroupRegistration")
public class GroupRegistration extends HttpServlet {
	

	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,IOException {
    	
    	response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	HttpSession session = request.getSession();
    	try{
    		
    		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();            
            String Addeddate=dateFormat.format(date); 
    		
    		
    		String fname = request.getParameter("fname");
    		String lname = request.getParameter("lname");
    		String email = request.getParameter("email");
    		
    		String password = request.getParameter("passwords");
    		
    		String Usertype = request.getParameter("Usertype");
    		String groupname = request.getParameter("groupname");
    		
    		
    		 Connection con = ConnectionProvider.getConn(); 
             
             Statement st = con.createStatement();
             Statement stat = con.createStatement();
           
             ResultSet rs = st.executeQuery("select * from groupuser where email='"+email+"' AND groupname='"+groupname+"'");
            
          
             if(rs.next())
             {
                  
                 response.sendRedirect("group_login.jsp?Exist=Exist");
             }else{
                 
            	 
                 int r = st.executeUpdate("insert into groupuser (firstname,email,lastname,regDate,password,Usertype,groupname) values('"+fname+"','"+email+"','"+lname+"','"+Addeddate+"','"+password+"','"+Usertype+"','"+groupname+"')");
                 if(r>0){
                     
                 	
                 	
                 	//System.out.println("-------UseID-------f"+UseID);
                     String msg ="You have successfully registred.<br> Your Details.<br>"+
                            
                             "Full Name : "+fname +"<br>"+
                             "Email : "+ email + " "+"<br>"+
                             "Password :"+ password;
                     
                     String sub = "Successfully Registered";
                     
                    // SendMail.EmailSending(email, sub, msg);
                 
                 }
                 
                	 
                	 response.sendRedirect("group_login.jsp?success=success");
                 
            
             }
         } catch (Exception e) { e.getMessage();
         }finally{
    		out.close();
    		
    	}
	}

	
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
	
	
	
	

}
