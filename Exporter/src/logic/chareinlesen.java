package logic;


import java.io.Serializable;
import java.io.ObjectInputStream.GetField;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.CAA.utils.msgbox;


public class chareinlesen implements Serializable {
	public Document doc;
	public String zeilen[];
	public int anz = 0;
	public String nick = "";
	public int Lep = 0;
	public int Aup = 0;
	public int Asp = 0;
	public int Ka = 0;
	public int ini = 0;
	public int atBasis = 0; 
	public int paBasis = 0;
	public int fkBasis = 0;
	public int akrobatik = 0;
	public int Mr = 0;
	public int So = 0; //Sozialstatus
	public int RsGesamt = 0;
	public int RsBeinLinks = 0;
	public int RsBeinRechts = 0;
	public int RsBauch = 0;
	public int RsBrust = 0;
	public int RsKopf = 0;
	public int RsArmLinks = 0;
	public int RsArmRechts = 0;	
	public int RsRuecken = 0;
	public int Be = 0;
	public boolean balance;
	public boolean herausragendeBalance;
	public boolean gutAussehend;
	public boolean herausragendesAussehen;
	public boolean guterRuf;
	public boolean innererKompass;
	public boolean richtungssinn;
	public boolean wohlklang;
	public boolean SchlangenMensch;
	public boolean Tierfreund;
	public boolean HerausragenderSechsterSinn;
	public boolean zauberer = false;
	public Vector<String> dataTalente = new Vector<String>();
	public Vector<String> dataEigenschaften = new Vector<String>();
	boolean RepriElf;
	static public String at = "";
	static public String pa = "";
	static public String atWert = "";
	public int liturgiekenntniss;
	public String dukaten = "0";
	public String silber = "0";
	public String heller = "0";
	public String kreuzer = "0";


	static public  boolean tollpatsch = false;
	static public  boolean wildeMagie = false;

	int anz_eigenschaften = 0;
	String tempEigenschaften[] = new String[8];
	public LinkedList WaffenListe = new LinkedList();
	public LinkedList WaffenListeAT = new LinkedList();
	public LinkedList WaffenListePA = new LinkedList();
	public LinkedList<talent> talente = new LinkedList();
	public LinkedList<talent> talenteNachKategorien[] = new LinkedList[12];
	public LinkedList<eigenschaft> eigenschaften = new LinkedList();
	public LinkedList<String> Vorteile = new LinkedList();
	public LinkedList<String> Nachteile = new LinkedList();
	public LinkedList<String> Sfs = new LinkedList();

	public int athletikWert;

	public void Dateiauswahl(Document ret) {
		at = "";
		pa = "";
		atWert = "";
		this.doc = ret;
		gogo();
	}

	public  void gogo ()
	{
		try {
			doc.getDocumentElement().normalize();
			angaben();
			addTalentClasses();
			eigenschaften(); 
			talente();
			metatalente();
			zauber();
			talentspezialisierungen();
			vorteile();
			metaEigenschaften();
			liturgien();
			kampfWerte();
			sonderfertigkeiten();
			geldBörse();

			try{
				createReg();
			}catch(Exception e){
				JOptionPane.showMessageDialog(new JFrame(), e.getMessage() + "\n" + e.toString() + "\n createreg" + "\n " + e.getLocalizedMessage());
			}
			fillData();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage() + "\n" + e.toString() + "\n " + e.getLocalizedMessage());
		}
	}
	private void geldBörse() {
		if(xPath("/daten/geld/Dukat/anzahl").getLength() > 0)
			dukaten = getVeryFirstByXPath("/daten/geld/Dukat/anzahl");

		if(xPath("/daten/geld/Silbertaler/anzahl").getLength() > 0)
			silber = getVeryFirstByXPath("/daten/geld/Silbertaler/anzahl");

		if(xPath("/daten/geld/Heller/anzahl").getLength() > 0)
			heller = getVeryFirstByXPath("/daten/geld/Heller/anzahl");

		if(xPath("/daten/geld/Kreuzer/anzahl").getLength() > 0)
			kreuzer = getVeryFirstByXPath("/daten/geld/Kreuzer/anzahl");

	}

	private void sonderfertigkeiten() {		
		NodeList nodeLst = xPath("/daten/sonderfertigkeiten/sonderfertigkeit/name");
		for(int i = 0; i < nodeLst.getLength(); i++){
			Sfs.add(nodeLst.item(i).getChildNodes().item(0).getTextContent());
			if(nodeLst.item(i).getChildNodes().item(0).getTextContent().contains("Astrale"))
				System.out.println(i + "");
		}
	}

	/*
	 * und Nachteile
	 */
	private void vorteileEinlesen() {
		NodeList nodeLst = doc.getElementsByTagName("vorteil");
		for(int i = 0; i < nodeLst.getLength(); i++){
			Node fstNode = nodeLst.item(i);
			NodeList chNodes = fstNode.getChildNodes();
			Node blub = chNodes.item(0);
			String bezeichnung = blub.getTextContent();

			blub = chNodes.item(3);
			if(blub.getTextContent().contains("t")){
				Vorteile.add(bezeichnung);	
			}else{
				Nachteile.add(bezeichnung);
			}

		}
	}

	private void kampfWerte() {

		NodeList kampfsets = xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"]");

		if(xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]").getLength() > 0){
			RsGesamt = Integer.parseInt(getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ruestungzonen/gesamtschutz"));
			
			RsBeinLinks = Integer.parseInt(getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ruestungzonen/linkesbein"));
			RsBeinRechts = Integer.parseInt(getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ruestungzonen/rechtesbein"));
			RsBauch = Integer.parseInt(getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ruestungzonen/bauch"));
			RsBrust = Integer.parseInt(getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ruestungzonen/brust"));
			RsKopf = Integer.parseInt(getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ruestungzonen/kopf"));
			RsArmLinks = Integer.parseInt(getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ruestungzonen/linkerarm"));
			RsArmRechts = Integer.parseInt(getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ruestungzonen/rechterarm"));
			RsRuecken = Integer.parseInt(getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ruestungzonen/ruecken"));
			
			ini = Integer.parseInt(getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ini"));



			Be = Integer.parseInt(getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ruestungzonen/behinderung"));

			NodeList nahkampfWaffenAt = xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/nahkampfwaffen[@inbenutzung=\"true\"]/nahkampfwaffe/at");
			NodeList nahkampfWaffenPa = xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/nahkampfwaffen[@inbenutzung=\"true\"]/nahkampfwaffe/pa");
			NodeList nahkampfWaffenDmg = xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/nahkampfwaffen[@inbenutzung=\"true\"]/nahkampfwaffe/tpinkl");
			NodeList nahkampfWaffenName = xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/nahkampfwaffen[@inbenutzung=\"true\"]/nahkampfwaffe/name");
			NodeList nahkampfWaffenDk =  xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/nahkampfwaffen[@inbenutzung=\"true\"]/nahkampfwaffe/dk");
			NodeList nahkampfWaffenIni =   xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/nahkampfwaffen[@inbenutzung=\"true\"]/nahkampfwaffe/ini");
			NodeList nahkampfWaffenBf =  xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/nahkampfwaffen[@inbenutzung=\"true\"]/nahkampfwaffe/bfmin");
			String at, pa, dmg, name, dk, ini, bf;
			for(int i = 0; i < nahkampfWaffenAt.getLength(); i++){
				name = nahkampfWaffenName.item(i).getChildNodes().item(0).getTextContent();
				dk = nahkampfWaffenDk.item(i).getChildNodes().item(0).getTextContent();
				ini = nahkampfWaffenIni.item(i).getChildNodes().item(0).getTextContent();
				at = nahkampfWaffenAt.item(i).getChildNodes().item(0).getTextContent();
				pa = nahkampfWaffenPa.item(i).getChildNodes().item(0).getTextContent();
				dmg = nahkampfWaffenDmg.item(i).getChildNodes().item(0).getTextContent();
				bf = nahkampfWaffenBf.item(i).getChildNodes().item(0).getTextContent();

				if(!WaffeSchonInListe(name))
					WaffenListe.add(new Waffe(name, at, pa, dmg, dk, ini, bf));
			}


			name = "Raufen";
			at = getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/raufen/at");
			pa = getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/raufen/pa");
			dmg = getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/raufen/tp");
			WaffenListe.add(new Waffe(name, at, pa, dmg, "H", "0", "0"));


			name = "Ringen";
			at = getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ringen/at");
			pa = getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ringen/pa");
			dmg = getVeryFirstByXPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/ringen/tp");
			WaffenListe.add(new Waffe(name, at, pa, dmg, "H", "0", "0"));


			//WaffenListe.add(new Waffe("Ausweichen", "0", akrobatik + "", "0", "H", "0", "0"));

			NodeList fkWaffenName = xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/fernkampfwaffen[@inbenutzung=\"true\"]/fernkampfwaffe/name");
			NodeList fkWaffenAt = xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/fernkampfwaffen[@inbenutzung=\"true\"]/fernkampfwaffe/at");
			NodeList fkWaffenDmg = xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/fernkampfwaffen[@inbenutzung=\"true\"]/fernkampfwaffe/tp");

			for(int i = 0; i < fkWaffenName.getLength(); i++){
				name = fkWaffenName.item(i).getChildNodes().item(0).getTextContent();
				at = fkWaffenAt.item(i).getChildNodes().item(0).getTextContent();
				dmg = fkWaffenDmg.item(i).getChildNodes().item(0).getTextContent();
				pa = "0";

				WaffenListe.add(new Waffe(name, at, pa, dmg, "F", "0", "0"));
			}

			NodeList schilderName = xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/schilder[@inbenutzung=\"true\"]/schild/name");
			NodeList schilderPa = xPath("/daten/kampfsets/kampfset[@inbenutzung=\"true\"][@tzm=\"true\"][@nr=\"1\"]/schilder[@inbenutzung=\"true\"]/schild/pa");
			for(int i = 0; i < schilderName.getLength(); i++){
				name = schilderName.item(i).getChildNodes().item(0).getTextContent();
				pa = schilderPa.item(i).getChildNodes().item(0).getTextContent();
				at = "0";
				dmg = "0";
				WaffenListe.add(new Waffe(name, at, pa, dmg, "H", "0", "0"));
			}

		}else{
			String name = "Raufen";
			at = xPath("/daten/set/raufen/at").item(0).getChildNodes().item(0).toString();
			if(at.contains("text"))
				at = at.substring("[text:  ".length(), at.length() - 1);

			pa = xPath("/daten/set/raufen/pa").item(0).getChildNodes().item(0).toString();
			if(pa.contains("text"))
				pa = pa.substring("[text:  ".length(), pa.length() - 1);

			String dmg = xPath("/daten/set/raufen/tp").item(0).getChildNodes().item(0).toString();
			if(dmg.contains("text"))
				dmg = dmg.substring("[text:  ".length(), dmg.length() - 1);

			WaffenListe.add(new Waffe(name, at.trim(), pa.trim(), dmg.trim(), "H", "0", "0"));


			name = "Ringen";
			at = xPath("/daten/set/ringen/at").item(0).getChildNodes().item(0).toString();
			if(at.contains("text"))
				at = at.substring("[text:  ".length(), at.length() - 1);

			pa = xPath("/daten/set/ringen/pa").item(0).getChildNodes().item(0).toString();
			if(pa.contains("text"))
				pa = pa.substring("[text:  ".length(), pa.length() - 1);

			dmg = xPath("/daten/set/ringen/tp").item(0).getChildNodes().item(0).toString();
			if(dmg.contains("text"))
				dmg = dmg.substring("[text:  ".length(), dmg.length() - 1);

			WaffenListe.add(new Waffe(name, at.trim(), pa.trim(), dmg.trim(), "H", "0", "0"));

			ini = Integer.parseInt(getVeryFirstByXPath("/daten/eigenschaften/initiative/akt"));
		}

		
		//für den akrobatik ini modifikator
		for(int i = 0; i < talenteNachKategorien[this.talentKategorieToInt("Körperlich")].size(); i++)
			if(talenteNachKategorien[this.talentKategorieToInt("Körperlich")].get(i).bezeichnung.contains("kroba"))
				akrobatik = talenteNachKategorien[this.talentKategorieToInt("Körperlich")].get(i).tap;
		
		
		paBasis = Integer.parseInt(getVeryFirstByXPath("/daten/eigenschaften/parade/akt"));
		atBasis = Integer.parseInt(getVeryFirstByXPath("/daten/eigenschaften/attacke/akt"));
		fkBasis = Integer.parseInt(getVeryFirstByXPath("/daten/eigenschaften/fernkampf-basis/akt"));



		trenneWaffenFuerAt();
		trenneWaffenFuerPa();
	}


	public void trenneWaffenFuerAt(){
		for(int i = 0; i < WaffenListe.size(); i++){
			Waffe Weapon = (Waffe) WaffenListe.get(i);
			if(!Weapon.AT.equals("0"))
				WaffenListeAT.add(Weapon);
		}
	}

	public void trenneWaffenFuerPa(){
		for(int i = 0; i < WaffenListe.size(); i++){
			Waffe Weapon = (Waffe) WaffenListe.get(i);
			if(!Weapon.PA.equals("0"))
				WaffenListePA.add(Weapon);
		}	
	}

	public  NodeList xPath(String path){
		try {
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr;

			expr = xpath.compile(path);
			Object result = expr.evaluate(doc, XPathConstants.NODESET);

			return (NodeList) result;
		} catch (XPathExpressionException e) {
			new msgbox("ERROR: " + path);
		}
		return null;
	}

	public String getVeryFirstByXPath(String path){
		NodeList nodes = xPath(path);
		int anz = nodes.getLength();
		if(anz != 1){
			new msgbox("ERROR: " + path);
			return null; 
		}
		return nodes.item(0).getChildNodes().item(0).getTextContent();
	}
	private void angaben() {
		nick = getVeryFirstByXPath("/daten/angaben/name");

	}

	private void liturgien() {
		NodeList sonderfertigkeitenNamenAusführlich = xPath("/daten/sonderfertigkeiten/sonderfertigkeit[grad]/nameausfuehrlich");
		NodeList sonderfertigkeitenGrad = xPath("/daten/sonderfertigkeiten/sonderfertigkeit[grad]/grad");
		NodeList sonderfertigkeitenName = xPath("/daten/sonderfertigkeiten/sonderfertigkeit[grad]/name");

		eigenschaft mu = (eigenschaft) eigenschaften.get(0);
		eigenschaft in = (eigenschaft) eigenschaften.get(2);
		eigenschaft ch = (eigenschaft) eigenschaften.get(3);
		int litKenn = 0;

		for(int i = 0; i < talenteNachKategorien[talentKategorieToInt("Wissen")].size(); i++)
			if(((talent)talenteNachKategorien[talentKategorieToInt("Wissen")].get(i)).bezeichnung.contains("Litur"))
				litKenn = ((talent)talenteNachKategorien[talentKategorieToInt("Wissen")].get(i)).tap;

		for(int i = 0; i < sonderfertigkeitenNamenAusführlich.getLength(); i++){
			String name = sonderfertigkeitenNamenAusführlich.item(i).getChildNodes().item(0).getTextContent();
			if(name.contains("Liturgie") && ! name.contains("kenn")){
				int grad = Integer.parseInt(sonderfertigkeitenGrad.item(i).getChildNodes().item(0).getTextContent());
				talent tempi = new talent(sonderfertigkeitenName.item(i).getChildNodes().item(0).getTextContent(), litKenn+"" ,mu.wert,  in.wert , ch.wert ,"MU/IN/CH", false, false);
				tempi.erleichterung+= (grad-1) * 2;
				tempi.sMod+= "Grad " + grad + ", ";
				this.talenteNachKategorien[talentKategorieToInt("Liturgie")].add(tempi);				
			}
		}
	}

	private void eigenschaften() {
		eigenschaften.add(new eigenschaft(getVeryFirstByXPath("/daten/eigenschaften/mut/akt"), "Mut"));
		eigenschaften.add(new eigenschaft(getVeryFirstByXPath("/daten/eigenschaften/klugheit/akt"), "Klugheit"));
		eigenschaften.add(new eigenschaft(getVeryFirstByXPath("/daten/eigenschaften/intuition/akt"), "Intuition"));
		eigenschaften.add(new eigenschaft(getVeryFirstByXPath("/daten/eigenschaften/charisma/akt"), "Charisma"));
		eigenschaften.add(new eigenschaft(getVeryFirstByXPath("/daten/eigenschaften/fingerfertigkeit/akt"), "Fingerfertigkeit"));
		eigenschaften.add(new eigenschaft(getVeryFirstByXPath("/daten/eigenschaften/gewandtheit/akt"), "Gewandtheit"));
		eigenschaften.add(new eigenschaft(getVeryFirstByXPath("/daten/eigenschaften/konstitution/akt"), "Konstitution"));
		eigenschaften.add(new eigenschaft(getVeryFirstByXPath("/daten/eigenschaften/koerperkraft/akt"),"Körperkraft"));
		Asp = Integer.parseInt(getVeryFirstByXPath("/daten/eigenschaften/astralenergie/akt"));
		Ka = Integer.parseInt(getVeryFirstByXPath("/daten/eigenschaften/karmaenergie/akt"));
		Aup = Integer.parseInt(getVeryFirstByXPath("/daten/eigenschaften/ausdauer/akt"));
		Lep = Integer.parseInt(getVeryFirstByXPath("/daten/eigenschaften/lebensenergie/akt"));
		Mr = Integer.parseInt(getVeryFirstByXPath("/daten/eigenschaften/magieresistenz/akt"));
		So = Integer.parseInt(getVeryFirstByXPath("/daten/eigenschaften/sozialstatus/akt"));
	}

	/**
	 * @param kategorie
	 * 0 = Kampf
	 * 1 = Körperlich
	 * 2 = Gesellschaft
	 * 3 = Natur
	 * 4 = Wissen & Ritualkenntniss
	 * 5 = Sprachen
	 * 6 = Schriften
	 * 7 = Handwerk
	 * 8 = Meta
	 * 9 = Zauber
	 * 10 = Gabe
	 * 11 = Liturgie
	 * falsche eingabe landet bei 0, da im Würfeltool eh keine Kampftalente angezeigt werden :D
	 */
	public int talentKategorieToInt(String kategorie){
		if(kategorie.contains("Kampf"))
			return 0;
		else if(kategorie.equals("Körperlich"))
			return 1;
		else if(kategorie.equals("Gesellschaft"))
			return 2;
		else if(kategorie.equals("Natur"))
			return 3;
		else if(kategorie.equals("Wissen"))
			return 4;
		else if(kategorie.equals("Sprachen"))
			return 5;
		else if(kategorie.equals("Schriften"))
			return 6;
		else if(kategorie.equals("Handwerk"))
			return 7;
		else if(kategorie.equals("Meta"))
			return 8;
		else if(kategorie.contains("kennt"))
			return 4;
		else if(kategorie.equals("Zauber"))
			return 9;
		else if(kategorie.equals("Gaben"))
			return 10;
		else if(kategorie.equals("Liturgie"))
			return 11;
		else if(kategorie.equals("Liturgien")) //für den Exporter
			return 11;

		return 0;
	}

	private void talente() {
		eigenschaft mu = (eigenschaft) eigenschaften.get(0);
		eigenschaft kl = (eigenschaft) eigenschaften.get(1);
		eigenschaft in = (eigenschaft) eigenschaften.get(2);
		eigenschaft ch = (eigenschaft) eigenschaften.get(3);
		eigenschaft ff = (eigenschaft) eigenschaften.get(4);
		eigenschaft ge = (eigenschaft) eigenschaften.get(5);
		eigenschaft ko = (eigenschaft) eigenschaften.get(6);
		eigenschaft kk = (eigenschaft) eigenschaften.get(7);


		NodeList talentNamen = xPath("/daten/talente/talent/name");
		NodeList talentWerte = xPath("/daten/talente/talent/wert");
		NodeList talentProbe = xPath("/daten/talente/talent/probe");
		NodeList talentBereiche = xPath("/daten/talente/talent/bereich");

		for(int i = 0; i < talentNamen.getLength(); i++){
			String name = talentNamen.item(i).getChildNodes().item(0).getTextContent();
			String wert = talentWerte.item(i).getChildNodes().item(0).getTextContent();
			String probe = talentProbe.item(i).getChildNodes().item(0).getTextContent();
			String bereich = talentBereiche.item(i).getChildNodes().item(0).getTextContent();

			if(name.contains("Athletik"))
				this.athletikWert = Integer.parseInt(wert);


			String wert1 = String.valueOf(probe.charAt(1));
			String wert2 = String.valueOf(probe.charAt(4));
			String wert3 = String.valueOf(probe.charAt(7));


			switch (wert1.charAt(0)) {
			case 'U': wert1 = mu.wert; break;
			case 'L': wert1 = kl.wert; break;
			case 'K': wert1 = kk.wert; break;
			case 'O': wert1 = ko.wert; break;
			case 'N': wert1 = in.wert; break;
			case 'E': wert1 = ge.wert; break;
			case 'F': wert1 = ff.wert; break;
			case 'H': wert1 = ch.wert; break;
			}

			switch (wert2.charAt(0)) {
			case 'U': wert2 = mu.wert; break;
			case 'L': wert2 = kl.wert; break;
			case 'K': wert2 = kk.wert; break;
			case 'O': wert2 = ko.wert; break;
			case 'N': wert2 = in.wert; break;
			case 'E': wert2 = ge.wert; break;
			case 'F': wert2 = ff.wert; break;
			case 'H': wert2 = ch.wert; break;
			}

			switch (wert3.charAt(0)) {
			case 'U': wert3 = mu.wert; break;
			case 'L': wert3 = kl.wert; break;
			case 'K': wert3 = kk.wert; break;
			case 'O': wert3 = ko.wert; break;
			case 'N': wert3 = in.wert; break;
			case 'E': wert3 = ge.wert; break;
			case 'F': wert3 = ff.wert; break;
			case 'H': wert3 = ch.wert; break;
			}

			//talente.add(new talent(name, wert, wert1, wert2, wert3, probe, false, false));
			talent tempTalent = new talent(name, wert, wert1, wert2, wert3, probe, false, false);

			if(bereich.contains("Liturgie"))
				tempTalent.bezeichnung = "Liturgiekenntniss: " + tempTalent.bezeichnung;

			this.talenteNachKategorien[this.talentKategorieToInt(bereich)].add(tempTalent);
		}
	}

	/**
	 * Erstellt die Objekte für die JList
	 */
	private void fillData() {
		for(int i = 1; i < talenteNachKategorien.length; i++)
			if(talenteNachKategorien[i].size() > 1)
				for(int x = 0; x < talenteNachKategorien[i].size(); x++)
					talente.add(talenteNachKategorien[i].get(x));

		for(int i = 0; i < talente.size(); i++)
		{	
			talent a = (talent) talente.get(i);
			if(a.bezeichnung.charAt(0)!= '_')
				dataTalente.add(a.bezeichnung + "  " + a.probe + "  " + a.tap);
			else
				dataTalente.add(a.bezeichnung);
		}

		for(int i = 0; i < eigenschaften.size(); i++)
		{	
			eigenschaft a = (eigenschaft) eigenschaften.get(i);
			dataEigenschaften.add(a.name + "   " + a.wert);
		}
	}

	private void talentspezialisierungen() {

		NodeList sonderfertigkeitenNamen = xPath("/daten/sonderfertigkeiten/sonderfertigkeit/name");
		for(int i = 0; i < sonderfertigkeitenNamen.getLength(); i++){

			String name = sonderfertigkeitenNamen.item(i).getChildNodes().item(0).getTextContent();

			if(name.contains("spezialisierung")){ 	
				name = name.substring(name.indexOf("spezialisierung ") + "spezialisierung ".length());
				name = name.replace("[Magier]", "");
				name = name.replace("  ", " ");
				name = name.replace("  ", " ");
				name = name.replace('[', '(');
				name = name.replace(']', ')');
				
				//Talentspezialisierung => weiteres Talent, jedoch muss erstmal geschaut werden, zu welcher Kategorie...
				boolean gefunden = false;
				for(int x = 0; x < talenteNachKategorien.length; x++) //jede kategorie
					for(int y = 0; y < talenteNachKategorien[x].size(); y++){
						if(name.contains(((talent) talenteNachKategorien[x].get(y)).bezeichnung)){
							talent spezi = new talent(name, Integer.toString(((talent) talenteNachKategorien[x].get(y)).tap +2), Integer.toString(((talent) talenteNachKategorien[x].get(y)).wert1), Integer.toString(((talent) talenteNachKategorien[x].get(y)).wert2), Integer.toString(((talent) talenteNachKategorien[x].get(y)).wert3),((talent) talenteNachKategorien[x].get(y)).probe, false, false);
							talenteNachKategorien[x].add(y+1, spezi);
							gefunden = true;
							break;
						}
						if(gefunden) 
							break;
					}
			}
		}

	}

	private void metatalente() {
		eigenschaft mu = (eigenschaft) eigenschaften.get(0);
		eigenschaft kl = (eigenschaft) eigenschaften.get(1);
		eigenschaft in = (eigenschaft) eigenschaften.get(2);
		eigenschaft ch = (eigenschaft) eigenschaften.get(3);
		eigenschaft ff = (eigenschaft) eigenschaften.get(4);
		eigenschaft ge = (eigenschaft) eigenschaften.get(5);
		eigenschaft ko = (eigenschaft) eigenschaften.get(6);
		eigenschaft kk = (eigenschaft) eigenschaften.get(7);

		int valJagd = 0;
		boolean waffenwertJagdDrin = false;
		boolean sonstigeEigensch = false;
		int waffenwertJagd = 0;
		int valNahrungPflanze = 0;
		int valNahrungAckerbau = 0;
		int valKraeutersuche = 0;
		int valwache = 0;
		int valLied = 0;
		int valInfo = 0;
		int valBruchVorbe = 0;
		int valBruch = 0;
		int valTurmSpringen = 0;
		int valBruchOhneKlettern = 0;
		for(int x = 0; x < talenteNachKategorien.length; x++)
			for(int i = 0; i < talenteNachKategorien[x].size(); i++){
				String bezeichnung = ((talent)talenteNachKategorien[x].get(i)).bezeichnung;
				int tap = ((talent)talenteNachKategorien[x].get(i)).tap;
				//Pirsch und Ansitzjagd
				if(bezeichnung.equals("Wildnisleben") || bezeichnung.equals("Fährtensuche")|| bezeichnung.equals("Schleichen") || bezeichnung.contains("Tierkunde"))
					valJagd+=tap;
				if(bezeichnung.equals("Bogen") || bezeichnung.equals("Armbrust")){
					if(waffenwertJagd == 0)
						waffenwertJagd = tap;
					else
						waffenwertJagd = Math.max(waffenwertJagd, tap);
					valJagd+=waffenwertJagd;
					waffenwertJagdDrin = true;
				}

				//Wache halten
				if(bezeichnung.equals("Selbstbeherrschung"))
					valwache+= (3*tap);
				if(bezeichnung.equals("Sinnenschärfe"))
					valwache+= (4*tap);
				if(bezeichnung.equals("Wildnisleben") || bezeichnung.equals("Sich Verstecken")|| bezeichnung.equals("Schleichen"))
					valwache+= tap;


				//Nahrung Sammeln
				if(bezeichnung.equals("Wildnisleben") || bezeichnung.equals("Sinnenschärfe")){
					valNahrungPflanze += tap;
					valNahrungAckerbau += tap;
				}else if(bezeichnung.equals("Pflanzenkunde"))
					valNahrungPflanze += tap;
				else if(bezeichnung.equals("Ackerbau"))
					valNahrungAckerbau += tap;

				//Kräutersuche
				if(bezeichnung.equals("Wildnisleben") || bezeichnung.equals("Sinnenschärfe") || bezeichnung.equals("Pflanzenkunde"))
					valKraeutersuche += tap;


				//Lieder dichten
				if(bezeichnung.equals("Sagen und Legenden") || bezeichnung.equals("Musizieren ")|| bezeichnung.equals("Singen"))
					valLied+= tap;

				//Informationsbeschaffung
				if(bezeichnung.equals("Gassenwissen") || bezeichnung.equals("Überreden") || bezeichnung.equals("Menschenkenntnis"))
					valInfo+= tap;

				//Bruch Vorbereitung (gassenwissen *2)
				if(bezeichnung.equals("Gassenwissen") || bezeichnung.equals("Überreden") || bezeichnung.equals("Menschenkenntnis") || bezeichnung.equals("Sinnenschärfe"))
					valBruchVorbe+= tap;
				if(bezeichnung.equals("Gassenwissen"))
					valBruchVorbe+= tap;

				//Bruch durchführung
				if(bezeichnung.equals("Schleichen") || bezeichnung.equals("Sich verstecken") || bezeichnung.equals("Sinnenschärfe") || bezeichnung.equals("Schlösser knacken") || bezeichnung.equals("Schätzen") || bezeichnung.equals("Klettern"))
					valBruch+= tap;
				if(bezeichnung.equals("Schleichen") || bezeichnung.equals("Sich verstecken") || bezeichnung.equals("Sinnenschärfe") || bezeichnung.equals("Schlösser knacken") || bezeichnung.equals("Schätzen"))
					valBruchOhneKlettern+= tap;
				
				//Turmspringen
				if(bezeichnung.equals("Schwimmen") || bezeichnung.equals("Athletik") ||  bezeichnung.equals("Körperbeherrschung"))
					valTurmSpringen+=tap;
			}
		valJagd/=5;
		valNahrungPflanze/=3;
		valNahrungAckerbau/=3;
		valKraeutersuche/=3;
		valwache/=10;
		valLied/=3;
		valInfo/=3;
		valBruchVorbe/=5;
		valBruch/=6;
		valBruchOhneKlettern/=5;
		valTurmSpringen/=3;
		//talente.add(new talent("Einbruchsvorbereitung", ""+valBruchVorbe,  mu.wert, in.wert, ch.wert, "MU/IN/CH", false, false));
		talenteNachKategorien[this.talentKategorieToInt("Meta")].add(new talent("Meta: Einbruch mit Klettern", ""+valBruch,  mu.wert, in.wert, ch.wert, "IN/GE/FF", false, false));
		talenteNachKategorien[this.talentKategorieToInt("Meta")].add(new talent("Meta: Einbruch ohne Klettern", ""+valBruchOhneKlettern,  in.wert, ge.wert, ff.wert, "IN/GE/FF", false, false));
		talenteNachKategorien[this.talentKategorieToInt("Meta")].add(new talent("Meta: Informationsbeschaffung", ""+valInfo,  mu.wert, in.wert, ch.wert, "MU/IN/CH", false, false));
		talenteNachKategorien[this.talentKategorieToInt("Meta")].add(new talent("Meta: Kräutersuchen", ""+valKraeutersuche, mu.wert, in.wert, ff.wert, "MU/IN/FF", false, false));
		talenteNachKategorien[this.talentKategorieToInt("Meta")].add(new talent("Meta: Lied dichten", ""+valLied, kl.wert, in.wert, ch.wert, "KL/IN/CH", false, false));
		talenteNachKategorien[this.talentKategorieToInt("Meta")].add(new talent("Meta: Nahrung Sammeln - Pflanzenkunde", ""+valNahrungPflanze, mu.wert, in.wert, ff.wert, "MU/IN/FF", false, false));
		talenteNachKategorien[this.talentKategorieToInt("Meta")].add(new talent("Meta: Nahrung Sammeln - Ackerbau", ""+valNahrungAckerbau, mu.wert, in.wert, ff.wert, "MU/IN/FF", false, false));
		talenteNachKategorien[this.talentKategorieToInt("Meta")].add(new talent("Meta: Pirsch - und Ansitzjagd", ""+valJagd, mu.wert, in.wert, ge.wert, "MU/IN/GE", false, false));
		talenteNachKategorien[this.talentKategorieToInt("Meta")].add(new talent("Meta: Wache halten", ""+valwache, mu.wert, in.wert, ko.wert, "MU/IN/KO", false, false));
		talenteNachKategorien[this.talentKategorieToInt("Meta")].add(new talent("Meta: Turmspringen", ""+valTurmSpringen, mu.wert, in.wert, kk.wert, "MU/IN/KK", false, false));



	}

	private void zauber(){
		eigenschaft mu = (eigenschaft) eigenschaften.get(0);
		eigenschaft kl = (eigenschaft) eigenschaften.get(1);
		eigenschaft in = (eigenschaft) eigenschaften.get(2);
		eigenschaft ch = (eigenschaft) eigenschaften.get(3);
		eigenschaft ff = (eigenschaft) eigenschaften.get(4);
		eigenschaft ge = (eigenschaft) eigenschaften.get(5);
		eigenschaft ko = (eigenschaft) eigenschaften.get(6);
		eigenschaft kk = (eigenschaft) eigenschaften.get(7);

		RepriElf = isElf();
		NodeList zauberNamen = xPath("/daten/zauber/zauber/name");
		NodeList zauberVariante = xPath("/daten/zauber/zauber/variante");
		NodeList zauberWerte = xPath("/daten/zauber/zauber/wert");
		NodeList zauberProbe = xPath("/daten/zauber/zauber/probe");
		NodeList zauberHauszauber = xPath("/daten/zauber/zauber/hauszauber");
		NodeList zauberMerkmale = xPath("/daten/zauber/zauber/merkmale");

		for(int i = 0; i < zauberNamen.getLength(); i++){
			String name = zauberNamen.item(i).getChildNodes().item(0).getTextContent();
			String wert = zauberWerte.item(i).getChildNodes().item(0).getTextContent();
			String probe = zauberProbe.item(i).getChildNodes().item(0).getTextContent();
			String hauszaubi = zauberHauszauber.item(i).getChildNodes().item(0).getTextContent();
		try{
			String variante = zauberVariante.item(i).getChildNodes().item(0).getTextContent();
		
			if(variante != null)
				if(!variante.equals(""))
					name += " (" + variante + ")";
		}catch(Exception e){}
			String merkmale = zauberMerkmale.item(i).getChildNodes().item(0).getTextContent();

			String wert1 = String.valueOf(probe.charAt(1));
			String wert2 = String.valueOf(probe.charAt(4));
			String wert3 = String.valueOf(probe.charAt(7));

			switch (wert1.charAt(0)) {
			case 'U': wert1 = mu.wert; break;
			case 'L': wert1 = kl.wert; break;
			case 'K': wert1 = kk.wert; break;
			case 'O': wert1 = ko.wert; break;
			case 'N': wert1 = in.wert; break;
			case 'E': wert1 = ge.wert; break;
			case 'F': wert1 = ff.wert; break;
			case 'H': wert1 = ch.wert; break;
			}

			switch (wert2.charAt(0)) {
			case 'U': wert2 = mu.wert; break;
			case 'L': wert2 = kl.wert; break;
			case 'K': wert2 = kk.wert; break;
			case 'O': wert2 = ko.wert; break;
			case 'N': wert2 = in.wert; break;
			case 'E': wert2 = ge.wert; break;
			case 'F': wert2 = ff.wert; break;
			case 'H': wert2 = ch.wert; break;
			}

			switch (wert3.charAt(0)) {
			case 'U': wert3 = mu.wert; break;
			case 'L': wert3 = kl.wert; break;
			case 'K': wert3 = kk.wert; break;
			case 'O': wert3 = ko.wert; break;
			case 'N': wert3 = in.wert; break;
			case 'E': wert3 = ge.wert; break;
			case 'F': wert3 = ff.wert; break;
			case 'H': wert3 = ch.wert; break;
			}
			if(RepriElf){
				if(!(probe.equals("KL/IN/IN") || probe.equals("IN/KL/IN") || probe.equals("IN/IN/KL")))
				{
					if(probe.charAt(1) == 'L'){
						probe = "IN" + probe.substring(2);
						wert1 = in.wert;
					}else{ 
						if(probe.charAt(4) == 'L'){
							probe = probe.substring(0, 3) + "IN" + probe.substring(5);
							wert2 = in.wert;
						}else{ 
							if(probe.charAt(7) == 'L'){
								probe = probe.substring(0,5) + "IN";
								wert3 = in.wert;
							}
						}
					}
				}
			}

			if(probe.contains("**")){ //Sonderfall: Attributo
				probe = probe.substring(0, 6);

				String[] eigensch = {"MU", "KL", "IN", "CH", "FF", "GE", "KO", "KK"};
				String[] werte = {mu.wert, kl.wert, in.wert, ch.wert, ff.wert, ge.wert, ko.wert, kk.wert};
				for(int x = 0; x < eigensch.length; x++){
					talenteNachKategorien[talentKategorieToInt("Zauber")].add(new talent(name + " " + eigensch[x], wert, wert1, wert2, werte[x], probe+eigensch[x], true, false, hauszaubi.contains("t"), merkmale));
				}
			}else{
				talenteNachKategorien[talentKategorieToInt("Zauber")].add(new talent(name, wert, wert1, wert2, wert3, probe, true, false, hauszaubi.contains("t"), merkmale));
			}
		}
	}

	public boolean isElf(){
		NodeList sfs = xPath("/daten/sonderfertigkeiten/sonderfertigkeit/nameausfuehrlich");
		for(int i = 0; i < sfs.getLength(); i++){
			String name = sfs.item(i).getChildNodes().item(0).getTextContent();
			if(name.contains("Reprä") && (name.contains("Elf") || name.contains("elf")))
				return true;
		}

		return false;
	}

	private void addTalentClasses()
	{
		String Klassen[] = {"_____Kampf_____", "_____Körperlich_____", "_____Gesellschaftlich_____", "_____Natur_____", "_____Wissen_____", "_____Sprachen_____","_____Schriften_____", "_____Handwerk_____", "_____Metatalente_____", "_____Zauber_____", "_____Gaben_____", "_____Liturgien_____"};
		for(int i = 0; i < Klassen.length; i++){
			this.talenteNachKategorien[i] = new LinkedList();
			this.talenteNachKategorien[i].add(new talent(Klassen[i],"","","","","", false, false));
		}
	}

	private void metaEigenschaften() {
		try{
			boolean sonstigeEigensch = false;
			eigenschaft mu = (eigenschaft) eigenschaften.get(0);
			eigenschaft kl = (eigenschaft) eigenschaften.get(1);
			eigenschaft in = (eigenschaft) eigenschaften.get(2);
			eigenschaft ch = (eigenschaft) eigenschaften.get(3);
			eigenschaft ff = (eigenschaft) eigenschaften.get(4);
			eigenschaft ge = (eigenschaft) eigenschaften.get(5);
			eigenschaft ko = (eigenschaft) eigenschaften.get(6);
			eigenschaft kk = (eigenschaft) eigenschaften.get(7);

			for(int x = 0; x < talenteNachKategorien.length; x++)
				for(int i = 0; i < talenteNachKategorien[x].size(); i++){
					String bezeichnung = ((talent)talenteNachKategorien[x].get(i)).bezeichnung;
					int tap = ((talent)talenteNachKategorien[x].get(i)).tap;
					if(bezeichnung.contains("Dschin") || bezeichnung.contains("Elementarer Diener") || bezeichnung.contains("Meister der")){
						//(mu + in + ch + ch + zfw) / 5
						float value = 0.0f;
						value += Integer.parseInt(mu.wert) + Integer.parseInt(in.wert) + Integer.parseInt(ch.wert) + Integer.parseInt(ch.wert) + tap;
						int taw = Math.round((value/5));
						eigenschaften.add(new eigenschaft(taw+"", "Beherschung: " + bezeichnung));
					}

					if(bezeichnung.contains("Invocatio")){
						//MU+MU+KL+CH+ZFW / 5
						float value = 0.0f;
						value+= Integer.parseInt(mu.wert) + Integer.parseInt(mu.wert) + Integer.parseInt(kl.wert) + Integer.parseInt(ch.wert) + tap;
						int taw = Math.round(value/5);
						eigenschaften.add(new eigenschaft(taw+"", "Beherschung: " + bezeichnung));

					}
				}
		}catch(Exception e){
			JOptionPane.showMessageDialog(new JFrame(), "In MetaEigenschaften() ist aufgetreten: \n " + e.toString());
		}
	}

	private void vorteile() {
		NodeList vorteileNamen = xPath("/daten/vorteile/vorteil/name");
		NodeList vorteileNachteile = xPath("/daten/vorteile/vorteil/istvorteil");
		for (int s = 0; s < vorteileNamen.getLength(); s++) {
			try{
				String name = vorteileNamen.item(s).getChildNodes().item(0).getTextContent();

				if(name.contains("Balance"))
					balance = true;
				else if(name.contains("Herausragende Balance"))
				{
					balance = false;
					herausragendeBalance = true;
				}else if(name.contains("Gutaussehend"))
					gutAussehend = true;
				else if(name.contains("Tierfreund"))
					Tierfreund=true;
				else if(name.contains("Schlangenmensch"))
					SchlangenMensch=true;
				else if(name.contains("Herausragender sechster Sinn"))
					HerausragenderSechsterSinn=true;
				else if(name.contains("Herausragendes Aussehen"))
				{
					gutAussehend = false;
					herausragendesAussehen = true;
				}else if(name.contains("Guter Ruf"))
					guterRuf = true;
				else if(name.contains("Innerer Kompass"))
					innererKompass = true;
				else if(name.contains("Richtungssinn"))
					richtungssinn = true;
				else if(name.contains("Wohlklang"))
					wohlklang = true;



				if(name.contains("patsch"))
					tollpatsch = true;
				if(name.contains("Wilde Magie"))
					wildeMagie = true;

				String istVorteil = vorteileNachteile.item(s).getChildNodes().item(0).getTextContent();

				if(!name.contains(":") || name.contains("Astrale Regeneration") || name.contains("Flink")){
					if(istVorteil.contains("t")){
						Vorteile.add(name);	
					}else{
						Nachteile.add(name);
					}
				}else{
					String value = name.substring(name.length()-2);
					name = name.substring(0, name.length()-3);

					if(name.charAt(name.length()-1) == ':') //bei manchen ist noch nen : drin...
						name = name.substring(0, name.length() -1);

					if(!name.contains("Beher") && !name.contains("Heraus") && !name.contains("Eigenschaft") && !name.contains("Schulden") && !name.contains("Verbindungen") && !name.contains("esitz") && !name.contains("Stigma") && !name.contains("Gebildet") && !name.contains("Astrale Regeneration") && !name.contains("Lebenskraft") && !name.contains("Niedrige") && !name.contains("Hohe")&& !name.contains("Flink") && !name.contains("Falken") && !name.contains("Ausr")){
						eigenschaften.add(new eigenschaft(value, name));
					}
				}


			}catch(Exception e){
			}	
		}
		/*
		 * soooo nun müssen noch die modifikationen für die jeweiligen vorteile gsetzt werden...
		 */
		for(int x = 0; x < talenteNachKategorien.length; x++)
			for(int i = 0; i < talenteNachKategorien[x].size(); i++){
				String bezeichnung = ((talent)talenteNachKategorien[x].get(i)).bezeichnung;
				if(balance)
					if(bezeichnung.equals("Körperbeherrschung") || bezeichnung.equals("Tanzen") || bezeichnung.equals("Athletik") || bezeichnung.equals("Akrobatik"))
					{
						((talent)talenteNachKategorien[x].get(i)).erleichterung -= 3;
						((talent)talenteNachKategorien[x].get(i)).sMod+= "Balance, ";
					}

				if(herausragendeBalance)
					if(bezeichnung.equals("Körperbeherrschung") || bezeichnung.equals("Tanzen") || bezeichnung.equals("Athletik") || bezeichnung.equals("Akrobatik"))
					{
						((talent)talenteNachKategorien[x].get(i)).erleichterung -= 7;
						((talent)talenteNachKategorien[x].get(i)).sMod+= "Herausragende Balance, ";
					}

				if(gutAussehend)
					if(((talent)talenteNachKategorien[x].get(i)).probe.contains("CH")){
						if(bezeichnung.equals("Betören") || bezeichnung.equals("Etikette") || bezeichnung.equals("Gassenwissen") || bezeichnung.equals("Lehren") || bezeichnung.equals("Menschenkenntnis") || bezeichnung.equals("Schauspielerei") || bezeichnung.equals("Schriftlicher Ausdruck") || bezeichnung.equals("Sich Verkleiden") || bezeichnung.equals("Überreden") || bezeichnung.equals("Überzeugen"))
						{
							((talent)talenteNachKategorien[x].get(i)).erleichterung -= 1;
							((talent)talenteNachKategorien[x].get(i)).sMod+= "Gut Aussehend, ";
						}
					}

				if(herausragendesAussehen)
					if(((talent)talenteNachKategorien[x].get(i)).probe.contains("CH")){
						if(bezeichnung.equals("Betören") || bezeichnung.equals("Etikette") || bezeichnung.equals("Gassenwissen") || bezeichnung.equals("Lehren") || bezeichnung.equals("Menschenkenntnis") || bezeichnung.equals("Schauspielerei") || bezeichnung.equals("Schriftlicher Ausdruck") || bezeichnung.equals("Sich Verkleiden") || bezeichnung.equals("Überreden") || bezeichnung.equals("Überzeugen"))
						{
							((talent)talenteNachKategorien[x].get(i)).erleichterung -= 3;
							((talent)talenteNachKategorien[x].get(i)).sMod+= "Herausragendes Aussehen, ";
						}
					}

				if(guterRuf)
					if(bezeichnung.equals("Betören") || bezeichnung.equals("Etikette") || bezeichnung.equals("Gassenwissen") || bezeichnung.equals("Lehren") || bezeichnung.equals("Menschenkenntnis") || bezeichnung.equals("Schauspielerei") || bezeichnung.equals("Schriftlicher Ausdruck") || bezeichnung.equals("Sich Verkleiden") || bezeichnung.equals("Überreden") || bezeichnung.equals("Überzeugen"))
					{
						((talent)talenteNachKategorien[x].get(i)).erleichterung -= 1;
						((talent)talenteNachKategorien[x].get(i)).sMod+= "Guter Ruf, ";
					}

				if(innererKompass)
					if(bezeichnung.equals("Orientierung") || bezeichnung.equals("Wildnissleben") || bezeichnung.equals("Gassenwissen"))
					{				
						((talent)talenteNachKategorien[x].get(i)).erleichterung -= 7;
						((talent)talenteNachKategorien[x].get(i)).sMod+= "Innerer Kompass, ";
					}

				if(richtungssinn)
					if(bezeichnung.equals("Orientierung") || bezeichnung.equals("Wildnissleben"))
					{				
						((talent)talenteNachKategorien[x].get(i)).erleichterung -= 5;
						((talent)talenteNachKategorien[x].get(i)).sMod+= "Richtungssinn, ";
					}

				if(HerausragenderSechsterSinn)
					if(bezeichnung.equals("Odem") || bezeichnung.equals("Analys") || bezeichnung.equals("Occolus"))
					{
						((talent)talenteNachKategorien[x].get(i)).erleichterung-=3;
						((talent)talenteNachKategorien[x].get(i)).sMod+="Herausragender sechster Sinn, ";			
					}

				if(SchlangenMensch && bezeichnung.equals("Fesseln/Entfesseln"))
				{
					((talent)talenteNachKategorien[x].get(i)).erleichterung-=1;
					((talent)talenteNachKategorien[x].get(i)).sMod+= "Schlangenmensch, ";
				}

				if(wohlklang)
				{
					if(bezeichnung.equals("Singen"))
					{
						((talent)talenteNachKategorien[x].get(i)).erleichterung -= 5;
						((talent)talenteNachKategorien[x].get(i)).sMod+= "Wohlklang, ";
					}

					if(bezeichnung.equals("Betören") || bezeichnung.equals("Etikette") || bezeichnung.equals("Gassenwissen") || bezeichnung.equals("Lehren") || bezeichnung.equals("Menschenkenntnis") || bezeichnung.equals("Schauspielerei") || bezeichnung.equals("Schriftlicher Ausdruck") || bezeichnung.equals("Sich Verkleiden") || bezeichnung.equals("Überreden") || bezeichnung.equals("Überzeugen"))
					{
						((talent)talenteNachKategorien[x].get(i)).erleichterung -= 2;
						((talent)talenteNachKategorien[x].get(i)).sMod+= "Wohlklang, ";
					}
				}

				if(Tierfreund)
					if(bezeichnung.equals("Abrichten") || bezeichnung.equals("Reiten") || bezeichnung.equals("Sanftmut") || bezeichnung.equals("Herr über das Tierreich"))
					{
						((talent)talenteNachKategorien[x].get(i)).erleichterung-=3;
						((talent)talenteNachKategorien[x].get(i)).sMod+= "Tierfreund, ";
					}
			}

	}

	private void createReg() {
		boolean Regeneration[] = {false,false};
		boolean meisterlicheReg = false;
		int astraRegIII = 0;
		boolean AstralerBlock = false;
		int schnelleHeilung = 0;
		boolean schlafstörung = false;
		boolean schlechteReg = false;
		String RegenerationString = "";

		for (int s = 0; s < Vorteile.size(); s++) {

			if(Vorteile.get(s).contains("Astrale Regeneration"))
				astraRegIII = Integer.parseInt(Vorteile.get(s).substring(Vorteile.get(s).length() -1));
			else if(Vorteile.get(s).contains("örung")) //schlafstörung
				schlafstörung = true;
			else if(Vorteile.get(s).contains("Schlechte Regeneration"))
				schlechteReg = true;
			else if(Vorteile.get(s).contains("Astraler Block"))
				AstralerBlock = true;
			else if(Vorteile.get(s).contains("Schnelle Heilung"))
				schnelleHeilung = Integer.parseInt(Vorteile.get(s).substring(Vorteile.get(s).length() -1));
			else if(Vorteile.get(s).contains("zaub"))
				zauberer = true;
		}


		for(int i = 0; i < Sfs.size(); i++){
			if(Sfs.get(i).contains("Regeneration I"))
				Regeneration[0] = true;

			if(Sfs.get(i).contains("Regeneration II"))
				Regeneration[1] = true;

			if(Sfs.get(i).contains("Meisterliche Regeneration"))
				meisterlicheReg = true;
		}

		RegenerationString += "<table><th bgcolor=#" + ausgabeErstellen.hintergrundfarbe() + ">LEP-Regeneration</th>";
		RegenerationString += "<tr>Modifikationen: ";

		if(schnelleHeilung != 0)
			RegenerationString += "Schnelle Heilung " + schnelleHeilung +", ";
		if(schlafstörung)
			RegenerationString += "Schlafstörungen, ";
		if(schlechteReg)
			RegenerationString += "Schlechte Regeneration, ";
		if(RegenerationString.contains(","))
			RegenerationString = RegenerationString.substring(0, RegenerationString.length() -2);

		if(RegenerationString.substring(RegenerationString.length()-10).contains("kationen"))
			RegenerationString = RegenerationString.substring(0, RegenerationString.length() - "<tr>Modifikationen: ".length());

		RegenerationString += "</td></tr>";
		RegenerationString +="<tr><td>[r: ";

		String wurf ="";
		wurf += "1d6";
		if(schnelleHeilung != 0)
			wurf+="+"+schnelleHeilung;
		if(schlechteReg)
			wurf += "-1";
		if(schlafstörung)
			wurf += "-1";

		RegenerationString += wurf;
		RegenerationString += "+\" Lebenspunkte durch "+ wurf + "\"]</td></tr>";
		RegenerationString+="<tr><td>[h: wurf = 1d20][h: dif = " +((eigenschaft) eigenschaften.get(6)).wert + " - wurf - " + 0 +"][r: \"Konsti-Probe";
		RegenerationString+= "(\" + " +((eigenschaft) eigenschaften.get(6)).wert + " + \")"  + " gew&uuml;rfelt wurde: \" + wurf + \"; \" + dif + \" Punkte &uuml;ber\"]";

		if(zauberer){

			RegenerationString+="<tr><th bgcolor=#" + ausgabeErstellen.hintergrundfarbe() + ">ASP-Regeneration</th></tr>";

			RegenerationString += "<tr><td>Modifikationen: ";
			wurf = "";
			if(!meisterlicheReg)
			{
				wurf+="d6";				
			}else{
				RegenerationString += "Meisterliche Regeneration, ";
				wurf+="+1";
				//jetzt erstmal herausfinden ob kl oder in die leiteigenschaft ist T.T

				for(int i = 0; i < Sfs.size(); i++){
					if(Sfs.get(i).contains("Repräsentation")){
						String name = Sfs.get(i);
						if(name.contains("Alchi") || name.contains("Borba") || name.contains("Drui") || name.contains("Herren") || name.contains("mag") || name.contains("Scharl") || name.contains("Zib")){
							wurf = "[round("+((eigenschaft) eigenschaften.get(0)).wert  +"/3)"+wurf;
						}else{
							wurf = "[round("+((eigenschaft) eigenschaften.get(1)).wert  +"/3)"+wurf;
						}
					}
				}

			}


			if(Regeneration[1]){
				RegenerationString += "Regeneration II, ";
				wurf += "+2";
			}else if(Regeneration[0]){
				RegenerationString += "Regeneration I, ";
				wurf += "+1";
			}

			if(astraRegIII != 0){
				RegenerationString += "Astrale Regeneration " + astraRegIII +", ";
				wurf+= "+" + astraRegIII;
			}

			if(AstralerBlock){
				RegenerationString += "Astraler Block ";
				wurf+= "-1";
			}

			if(RegenerationString.substring(RegenerationString.length()-10).contains("kationen"))
				RegenerationString = RegenerationString.substring(0, RegenerationString.length() - "Modifikationen: ".length());

			RegenerationString = RegenerationString.substring(0, RegenerationString.length()-2) + "</td></tr>";
			RegenerationString += "<tr><td>ASP durch "; 
			if(wurf.charAt(0)== '[')
				RegenerationString += wurf.substring(1);
			else
				RegenerationString += wurf;
			RegenerationString += "=";
			RegenerationString += "[r: "; 

			if(wurf.charAt(0)== '[')
				RegenerationString += wurf.substring(1);
			else
				RegenerationString += wurf;
			RegenerationString += "]</td></tr>";

			RegenerationString+="<tr><td>[h: wurf = 1d20][h: dif = " +((eigenschaft) eigenschaften.get(2)).wert + " - wurf - " + 0 +"][r: \"Intutions-Probe";
			RegenerationString+= "(\" + " +((eigenschaft) eigenschaften.get(2)).wert + " + \")"  + " gew&uuml;rfelt wurde: \" + wurf + \"; \" + dif + \" Punkte &uuml;ber\"]";

			ausgabeErstellen.RegString = RegenerationString;

		}
	}

	private void schlechteEigenschaften() {
		NodeList nodeLst =  doc.getElementsByTagName("vorteil");
		for (int s = 0; s < nodeLst.getLength(); s++) {
			Node fstNode = nodeLst.item(s);
			NodeList chNodes = fstNode.getChildNodes();
			Node blub = chNodes.item(0);

			String name = blub.getTextContent();
			String value = "";
			if(name.contains(":")){
				value = name.substring(name.length()-2);
				name = name.substring(0, name.length()-3);

				if(name.charAt(name.length()-1) == ':') //bei manchen ist noch nen : drin...
					name = name.substring(0, name.length() -1);

				if(!name.contains("Beher") && !name.contains("Heraus") && !name.contains("Eigenschaft") && !name.contains("Schulden") && !name.contains("Verbindungen") && !name.contains("esitz") && !name.contains("Stigma") && !name.contains("Gebildet") && !name.contains("Astrale Regeneration") && !name.contains("Lebenskraft") && !name.contains("Niedrige") && !name.contains("Hohe")&& !name.contains("Flink") && !name.contains("Falken") && !name.contains("Ausr") && !name.contains("Ausdauernd") && !name.contains("Meisterhandwerk")){
					eigenschaften.add(new eigenschaft(value, name));
				}

			}else if(name.contains("patsch"))
				tollpatsch = true;
			else if(name.contains("Wilde Magie"))
				wildeMagie = true;
		}
	}



	public boolean WaffeSchonInListe(String name){
		for(int i = 0; i < WaffenListe.size(); i++)
			if(((Waffe)WaffenListe.get(i)).bezeichnung.contains(name))
				return true;

		return false;
	}

	public String[] getWaffenAT() {
		String[] result = new String[WaffenListeAT.size()];
		for(int i = 0; i < WaffenListeAT.size(); i++)
			result[i] = ((Waffe)WaffenListeAT.get(i)).bezeichnung;
		return result;
	}

	public String[] getWaffenPA() {
		String[] result = new String[WaffenListePA.size()];
		for(int i = 0; i < WaffenListePA.size(); i++)
			result[i] = ((Waffe)WaffenListePA.get(i)).bezeichnung;
		return result;
	}

	public void setWaffePA(int i) {
		this.pa = ((Waffe)WaffenListePA.get(i)).PA;
	}

	public void setWaffeAT(int i) {
		this.at = ((Waffe)WaffenListeAT.get(i)).AT;
		this.atWert = ((Waffe) WaffenListeAT.get(i)).dmg;
	}
}