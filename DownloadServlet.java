
package poll;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import poll.AESFileEncryption;
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			
			
			HttpSession session=request.getSession();
			//String f_id=(String)session.getAttribute("f_id");
			String Userid = (String)session.getAttribute("userId");
			
			System.out.println("user id "+Userid);
			
			
			String file_name = request.getParameter("fileName");
			System.out.println("File name is "+file_name);
			String file_id = request.getParameter("fileId");
			System.out.println("File id rts is "+file_id);
			long start = System.currentTimeMillis();
			
			// String file_name = "";
			Connection con = ConnectionProvider.getConn();
			
			Statement st = con.createStatement();
			Statement stat = con.createStatement();
			GlobalFunction GF = new GlobalFunction();

			
			AESAlgorithm tes = new AESAlgorithm();

			
			/*startDT = System.currentTimeMillis();*/
			
			
			FileOutputStream fos11 = new FileOutputStream(ConnectionProvider.path+"//D_"+ file_name);
			byte[] array1 = new byte[1];
			byte[] buffer = new byte[1];

			
			AESFileEncryption en = new AESFileEncryption();
			File file1 = new File(ConnectionProvider.path+"//" + file_name);
			File file2 = new File(ConnectionProvider.path+"//D_" + file_name);
			en.fileDecrypt(file1, file2);
			 long end = System.currentTimeMillis();
             long sum = end - start;
             float sum1=sum/1000F;
            
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			
			String filepath = ConnectionProvider.path;//"E:\\upload\\";
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + "\\D_" + file_name + "\"");

			FileInputStream fileInputStream = new FileInputStream(filepath+ "\\D_" + file_name);

			int i;
			while ((i = fileInputStream.read()) != -1) {
				out.write(i);
			}
			long end2 = System.currentTimeMillis();
	         long sum21 = end2 - end; 
	         float sum2=sum21/1000F;
	         System.out.println("Decryption Time-------"+sum2);
	         System.out.println("Download Time--------"+sum1);
	         st = con.createStatement();
	         String sqlss = "INSERT INTO graphtbl2(fileid,downloadTime,DecryptTime,user_id) VALUE("+file_id+","+sum1+","+sum2+",'"+Userid+"')";
	         //System.out.println("enr========="+sqlss);
	         st.executeUpdate(sqlss);
	         
	         
	          
	         fileInputStream.close();   
	         out.close();   
	          
	           
		} catch (Exception e) {

			System.out.println(e);
		}

	}
}