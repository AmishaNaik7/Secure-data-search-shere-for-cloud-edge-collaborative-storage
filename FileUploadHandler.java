
package poll;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import poll.AESFileEncryption;

@WebServlet("/FileUploadHandler")
public class FileUploadHandler extends HttpServlet {
   private final String UPLOAD_DIRECTORY = ConnectionProvider.path;//"E:/upload";
   private final String UPLOAD_DIRECTORY1 = "D:/upload/proxy";
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   
	   HttpSession session = request.getSession();
       //process only if its multipart content
       if(ServletFileUpload.isMultipartContent(request)){
           try {
        	   
        	   long startUT = 0, endtimeUT = 0;
               long elapsedTimeMillisUT = 0;
               float elapsedTimeSecUT = 0;
               
               long startET = 0, endtimeET = 0;
               long elapsedTimeMillisET = 0;
               float elapsedTimeSecET = 0;
               
               
               
        	   startUT=System.currentTimeMillis();
        	   
        	   
               List<FileItem> multiparts = new ServletFileUpload(
                                        new DiskFileItemFactory()).parseRequest(request);
               DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
               Date date = new Date();            
               String Addeddate=dateFormat.format(date);
               String u_id=(String)session.getAttribute("userId");
               int UserID = Integer.parseInt(u_id);
               Connection con = ConnectionProvider.getConn(); 
               GlobalFunction GF = new GlobalFunction();
               
               String FileName = "";
               String Userid = "";
               String SecKey = "";
               String identity = "";
               String FileExtention = "";
               String FileData = "";
               long FileSize = 0;
               //String digest = "test";
               
               
               for(FileItem item : multiparts){
                   if(!item.isFormField()){
                	   
                       String name = new File(item.getName()).getName();
                       item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
                       
                       FileName = item.getName();
                       FileExtention = item.getContentType();
                       startUT=System.currentTimeMillis();
                       FileName = item.getName();
                       FileSize = item.getSize();
                       float Fsize = (float)FileSize / 60;
                       elapsedTimeSecUT = Fsize / 1000F;
                       
                       
                   }else{
                	   
                	   String UKey = item.getFieldName();
                	   
                	  
                	   
                	   
                	   if(UKey.equalsIgnoreCase("SecKey")){
                		   SecKey = item.getString();
                		   
                	   }
                	   if(UKey.equalsIgnoreCase("identity")){
                		   identity = item.getString();
                		   
                	   }
                	   
                   }
               }
               
               endtimeUT = System.currentTimeMillis();
               
               String pp = UPLOAD_DIRECTORY+"/"+FileName;
               
               File path = new File(pp);
               
               BufferedReader br = new BufferedReader(new FileReader(UPLOAD_DIRECTORY+"/"+FileName));
               try {
                   StringBuilder sb = new StringBuilder();
                   String line = br.readLine();
                   startET=System.currentTimeMillis();

                   while (line != null) {
                       sb.append(line);
                       sb.append(System.lineSeparator());
                       line = br.readLine();
                   }
                   
                   startET=System.currentTimeMillis();
                   
                   String encData = sb.toString();

                   AESAlgorithm tes = new AESAlgorithm();
                   AESFileEncryption AES = new AESFileEncryption();
                   AES.fileEncrypt(path);
                   
                  // FileData = tes.encrypt(encData,"mySalt");
                   
                   
                   
                   endtimeET = System.currentTimeMillis();
                   
                   elapsedTimeMillisET = endtimeET - startET;
                   elapsedTimeSecET = (elapsedTimeMillisET / 500F);
                   System.out.println("Encryption Time-------"+elapsedTimeSecET);
                   
                   //System.out.println("-----FileData----"+FileData);
                   
               } finally {
                   br.close();
                  
               }
               
               
               MessageDigest md = MessageDigest.getInstance("MD5");
               System.out.println("-----afterObj----"+UPLOAD_DIRECTORY+"/"+FileName);
       		String digest = GF.getDigest(new FileInputStream(UPLOAD_DIRECTORY+"/"+FileName), md, 2048);
       		
       		System.out.println("-----digest----"+digest);
       		endtimeUT = System.currentTimeMillis();
       		elapsedTimeMillisUT = (endtimeUT - startUT);
       		System.out.println("Upload Time--------"+elapsedTimeSecUT);
       		
               System.out.println("Connection created ");
               PreparedStatement st=con.prepareStatement("insert into userfile(uploadedBy,fileName,uploadDate,type,FileSize,digestKey,secKey,identity) values(?,?,?,?,?,?,?,?)");
               
               st.setInt(1,UserID);
               st.setString(2, FileName);
                st.setString(3, Addeddate);
                st.setString(4, FileExtention);                 
                st.setLong(5, FileSize);
                st.setString(6, digest);
               // st.setString(7, FileData);
                st.setString(7, SecKey);
                st.setString(8, identity);
                
            /*st.executeUpdate();*/
            int s = st.executeUpdate();
            if(s>0){
            	int lfid = GF.getLatestFileID();
            	
            	GF.setGraphTime(lfid, elapsedTimeSecUT, elapsedTimeSecET,UserID);
            	
            }
            CopyToServer1 CTS = new CopyToServer1();
			CTS.CopyToFolder1(FileName);
            
            int lfid = GF.getLatestFileID();
            
            System.out.println("lf id "+lfid);
            System.out.println("elapsedTimeMillisUT "+elapsedTimeMillisUT);
            System.out.println("elapsedTimeSecUT "+elapsedTimeSecUT);
            System.out.println("u id "+u_id);
            System.out.println("File name "+FileName);   
              request.setAttribute("message", "File Uploaded Successfully");
           } catch (Exception ex) {
              request.setAttribute("message", "File Upload Failed due to " + ex);
           }          
        
       }else{
           request.setAttribute("message",
                                "Sorry this Servlet only handles file upload request");
       }
   
       request.getRequestDispatcher("/UploadFile.jsp?status=success").forward(request, response);
    
   }
 
}