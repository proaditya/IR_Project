package eclassify;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.util.SharedByteArrayInputStream;

public class EmailExtract {

	String file_name;
	MimeMessage message;
	
	public EmailExtract(String _file_name) throws IOException, MessagingException {
		file_name = _file_name;
		processMessage();
	}
	
	
	
	String getContent() throws MessagingException, IOException
	{
		String tempp = message.getContentType();
		if(message.getContent() instanceof String)
		{
			
			if(message.getContentType().startsWith("text/plain"))
				return pre_process_HTML(message.getContent().toString());
			else if(message.getContentType().startsWith("text/html"))
			{
				String htmlString = message.getContent().toString();
				String noHTMLString = pre_process_HTML(htmlString);
				return noHTMLString;
			}
		}
		else if(message.getContent() instanceof Multipart)
		{
			String temp = "";
			Multipart mp = (Multipart)message.getContent();
			for(int i=0;i<mp.getCount();i++)
			{
				BodyPart b = mp.getBodyPart(i);
				//ignoring non text parts of the email such as HTML
				String tempp1 = b.getContentType();
				if(tempp1.startsWith("text/plain"))
					temp+=pre_process_HTML(b.getContent().toString()+ "\n");
				else if(tempp1.startsWith("text/html"))
					temp+=pre_process_HTML(b.getContent().toString());
			}
			return temp;
		}
		else
		{
			if(message.getContent() instanceof SharedByteArrayInputStream)
			{
				SharedByteArrayInputStream byt = (SharedByteArrayInputStream)message.getContent();
				MimeMessage mess = new MimeMessage(Session.getDefaultInstance(new Properties()), byt);
				String ttt = mess.getContentType();
				String t = mess.getContent().toString();
				String temp = message.getContent().toString();
				return pre_process_HTML(t);
			}
		}
		return "";
	}
	
	String pre_process_HTML(String str)
	{
//		String temp = str.replaceAll("\\<.*?\\>", "");
//		temp.replaceAll("nbsp", "");
		return Utils.strip(str);
	}
	
	String getFromAddress() throws MessagingException
	{
		return message.getFrom().toString();
	}
	
	String getToAddress() throws MessagingException
	{
		return message.getAllRecipients().toString();
	}
	
	
	private void processMessage() throws IOException, MessagingException {
		// TODO Auto-generated method stub
		String content =readFileAsString(file_name);
		Session s = Session.getDefaultInstance(new Properties());
		InputStream is = new ByteArrayInputStream(content.getBytes());
		this.message = new MimeMessage(s,is);
	}


	private static String readFileAsString(String filePath) throws java.io.IOException{
	    byte[] buffer = new byte[(int) new File(filePath).length()];
	    BufferedInputStream f = new BufferedInputStream(new FileInputStream(filePath));
	    f.read(buffer);
	    return new String(buffer);
	}
}
