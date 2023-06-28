
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
@WebServlet("/RegenerateFile")
public class RegenerateFile extends HttpServlet {
	

	private static final long serialVersionUID = 1L;
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
	
	loginRequest(request,response);
}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
	
	loginRequest(request,response);
}

private void loginRequest(HttpServletRequest request,
		HttpServletResponse response) throws ServletException,IOException {
	
	PrintWriter out = response.getWriter();
	
	try{
		
		int fileID = Integer.parseInt(request.getParameter("Fileid"));
		
		System.out.println(fileID);
		Connection con = ConnectionProvider.getConn();
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery("select * from userfile where id="+fileID);
		
		if(rs.next()){
			
			String digestKey = rs.getString("digestKey");
			String fileName = rs.getString("fileName");
			
			CopyToServer2 CTS = new CopyToServer2();
			CTS.CopyToFolde2(fileName);
		
     		
		}
		
		
		
		
		
	}catch(Exception e){
		out.println(e);
	}finally{
		
		out.close();
	}
	
	
}
	

}
