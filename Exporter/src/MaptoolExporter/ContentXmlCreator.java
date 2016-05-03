package MaptoolExporter;

import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.CAA.utils.msgbox;


import logic.Waffe;
import logic.ausgabeErstellen;
import logic.chareinlesen;
import logic.eigenschaft;
import logic.talent;

public class ContentXmlCreator {
	String gessamtSfs = "";
	chareinlesen derChar;
	HashMap<String, String> SfDictonary = new HashMap();
	public ContentXmlCreator(chareinlesen a){
		derChar = a;
		fülleSfDictonary();
	}

	public String createContent() {
		/*
		---------- Eigenschaften ---------- (einfache Ganzzahlen)
		MU			x												
		KL			x
		IN			x
		CH			x
		FF			x
		GE			x
		KO			x
		KK			x
		---------- Basiswerte ---------- (einfache Ganzzahlen)
		 *@LeP		x
		@MaxLeP		x
		 *@AsP		x
		@MaxAsP		x
		 *@KaP		x
		@MaxKaP		x
		AuP			x
		@MaxAuP		x
		INI			x
		AtBasis:0	x
		FeBasis:0	x
		AuswBasis:0	x
		 *Wunden (WU):0	x
		@RS:0		x
		@BE:0		x
		---------- Talente ----------
		@Koerper 		x
		@Gesellschaft	x
		@Natur			x
		@Wissen			x
		@SprachenSchriften	x
		@Handwerk			x
		@Kampf				x
		@Gaben				x
		@Zauber				x
		@Hauszauber			x
		@Rituale			[x]
		@Liturgien			x
		// Sonstiges hat derzeit keinen Nutzen; ward für Liturgien und Rituale vorgesehen
		@Sonstiges
		---------- Vor- und Nachteile ----------
		@Vorteile 	x
		@Nachteile	x
		@schlechteEigenschaften	x

		@AllgemeineSF
		@KampfSF
		@NahkampfSF
		@FernkampfSF
		@Hand2HandStyles
		@Hand2HandSF
		@MagieSF
		@KlerikaleSF

		---------- Verschiedenes ----------
		// Inventar hat noch keine konkrete Ausarbeitung
		Inventar
		Optionen:NahkampfwaffeID=1 ;
		Waffen:WaffenLaufIndex=1 ; Waffe1Bezeichnung=Raufen ; Waffe1DK=H ; Waffe1TP=1d6 ; Waffe1INI=0 ; Waffe1AT=0 ; Waffe1PA=0 ; Waffe1BF=0 ;
		---------- Versionen ----------
		CF:1.5f01
		LM:1.5f01
		LC:1.5r */

		String content = "";
		content += "<net.rptools.maptool.model.Token>\n";
		content += soZeugs();

		content += "<propertyMapCI>\n";
		content += "<store>\n";
		content += ContentStatisch();
		content += contentAkrobatikModi();
		content += ContentEigenschaften();
		content += ContentBasisWerte();
		content += ContentTalente();
		content += ContentVorteile();
		content += ContentSonderfertigkeiten();
		content += ContentTalente();
		content += ContentAtPa();
		content += ContentGeld();
		content += "</store>\n";
		content += "</propertyMapCI>\n";
		content += "<macroPropertiesMap/>\n";
		content += "<speechMap/>\n";




		content += "</net.rptools.maptool.model.Token>\n";
		return content;
	}
	private String ContentGeld() {
		String result = "<entry>\n";
		result += "<string>inventarmisc</string>\n";
		result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "<key>InventarMisc</key>\n";
		result += "<value class=\"string\">";
		result += "{&quot;behaelter1&quot;:&quot;Rucksack&quot;,&quot;behaelter2&quot;:&quot;G&amp;uuml;rteltasche&quot;,&quot;behaelter3&quot;:&quot;Beutel&quot;,&quot;behaelter4&quot;:&quot;Am K&amp;ouml;rper getragen&quot;,&quot;behaelter5&quot;:&quot;Satteltaschen&quot;,&quot;dukaten&quot;:1,&quot;silbertaler&quot;:2,&quot;heller&quot;:3,&quot;kreuzer&quot;:4}";
		result += "</value>\n";
		result += "<outer-class reference=\"../../../..\"/>\n";
		result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "</entry>\n";
		return result;
	}

	private String ContentStatisch() {
		String result = "";

		result += "<entry>\n";
		result += "<string>Wundenmodifikator</string>\n";
		result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "<key>Wundenmodifikator</key>\n";
		result += "<value class=\"string\">0</value>\n";
		result += "<outer-class reference=\"../../../..\"/>\n";
		result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "</entry>\n";


		return result;
	}

	private String contentAkrobatikModi(){
		String result = "";

		result += "<entry>\n";
		result += "<string>AusweichenAkrobatikmodifikator</string>\n";
		result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "<key>AusweichenAkrobatikmodifikator</key>\n";
		result += "<value class=\"string\">" + Math.max(0, (derChar.akrobatik-9)/3) + "</value>\n";
		result += "<outer-class reference=\"../../../..\"/>\n";
		result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "</entry>\n";
		
		result += "<entry>\n";
		result += "<string>AusweichenAthletikmodifikator</string>\n";
		result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "<key>AusweichenAthletikmodifikator</key>\n";
		result += "<value class=\"string\">" + Math.max(0, (derChar.akrobatik-9)/3) + "</value>\n";
		result += "<outer-class reference=\"../../../..\"/>\n";
		result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "</entry>\n";

		return result;
	}

	private String ContentAtPa() {
		String result = "";


		result += "<entry>\n";
		result += "<string>waffen</string>\n";
		result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "<key>Waffen</key>\n";
		result += "<value class=\"string\">WaffenLaufIndex=" + derChar.WaffenListe.size() + ";";

		for(int i = 0; i < derChar.WaffenListe.size(); i ++){
			if(!((Waffe)derChar.WaffenListe.get(i)).bezeichnung.contains("Ausw")){ //Ausweichen ist im Würfeltool wie eine Waffe auswählbar, jedoch nicht im Ruleset - da hat es ein eigenes Feld
				result += "Waffe" + (i+1) + "bezeichnung=" + ((Waffe) derChar.WaffenListe.get(i)).bezeichnung + " ; ";
				result += "Waffe" + (i+1) + "DK=" + ((Waffe) derChar.WaffenListe.get(i)).DK + "; ";
				result += "Waffe" + (i+1) + "TP=" + ((Waffe) derChar.WaffenListe.get(i)).dmg + "; ";
				result += "Waffe" + (i+1) + "INI=" + ((Waffe) derChar.WaffenListe.get(i)).INI + "; ";
				result += "Waffe" + (i+1) + "AT=" + ((Waffe) derChar.WaffenListe.get(i)).AT + "; ";
				result += "Waffe" + (i+1) + "PA=" + ((Waffe) derChar.WaffenListe.get(i)).PA + "; ";
				result += "Waffe" + (i+1) + "BF=" + ((Waffe) derChar.WaffenListe.get(i)).BF + "; ";
			}
		}
		result += "</value>\n";
		result += "<outer-class reference=\"../../../..\"/>\n";
		result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "</entry>\n";
		return result; 
	}

	private String ContentSonderfertigkeiten() {
		String result = "";
		//die Aufteilung auf die Unterschiedlichen Sftypen erfolgt erst hier, weil das Chareinlesen auch die Grundlage
		//für mein Würfeltool ist, wo das nicht unnötigerweise gemacht werden muss (dauert ja auch alles seine Zeit, auch wenns schnell geht)

		/*
		@AllgemeineSF
		@KampfSF
		@NahkampfSF
		@FernkampfSF
		@Hand2HandStyles
		@Hand2HandSF
		@MagieSF
		@KlerikaleSF
		 */
		LinkedList<String>[] sfs = new LinkedList[8];

		for(int i = 0; i < 8; i++)
			sfs[i] = new LinkedList<String>();


		try{
			for(int a = 0; a < derChar.Sfs.size(); a++){
				sfs[SfTypeZuIndex(SfZuSfType(derChar.Sfs.get(a)))].add(derChar.Sfs.get(a));
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(new JFrame(), "befüllung der listen", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
		}
		String[] key = {"AllgemeineSF", "KampfSF", "NahkampfSF", "FernkampfSF", "Hand2HandStyles", "Hand2HandSF", "MagieSF", "KlerikaleSF"};

		for(int i = 0; i < 8; i++){
			result += "<entry>\n";
			result += "<string>" + key[i].substring(0, 1).toLowerCase() + key[i].substring(1) +"</string>\n";
			result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			result += "<key>" + key[i] + "</key>\n";
			result += "<value class=\"string\">" + createVorteileString(sfs[i]) +"</value>\n";
			result += "<outer-class reference=\"../../../..\"/>\n";
			result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			result += "</entry>\n";

			result += "<entry>\n";
			result += "<string>" + key[i].substring(0, 1).toLowerCase() + key[i].substring(1) + "_Anzeigespeicher" +"</string>\n";
			result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			result += "<key>" + key[i] + "_Anzeigespeicher" + "</key>\n";
			result += "<value class=\"string\">" + createVorteileString(sfs[i]) +"</value>\n";
			result += "<outer-class reference=\"../../../..\"/>\n";
			result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			result += "</entry>\n";
		}


		return result;

	}
	private void fülleSfDictonary(){
		/*
	@AllgemeineSF x
	@KampfSF
	@NahkampfSF
	@FernkampfSF
	@Hand2HandStyles x
	@Hand2HandSF    x
	@MagieSF 		x
	@KlerikaleSF	x
		 */
		//Hand2HandSF
		SfDictonary.put("Akklimatisierung: Hitze","AllgemeineSF");
		SfDictonary.put("Akklimatisierung: Kälte","AllgemeineSF");
		SfDictonary.put("Akoluth (Angrosch)","KlerikaleSF");
		SfDictonary.put("Akoluth (Aves)","KlerikaleSF");
		SfDictonary.put("Akoluth (Boron)","KlerikaleSF");
		SfDictonary.put("Akoluth (Dunkle Zeiten)","KlerikaleSF");
		SfDictonary.put("Akoluth (Efferd)","KlerikaleSF");
		SfDictonary.put("Akoluth (Firun)","KlerikaleSF");
		SfDictonary.put("Akoluth (Gravesh)","KlerikaleSF");
		SfDictonary.put("Akoluth (Hesinde)","KlerikaleSF");
		SfDictonary.put("Akoluth (H'Ranga)","KlerikaleSF");
		SfDictonary.put("Akoluth (H'Szint)","KlerikaleSF");
		SfDictonary.put("Akoluth (Ifirn)","KlerikaleSF");
		SfDictonary.put("Akoluth (Ingerimm)","KlerikaleSF");
		SfDictonary.put("Akoluth (Kor)","KlerikaleSF");
		SfDictonary.put("Akoluth (Nandus)","KlerikaleSF");
		SfDictonary.put("Akoluth (Peraine)","KlerikaleSF");
		SfDictonary.put("Akoluth (Phex)","KlerikaleSF");
		SfDictonary.put("Akoluth (Praios)","KlerikaleSF");
		SfDictonary.put("Akoluth (Rahja)","KlerikaleSF");
		SfDictonary.put("Akoluth (Rondra)","KlerikaleSF");
		SfDictonary.put("Akoluth (Swafnir)","KlerikaleSF");
		SfDictonary.put("Akoluth (Travia)","KlerikaleSF");
		SfDictonary.put("Akoluth (Tsa)","KlerikaleSF");
		SfDictonary.put("Akoluth (Zsahh)","KlerikaleSF");
		SfDictonary.put("Astrale Meditation","MagieSF");
		SfDictonary.put("Aufmerksamkeit","KampfSF");
		SfDictonary.put("Aura der Heiligkeit","KlerikaleSF");
		SfDictonary.put("Aura verhüllen","MagieSF");
		SfDictonary.put("Aurapanzer","MagieSF");
		SfDictonary.put("Ausfall","NahkampfSF");
		SfDictonary.put("Auspendeln","Hand2HandSF");
		SfDictonary.put("Außergewöhnliche Schussweite","");
		SfDictonary.put("Ausweichen I","KampfSF");
		SfDictonary.put("Ausweichen II","KampfSF");
		SfDictonary.put("Ausweichen III","KampfSF");
		SfDictonary.put("Auxiliator","");
		SfDictonary.put("Befreiungsschlag","NahkampfSF");
		SfDictonary.put("Beidhändiger Kampf I","NahkampfSF");
		SfDictonary.put("Beidhändiger Kampf II","NahkampfSF");
		SfDictonary.put("Beinarbeit","Hand2HandSF");
		SfDictonary.put("Berittener Schütze","FernkampfSF");
		SfDictonary.put("Betäubungsschlag","NahkampfSF");
		SfDictonary.put("Bewahrer der Ahnenmacht","");
		SfDictonary.put("Binden","Hand2HandSF");
		SfDictonary.put("Biss","Hand2HandSF");
		SfDictonary.put("Blindkampf","KampfSF");
		SfDictonary.put("Block","Hand2HandSF");
		SfDictonary.put("Blutmagie","MagieSF");
		SfDictonary.put("Chimärenmeister","MagieSF");
		SfDictonary.put("Dämonenbindung I","MagieSF");
		SfDictonary.put("Dämonenbindung II","MagieSF");
		SfDictonary.put("Defensiver Kampfstil","NahkampfSF");
		SfDictonary.put("Doppelangriff","NahkampfSF");
		SfDictonary.put("Doppelschlag","Hand2HandSF");
		SfDictonary.put("Dschungelkundig","AllgemeineSF");
		SfDictonary.put("Eisenarm","Hand2HandSF");
		SfDictonary.put("Eisenhagel","FernkampfSF");
		SfDictonary.put("Eiserner Wille I","MagieSF");
		SfDictonary.put("Eiserner Wille II","MagieSF");
		SfDictonary.put("Eiskundig","AllgemeineSF");
		SfDictonary.put("Elementarharmonisierte Aura (Feuer/Wasser)","MagieSF");
		SfDictonary.put("Elementarharmonisierte Aura (Humus/Eis)","MagieSF");
		SfDictonary.put("Elementarharmonisierte Aura (Luft/Erz)","MagieSF");
		SfDictonary.put("Entwaffnen","NahkampfSF");
		SfDictonary.put("Exorzist","MagieSF");
		SfDictonary.put("Fälscher","AllgemeineSF");
		SfDictonary.put("Fernzauberei","MagieSF");
		SfDictonary.put("Festnageln","Hand2HandSF");
		SfDictonary.put("Finte","NahkampfSF");
		SfDictonary.put("Form der Formlosigkeit","MagieSF");
		SfDictonary.put("Formation","NahkampfSF");
		SfDictonary.put("Former der Leiber","");
		SfDictonary.put("Fußfeger","Hand2HandSF");
		SfDictonary.put("Geber der Gestalt","MagieSF");
		SfDictonary.put("Geber des Funkens","");
		SfDictonary.put("Gebieter der Rotte","");
		SfDictonary.put("Gebirgskundig","AllgemeineSF");
		SfDictonary.put("Gedankenschutz","MagieSF");
		SfDictonary.put("Gefäß der Sterne","MagieSF");
		SfDictonary.put("Gegenhalten","Hand2HandSF");
		SfDictonary.put("Gerade","Hand2HandSF");
		SfDictonary.put("Geschützmeister","FernkampfSF");
		SfDictonary.put("Gezielter Stich","NahkampfSF");
		SfDictonary.put("Golembauer","MagieSF");
		SfDictonary.put("Griff","Hand2HandSF");
		SfDictonary.put("Große Meditation","MagieSF");
		SfDictonary.put("Halbschwert","NahkampfSF");
		SfDictonary.put("Halten","Hand2HandSF");
		SfDictonary.put("Hammerschlag","NahkampfSF");
		SfDictonary.put("Handkante","Hand2HandSF");
		SfDictonary.put("Herr über die Gebeine","");
		SfDictonary.put("Hoher Tritt","Hand2HandSF");
		SfDictonary.put("Höhere Dämonenbindung","MagieSF");
		SfDictonary.put("Höhlenkundig","AllgemeineSF");
		SfDictonary.put("Hypervehemenz","MagieSF");
		SfDictonary.put("Improvisierte Waffen","NahkampfSF");
		SfDictonary.put("Invocatio Integra","MagieSF");
		SfDictonary.put("Kampf im Wasser","NahkampfSF");
		SfDictonary.put("Kampfgespür","KampfSF");
		SfDictonary.put("Kampfreflexe","KampfSF");
		SfDictonary.put("Karmalqueste","KlerikaleSF");
		SfDictonary.put("Klammer","Hand2HandSF");
		SfDictonary.put("Klingensturm","NahkampfSF");
		SfDictonary.put("Klingentänzer","NahkampfSF");
		SfDictonary.put("Klingenwand","NahkampfSF");
		SfDictonary.put("Knaufschlag","NahkampfSF");
		SfDictonary.put("Knie","Hand2HandSF");
		SfDictonary.put("Konzentrationsstärke","MagieSF");
		SfDictonary.put("Kopfstoß","Hand2HandSF");
		SfDictonary.put("Kraftlinienmagie I","MagieSF");
		SfDictonary.put("Kraftlinienmagie II","MagieSF");
		SfDictonary.put("Kraftspeicher","MagieSF");
		SfDictonary.put("Kraftvolles Rempeln","");
		SfDictonary.put("Kreuzblock","Hand2HandSF");
		SfDictonary.put("Kriegsreiterei","KampfSF");
		SfDictonary.put("Kulturkunde (Almada)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Amazonen)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Ambosszwerge)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Andergast/Nostria)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Aranien)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Archaische Achaz)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Auelfen)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Bornland)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Brillantzwerge)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Brobim)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Bukanier)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Dschungelstämme)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Erzzwerge)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Ferkina)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Firnelfen)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Fjarninger)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Gjalskerländer)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Goblins)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Grolme)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Horasreich)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Hügelzwerge)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Maraskan)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Mittelreich)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Nivesen)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Norbarden)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Nordlande)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Novadi)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Orks)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Stammesachaz)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Steppenelfen)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Südaventurien)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Svellttal)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Thorwal)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Tocamuyac)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Trolle)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Trollzacker)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Tulamidenlande)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Waldelfen)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Zahori)","AllgemeineSF");
		SfDictonary.put("Kulturkunde (Zyklopeninseln)","AllgemeineSF");
		SfDictonary.put("Linkhand","NahkampfSF");
		SfDictonary.put("Liturgie: Achmad'ayan ankhrella al'nurach Shaitanim","KlerikaleSF");
		SfDictonary.put("Liturgie: Aller Welt Freund","KlerikaleSF");
		SfDictonary.put("Liturgie: Allmacht der Lohe","KlerikaleSF");
		SfDictonary.put("Liturgie: Am Busen der Natur (Zuflucht finden)","KlerikaleSF");
		SfDictonary.put("Liturgie: Anathema","KlerikaleSF");
		SfDictonary.put("Liturgie: Angroschs Opfergabe","KlerikaleSF");
		SfDictonary.put("Liturgie: Angroschs Zorn (Waliburias Wehr)","KlerikaleSF");
		SfDictonary.put("Liturgie: Anrufug der Erdkraft","KlerikaleSF");
		SfDictonary.put("Liturgie: Anrufung der Winde","KlerikaleSF");
		SfDictonary.put("Liturgie: Anrufung der Winde (III)","KlerikaleSF");
		SfDictonary.put("Liturgie: Anrufung Nuiannas","KlerikaleSF");
		SfDictonary.put("Liturgie: Arcanum Interdictum","KlerikaleSF");
		SfDictonary.put("Liturgie: Argelions bannende Hand","KlerikaleSF");
		SfDictonary.put("Liturgie: Argelions Mantel","KlerikaleSF");
		SfDictonary.put("Liturgie: Argelions Spiegel","KlerikaleSF");
		SfDictonary.put("Liturgie: Ascandears Hingabe","KlerikaleSF");
		SfDictonary.put("Liturgie: Auge des Händlers","KlerikaleSF");
		SfDictonary.put("Liturgie: Auge des Mondes","KlerikaleSF");
		SfDictonary.put("Liturgie: Auge Xeledonos - Xeledons Helles Licht","KlerikaleSF");
		SfDictonary.put("Liturgie: Aura der Form","KlerikaleSF");
		SfDictonary.put("Liturgie: Aura des Regenbogens","KlerikaleSF");
		SfDictonary.put("Liturgie: Auraprüfung","KlerikaleSF");
		SfDictonary.put("Liturgie: Azilas Quellgesang","KlerikaleSF");
		SfDictonary.put("Liturgie: Bannfluch des Heiligen Khalid","KlerikaleSF");
		SfDictonary.put("Liturgie: Begehen der Heiligen Wasser","KlerikaleSF");
		SfDictonary.put("Liturgie: Belemans Hochzeit","KlerikaleSF");
		SfDictonary.put("Liturgie: Bishdariels Angesicht (Kleine Liturgie des Hl. Nemekath)","KlerikaleSF");
		SfDictonary.put("Liturgie: Bishdariels Auge","KlerikaleSF");
		SfDictonary.put("Liturgie: Bishdariels Auge (III)","KlerikaleSF");
		SfDictonary.put("Liturgie: Bishdariels Auge (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Bishdariels Warnung","KlerikaleSF");
		SfDictonary.put("Liturgie: Blendstrahl aus Alveran","KlerikaleSF");
		SfDictonary.put("Liturgie: Blick an den klaren Himmel (Sterne funkeln immerfort)","KlerikaleSF");
		SfDictonary.put("Liturgie: Blick der Weberin","KlerikaleSF");
		SfDictonary.put("Liturgie: Blick für das Handwerk","KlerikaleSF");
		SfDictonary.put("Liturgie: Blick in die Flammen","KlerikaleSF");
		SfDictonary.put("Liturgie: Blutschwur (Großer Eidsegen)","KlerikaleSF");
		SfDictonary.put("Liturgie: Bootssegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Borons süße Gnade","KlerikaleSF");
		SfDictonary.put("Liturgie: Borons süße Gnade (V)","KlerikaleSF");
		SfDictonary.put("Liturgie: Buchprüfung","KlerikaleSF");
		SfDictonary.put("Liturgie: Canyzeths Weisheit","KlerikaleSF");
		SfDictonary.put("Liturgie: Cereborns Handreichung (Handwerkssegen)","KlerikaleSF");
		SfDictonary.put("Liturgie: Daradors Bann der Schatten","KlerikaleSF");
		SfDictonary.put("Liturgie: Daradors prüfender Blick (Unverstellter Blick)","KlerikaleSF");
		SfDictonary.put("Liturgie: Das Schwarze Fell durch das Rote Blut","KlerikaleSF");
		SfDictonary.put("Liturgie: Der Gänsemutter warmes Nest (Zuflucht finden)","KlerikaleSF");
		SfDictonary.put("Liturgie: Des Einen bezaubernder Sphärenklang","KlerikaleSF");
		SfDictonary.put("Liturgie: Des Herren Goldener Mittag (Weisung des Himmels)","KlerikaleSF");
		SfDictonary.put("Liturgie: Dorlens Verbrüderung","KlerikaleSF");
		SfDictonary.put("Liturgie: Dreifacher Saatsegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Dythlinds Wandeln im Feuer (Vertrauter der Flamme)","KlerikaleSF");
		SfDictonary.put("Liturgie: Efferdsegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Eherne Kraft - lodernder Zorn","KlerikaleSF");
		SfDictonary.put("Liturgie: Ehrenhafter Zweikampf","KlerikaleSF");
		SfDictonary.put("Liturgie: Eidechsenhaut","KlerikaleSF");
		SfDictonary.put("Liturgie: Eidsegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Ein Bild für die Ewigkeit","KlerikaleSF");
		SfDictonary.put("Liturgie: Ein Freund in Zeiten der Not","KlerikaleSF");
		SfDictonary.put("Liturgie: Entzug von Nandus' Gaben","KlerikaleSF");
		SfDictonary.put("Liturgie: Erneuerung des Geborstenen","KlerikaleSF");
		SfDictonary.put("Liturgie: Etilias Gnade","KlerikaleSF");
		SfDictonary.put("Liturgie: Etilias Zeit der Meditation (Ruf zur Ruhe)","KlerikaleSF");
		SfDictonary.put("Liturgie: Ewige Jugend","KlerikaleSF");
		SfDictonary.put("Liturgie: Ewiger Wächter","KlerikaleSF");
		SfDictonary.put("Liturgie: Exkommunikation","KlerikaleSF");
		SfDictonary.put("Liturgie: Exkommunikation (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Exkommunikation (V)","KlerikaleSF");
		SfDictonary.put("Liturgie: Exorzismus","KlerikaleSF");
		SfDictonary.put("Liturgie: Exorzismus (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Exorzismus (V)","KlerikaleSF");
		SfDictonary.put("Liturgie: Exorzismus (VI)","KlerikaleSF");
		SfDictonary.put("Liturgie: Feuersegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Feuertaufe (Initiation)","KlerikaleSF");
		SfDictonary.put("Liturgie: Firuns Einsicht","KlerikaleSF");
		SfDictonary.put("Liturgie: Flagge des Regenbogens","KlerikaleSF");
		SfDictonary.put("Liturgie: Fluch wider die Ungläubigen","KlerikaleSF");
		SfDictonary.put("Liturgie: Freundliche Aufnahme","KlerikaleSF");
		SfDictonary.put("Liturgie: Frieden der Melodie","KlerikaleSF");
		SfDictonary.put("Liturgie: Fünfte Lobpreisung des Frühlings","KlerikaleSF");
		SfDictonary.put("Liturgie: Fürbitten des Heiligen Therbûn","KlerikaleSF");
		SfDictonary.put("Liturgie: Garafans Gleißende Schwingen","KlerikaleSF");
		SfDictonary.put("Liturgie: Gebieter der Lava","KlerikaleSF");
		SfDictonary.put("Liturgie: Geburtssegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Geläutert sei Erz und Goldgestein","KlerikaleSF");
		SfDictonary.put("Liturgie: Gemeinschaft der treuen Gefährten","KlerikaleSF");
		SfDictonary.put("Liturgie: Gesang der Delphine","KlerikaleSF");
		SfDictonary.put("Liturgie: Gesang der Wale (Ruf der Gefährten)","KlerikaleSF");
		SfDictonary.put("Liturgie: Gesang der Wale (Ruf der Gefährten) (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Gesegneter Fang","KlerikaleSF");
		SfDictonary.put("Liturgie: Geteiltes Leid","KlerikaleSF");
		SfDictonary.put("Liturgie: Gift der Erkenntnis","KlerikaleSF");
		SfDictonary.put("Liturgie: Gilborns heilige Aura (Argelions Mantel)","KlerikaleSF");
		SfDictonary.put("Liturgie: Gleichklang des Geistes","KlerikaleSF");
		SfDictonary.put("Liturgie: Glückssegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Goldene Hand","KlerikaleSF");
		SfDictonary.put("Liturgie: Goldene Rüstung","KlerikaleSF");
		SfDictonary.put("Liturgie: Goldener Blick","KlerikaleSF");
		SfDictonary.put("Liturgie: Gott der Götter","KlerikaleSF");
		SfDictonary.put("Liturgie: Gott der Götter (III)","KlerikaleSF");
		SfDictonary.put("Liturgie: Gott der Götter (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Gott der Götter (V)","KlerikaleSF");
		SfDictonary.put("Liturgie: Gott der Götter (VI)","KlerikaleSF");
		SfDictonary.put("Liturgie: Göttliche Strafe","KlerikaleSF");
		SfDictonary.put("Liturgie: Göttliche Strafe (V)","KlerikaleSF");
		SfDictonary.put("Liturgie: Göttliche Strafe (VI)","KlerikaleSF");
		SfDictonary.put("Liturgie: Göttliche Verständigung","KlerikaleSF");
		SfDictonary.put("Liturgie: Göttliche Verständigung (III)","KlerikaleSF");
		SfDictonary.put("Liturgie: Göttliche Verständigung (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Göttliches Zeichen","KlerikaleSF");
		SfDictonary.put("Liturgie: Göttliches Zeichen (III)","KlerikaleSF");
		SfDictonary.put("Liturgie: Grabsegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Graues Siegel","KlerikaleSF");
		SfDictonary.put("Liturgie: Graues Siegel (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Große Seelenprüfung","KlerikaleSF");
		SfDictonary.put("Liturgie: Große Seelenwaschung (Exorzismus)","KlerikaleSF");
		SfDictonary.put("Liturgie: Große Seelenwaschung (Exorzismus) (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Große Seelenwaschung (Exorzismus) (V)","KlerikaleSF");
		SfDictonary.put("Liturgie: Große Seelenwaschung (Exorzismus) (VI)","KlerikaleSF");
		SfDictonary.put("Liturgie: Große Weihe des Heimsteins","KlerikaleSF");
		SfDictonary.put("Liturgie: Großer Eidsegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Großer Giftbann (Großer Speisesegen)","KlerikaleSF");
		SfDictonary.put("Liturgie: Großer Reisesegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Großer Speisesegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Großer Weihesegen der Waffe","KlerikaleSF");
		SfDictonary.put("Liturgie: Gruß des Versunkenen","KlerikaleSF");
		SfDictonary.put("Liturgie: Händlersegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Handwerkssegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Harmoniesegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Hashnabiths Flehen","KlerikaleSF");
		SfDictonary.put("Liturgie: Hauch Borons","KlerikaleSF");
		SfDictonary.put("Liturgie: Hauch der Leidenschaft (Handwerkssegen)","KlerikaleSF");
		SfDictonary.put("Liturgie: Hausfrieden","KlerikaleSF");
		SfDictonary.put("Liturgie: Haut des Chamäleons (Verborgen wie der Neumond)","KlerikaleSF");
		SfDictonary.put("Liturgie: Heilige Salbung der Peraine (Tsas Heiliges Lebensgeschenk)","KlerikaleSF");
		SfDictonary.put("Liturgie: Heilige Schmiedeglut","KlerikaleSF");
		SfDictonary.put("Liturgie: Heiliger Befehl","KlerikaleSF");
		SfDictonary.put("Liturgie: Heiliger Lehnseid","KlerikaleSF");
		SfDictonary.put("Liturgie: Heiliges Liebesspiel","KlerikaleSF");
		SfDictonary.put("Liturgie: Heilungssegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Herbeirufung der Diener des Herren","KlerikaleSF");
		SfDictonary.put("Liturgie: Herbeirufung der Heerscharen des Rattenkindes","KlerikaleSF");
		SfDictonary.put("Liturgie: Herr über Feuer und Glut","KlerikaleSF");
		SfDictonary.put("Liturgie: Hesindes Fingerzeig (Buchprüfung)","KlerikaleSF");
		SfDictonary.put("Liturgie: Hilfe in der Not","KlerikaleSF");
		SfDictonary.put("Liturgie: Hoftag der Sprachen","KlerikaleSF");
		SfDictonary.put("Liturgie: Indoktrination","KlerikaleSF");
		SfDictonary.put("Liturgie: Ingalfs Alchimie","KlerikaleSF");
		SfDictonary.put("Liturgie: Ingerimms Zorn verschone uns","KlerikaleSF");
		SfDictonary.put("Liturgie: Initiation","KlerikaleSF");
		SfDictonary.put("Liturgie: Innere Ruhe","KlerikaleSF");
		SfDictonary.put("Liturgie: Jagdglück","KlerikaleSF");
		SfDictonary.put("Liturgie: Kälbchensegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Keta ajaban kud'a - Wundersames Teilen des Martyriums","KlerikaleSF");
		SfDictonary.put("Liturgie: Keta ajaban kud'a - Wundersames Teilen des Martyriums (V)","KlerikaleSF");
		SfDictonary.put("Liturgie: Keta ajaban kud'a - Wundersames Teilen des Martyriums (VI)","KlerikaleSF");
		SfDictonary.put("Liturgie: Khablas Jugend","KlerikaleSF");
		SfDictonary.put("Liturgie: Khablas Jugend (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Khablas makelloser Leib","KlerikaleSF");
		SfDictonary.put("Liturgie: Khablas makelloser Leib (VI)","KlerikaleSF");
		SfDictonary.put("Liturgie: Kirschblütenregen","KlerikaleSF");
		SfDictonary.put("Liturgie: Kleine Liturgie des Heiligen Nemekath","KlerikaleSF");
		SfDictonary.put("Liturgie: Kleine Segnung des Heimsteins","KlerikaleSF");
		SfDictonary.put("Liturgie: Kleiner Giftbann","KlerikaleSF");
		SfDictonary.put("Liturgie: Konsekration","KlerikaleSF");
		SfDictonary.put("Liturgie: Kräftigung der Schwachen und Versehrten","KlerikaleSF");
		SfDictonary.put("Liturgie: Kräutersegen des Heiligen Nemekath (Rahjas Rauschsegen)","KlerikaleSF");
		SfDictonary.put("Liturgie: Lagorax' Hammer rufen","KlerikaleSF");
		SfDictonary.put("Liturgie: Levthans Fesseln","KlerikaleSF");
		SfDictonary.put("Liturgie: Licht des Herrn","KlerikaleSF");
		SfDictonary.put("Liturgie: Licht des verborgenen Pfades","KlerikaleSF");
		SfDictonary.put("Liturgie: Lohn der Unverzagten","KlerikaleSF");
		SfDictonary.put("Liturgie: Lug und Trug (Unverstellter Blick)","KlerikaleSF");
		SfDictonary.put("Liturgie: Mannschaftssegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Marbos Geisterblick (Nemekaths Geisterblick)","KlerikaleSF");
		SfDictonary.put("Liturgie: Marbos Geleit","KlerikaleSF");
		SfDictonary.put("Liturgie: Märtyrersegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Meisterstück (Wandeln in Hesindes Hain)","KlerikaleSF");
		SfDictonary.put("Liturgie: Mondsilberzunge","KlerikaleSF");
		SfDictonary.put("Liturgie: Namenlose Kälte","KlerikaleSF");
		SfDictonary.put("Liturgie: Namenlose Raserei","KlerikaleSF");
		SfDictonary.put("Liturgie: Namenloser Zweifel - Namenlose Erleuchtung","KlerikaleSF");
		SfDictonary.put("Liturgie: Namenloses Vergessen","KlerikaleSF");
		SfDictonary.put("Liturgie: Nandus Schriftkenntnis (Schrifttum ferner Lande)","KlerikaleSF");
		SfDictonary.put("Liturgie: Nemekaths Bannfluch (Bishdariels Warnung)","KlerikaleSF");
		SfDictonary.put("Liturgie: Nemekaths Geisterblick","KlerikaleSF");
		SfDictonary.put("Liturgie: Nemekaths Zwiesprache","KlerikaleSF");
		SfDictonary.put("Liturgie: Neun Streiche in Einem","KlerikaleSF");
		SfDictonary.put("Liturgie: Nimmermüde Wanderschaft","KlerikaleSF");
		SfDictonary.put("Liturgie: Noionas Zuspruch (Segen der Hl. Velvenya)","KlerikaleSF");
		SfDictonary.put("Liturgie: Objektsegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Objektsegen (II)","KlerikaleSF");
		SfDictonary.put("Liturgie: Objektsegen (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Objektsegen (V)","KlerikaleSF");
		SfDictonary.put("Liturgie: Objektweihe","KlerikaleSF");
		SfDictonary.put("Liturgie: Objektweihe (III)","KlerikaleSF");
		SfDictonary.put("Liturgie: Objektweihe (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Objektweihe (V)","KlerikaleSF");
		SfDictonary.put("Liturgie: Objektweihe (VI)","KlerikaleSF");
		SfDictonary.put("Liturgie: Ordination","KlerikaleSF");
		SfDictonary.put("Liturgie: Parinors Vermächtnis","KlerikaleSF");
		SfDictonary.put("Liturgie: Pech und Schwefel","KlerikaleSF");
		SfDictonary.put("Liturgie: Peraines Pflanzengespür","KlerikaleSF");
		SfDictonary.put("Liturgie: Phexens Augenzwinkern","KlerikaleSF");
		SfDictonary.put("Liturgie: Phexens Elsterflug","KlerikaleSF");
		SfDictonary.put("Liturgie: Phexens Kunstverstand (Blick für das Handwerk)","KlerikaleSF");
		SfDictonary.put("Liturgie: Phexens Meisterschlüssel","KlerikaleSF");
		SfDictonary.put("Liturgie: Phexens Nebelleib","KlerikaleSF");
		SfDictonary.put("Liturgie: Phexens Schatten","KlerikaleSF");
		SfDictonary.put("Liturgie: Phexens Sternenwurf","KlerikaleSF");
		SfDictonary.put("Liturgie: Phexens wunderbare Verständigung","KlerikaleSF");
		SfDictonary.put("Liturgie: Praios' Magiebann","KlerikaleSF");
		SfDictonary.put("Liturgie: Praios Mahnung","KlerikaleSF");
		SfDictonary.put("Liturgie: Prophezeiung","KlerikaleSF");
		SfDictonary.put("Liturgie: Purgation","KlerikaleSF");
		SfDictonary.put("Liturgie: Quellsegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Rahjalinas Kuss","KlerikaleSF");
		SfDictonary.put("Liturgie: Rahjalinas Weinranke (Parinors Vermächtnis)","KlerikaleSF");
		SfDictonary.put("Liturgie: Rahjas Erquickung (Schlaf des Gesegneten)","KlerikaleSF");
		SfDictonary.put("Liturgie: Rahjas Fest der Freude","KlerikaleSF");
		SfDictonary.put("Liturgie: Rahjas Freiheit","KlerikaleSF");
		SfDictonary.put("Liturgie: Rahjas geheiligter Wein","KlerikaleSF");
		SfDictonary.put("Liturgie: Rahjas heitere Gelassenheit (Segen der Heiligen Noiona (IV))","KlerikaleSF");
		SfDictonary.put("Liturgie: Rahjas heitere Gelassenheit (Segen der Heiligen Noiona)","KlerikaleSF");
		SfDictonary.put("Liturgie: Rahjas Rauschsegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Rahjas Schoß","KlerikaleSF");
		SfDictonary.put("Liturgie: Rahjas Sinnlichkeit","KlerikaleSF");
		SfDictonary.put("Liturgie: Reichung des Amethyst","KlerikaleSF");
		SfDictonary.put("Liturgie: Reichung des Amethyst (II)","KlerikaleSF");
		SfDictonary.put("Liturgie: Reichung des Amethyst (III)","KlerikaleSF");
		SfDictonary.put("Liturgie: Reichung des Amethyst (V)","KlerikaleSF");
		SfDictonary.put("Liturgie: Reisesegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Rondras Hochzeit","KlerikaleSF");
		SfDictonary.put("Liturgie: Rondras wundersame Rüstung","KlerikaleSF");
		SfDictonary.put("Liturgie: Ruf der Gefährten","KlerikaleSF");
		SfDictonary.put("Liturgie: Ruf der Gefährten (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Ruf in Borons Arme","KlerikaleSF");
		SfDictonary.put("Liturgie: Ruf in Borons Arme (III)","KlerikaleSF");
		SfDictonary.put("Liturgie: Ruf zur Ruhe","KlerikaleSF");
		SfDictonary.put("Liturgie: Salbung der Heiligen Noiona (Exorzismus)","KlerikaleSF");
		SfDictonary.put("Liturgie: Salbung der Heiligen Noiona (Exorzismus) (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Salbung der Heiligen Noiona (Exorzismus) (V)","KlerikaleSF");
		SfDictonary.put("Liturgie: Salbung der Heiligen Noiona (Exorzismus) (VI)","KlerikaleSF");
		SfDictonary.put("Liturgie: Sankt Gilborns Bannfluch (Argelions bannende Hand)","KlerikaleSF");
		SfDictonary.put("Liturgie: Schattenlarve","KlerikaleSF");
		SfDictonary.put("Liturgie: Schiffssegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Schlaf des Gesegneten","KlerikaleSF");
		SfDictonary.put("Liturgie: Schlangenstab","KlerikaleSF");
		SfDictonary.put("Liturgie: Schleichende Fäulnis","KlerikaleSF");
		SfDictonary.put("Liturgie: Schneesturm/Eissturm","KlerikaleSF");
		SfDictonary.put("Liturgie: Schrifttum ferner Lande","KlerikaleSF");
		SfDictonary.put("Liturgie: Schutzsegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Schutzsegen (II)","KlerikaleSF");
		SfDictonary.put("Liturgie: Schutzsegen (III)","KlerikaleSF");
		SfDictonary.put("Liturgie: Schwindende Zauberkraft","KlerikaleSF");
		SfDictonary.put("Liturgie: Seelenbannung","KlerikaleSF");
		SfDictonary.put("Liturgie: Seelenprüfung","KlerikaleSF");
		SfDictonary.put("Liturgie: Seelenschatten","KlerikaleSF");
		SfDictonary.put("Liturgie: Segen der Heiligen Ardare","KlerikaleSF");
		SfDictonary.put("Liturgie: Segen der Heiligen Noiona","KlerikaleSF");
		SfDictonary.put("Liturgie: Segen der Heiligen Noiona (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Segen der Heiligen Theria","KlerikaleSF");
		SfDictonary.put("Liturgie: Segen der Heiligen Velvenya","KlerikaleSF");
		SfDictonary.put("Liturgie: Segen des Heiligen Badilak (Segen der Heiligen Noiona (IV))","KlerikaleSF");
		SfDictonary.put("Liturgie: Segen des Heiligen Badilak (Segen der Heiligen Noiona)","KlerikaleSF");
		SfDictonary.put("Liturgie: Segen des Heiligen Hlûthar","KlerikaleSF");
		SfDictonary.put("Liturgie: Segen des Plättlings","KlerikaleSF");
		SfDictonary.put("Liturgie: Segensreiches Wasser","KlerikaleSF");
		SfDictonary.put("Liturgie: Segnung der Schlacht","KlerikaleSF");
		SfDictonary.put("Liturgie: Segnung der Stählernen Stirn","KlerikaleSF");
		SfDictonary.put("Liturgie: Segnung des Heimes","KlerikaleSF");
		SfDictonary.put("Liturgie: Sichere Wanderung im Schnee","KlerikaleSF");
		SfDictonary.put("Liturgie: Sichere Wanderung im Schnee (III)","KlerikaleSF");
		SfDictonary.put("Liturgie: Sicherer Weg durch Fels","KlerikaleSF");
		SfDictonary.put("Liturgie: Sicht auf Madas Welt","KlerikaleSF");
		SfDictonary.put("Liturgie: Siegel Borons","KlerikaleSF");
		SfDictonary.put("Liturgie: Siegel Borons (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Simias Kelch (Tsas Segensreicher Neuanfang)","KlerikaleSF");
		SfDictonary.put("Liturgie: Speisesegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Speisung der Bedürftigen","KlerikaleSF");
		SfDictonary.put("Liturgie: Speisung der hungernden Seele","KlerikaleSF");
		SfDictonary.put("Liturgie: Speisung der hungernden Seele (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Sprechende Symbole","KlerikaleSF");
		SfDictonary.put("Liturgie: Sterne funkeln immerfort","KlerikaleSF");
		SfDictonary.put("Liturgie: Sternenglanz","KlerikaleSF");
		SfDictonary.put("Liturgie: Sternenspur","KlerikaleSF");
		SfDictonary.put("Liturgie: Sternenstaub","KlerikaleSF");
		SfDictonary.put("Liturgie: Sulvas Gnade","KlerikaleSF");
		SfDictonary.put("Liturgie: Swafnirs Fluke","KlerikaleSF");
		SfDictonary.put("Liturgie: Swafnirs Ruhelied","KlerikaleSF");
		SfDictonary.put("Liturgie: Thalionmels Schlachtgesang","KlerikaleSF");
		SfDictonary.put("Liturgie: Therbûns Erkenntnis","KlerikaleSF");
		SfDictonary.put("Liturgie: Tierempathie","KlerikaleSF");
		SfDictonary.put("Liturgie: Tiergestalt","KlerikaleSF");
		SfDictonary.put("Liturgie: Tiersprache","KlerikaleSF");
		SfDictonary.put("Liturgie: Tranksegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Traviabund (Großer Eidsegen)","KlerikaleSF");
		SfDictonary.put("Liturgie: Travias Gebet der sicheren Zuflucht","KlerikaleSF");
		SfDictonary.put("Liturgie: Travias Gebet der verborgenen Halle","KlerikaleSF");
		SfDictonary.put("Liturgie: Travinians Segen der Schwelle","KlerikaleSF");
		SfDictonary.put("Liturgie: Trophäe erhalten","KlerikaleSF");
		SfDictonary.put("Liturgie: Tsas ewige Jugend","KlerikaleSF");
		SfDictonary.put("Liturgie: Tsas Heiliges Lebensgeschenk","KlerikaleSF");
		SfDictonary.put("Liturgie: Tsas Lebensschutz","KlerikaleSF");
		SfDictonary.put("Liturgie: Tsas Segensreicher Neuanfang","KlerikaleSF");
		SfDictonary.put("Liturgie: Tsas Wunderbare Erneuerung","KlerikaleSF");
		SfDictonary.put("Liturgie: Tsas Wundersame Fruchtbarkeit","KlerikaleSF");
		SfDictonary.put("Liturgie: Über die Wolken","KlerikaleSF");
		SfDictonary.put("Liturgie: Über die Wolken (VI)","KlerikaleSF");
		SfDictonary.put("Liturgie: Ucuris Geleit","KlerikaleSF");
		SfDictonary.put("Liturgie: Unterpfand des Heiligen Rhys","KlerikaleSF");
		SfDictonary.put("Liturgie: Unverstellter Blick","KlerikaleSF");
		SfDictonary.put("Liturgie: Urischars ordnender Blick","KlerikaleSF");
		SfDictonary.put("Liturgie: Verborgen wie der Neumond","KlerikaleSF");
		SfDictonary.put("Liturgie: Versiegeltes Wissen (Graues Siegel)","KlerikaleSF");
		SfDictonary.put("Liturgie: Versiegeltes Wissen (Graues Siegel) (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Vertrauter der Flamme","KlerikaleSF");
		SfDictonary.put("Liturgie: Vertrauter des Felsens","KlerikaleSF");
		SfDictonary.put("Liturgie: Vertreibung des Dunkelsinns","KlerikaleSF");
		SfDictonary.put("Liturgie: Visionssuche","KlerikaleSF");
		SfDictonary.put("Liturgie: Waffenfluch","KlerikaleSF");
		SfDictonary.put("Liturgie: Waliburias Wehr","KlerikaleSF");
		SfDictonary.put("Liturgie: Wandeln in Hesindes Hain","KlerikaleSF");
		SfDictonary.put("Liturgie: Weg des Fuchses","KlerikaleSF");
		SfDictonary.put("Liturgie: Weihe der Ewigen Flamme","KlerikaleSF");
		SfDictonary.put("Liturgie: Weihe der letzten Ruhestatt","KlerikaleSF");
		SfDictonary.put("Liturgie: Weihegesang der Heiligen Elida von Salza","KlerikaleSF");
		SfDictonary.put("Liturgie: Weisheitssegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Weisung des Himmels","KlerikaleSF");
		SfDictonary.put("Liturgie: Wille zur Wahrheit","KlerikaleSF");
		SfDictonary.put("Liturgie: Winterschlaf","KlerikaleSF");
		SfDictonary.put("Liturgie: Wort der Wahrheit (Heiliger Befehl)","KlerikaleSF");
		SfDictonary.put("Liturgie: Wundersame Blütenpracht","KlerikaleSF");
		SfDictonary.put("Liturgie: Wundsegen","KlerikaleSF");
		SfDictonary.put("Liturgie: Wundsegen (III)","KlerikaleSF");
		SfDictonary.put("Liturgie: Wundsegen (IV)","KlerikaleSF");
		SfDictonary.put("Liturgie: Zerschmetternder Bannstrahl","KlerikaleSF");
		SfDictonary.put("Liturgie: Zuflucht finden","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Angrosch)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Aves)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Boron)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Dunkle Zeiten)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Efferd)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Firun)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Gravesh)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Hesinde)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (H'Ranga)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (H'Szint)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Ifirn)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Ingerimm)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Kor)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Namenloser)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Nandus)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Peraine)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Phex)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Praios)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Rahja)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Rondra)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Swafnir)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Travia)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Tsa)","KlerikaleSF");
		SfDictonary.put("Liturgiekenntnis (Zsahh)","KlerikaleSF");
		SfDictonary.put("Maraskankundig","AllgemeineSF");
		SfDictonary.put("Matrixgeber","MagieSF");
		SfDictonary.put("Matrixkontrolle","MagieSF");
		SfDictonary.put("Matrixregeneration I","MagieSF");
		SfDictonary.put("Matrixregeneration II","MagieSF");
		SfDictonary.put("Matrixverständnis","MagieSF");
		SfDictonary.put("Meereskundig","AllgemeineSF");
		SfDictonary.put("Meister der Improvisation","AllgemeineSF");
		SfDictonary.put("Meisterliche Regeneration","MagieSF");
		SfDictonary.put("Meisterliche Zauberkontrolle","MagieSF");
		SfDictonary.put("Meisterliche Zauberkontrolle I","MagieSF");
		SfDictonary.put("Meisterliche Zauberkontrolle II","MagieSF");
		SfDictonary.put("Meisterliches Entwaffnen","NahkampfSF");
		SfDictonary.put("Meisterparade","NahkampfSF");
		SfDictonary.put("Meisterschütze (Armbrust)","FernkampfSF");
		SfDictonary.put("Meisterschütze (Belagerungswaffen)","FernkampfSF");
		SfDictonary.put("Meisterschütze (Blasrohr)","FernkampfSF");
		SfDictonary.put("Meisterschütze (Bogen)","FernkampfSF");
		SfDictonary.put("Meisterschütze (Diskus)","FernkampfSF");
		SfDictonary.put("Meisterschütze (Schleuder)","FernkampfSF");
		SfDictonary.put("Meisterschütze (Wurfbeile)","FernkampfSF");
		SfDictonary.put("Meisterschütze (Wurfmesser)","FernkampfSF");
		SfDictonary.put("Meisterschütze (Wurfspeere)","FernkampfSF");
		SfDictonary.put("Merkmalskenntnis: Antimagie","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Beschwörung","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Dämonisch","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Dämonisch (Agrimoth)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Dämonisch (Amazeroth)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Dämonisch (Asfaloth)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Dämonisch (Belhalhar)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Dämonisch (Belzhorash)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Dämonisch (Blakharaz)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Dämonisch (Lolgramoth)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Dämonisch (Thargunitoth)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Eigenschaften","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Einfluss","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Elementar","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Elementar (Eis)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Elementar (Erz)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Elementar (Feuer)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Elementar (Humus)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Elementar (Luft)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Elementar (Wasser)","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Form","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Geisterwesen","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Heilung","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Hellsicht","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Herbeirufung","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Herrschaft","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Illusion","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Kraft","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Limbus","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Metamagie","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Objekt","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Schaden","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Telekinese","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Temporal","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Umwelt","MagieSF");
		SfDictonary.put("Merkmalskenntnis: Verständigung","MagieSF");
		SfDictonary.put("Nandusgefälliges Wissen","AllgemeineSF");
		SfDictonary.put("Nekromant","MagieSF");
		SfDictonary.put("Niederringen","Hand2HandSF");
		SfDictonary.put("Niederwerfen","NahkampfSF");
		SfDictonary.put("Ortskenntnis (Örtlichkeit 1)","AllgemeineSF");
		SfDictonary.put("Ortskenntnis (Örtlichkeit 2)","AllgemeineSF");
		SfDictonary.put("Ortskenntnis (Stadtteil/Kleinstadt)","AllgemeineSF");
		SfDictonary.put("Ortskenntnis (Strecke 1)","AllgemeineSF");
		SfDictonary.put("Ortskenntnis (Strecke 2)","AllgemeineSF");
		SfDictonary.put("Ottagaldr","MagieSF");
		SfDictonary.put("Parierwaffen I","NahkampfSF");
		SfDictonary.put("Parierwaffen II","NahkampfSF");
		SfDictonary.put("Reaktivierungsgespür (Arkanoglyphen)","");
		SfDictonary.put("Reaktivierungsgespür (Runenkunde)","");
		SfDictonary.put("Regeneration I","MagieSF");
		SfDictonary.put("Regeneration II","MagieSF");
		SfDictonary.put("Reiterkampf","KampfSF");
		SfDictonary.put("Reiterkampf (Streitwagen)","KampfSF");
		SfDictonary.put("Repräsentation: Achaz","MagieSF");
		SfDictonary.put("Repräsentation: Borbaradianer","MagieSF");
		SfDictonary.put("Repräsentation: Druide","MagieSF");
		SfDictonary.put("Repräsentation: Elf","MagieSF");
		SfDictonary.put("Repräsentation: Geode","MagieSF");
		SfDictonary.put("Repräsentation: Hexe","MagieSF");
		SfDictonary.put("Repräsentation: Magier","MagieSF");
		SfDictonary.put("Repräsentation: Scharlatan","MagieSF");
		SfDictonary.put("Repräsentation: Schelm","MagieSF");
		SfDictonary.put("Ritualkenntnis: Achaz-Schamane","MagieSF");
		SfDictonary.put("Ritualkenntnis: Alchimist","MagieSF");
		SfDictonary.put("Ritualkenntnis: Derwisch","MagieSF");
		SfDictonary.put("Ritualkenntnis: Druide","MagieSF");
		SfDictonary.put("Ritualkenntnis: Durro-Dûn","MagieSF");
		SfDictonary.put("Ritualkenntnis: Ferkina-Schamane","MagieSF");
		SfDictonary.put("Ritualkenntnis: Geode","MagieSF");
		SfDictonary.put("Ritualkenntnis: Gildenmagie","MagieSF");
		SfDictonary.put("Ritualkenntnis: Gjalsker-Schamane","MagieSF");
		SfDictonary.put("Ritualkenntnis: Goblin-Schamanin","MagieSF");
		SfDictonary.put("Ritualkenntnis: Hexe","MagieSF");
		SfDictonary.put("Ritualkenntnis: Kristallomantie","MagieSF");
		SfDictonary.put("Ritualkenntnis: Nivesen-Schamane","MagieSF");
		SfDictonary.put("Ritualkenntnis: Ork-Schamane","MagieSF");
		SfDictonary.put("Ritualkenntnis: Runenzauberei","MagieSF");
		SfDictonary.put("Ritualkenntnis: Scharlatan","MagieSF");
		SfDictonary.put("Ritualkenntnis: Seher","MagieSF");
		SfDictonary.put("Ritualkenntnis: Trollzacker-Schamane","MagieSF");
		SfDictonary.put("Ritualkenntnis: Waldmenschen-Schamane","MagieSF");
		SfDictonary.put("Ritualkenntnis: Waldmenschen-Schamane (Tocamuyac)","MagieSF");
		SfDictonary.put("Ritualkenntnis: Waldmenschen-Schamane (Utulus)","MagieSF");
		SfDictonary.put("Ritualkenntnis: Zaubertänzer","MagieSF");
		SfDictonary.put("Ritualkenntnis: Zaubertänzer (Hazaqi)","MagieSF");
		SfDictonary.put("Ritualkenntnis: Zaubertänzer (Majuna)","MagieSF");
		SfDictonary.put("Ritualkenntnis: Zaubertänzer (novadische Sharisad)","MagieSF");
		SfDictonary.put("Ritualkenntnis: Zaubertänzer (tulamidische Sharisad)","MagieSF");
		SfDictonary.put("Ritualkenntnis: Zibilja","MagieSF");
		SfDictonary.put("Rosstäuscher","AllgemeineSF");
		SfDictonary.put("Runenkunde","MagieSF");
		SfDictonary.put("Rüstungsgewöhnung I (Amazonenrüstung (kompl))","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Anaurak)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Brigantina)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Bronzeharnisch)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Brustplatte, Leder)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Brustplatte, Stahl)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Brustschalen)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Dicke Kleidung)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Eisenmantel)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Fünflagenharnisch)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Gambeson)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Garether Platte)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Gestechrüstung)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Gladiatorenschulter)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Hartholzharnisch)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Horasischer Reiterharnisch)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Iryanrüstung)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Kettenhemd, Halbarm)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Kettenhemd, Lang)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Kettenmantel)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Kettenweste)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Krötenhaut)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Kürass)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Kusliker Lamellar)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Lederharnisch)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Lederweste, dick)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Leichte Platte)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Mammutonpanzer)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Mattenrücken)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Pelzweste)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Pelzweste, dick)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Ringelpanzer)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Ringmantel (Brabakmantel))","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Schuppenpanzer)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Schuppenpanzer, Lang)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Spiegelpanzer)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Tuchrüstung)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung I (Unterzeug mit Kettenteilen)","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung II","KampfSF");
		SfDictonary.put("Rüstungsgewöhnung III","KampfSF");
		SfDictonary.put("Scharfschütze (Armbrust)","FernkampfSF");
		SfDictonary.put("Scharfschütze (Belagerungswaffen)","FernkampfSF");
		SfDictonary.put("Scharfschütze (Blasrohr)","FernkampfSF");
		SfDictonary.put("Scharfschütze (Bogen)","FernkampfSF");
		SfDictonary.put("Scharfschütze (Diskus)","FernkampfSF");
		SfDictonary.put("Scharfschütze (Schleuder)","FernkampfSF");
		SfDictonary.put("Scharfschütze (Wurfbeile)","FernkampfSF");
		SfDictonary.put("Scharfschütze (Wurfmesser)","FernkampfSF");
		SfDictonary.put("Scharfschütze (Wurfspeere)","FernkampfSF");
		SfDictonary.put("Schildkampf I","NahkampfSF");
		SfDictonary.put("Schildkampf II","NahkampfSF");
		SfDictonary.put("Schildspalter","NahkampfSF");
		SfDictonary.put("Schmetterschlag","Hand2HandSF");
		SfDictonary.put("Schmutzige Tricks","Hand2HandSF");
		SfDictonary.put("Schnellladen (Armbrust)","FernkampfSF");
		SfDictonary.put("Schnellladen (Bogen)","FernkampfSF");
		SfDictonary.put("Schnellziehen","KampfSF");
		SfDictonary.put("Schwinger","Hand2HandSF");
		SfDictonary.put("Schwitzkasten","Hand2HandSF");
		SfDictonary.put("Semipermanenz I","MagieSF");
		SfDictonary.put("Semipermanenz II","MagieSF");
		SfDictonary.put("Signaturkenntnis","MagieSF");
		SfDictonary.put("Simultanzaubern","MagieSF");
		SfDictonary.put("Spätweihe Alveranische Gottheit","KlerikaleSF");
		SfDictonary.put("Spätweihe Namenloser","KlerikaleSF");
		SfDictonary.put("Spätweihe Nichtalveranische Gottheit","KlerikaleSF");
		SfDictonary.put("Spießgespann","NahkampfSF");
		SfDictonary.put("Spontanzeichen (Arkanoglyphen)","");
		SfDictonary.put("Spontanzeichen (Runenkunde)","");
		SfDictonary.put("Sprung","Hand2HandSF");
		SfDictonary.put("Sprungtritt","Hand2HandSF");
		SfDictonary.put("Standfest","AllgemeineSF");
		SfDictonary.put("Stapeleffekt","MagieSF");
		SfDictonary.put("Steppenkundig","AllgemeineSF");
		SfDictonary.put("Sturmangriff","NahkampfSF");
		SfDictonary.put("Sumpfkundig","AllgemeineSF");
		SfDictonary.put("Talentspezialisierung Gassenwissen (Ortseinschätzung)","AllgemeineSF");
		SfDictonary.put("Talentspezialisierung Überreden (Betteln)","AllgemeineSF");
		SfDictonary.put("Tanz der Mada","MagieSF");
		SfDictonary.put("Tod von links","NahkampfSF");
		SfDictonary.put("Todesstoß","NahkampfSF");
		SfDictonary.put("Traumgänger","MagieSF");
		SfDictonary.put("Tritt","Hand2HandSF");
		SfDictonary.put("Turnierreiterei","KampfSF");
		SfDictonary.put("Umreißen","Hand2HandSF");
		SfDictonary.put("Unterwasserkampf","KampfSF");
		SfDictonary.put("Verbotene Pforten","MagieSF");
		SfDictonary.put("Versteckte Klinge","NahkampfSF");
		SfDictonary.put("Vertrautenbindung","MagieSF");
		SfDictonary.put("Vielfache Ladungen","MagieSF");
		SfDictonary.put("Waffe zerbrechen","NahkampfSF");
		SfDictonary.put("Waffenloser Kampfstil: Bornländisch","Hand2HandStyles");
		SfDictonary.put("Waffenloser Kampfstil: Gladiatorenstil","Hand2HandStyles");
		SfDictonary.put("Waffenloser Kampfstil: Hammerfaust","Hand2HandStyles");
		SfDictonary.put("Waffenloser Kampfstil: Hruruzat","Hand2HandStyles");
		SfDictonary.put("Waffenloser Kampfstil: Mercenario","Hand2HandStyles");
		SfDictonary.put("Waffenloser Kampfstil: Unauer Schule","Hand2HandStyles");
		SfDictonary.put("Waldkundig","AllgemeineSF");
		SfDictonary.put("Windmühle","NahkampfSF");
		SfDictonary.put("Wuchtschlag","NahkampfSF");
		SfDictonary.put("Wurf","Hand2HandSF");
		SfDictonary.put("Würgegriff","Hand2HandSF");
		SfDictonary.put("Wüstenkundig","AllgemeineSF");
		SfDictonary.put("Zauber bereithalten","MagieSF");
		SfDictonary.put("Zauber unterbrechen","MagieSF");
		SfDictonary.put("Zauber vereinigen","MagieSF");
		SfDictonary.put("Zauberkontrolle","MagieSF");
		SfDictonary.put("Zauberroutine","MagieSF");
		SfDictonary.put("Zauberzeichen","MagieSF");



	}



	private String SfZuSfType(String name){
		gessamtSfs += name +"\n";

		if(SfDictonary.containsKey(name))
			return SfDictonary.get(name);
		else
			return "AllgemeineSF";
	}

	private int SfTypeZuIndex(String type){
		/*  @AllgemeineSF 
		@KampfSF
		@NahkampfSF
		@FernkampfSF
		@Hand2HandStyles 
		@Hand2HandSF    
		@MagieSF 		
		@KlerikaleSF	 */
		if(type.equals("AllgemeineSF"))
			return 0;
		if(type.equals("KampfSF"))
			return 1;
		if(type.equals("NahkampfSF"))
			return 2;
		if(type.equals("FernkampfSF"))
			return 3;
		if(type.equals("Hand2HandStyles"))
			return 4;
		if(type.equals("Hand2HandSF"))
			return 5;
		if(type.equals("MagieSF"))
			return 6;
		if(type.equals("KlerikaleSF"))
			return 7;

		return 0;
	}

	private String soZeugs() {
		String zeugs = "";
		zeugs += "<id>\n";
		zeugs += "<baGUID>" +"CgAADK1LundsAAAAAgAADA==" + "</baGUID>\n";
		zeugs += "</id>\n";
		zeugs += "<beingImpersonated>false</beingImpersonated>\n";
		zeugs += "<exposedAreaGUID>\n";
		zeugs += "<baGUID>" + "CgAADK1LundsAAAAAgAADA==" + "</baGUID>\n";
		zeugs += "</exposedAreaGUID>\n";
		zeugs += "<imageAssetMap>\n";
		zeugs += "<entry>\n";
		zeugs += "<null/>\n";
		zeugs += "<net.rptools.lib.MD5Key>\n";
		zeugs += "<id>f427aa49b28500217ac46ba37c2998a4</id>\n";
		zeugs += "</net.rptools.lib.MD5Key>\n";
		zeugs += "</entry>\n";
		zeugs += "</imageAssetMap>\n";
		zeugs += "<x>629</x>\n";
		zeugs += "<y>200</y>\n";
		zeugs += "<z>16</z>\n";
		zeugs += "<anchorX>0</anchorX>\n";
		zeugs += "<anchorY>0</anchorY>\n";
		zeugs += "<sizeScale>1.0</sizeScale>\n";
		zeugs += "<lastX>0</lastX>\n";
		zeugs += "<lastY>0</lastY>\n";
		zeugs += "<snapToScale>true</snapToScale>\n";
		zeugs += "<width>50</width>\n";
		zeugs += "<height>50</height>\n";
		zeugs += "<scaleX>1.0</scaleX>\n";
		zeugs += "<scaleY>1.0</scaleY>\n";
		zeugs += "<sizeMap>\n";
		zeugs += "<entry>\n";
		zeugs += "<java-class>net.rptools.maptool.model.SquareGrid</java-class>\n";
		zeugs += "<net.rptools.maptool.model.GUID>\n";
		zeugs += "<baGUID>CgAADK1LundsAAAAAgAADA==</baGUID>\n";
		zeugs += "</net.rptools.maptool.model.GUID>\n";
		zeugs += "</entry>\n";
		zeugs += "</sizeMap>\n";
		zeugs += "<snapToGrid>false</snapToGrid>\n";
		zeugs += "<isVisible>true</isVisible>\n";
		zeugs += "<visibleOnlyToOwner>false</visibleOnlyToOwner>\n";
		zeugs += ContentNick();
		zeugs += "<ownerType>0</ownerType>\n";
		zeugs += "<tokenShape>CIRCLE</tokenShape>\n";
		zeugs += "<tokenType>NPC</tokenType>\n";
		zeugs += "<layer>TOKEN</layer>\n";
		zeugs += "<propertyType>Basic</propertyType>\n";
		zeugs += "<isFlippedX>false</isFlippedX>\n";
		zeugs += "<isFlippedY>false</isFlippedY>\n";
		zeugs += "<hasSight>false</hasSight>\n";
		zeugs += "<state/>\n";

		return zeugs;
	}

	private String ContentBasisWerte() {
		String result = "";

		String[] key =    {"LeP",			"MaxLeP", 	      "AsP",	   "MR",  	     "MaxAsp",      "MaxKaP",      "KaP",     "AuP",        "MaxAuP",    "INI",       "AtBasis",       "PaBasis",               "FeBasis",      "Wunden", "RS", "BE", "RSKopf", "RSBrust", "RSBauch", "RSBeinLinks", "RSBeinRechts", "RSRuecken", "RSArmLinks", "RSArmRechts"};
		int[] value = {   derChar.Lep ,     derChar.Lep ,    derChar.Asp , derChar.Mr,    derChar.Asp,    derChar.Ka,   derChar.Ka, derChar.Aup, derChar.Aup,  derChar.ini, derChar.atBasis, derChar.paBasis,  derChar.fkBasis, 0,      derChar.RsGesamt, derChar.Be, derChar.RsKopf, derChar.RsBrust, derChar.RsBauch, derChar.RsBeinLinks, derChar.RsBeinRechts, derChar.RsRuecken, derChar.RsArmLinks, derChar.RsArmRechts}; 

		for(int i = 0; i < key.length; i++){
			result += "<entry>\n";
			result += "<string>" + key[i].toLowerCase() + "</string>\n";
			result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			result += "<key>" + key[i] +"</key>\n";

			if(key.equals("AsP") || key.equals("MaxAsp") ||  key.equals("MaxKaP") || key.equals("KaP")){ //muss leer sein, wenn der Char keine Asp hat
				if(value[i] > 0 ){
					result += "<value class=\"string\">\n";
					result += (value[i] + "");
					result += "</value>\n";
				}
			}else{
				result += "<value class=\"string\">\n";
				result += (value[i] + "");
				result += "</value>\n";
			}
			result += "<outer-class reference=\"../../../..\"/>\n";
			result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			result += "</entry>\n";

		}


		return result;
	}
	private String ContentTalente() {

		/*
		 * [
		 *   {"Talentwert":7,"Anzeigen":"","Spezialisierungen":"","Probe":"{"Eigenschaft2":"GE","Eigenschaft1":"MU","Eigenschaft3":"KK"}","Talent":"Akrobatik"},
			 {"Talentwert":0,"Anzeigen":"checked","Spezialisierungen":"Laufen, Springen","Probe":"{"Eigenschaft2":"KO","Eigenschaft1":"GE","Eigenschaft3":"KK"}","Talent":"Athletik"}
			]

			Das Feld "Anzeigen" muss vom Exporter mit dem String "checked" gefüllt werden!
			<entry>
			        <string>gesellschaft</string>
        			<net.rptools.CaseInsensitiveHashMap_-KeyValue>
          			<key>Gesellschaft</key>
          			<value class="string">[..]</value>
          			<outer-class reference="../../../.."/>
        			</net.rptools.CaseInsensitiveHashMap_-KeyValue>
      		</entry>
		 */


		String result = "";
		//die Talent-Klassen die so direkt gehen
		String klassen[] = {"Kampftalente", "Gesellschaft", "Wissen", "Handwerk", "Gaben" /*, "Liturgien"*/};

		for(int i = 0; i < klassen.length; i++){
			result += "<entry>\n";
			result += "<string>\n";
			result += klassen[i].substring(0,1).toLowerCase() + klassen[i].substring(1);
			result += "</string>\n";
			result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			result += "<key>" + klassen[i]+ "</key>\n";
			result += "<value class=\"string\">[";
			result += ContentTalenteToJson(derChar.talenteNachKategorien[derChar.talentKategorieToInt(klassen[i])]);
			result += "]</value>\n";
			result += "<outer-class reference=\"../../../..\"/>\n";
			result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			result += "</entry>\n";
		}


		//"Natur" + Meta
		result += "<entry>\n";
		result += "<string>Natur</string>\n";
		result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "<key>Natur</key>\n";
		result += "<value class=\"string\">[";
		result += ContentTalenteToJson(derChar.talenteNachKategorien[derChar.talentKategorieToInt("Natur")]) + "," +  ContentTalenteToJson(derChar.talenteNachKategorien[derChar.talentKategorieToInt("Meta")]);
		result += "]</value>\n";
		result += "<outer-class reference=\"../../../..\"/>\n";
		result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "</entry>\n";
		//Körper
		result += "<entry>\n";
		result += "<string>\n";
		result += "koerper";
		result += "</string>\n";
		result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "<key>" + "Koerper" + "</key>\n";
		result += "<value class=\"string\">[";
		result += ContentTalenteToJson(derChar.talenteNachKategorien[derChar.talentKategorieToInt("Körperlich")]);
		result += "]</value>\n";
		result += "<outer-class reference=\"../../../..\"/>\n";
		result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "</entry>\n";

		//SprachenSchriften
		result += "<entry>\n";
		result += "<string>\n";
		result += "sprachenschriften";
		result += "</string>\n";
		result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "<key>" + "SprachenSchriften" + "</key>\n";
		result += "<value class=\"string\">[";
		result += ContentTalenteToJson(derChar.talenteNachKategorien[derChar.talentKategorieToInt("Sprachen")]) + ", " + ContentTalenteToJson(derChar.talenteNachKategorien[derChar.talentKategorieToInt("Schriften")]);
		result += "]</value>\n";
		result += "<outer-class reference=\"../../../..\"/>\n";
		result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "</entry>\n";


		//Zauber
		result += "<entry>\n";
		result += "<string>\n";
		result += "zauber";
		result += "</string>\n";
		result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "<key>" + "Zauber" + "</key>\n";
		result += "<value class=\"string\">[";
		result += ContentTalenteToJson(derChar.talenteNachKategorien[derChar.talentKategorieToInt("Zauber")]);
		result += "]</value>\n";
		result += "<outer-class reference=\"../../../..\"/>\n";
		result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "</entry>\n";


		/*
		//Hauszauber
		result += "<entry>\n";
		result += "<string>\n";
		result += "hauszauber";
		result += "</string>\n";
		result += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "<key>" + "Hauszauber" + "</key>\n";
		result += "<value class=\"string\">[";
		result += ContentTalenteToJson(derChar.talenteNachKategorien[derChar.talentKategorieToInt("Zauber")], true);
		result += "]</value>\n";
		result += "<outer-class reference=\"../../../..\"/>\n";
		result += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		result += "</entry>\n";
		 */
		return result;
	}


	private String ContentTalenteToJson(LinkedList<talent> a, boolean sollHauszauber){
		String result = "";

		//{"Talentwert":9,"Anzeigen":"checked","Spezialisierungen":"","Probe":"{\"Eigenschaft2\":\"CH\",\"Eigenschaft1\":\"IN\",\"Eigenschaft3\":\"CH\"}","Talent":"Betören"}
		int anz = 0;
		for(int i = 0; i < a.size(); i++){
			if(a.get(i).hauszauber == sollHauszauber){
				if(anz > 0)
					result+= ",";
				result += "{\"Talentwert\":";
				result +=a.get(i).tap;
				result += ",\"Anzeigen\":\"checked\",\"Spezialisierungen\":\"\",\"Probe\":\"{\\\"Eigenschaft2\\\":\\\"";
				result += a.get(i).probe.substring(3, 5) + "\\";
				result += "\",\\\"Eigenschaft1\\\":\\\"";
				result += a.get(i).probe.substring(0,2) + "\\";
				result += "\",\\\"Eigenschaft3\\\":\\\"";
				result += a.get(i).probe.substring(6,8) + "\\";
				result += "\"}\",\"Talent\":\"";
				result += ausgabeErstellen.ReplaceUmlaute(a.get(i).bezeichnung) ;
				result += "\"}";
				anz++;
			}
		}

		return result;
	}

	private String ContentTalenteToJson(LinkedList<talent> a){
		String result = "";
		int startwert = (a.get(0).bezeichnung.contains("_")?1:0); //die Bereichsmakierungen für das Würfeltool müssen ja nicht mit Exportiert werden
		//{"Talentwert":0,"Anzeigen":"checked","Spezialisierungen":"Laufen, Springen","Probe":"{"Eigenschaft2":"KO","Eigenschaft1":"GE","Eigenschaft3":"KK"}","Talent":"Athletik"}

		for(int i = startwert; i < a.size(); i++){
			if(i > startwert )
				result+= ",";
			result += "{\"Talentwert\":";
			result +=a.get(i).tap;
			result += ",\"Anzeigen\":\"checked\",\"Spezialisierungen\":\"\",\"Probe\":\"{\\\"Eigenschaft2\\\":\\\"";
			result += a.get(i).probe.substring(3, 5) + "\\";
			result += "\",\\\"Eigenschaft1\\\":\\\"";
			result += a.get(i).probe.substring(0,2) + "\\";
			result += "\",\\\"Eigenschaft3\\\":\\\"";
			result += a.get(i).probe.substring(6,8) + "\\";
			result += "\"}\",\"Talent\":\"";
			result += ausgabeErstellen.ReplaceUmlaute(a.get(i).bezeichnung) ;
			result += "\"}";
		}

		return result;
	}

	private String ContentEigenschaften() {
		String Eigenschaften = "";
		/*
		 *  <entry>
        		<string>ge</string>
        		<net.rptools.CaseInsensitiveHashMap_-KeyValue>
          		<key>GE</key>
          		<value class="string">11</value>
          		<outer-class reference="../../../.."/>
        		</net.rptools.CaseInsensitiveHashMap_-KeyValue>
      		</entry>
		 */
		String[] abkürzungen = {"mu", "kl", "in", "ch", "ff", "ge", "ko", "kk"};

		for(int i = 0; i < 8; i++){
			Eigenschaften += "<entry>\n";
			Eigenschaften += "<string>" + abkürzungen[i] + "</string>\n" ;
			Eigenschaften += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			Eigenschaften += "<key>" + abkürzungen[i].toUpperCase() + "</key>\n";
			Eigenschaften += "<value class=\"string\">" + ((eigenschaft) derChar.eigenschaften.get(i)).wert + "</value>\n";	
			Eigenschaften += "<outer-class reference=\"../../../..\"/>\n";
			Eigenschaften += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			Eigenschaften += "</entry>\n";
		}

		return Eigenschaften;
	}

	private String ContentVorteile() {

		String Vorteile ="";
		String[] key = {"Vorteile", "Nachteile"};
		//LinkedList<String>[] a = {derChar.Vorteile, derChar.Nachteile};  Listen Arrys gehen nicht T.T
		LinkedList<LinkedList<String>>a = new LinkedList();
		a.add(derChar.Vorteile);
		a.add(derChar.Nachteile);
		for(int i = 0; i < 2; i++){
			Vorteile += "<entry>\n";
			Vorteile += "<string>" + key[i].substring(0, 1).toLowerCase() + key[i].substring(1) +"</string>\n";
			Vorteile += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			Vorteile += "<key>" + key[i] + "</key>\n";
			Vorteile += "<value class=\"string\">" + createVorteileString(a.get(i)) +"</value>\n";
			Vorteile += "<outer-class reference=\"../../../..\"/>\n";
			Vorteile += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			Vorteile += "</entry>\n";

			Vorteile += "<entry>\n";
			Vorteile += "<string>" + key[i].substring(0, 1).toLowerCase() + key[i].substring(1) + "_Anzeigespeicher"+"</string>\n";
			Vorteile += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			Vorteile += "<key>" + key[i] + "_Anzeigespeicher" + "</key>\n";
			Vorteile += "<value class=\"string\">" + createVorteileString(a.get(i)) +"</value>\n";
			Vorteile += "<outer-class reference=\"../../../..\"/>\n";
			Vorteile += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
			Vorteile += "</entry>\n";
		}


		//schlechte Eigenschaften
		Vorteile += "<entry>\n";
		Vorteile += "<string>schlechteeigenschaften</string>\n";
		Vorteile += "<net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		Vorteile += "<key>schlechteEigenschaften</key>\n";
		Vorteile += "<value class=\"string\">" + createSchlechteEigenschaftenString() + "</value>\n";
		Vorteile += "<outer-class reference=\"../../../..\"/>\n";
		Vorteile += "</net.rptools.CaseInsensitiveHashMap_-KeyValue>\n";
		Vorteile += "</entry>\n";






		return Vorteile;
	}

	private String createSchlechteEigenschaftenString(){
		//Neugier=7; Goldgier=5;
		String result = "";
		for(int i = 8; i < derChar.eigenschaften.size(); i++){
			//https://github.com/xCAAx/DSA-Helden-Software-Plugin/issues/8
			//result +=  ausgabeErstellen.ReplaceUmlaute(derChar.eigenschaften.get(i).name +"=" + derChar.eigenschaften.get(i).wert);
			result += ausgabeErstellen.ReplaceUmlauteOhneHtml(derChar.eigenschaften.get(i).name +"=" + derChar.eigenschaften.get(i).wert);
			result+= "; ";
		}

		return result;
	}

	private String createVorteileString(LinkedList<String> a){
		//Gl&amp;uuml;ck, Gutaussehend, Gl&amp;uuml;ck, Gutaussehend, Gl&amp;uuml;ck, Gutaussehend, Gl&amp;uuml;ck, Gutaussehend, Gl&amp;uuml;ck, Gutaussehend, Gl&amp;uuml;ck, Gutaussehend
		String Vorteile = "";

		for(int i = 0; i < a.size(); i++){
			if(!a.get(i).contains("spezialisierung")){
				Vorteile += ausgabeErstellen.ReplaceUmlaute(a.get(i));
				Vorteile += ", ";
			}
		}

		//natürlich könnte man nur da ein , machen wo es benötigt wird, aber ich bezweifel dass so viele Abfragen
		//tatsächlich schneller sein sollen, als den String neu zu bilden...
		try{
			Vorteile = Vorteile.substring(0, Vorteile.length()-2);
		}catch(Exception e){
			//wenn im String nichts drin ist...
		}
		return Vorteile;
	}

	private String ContentNick() {
		String nick = "";
		nick +=   "<name>" + ausgabeErstellen.ReplaceUmlaute(derChar.nick) + "</name>\n";
		return nick;
	}

}