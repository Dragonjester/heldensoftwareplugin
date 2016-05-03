package gui;


import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import de.CAA.utils.msgbox;

import MaptoolExporter.exporter;
import MaptoolExporter.fileChooser;

import logic.chareinlesen;

import versControll.versControll;

@SuppressWarnings("serial")
public class Hauptfenster extends JFrame{

	/*
	 * erwachsen aus dem Umstand dass es zu Anfang nur das eine Fenster gab... ist dann aber irgendwie mehr geworden...
	 * muss vll mal erneuert werden, was diese Klasse ist und macht.....
	 * lol
	 */
	public Hauptfenster(Document ret, int menuIdx) throws TransformerException
	{

		if(menuIdx == 0){
			JPanel Flaeche = new JPanel();
			JP_Maptooltool maptool;
			this.setTitle("CAAs DSA-TOOL     Vers " + versControll.currentVers);
			this.add(Flaeche);

			Flaeche.setLayout(new BorderLayout());
			maptool = new JP_Maptooltool(ret);
			Flaeche.add(maptool);

			this.setVisible(true);
			this.setSize(500,450);
		}else if(menuIdx == 1){
			new Wahrscheinlichkeitsfenster();
		}else if(menuIdx == 2){
		//	JOptionPane.showMessageDialog(null, "!under construction!", "!!ACHTUNG!!", JOptionPane.OK_CANCEL_OPTION);
			File pfad = fileChooser.FileChooser();
			if(pfad != null){
				exporter exportiere = new exporter(ret, pfad);
			}
		}else if(menuIdx == 3){
			new StatistikAuswahlFrame(ret);
			//		                new CharAustauschLogin().setVisible(true);
		}
	}
}