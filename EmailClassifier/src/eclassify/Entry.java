package eclassify;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class Entry {

	public Entry() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws MessagingException, IOException {
		// TODO Auto-generated method stub
		String filename = "/home/aditya/Downloads/EmailTest/Training/att/15";
		String content =readFileAsString(filename);
		Session s = Session.getDefaultInstance(new Properties());
		InputStream is = new ByteArrayInputStream(content.getBytes());
		MimeMessage message = new MimeMessage(s,is);
		System.out.println(message.getContent().toString());
//		for (Enumeration<Header> e = message.getAllHeaders(); e.hasMoreElements();) {
//		    Header h = e.nextElement();
//		    System.out.print(h.getName());
//		    System.out.println(h.getValue());
//		}
	}
	
	private static String readFileAsString(String filePath) throws java.io.IOException{
	    byte[] buffer = new byte[(int) new File(filePath).length()];
	    BufferedInputStream f = new BufferedInputStream(new FileInputStream(filePath));
	    f.read(buffer);
	    return new String(buffer);
	}

}
