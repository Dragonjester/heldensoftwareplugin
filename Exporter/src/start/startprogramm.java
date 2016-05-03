package start;
import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import versControll.versControll;

import gui.Hauptfenster;
import gui.Wahrscheinlichkeitsfenster;
import helden.plugin.HeldenXMLDatenPlugin;
import helden.plugin.datenxmlplugin.DatenAustauschImpl;
import helden.plugin.HeldenXMLDatenPlugin;
import helden.plugin.datenxmlplugin.DatenAustauschImpl;

public class startprogramm implements HeldenXMLDatenPlugin{
	public static void main(String[] args) {
	}


	public ArrayList<String> getUntermenus() {
		ArrayList<String> liste = new ArrayList<String>();
		liste.add("Würfeltool");
		liste.add("Wahrscheinlichkeitsrechner");
		liste.add("Maptool Exporter (2.0beta)");
		liste.add("Häufigkeiten");
		return liste; 
	}



	public String getToolTipText() {
		return "Erstellt Talentproben-Makros für Maptools";
	}

	public ImageIcon getIcon() {
		return null;
	}

	public startprogramm() {
		super();
	}

	public String getMenuName() {
		return "CAAs DSA-Tool";
	}

	public void doWork(JFrame f) {
	}

	public void doWork(JFrame frame, Integer menuIdx, DatenAustauschImpl dai) {
		if(versControll.check()){
			try {
				JOptionPane.showMessageDialog(null, "Es ist eine neue Version verfügbar.\nDiese muss, sofern gewünscht, manuell heruntergeladen und installiert werden.", "CAAs Würfeltool", JOptionPane.OK_CANCEL_OPTION);
				Desktop.getDesktop().browse(new URI("http://dl.dropbox.com/u/377944/Würfeltool/index.html"));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Leider konnte die Seite nicht automatisch aufgerufen werden. \n Neue Vers Downloadbar unter \n http://tinyurl.com/3ajb9dx", "CAAs Würfeltool", JOptionPane.OK_CANCEL_OPTION);
			}
		}

		if(menuIdx == 2){

		}
		if(menuIdx == 0 ||menuIdx == 1 || menuIdx == 2 || menuIdx == 3){
			org.w3c.dom.Document request;
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			try {
				request = factory.newDocumentBuilder().newDocument();
			} catch (Exception ex) {
				request = null;
			}
			Element requestElement = request.createElement("action");
			request.appendChild(requestElement);
			requestElement.setAttribute("action", "held");
			requestElement.setAttribute("id", "selected");
			requestElement.setAttribute("format", "xml");
			// Ãœber die exec-Anweisung kÃ¶nnen Daten angefordert werden
			final org.w3c.dom.Document doc = (org.w3c.dom.Document) dai.exec(request);
			// Nun speichern wir die Daten als XML-Datei

			String ss = "/*";
			if ((ss != null) && (ss.length() > 0)) {
				// xpath fÃ¼r den Suchausdruck
				XPath xpath = XPathFactory.newInstance().newXPath();
				try  { 
					XPathExpression expr = xpath.compile(ss);
					Object result = expr.evaluate(doc, XPathConstants.NODESET);

					// Nun die ganzen Suchtreffer in ein neues Document
					NodeList nodes = (NodeList) result;
					Document ret;
					try {
						ret = factory.newDocumentBuilder().newDocument();
					} catch (Exception ex) {
						ret = null;
					}
					ret.appendChild(ret.createElement("result"));
					for (int i = 0; i < nodes.getLength(); i++) {
						ret.getDocumentElement().appendChild(ret.importNode(nodes.item(i), true));
					}
					// Und das Document sauber als XML ausgeben   name des docs: ret
					//new Hauptfenster(ret, menuIdx);
					new Hauptfenster(doc, menuIdx);
				} catch (Exception ex) {
					JFrame a = new JFrame();
					a.setSize(500,500);
					a.setVisible(true);
					ex.printStackTrace();
				}
			}
		}
	}	

	/**
	 * Gibt helden den Typ dieses Plugins
	 * @return SIMPLE
	 */
	public String getType() {
		return DATEN;

	}
}