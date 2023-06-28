 

package poll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Hex;

import poll.ConnectionProvider;


public class GlobalFunction {

	Connection con = ConnectionProvider.getConn();

	Statement st = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt1 = null;
	PreparedStatement pstmt2 = null;
	ResultSet rs = null;
	private boolean flag;

	public String getRole(int id) throws SQLException {

		String data = "";

		st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from role where id=" + id);
		while (rs.next()) {

			data = rs.getString("roleName");

		}
		return data;

	}
	public void setGraphTime(int fid,float uploadTime,float EncryptTime,int user_id) throws SQLException{
	   	   
        try{
        st = con.createStatement();
        String sqlss = "INSERT INTO graphtbl(fileid,uploadTime,EncryptTime,user_id) VALUE("+fid+","+uploadTime+","+EncryptTime+",'"+user_id+"')";
        //System.out.println("enr========="+sqlss);
        st.executeUpdate(sqlss);
        
    }catch (SQLException e) {
             System.out.println("Error:" + e);
         }
         
    }
	public static void copyFile(File sourceFile, File destFile)
			throws IOException {
		if (!sourceFile.exists()) {
			return;
		}
		if (!destFile.exists()) {
			destFile.createNewFile();
		}
		FileChannel source = null;
		FileChannel destination = null;
		source = new FileInputStream(sourceFile).getChannel();
		destination = new FileOutputStream(destFile).getChannel();
		if (destination != null && source != null) {
			destination.transferFrom(source, 0, source.size());
		}
		if (source != null) {
			source.close();
		}
		if (destination != null) {
			destination.close();
		}
	}

	public String getFullName(int Userid) throws SQLException {

		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from user where id="
					+ Userid);
			if (rs.next()) {
				data = rs.getString("fname") + " " + rs.getString("lname");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}

	public static String getDigest(InputStream is, MessageDigest md,
			int byteArraySize) throws NoSuchAlgorithmException, IOException {

		System.out.println("---------------------------");
		md.reset();
		byte[] bytes = new byte[byteArraySize];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			md.update(bytes, 0, numBytes);
		}
		byte[] digest = md.digest();
		String result = new String(Hex.encodeHex(digest));
		return result;
	}

	public int getLatestFileID() throws SQLException {

		int data = 0;
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select id from userfile ORDER BY id DESC LIMIT 1");
			if (rs.next()) {
				data = rs.getInt("id");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}

	public String getLatestUserID() throws SQLException {

		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select id from user ORDER BY id DESC LIMIT 1");
			if (rs.next()) {
				data = rs.getString("id");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}

	public static void CreateDirectory(String FolderName) {
		File file = new File("C:\\upload\\" + FolderName);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
	}

	public String getUserEmail(int Userid) throws SQLException {

		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select email from user where id='"
					+ Userid + "'");
			if (rs.next()) {
				data = rs.getString("email");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}

	public String getRoleName(int RoleID) throws SQLException {
		System.out.println("---------RoleID------" + RoleID);
		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select roleName from role where id="
							+ RoleID);
			if (rs.next()) {
				data = rs.getString("roleName");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}

	public String getUserRole(int Userid) throws SQLException {

		String data = "";
		int UserRoleID = 0;
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select UserRole from user where id="
							+ Userid);
			if (rs.next()) {
				UserRoleID = rs.getInt("UserRole");

				data = getRoleName(UserRoleID);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}

	public int getUserRoleID(int Userid) throws SQLException {

		int data = 0;
		int UserRoleID = 0;
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select UserRole from user where id="
							+ Userid);
			if (rs.next()) {
				data = rs.getInt("UserRole");

				
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}

	public String getFileName(int Fileid) throws SQLException {

		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select fileName from userfile where id="
							+ Fileid);
			if (rs.next()) {
				data = rs.getString("fileName");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}

	public String getFileExtention(File file) {

		String name = file.getName();
		try {
			return name.substring(name.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}

	}

	public boolean checkAdminLogin(String username, String password)
			throws SQLException {

		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from admin where name='"
					+ username + "' and password='" + password + "'");
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return false;
	}

	public int checkAuthorised(int fileID, int UserID) throws SQLException {
		int data = 0;
		try {
			st = con.createStatement();
			String strss1 = Integer.valueOf(UserID).toString();

			ResultSet rs = st.executeQuery("select * from userfile where id="
					+ fileID);
			if (rs.next()) {
				String[] Authorised = rs.getString("AuthorisedGroup")
						.split(",");
				for (int i = 0; i < Authorised.length; i++) {

					String strss = Authorised[i];

					if (strss.equalsIgnoreCase(strss1)) {

						data = Integer.parseInt(strss);

						return data;
					}

				}
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;
	}

	public boolean VerifySecretCode(int id, String SecretCode)
			throws SQLException {

		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from user where id=" + id
					+ " and SecretKey='" + SecretCode + "'");
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return false;
	}

	public boolean VerifyFileSecretCode(int Fileid, String SecretCode)
			throws SQLException {

		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from userfile where id="
					+ Fileid + " and SecKey='" + SecretCode + "'");
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return false;
	}

	public void setEncryptionTime(int fid, float ExcutionTime)
			throws SQLException {

		try {
			st = con.createStatement();
			String sqlss = "INSERT INTO encryption(fileId,ExcutionTime) VALUE("
					+ fid + "," + ExcutionTime + ")";
			System.out.println("enr=========" + sqlss);
			st.executeUpdate(sqlss);

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}

	}

	public void setDecryptionTime(int fid, float ExcutionTime)
			throws SQLException {

		try {
			st = con.createStatement();
			String sqlss = "INSERT INTO decryption(fileId,ExcutionTime) VALUE("
					+ fid + "," + ExcutionTime + ")";
			System.out.println("enr=========" + sqlss);
			st.executeUpdate(sqlss);

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}

	}

	public String getSecretCode(int id) throws SQLException {

		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select SecretKey from user where id=" + id);
			if (rs.next()) {
				data = rs.getString("SecretKey");
				return data;
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;
	}

	public String getGroupName(String id) throws SQLException {

		String grpid = "";
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select GroupID from user where id="
					+ id);
			if (rs.next()) {

				grpid = rs.getString("GroupID");
				return grpid;
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return grpid;
	}

	public String GroupName(String id) throws SQLException {

		String grpid = "";
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select GroupID from user where id="
					+ id);
			if (rs.next()) {

				grpid = rs.getString("GroupID");
				return grpid;
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return grpid;
	}

	public int getFromUser(int Requestid) throws SQLException {

		int data = 0;
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select fromUser from filerequest where id="
							+ Requestid);
			if (rs.next()) {
				data = rs.getInt("fromUser");
				return data;
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;
	}

	public int getRequestStatus(int Requestid) throws SQLException {

		int data = 0;
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select status from filerequest where id="
							+ Requestid);
			if (rs.next()) {
				data = rs.getInt("status");
				return data;
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;
	}

	public String getRequestDate(int Requestid) throws SQLException {

		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select req_date from filerequest where id="
							+ Requestid);
			if (rs.next()) {
				data = rs.getString("req_date");
				return data;
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;
	}

	public String getFileAccessLevel(int Fileid) throws SQLException {

		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from userfile where id="
					+ Fileid);
			if (rs.next()) {
				data = rs.getString("AccessLevel");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}

	public String getUserGroupName(String Gid) throws SQLException {

		String data = "";
		
		System.out.println("Group id is Hello:" + Gid);
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from groupuser where id="
					+ Gid);
			if (rs.next()) {

				return data = rs.getString("GroupName");

				// data = getRoleName(GroupName);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}
	public String UserGroupName(String Gid) throws SQLException {
		String data = "";
		String GroupName = "";
		System.out.println("user id is Hello:" + Gid);
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from groupuser where id="
					+ Gid);
			if (rs.next()) {

				return data = rs.getString("GroupName");

				// data = getRoleName(GroupName);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}
	public String getNumberOfUserInGroup(String GroupID) throws SQLException {

		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select count(id) from user where GroupID='"
							+ GroupID + "' and Usertype='User'");
			if (rs.next()) {
				data = rs.getString("count(id)");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}

	public void setShareFileHistory(int fileId, int UserId, String userEmail,
			int GroupId, String key, String regDate, int keysequence)
			throws SQLException {

		try {
			st = con.createStatement();
			String sqlss = "INSERT INTO fileshared(fileId,UserId,userEmail,GroupId,devidedkey,regDate,keysequence) VALUE("
					+ fileId
					+ ","
					+ UserId
					+ ",'"
					+ userEmail
					+ "',"
					+ GroupId
					+ ",'" + key + "','" + regDate + "'," + keysequence + ")";

			st.executeUpdate(sqlss);

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}

	}

	public String getUserGroupID(int UserID) throws SQLException {

		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from user where id="
					+ UserID);
			if (rs.next()) {
				data = rs.getString("GroupID");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}

	public Boolean checkFileIDUserIDFromFileShared(int fileid, int UserID)
			throws SQLException {

		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from fileshared where fileId="
							+ fileid + " and UserId=" + UserID);

			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return false;

	}

	public String getSharedKey(int UserID, int fileId) throws SQLException {

		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from fileshared where UserId="
							+ UserID + " and fileId=" + fileId);
			if (rs.next()) {
				data = rs.getString("devidedkey");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}

	public Boolean checkFileRequest(int fileid, int UserID) throws SQLException {

		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from filerequest where Userto="
							+ UserID + " and fileID=" + fileid);
			System.out.println("select * from filerequest where Userto="
					+ UserID + " and fileID=" + fileid);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return false;

	}

	public int getRequestFromUserID(int UserID, int fileId) throws SQLException {

		int data = 0;
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from filerequest where Userto="
							+ UserID + " and fileID=" + fileId);
			if (rs.next()) {
				data = rs.getInt("fromUser");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;

	}

	public String getDataOwnerKey(int fileId) throws SQLException {
		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from userfile where id="
					+ fileId);
			if (rs.next()) {
				data = rs.getString("UserSecretKey");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;
	}

	public String getPublicKey(int fileId) throws SQLException {
		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from userfile where id="
					+ fileId);
			if (rs.next()) {
				data = rs.getString("CloudSecretKey");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return data;
	}

	public Boolean checkAuthorisedUser(int fileOwner, int UserID)
			throws SQLException {

		String data = "";
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from user where GroupID=(select GroupID from user where id="
							+ fileOwner + ") and id=" + UserID);

			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return false;

	}
	public void setGraphTime1(int fid,float uploadTime,float EncryptTime,String user_id,String filename) throws SQLException
	{
		System.out.println("qq fid "+fid);
  	  System.out.println("qq  upload time "+uploadTime );
  	  System.out.println("q q enc type "+EncryptTime);
  	  System.out.println("qq	user "+user_id);
  	  System.out.println("qq  fiel name "+filename);
	System.out.println("'He   llo");
	}
	public void setGraphTime(int fid,float uploadTime,float EncryptTime,String user_id,String filename) throws SQLException{
	 	System.out.println("set graph method");   
		
	       try{
	    	   
	    	  
	    	   
	    	   
	       st = con.createStatement();
	       String sqlss = "INSERT INTO graphtbl(fileid,uploadTime,EncryptTime,user_id,filename) VALUE("+fid+","+uploadTime+","+EncryptTime+",'"+user_id+"','"+filename+"')";
	       //System.out.println("enr========="+sqlss);
	       st.executeUpdate(sqlss);
	       
	       
	       
	       System.out.println("Done");
	   }catch (SQLException e) {
	            System.out.println("Error:" + e);
	        }
	        
	   }
	
	
	public void setGraphDownloadTime(int fid,float uploadTime,float EncryptTime,String user_id) throws SQLException{
	 	   
	       try{
	    	   System.out.println("select * from graphtbl2 where fileid="+fid);
	    	   System.out.println(uploadTime);
	    	   System.out.println(EncryptTime);
	    	   System.out.println(user_id);
	    	 
	    	   PreparedStatement ps=con.prepareStatement("delete from graphtbl2 where user_id=?");
	    	   ps.setString(1, user_id);
	    	   ps.executeUpdate();
	       st = con.createStatement();
	       String sqlss = "INSERT INTO graphtbl2(fileid,downloadTime,DecryptTime,user_id) VALUE("+fid+","+uploadTime+","+EncryptTime+",'"+user_id+"')";
	       //System.out.println("enr========="+sqlss);
	       st.executeUpdate(sqlss);
	       
	   }catch (SQLException e) {
	            System.out.println("Error:" + e);
	        }
	    	
	    	  
	    	  
	 
	       
	        
	   }
	
	
	
	
	
	
	
	

}