
package poll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/UserdeActivation")
public class UserdeActivation extends HttpServlet {
	

	private static final long serialVersionUID = 1L;
	public static String impdtd = "30";
	private final String UPLOAD_DIRECTORY = "E:/upload";
	public static String impdtm = "5";

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
	
	loginRequest(request,response);
}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
	
	loginRequest(request,response);
}

private void loginRequest(HttpServletRequest request,
		HttpServletResponse response) throws ServletException,IOException {
	
	
	response.setContentType("text/html;charset=UTF-8");
	HttpSession session = request.getSession();
	PrintWriter out = response.getWriter();
	
	try{
		//String FileSecretKey = request.getParameter("FileSecretKey");
		int UserID = Integer.parseInt(request.getParameter("UserID"));
		
		
		Connection con = ConnectionProvider.getConn();
		Statement st = con.createStatement();
		Statement st1 = con.createStatement();
		
		GlobalFunction GF = new GlobalFunction();
		ResultSet rs = st.executeQuery("select * from user where id="+UserID);
		
		if(rs.next()){
			
			
			//String Usertype = rs.getString("Usertype");
			
			
			st1.executeUpdate("update user set Status='0' where id="+UserID);
			out.println("User successfully Deactivated.");
			out.println("<h1><a href='UserdeActivation.jsp'>Back</a></h1>");
		}
		
		
		
		
		
	}catch(Exception e){
		out.println(e);
	}finally{
		
		out.close();
	}
	
	
}
	

}
