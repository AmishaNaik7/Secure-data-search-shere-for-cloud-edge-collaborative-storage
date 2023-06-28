
package poll;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;






import poll.ConnectionProvider;



@WebServlet("/Registration")
public class Registration extends HttpServlet {
	
	
	
	
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
    		
    		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();            
            String Addeddate=dateFormat.format(date); 
    		
            
            
    		String fname = request.getParameter("fname");
    		String lname = request.getParameter("lname");
    		String email = request.getParameter("email");
    		String gender = request.getParameter("gender");
    		String password = request.getParameter("passwords");
    		String contactNo = request.getParameter("contactNo");
    		String Usertype = request.getParameter("Usertype");
    		String GroupName = request.getParameter("GroupName");
    		int userduration = Integer.parseInt(request.getParameter("userduration"));
    		


    		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.DATE, userduration); // Adding 5 days
            String expire_date = dateFormat.format(c.getTime());
            
          
    		
    		 Connection con = ConnectionProvider.getConn(); 
             
             Statement st = con.createStatement();
             Statement stat = con.createStatement();
           
             ResultSet rs = st.executeQuery("select * from user where email='"+email+"'");
            
          
             if(rs.next())
             {
                  
                 response.sendRedirect("registration.jsp?Exist=Exist");
             }else{
                 
            	 
                 int r = st.executeUpdate("insert into user (fname,email,lname,gender,regDate,password,contactNo,Usertype,GroupID,userduration,expire_date) values('"+fname+"','"+email+"','"+lname+"','"+gender+"','"+Addeddate+"','"+password+"','"+contactNo+"','"+Usertype+"','"+GroupName+"',"+userduration+",'"+expire_date+"')");
                 if(r>0){
                     
                 	
                 	
                 	//System.out.println("-------UseID-------f"+UseID);
                     String msg ="You have successfully registred.<br> Your Details.<br>"+
                            
                             "Full Name : "+fname +"<br>"+
                             "Email : "+ email + " "+"<br>"+
                             "Password :"+ password;
                     
                     String sub = "Successfully Registered";
                     
                    // SendMail.EmailSending(email, sub, msg);
                 
                 }
                 if(Usertype.equalsIgnoreCase("User")){
                	 
                	 response.sendRedirect("User_login.jsp?success=success");
                 }else{
                	 response.sendRedirect("Dataowner_login.jsp?success=success");
                	 
                 }
                 
            
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
