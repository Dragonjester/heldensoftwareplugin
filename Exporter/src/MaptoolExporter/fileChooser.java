package MaptoolExporter;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class fileChooser {
	public static File FileChooser(){
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter( new FileFilter()
		{
			@Override public boolean accept( File f )
			{
				return f.isDirectory() || f.getName().toLowerCase().endsWith( ".rptok" );
			}
			@Override public String getDescription()
			{
				return "MapTool Tokendateien (*.rptok)";
			}
		} );
		int state = fc.showDialog(null, "speichern");
		if ( state == JFileChooser.APPROVE_OPTION )
		{
			File file = fc.getSelectedFile();
			String filePath = file.getPath();
			
			if(!file.getPath().contains(".rptok")){
				filePath += ".rptok";
			}
			
			de.CAA.utils.msgbox a = new de.CAA.utils.msgbox(filePath);
			
			
			return new File(filePath);
		}
		else
			return null;
	}
}
