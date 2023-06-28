
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



@WebServlet("/AddGroup")
public class AddGroup extends HttpServlet {
	
	
	
	/**
	 * 
	 */
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
    		
    		
    		String GroupName = request.getParameter("GroupName");
    		
    		
    		
    		 Connection con = ConnectionProvider.getConn(); 
            
             Statement st = con.createStatement();
             Statement st1 = con.createStatement();
             
             
             ResultSet rs = st.executeQuery("select * from groupuser where groupname='"+GroupName+"'");
            
          
             if(rs.next())
             {
                  
                 response.sendRedirect("AddGroup.jsp?Exist=Exist");
             }else{
                 
            	
                 st1.executeUpdate("insert into groupuser (groupname) values('"+GroupName+"')");
                 
                	 response.sendRedirect("AddGroup.jsp?success=success");
                 	
                 	
                 
                 
                 
            
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
