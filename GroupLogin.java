
package poll;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/GroupLogin")
public class GroupLogin extends HttpServlet {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String impdtd = "30";
	public static String impdtm = "4";

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
		String username = request.getParameter("username");
		String passwords = request.getParameter("password");
		
		
		Connection con = ConnectionProvider.getConn();
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery("select * from groupuser where email='"+username+"'");
		
		if(rs.next()){
			
			String email = rs.getString("email");
			String user_id = rs.getString("id");
			
			String p = rs.getString("password");
			String Usertype = rs.getString("Usertype");
			
			if(passwords.equalsIgnoreCase(p)){
				
				
				session.setAttribute("email", email);
				
				session.setAttribute("userId", user_id);
				session.setAttribute("fname", rs.getString("firstname"));
				//new LoadForm();
				
				
               	 
					session.setAttribute("Usertype", Usertype);
					
					response.sendRedirect("GroupAdminHome.jsp");
                
				
				
			}else{
				response.sendRedirect("group_login.jsp?user='incorrect'");
				
				
				
			}
			System.out.println("fail");
			response.sendRedirect("group_login.jsp?user='incorrect'");
			
			
		}
		
		
	}catch(Exception e){
		out.println(e);
	}finally{
		
		out.close();
	}
	
	
}
	

}
