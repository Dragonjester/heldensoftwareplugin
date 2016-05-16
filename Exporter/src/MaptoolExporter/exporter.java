package MaptoolExporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import logic.ausgabeErstellen;
import logic.chareinlesen;
import logic.eigenschaft;
import logic.talent;

import org.w3c.dom.Document;

import de.CAA.utils.msgbox;


import java.io.InputStream;

public class exporter {
	String result;
	chareinlesen derChar;
	public exporter(Document ret, File pfad){
		try {
			derChar = new chareinlesen();
			derChar.Dateiauswahl(ret);
			ContentXmlCreator content = new ContentXmlCreator(derChar);
			String contentXML = content.createContent();


			File contentFile = new File("content.xml");

			FileOutputStream a = new FileOutputStream(contentFile);
			a.write(contentXML.getBytes());
			a.close();

	
			File propertiesFile = new File("properties.xml");
			a = new FileOutputStream(propertiesFile);
			a.write("<map><entry><string>version</string><string>1.3.b63</string></entry></map>".getBytes());
			a.close();

			
			boolean thumbnailFound = getClass().getResourceAsStream("/thumbnail") == null;
			boolean assetXMLFound = getClass().getResourceAsStream("/assets/f427aa49b28500217ac46ba37c2998a4") == null;
			boolean assetPictureFound = getClass().getResourceAsStream("/assets/f427aa49b28500217ac46ba37c2998a4.png") == null;
	
			if(thumbnailFound == true){
				JOptionPane.showMessageDialog(new JFrame(), "Nicht extrahiert: Thumbnail", "Fehler", JOptionPane.INFORMATION_MESSAGE);
			}
			
			if(assetXMLFound == true){
				JOptionPane.showMessageDialog(new JFrame(), "Nicht extrahiert: assetXML", "Fehler", JOptionPane.INFORMATION_MESSAGE);
			}
			
			if(assetPictureFound == true){
				JOptionPane.showMessageDialog(new JFrame(), "Nicht extrahiert: assetPicture", "Fehler", JOptionPane.INFORMATION_MESSAGE);
			}
			
			
			File thumbnailFile = inStreamToFile(getClass().getResourceAsStream("/thumbnail"), "thumbnail");
			
			File assetXML = inStreamToFile(getClass().getResourceAsStream("/assets/f427aa49b28500217ac46ba37c2998a4"), "f427aa49b28500217ac46ba37c2998a4");
			File assetPicture = inStreamToFile(getClass().getResourceAsStream("/assets/f427aa49b28500217ac46ba37c2998a4.png"), "f427aa49b28500217ac46ba37c2998a4.png");
			
			Vector zipFiles = new Vector();
			
			zipFiles.add(propertiesFile);
			zipFiles.add(assetXML);
			zipFiles.add(assetPicture);
			zipFiles.add(contentFile);
			zipFiles.add(thumbnailFile);
			Zip.packArchive(pfad, zipFiles);

			propertiesFile.delete();
			contentFile.delete();
			assetXML.delete();
			assetPicture.delete();

			JOptionPane.showMessageDialog(new JFrame(), "Export erfolgreich", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(new JFrame(), e1.getClass() + "\n" + stackTraceToString(e1.getStackTrace()), "FEHLER", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	
	public static String stackTraceToString(StackTraceElement[] stackTrace) {
        StringWriter sw = new StringWriter();
        printStackTrace(stackTrace, new PrintWriter(sw));
        return sw.toString();
    }
	
    public static void printStackTrace(StackTraceElement[] stackTrace, PrintWriter pw) {
        for(StackTraceElement stackTraceEl : stackTrace) {
            pw.println(stackTraceEl);
        }
    }
	
	public static File inStreamToFile(InputStream in, String fileName) throws FileNotFoundException, IOException {
		File f = new File(fileName);
		
		if(in == null){
			System.out.println("Inputstream ist NULL");
			return null;
		}
		
		f.deleteOnExit();
		OutputStream out = new FileOutputStream(f);
		byte buf[] = new byte[4096];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		out.close();

		return f;
	}
	

}