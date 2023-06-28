
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
@WebServlet("/RequestToTPA")
public class RequestToTPA extends HttpServlet {
	

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
		int fileID = Integer.parseInt(request.getParameter("Fileid"));
		
		System.out.println(fileID);
		Connection con = ConnectionProvider.getConn();
		Statement st = con.createStatement();
		Statement st1 = con.createStatement();
		
		GlobalFunction GF = new GlobalFunction();
		ResultSet rs = st.executeQuery("select * from userfile where id="+fileID);
		
		if(rs.next()){
			
			String digestKey = rs.getString("digestKey");
			String fileName = rs.getString("fileName");
			
			
			
			st1.executeUpdate("update userfile set VerificationStatus='1' where id="+fileID);
			out.println("Verofication Request Sent To TPA.");
			
		}
		
		
		
		
		
	}catch(Exception e){
		out.println(e);
	}finally{
		
		out.close();
	}
	
	
}
	

}
