package poll;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;


public class AESFileEncryption {

	
	public void fileEncrypt(File path) throws Exception{
		
		 FileReader file = new FileReader (path);
		    BufferedReader reader = new BufferedReader(file);

		    String text = "";
		    String line = reader.readLine();
		    while(line !=null)
		    {
		        text +=line; 
		        line = reader.readLine();
		    }

		    AESAlgorithm tes = new AESAlgorithm();
		    String test = tes.encrypt(text,"mySalt");
		    System.out.println("Encrypted : " + test);
		    

		    
			FileWriter fw = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(test);
			bw.close();

	}
	
	public void fileDecrypt(File path,File path2) throws Exception{
		
		FileReader file = new FileReader (path);
	    BufferedReader reader = new BufferedReader(file);

	    String text = "";
	    String line = reader.readLine();
	    while(line !=null)
	    {
	        text +=line; 
	        line = reader.readLine();
	    }

	    AESAlgorithm tes = new AESAlgorithm();
	    String test = tes.decrypt(text,"mySalt");
	    System.out.println("Decrypted : " + test);
	    
		FileWriter fw = new FileWriter(path2);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(test);
		bw.close();

		
	}
	

	public static void main(String[] args) throws Exception{
		
		AESFileEncryption en = new AESFileEncryption();
		File file = new File("C://apache-tomcat-7.0.42//tmpfiles//file.txt");
		//en.fileDecrypt(file);
	}
	
	
}
