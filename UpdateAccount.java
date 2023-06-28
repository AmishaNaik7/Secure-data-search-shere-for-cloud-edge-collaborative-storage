
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



@WebServlet("/UpdateAccount")
public class UpdateAccount extends HttpServlet {
	
	
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
    		String Usertype= (String)session.getAttribute("Usertype");
    		String fname = request.getParameter("fname");
    		String lname = request.getParameter("lname");
    		String email = request.getParameter("email");
    		String gender = request.getParameter("gender");
    		String password = request.getParameter("password");
    		String contactNo = request.getParameter("contactNo");
    		
    		
    		 Connection con = ConnectionProvider.getConn(); 
             
             Statement st = con.createStatement();
             Statement stat = con.createStatement();
           
             
            	 
                 int r = st.executeUpdate("update user set fname='"+fname+"',email='"+email+"',lname='"+lname+"',gender='"+gender+"',password='"+password+"',contactNo='"+contactNo+"' where email='"+email+"'");
                
                 if(Usertype.equalsIgnoreCase("User")){
                	 
                	 response.sendRedirect("update_account.jsp?status=success");
                 }else{
                	 
                	 response.sendRedirect("DOupdate_account.jsp?status=success"); 
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
