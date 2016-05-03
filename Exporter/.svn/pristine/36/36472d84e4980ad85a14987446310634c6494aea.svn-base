package MaptoolExporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * 
 * @author feldmaus
 *

public class md5Helper {
	public static byte[] createChecksum(String filename) throws Exception {
		InputStream fis =  new FileInputStream(filename);
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;
		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
		 	}
	 	} while (numRead != -1);
		fis.close();
		return complete.digest();
	}
	
	public static String getMD5Checksum(String filename) throws Exception {
		byte[] b = createChecksum(filename);
		String result = "";
		for (int i=0; i < b.length; i++) {
			result +=
				Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}
	
*/



public class md5Helper {
	//Intern, weil ich die Checksumme der thumbnail INNERHALB der .jar brauche, nicht die einer Datei irgendwo innerhalb des Dateisystemes
	private static byte[] createChecksumIntern(File filename) throws Exception {
		InputStream fis =  new FileInputStream(filename);
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;
		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
		 	}
	 	} while (numRead != -1);
		fis.close();
		return complete.digest();
	}
	
	public static String getMD5ChecksumIntern(File filename) throws Exception {
		byte[] b = createChecksumIntern(filename);
		String result = "";
		for (int i=0; i < b.length; i++) {
			result +=
				Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}

}
