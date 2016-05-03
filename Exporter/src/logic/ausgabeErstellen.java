package logic;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class ausgabeErstellen {

	private static boolean whisper = false;
	public static String RegString = "";

	public static void setWhisper(){
		whisper = !whisper;
	}


	public static String Talentprobe(talent a, int mod)
	{
		if(!a.bezeichnung.contains("_")){
			String ausgabe = "";
			ausgabe += "[h: Eigenschaft1 = " + a.wert1 + "]";
			ausgabe += "[h: Eigenschaft2 = " + a.wert2 + "]";
			ausgabe += "[h: Eigenschaft3 = " + a.wert3 + "]";
			ausgabe += "[h: Talentwert = " + a.tap + "]";
			ausgabe += "[h: Erschwernis = " + (a.erleichterung + mod) + "]";
			ausgabe += "<table><tr><td>" +ReplaceUmlaute(a.bezeichnung) +" (" + a.tap + ")" + "  (" + a.wert1 + " " + a.wert2 + " " + a.wert3 + ")" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			ausgabe += ReplaceUmlaute(a.sMod);
			if(!a.iszauber && chareinlesen.tollpatsch || a.iszauber && chareinlesen.wildeMagie){
				ausgabe += " Tollpatsch ";
			}
			ausgabe += " Erschwernis (" + mod +")</td></tr>";
			ausgabe += "[h: Wurf1 = 1d20][h: Wurf2 = 1d20][h: Wurf3 = 1d20]";
			ausgabe += "[h: Talentwertgrund = Talentwert]";
			ausgabe += "[h: Ergebnis = 0]";
			ausgabe += "[h: Max_Erschwernis1 = 0]";
			ausgabe += "[h: Max_Erschwernis2 = 0]";
			ausgabe += "[h: Max_Erschwernis3 = 0]";
			ausgabe += "[h,if(Erschwernis > Talentwert), CODE:{";
			ausgabe += "[h: Talentwert = Talentwert - Erschwernis]";
			ausgabe += "[h: Eigenschaft1 = Eigenschaft1 - Talentwert]";
			ausgabe += "[h: Eigenschaft1 = Eigenschaft2 - Talentwert]";
			ausgabe += "[h: Eigenschaft1 = Eigenschaft3 - Talentwert]};";
			ausgabe += "{[h: Talentwert = Talentwert - Erschwernis]}]";
			ausgabe += "[h: Einser = 0]";
			ausgabe += "[h, IF(Wurf1 == 1): Einser = Einser + 1]";
			ausgabe += "[h, IF(Wurf2 == 1): Einser = Einser + 1]";
			ausgabe += "[h, IF(Wurf3 == 1): Einser = Einser + 1]";
			ausgabe += "[h: Zwanziger = 0]";
			if(!a.iszauber && chareinlesen.tollpatsch || a.iszauber && chareinlesen.wildeMagie){
				ausgabe += "[h, IF(Wurf1 >= 19): Zwanziger = Zwanziger + 1]";
				ausgabe += "[h, IF(Wurf2 >= 19): Zwanziger = Zwanziger + 1]";
				ausgabe += "[h, IF(Wurf3 >= 19): Zwanziger = Zwanziger + 1]";
			}else{
				ausgabe += "[h, IF(Wurf1 >= 20): Zwanziger = Zwanziger + 1]";
				ausgabe += "[h, IF(Wurf2 >= 20): Zwanziger = Zwanziger + 1]";
				ausgabe += "[h, IF(Wurf3 >= 20): Zwanziger = Zwanziger + 1]";
			}
			ausgabe += "[h, IF(Einser == 3): Ergebnis = 1]";
			ausgabe += "[h, IF(Einser == 2): Ergebnis = 2]";
			ausgabe += "[h, IF(Zwanziger == 2): Ergebnis = 5]";
			ausgabe += "[h, IF(Zwanziger == 3): Ergebnis = 6]";

			ausgabe += "[h, IF(Wurf1 > Eigenschaft1): Talentwert = Talentwert - (Wurf1 - Eigenschaft1); Max_Erschwernis1 = Eigenschaft1 - Wurf1]";
			ausgabe += "[h, IF(Wurf2 > Eigenschaft2): Talentwert = Talentwert - (Wurf2 - Eigenschaft2); Max_Erschwernis2 = Eigenschaft2 - Wurf2]";
			ausgabe += "[h, IF(Wurf3 > Eigenschaft3): Talentwert = Talentwert - (Wurf3 - Eigenschaft3); Max_Erschwernis3 = Eigenschaft3 - Wurf3]";

			ausgabe += "[h, IF(Talentwert >= 0 && Ergebnis == 0): Ergebnis = 3]";
			ausgabe += " [h, IF(Talentwert < 0 && Ergebnis == 0): Ergebnis = 4]";
			ausgabe += "[h: MaximalErschwernis = Talentwert + min(Max_Erschwernis1, Max_Erschwernis2, Max_Erschwernis3)]";
			ausgabe += "[h, IF(Ergebnis == 3 && Talentwert > Talentwertgrund): Talentwert = Talentwertgrund]";
			ausgabe += "<tr><td>Gewürfelt: ";
			ausgabe += "[r: Wurf1+\" \"+Wurf2+\" \"+Wurf3+\"</td></tr>\"]";
			ausgabe += "<tr><td>";
			ausgabe += "[r, switch(Ergebnis):";
			ausgabe += "case 0: \" Fehlerhafte Probe\";";
			ausgabe += "case 1: \" Dreifach 1! Streich den Tag Rot im Kalender an!\";";
			ausgabe += "case 2: \" Doppel 1! Definitiv gelungen.\";";
			ausgabe += "case 3: \" Gelungene Probe. Uebrige Talentpunkte: \"+Talentwert+\" - Maximale Erschwernis: \"+MaximalErschwernis;";
			ausgabe += " case 4: \" Probe um \"+abs(Talentwert)+\" Punkte misslungen.\";";
			ausgabe += "case 5: \" Doppel 20 - Patzer!\";";
			ausgabe += " case 6: \" Dreifach 20 - ARGH!\";";
			ausgabe += " default: \" Irgendwas stimmt überhaupt nicht hier!\"]</td>";

			if(whispering()){
				ausgabe = ausgabe.replace("[r", "[r,g");
				ausgabe += "[r: \"<td>verdeckter Wurf</td>\"]";
			}
			return ausgabe;
		}

		return "";
	}

	public static String hintergrundfarbe() {

		int farbAuswahl = 0;
		farbAuswahl = ((int) (Math.random()*123456789)) % 7;

		switch(farbAuswahl){
		case 0: return "00FF00"; 
		case 1: return "00FFFF"; 
		case 2: return "FFFF00";
		case 3: return "808080";
		case 4: return "DFDFDF";
		case 5: return "33FFFF";
		case 6: return "99FFCC";
		default: return "FFFFFF";
		}
	}

	public static String trefferZone(){

		String  result = "";
		result += "<td>";
		result+= "[h: wurfTz = 1d20]";
		result+= "[r, switch(wurfTz):";
		result += "case 1: \" linkes Bein; Wunde: AT, PA, GE, Ini -2; GS -1\";";
		result += "case 2: \" rechtes Bein; Wunde: AT, PA, GE, Ini -2; GS -1\";";
		result += "case 3: \" linkes Bein; Wunde: AT, PA, GE, Ini -2; GS -1\";";
		result += "case 4: \" rechtes Bein; Wunde: AT, PA, GE, Ini -2; GS -1\";";
		result += "case 5: \" linkes Bein; Wunde: AT, PA, GE, Ini -2; GS -1\";";
		result += "case 6: \" rechtes Bein; Wunde: AT, PA, GE, Ini -2; GS -1\";";
		result += "case 7: \" Bauch; Wunde: AT, PA, KK, KO, GS, Ini -1; +1d6 SP\";";
		result += "case 8: \" Bauch; Wunde: AT, PA, KK, KO, GS, Ini -1; +1d6 SP\";";
		result += "case 9: \" Schildarm; Wunde: AT, PA, KK, FF-2 mit diesem Arm\";";
		result += "case 10: \" Schwertarm; Wunde: AT, PA, KK, FF-2 mit diesem Arm\";";
		result += "case 11: \" Schildarm; Wunde: AT, PA, KK, FF-2 mit diesem Arm\";";
		result += "case 12: \" Schwertarm; Wunde: AT, PA, KK, FF-2 mit diesem Arm\";";
		result += "case 13: \" Schildarm; Wunde: AT, PA, KK, FF-2 mit diesem Arm\";";
		result += "case 14: \" Schwertarm; Wunde: AT, PA, KK, FF-2 mit diesem Arm\";";
		result += "case 15: \" Brust; Wunde: AT, PA, KO, KK -1; +1d6 SP\";";
		result += "case 16: \" Brust; Wunde: AT, PA, KO, KK -1; +1d6 SP\";";
		result += "case 17: \" Brust; Wunde: AT, PA, KO, KK -1; +1d6 SP\";";
		result += "case 18: \" Brust; Wunde: AT, PA, KO, KK -1; +1d6 SP\";";
		result += "case 19: \" Kopf; Wunde: MU, KL, IN, INI -2; INI -2d6; +2d6 Sp\";";
		result += "case 20: \" Kopf; Wunde: MU, KL, IN, INI -2; INI -2d6; +2d6 Sp\";";
		result += "default: \" Irgendwas stimmt überhaupt nicht hier!\"]";

		if(whispering()){
			result = result.replace("[r", "[r,g");
		}
		return result;
	}
	public static String Eigenschaftsprobe(String val, String Name, int mod)
	{
		String result = "";
		result += "<table><tr><td>[h: wert = " + val + "]";
		result+="[h: wurf = 1d20][h: dif = wert - wurf - " + mod +"][r: \"Probe auf " + Name;
		result+= "(\" + wert + \")  Erschwernis: " + mod + " gew&uuml;rfelt wurde: \" + wurf + \"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\" + dif + \" Punkte &uuml;ber\"]";

		if(whispering())
			result = result.replace("[r", "[r,g");
		return result;
	}
	public static String Eigenschaftsprobe(eigenschaft eigenschaft, int i) {

		return  Eigenschaftsprobe(eigenschaft.wert, ReplaceUmlaute(eigenschaft.name), i);
	}


	public static boolean whispering(){
		return whisper;
	}

	public static String Regeneration(){
		String result = "";

		result += RegString;

		if(whispering())
			result = result.replace("[r", "[r,g");

		return result;
	}
	public static String ReplaceUmlaute(String bezeichnung) {
		String result = "";

		for (int i = 0; i < bezeichnung.length(); i++) {
			if (bezeichnung.charAt(i) == 'ü') {
				result += "&#252;";
			}else if (bezeichnung.charAt(i) == 'ö') {
				result+= "&#246;";
			}else if (bezeichnung.charAt(i) == 'ä') {
				result+= "&#228;";
			}else if (bezeichnung.charAt(i) == 'Ö') {
				result+= "&#214;";
			}else if (bezeichnung.charAt(i) == 'Ä') {
				result+= "&#196;";
			}else if (bezeichnung.charAt(i) == 'Ü') {
				result+= "&#220;";
			}else if(bezeichnung.charAt(i) == ']'){
				//result += "&#93;";
				result += "[r: \"]\"]";
				//result += "&#x5D;";
			}else if(bezeichnung.charAt(i)== '['){
				//result += "&#91;";
				//result += "&#x5B;";
				result += "[r: \"[\"]";
			}else if(bezeichnung.charAt(i) == 'ß'){
				result += "&#223;";
			}else{
				result+= String.valueOf(bezeichnung.charAt(i));
			}
		}


		return result;
	}

	public static Transferable anzD6(int i) {
		String output ="";
		for(int x = 0; x < i; x++)
			output+= "[d6]";

		if(whispering())
			output = output.replace("[r", "[r,g");
		return new StringSelection(output);
	}

	public static Transferable anzD20(int i) {
		String output = "";
		for(int x = 0; x < i; x++)
			output+= "[d20]";

		if(whispering())
			output = output.replace("[r", "[r,g");
		return new StringSelection(output);
	}

	public static String angriff(int mod) {
		// TODO Auto-generated method stub
		String ausgabe = "";
		ausgabe += "<table><tr><td>";

		ausgabe +="[h: atWert = " + chareinlesen.at + "]";
		ausgabe += "[h: atWurf = 1d20]";
		ausgabe += "[h: ctrlWurf = 1d20]";
		ausgabe += "[h: dif = atWert - atWurf - 0]";
		ausgabe += "[h: dmg = " + chareinlesen.atWert +"]";
		ausgabe += "[r: \"AtWert: \" + atwert + \" \"]";
		ausgabe += "[r: \"Gew&uuml;rfelt: \" + atWurf + \"&nbsp;&nbsp;<br>\"]";
		ausgabe += "[r: if(atWurf==20 && atWert<ctrlWurf, \"Kontrollwurf: \" + ctrlWurf + \" ->Patzerwurf: \" + 2d6, \"\")]";
		ausgabe += "[r: if(atWurf==20 && atWert>=ctrlWurf, \"Kontrollwurf: \" + ctrlWurf + \" ->nur daneben\", \"\")]";
		ausgabe += "[r: if(atWurf==1 && atWert>=ctrlWurf, \"Kontrollwurf: \" + ctrlWurf + \" -> Kritischer Treffer: \" + dmg * 2 + \" Schaden\", \"\")]";
		ausgabe += "[r: if(atWurf==1 && atWert<ctrlWurf, \"Kontrollwurf: \" + ctrlWurf + \" normaler Treffer: \" + dmg + \" Schaden\", \"\")]";
		ausgabe += "[r: if(atWurf > 1 && atWurf < 20 && dif >= 0 , \"Treffer mit \" + dmg + \" Schaden\", \"\")]";
		ausgabe += "[r: if(atWurf > 1 && atWurf < 20 && dif < 0, \"nicht getroffen\", \"\")]";
		ausgabe += "</td>";

		ausgabe += trefferZone();

		if(whispering()){
			ausgabe = ausgabe.replace("[r", "[r,g");
		}
		return ausgabe;
	}

	public static String parade(int mod){
		String ausgabe = "";
		ausgabe += "<table><tr><td>";

		ausgabe += "[h: paWert = " + chareinlesen.pa + "]";
		ausgabe += "[h: paWurf = 1d20]";
		ausgabe += "[h: ctrlWurf = 1d20]";
		ausgabe += "[h: dif = paWert - paWurf - " + mod + "]";
		ausgabe += "[r: \"PaWert : \" + paWert + \" Gew&uuml;rfelt: \" + paWurf+ \"&nbsp;&nbsp;<br>\"]";
		ausgabe += "[r: if(paWurf==20 && paWert <ctrlWurf, \"Kontrollwurf: \" + ctrlWurf + \" ->Patzerwurf: \" + 2d6, \"\")]";
		ausgabe += "[r: if(paWurf==20 && paWert >=ctrlWurf, \"Kontrollwurf: \" + ctrlWurf + \" ->nur nicht pariert\", \"\")]";
		ausgabe += "[r: if(paWurf==1 && paWert >=ctrlWurf, \"Kontrollwurf: \" + ctrlWurf + \" ->  gl&uuml;cklich pariert\", \"\")]";
		ausgabe += "[r: if(paWurf==1 && paWert <ctrlWurf, \"Kontrollwurf: \" + ctrlWurf + \" normal pariert\", \"\")]";
		ausgabe += "[r: if(paWurf > 1 && paWurf < 20 && dif >= 0 , \"pariert\", \"\")]";
		ausgabe += "[r: if(paWurf > 1 && paWurf < 20 && dif < 0, \"nicht pariert\", \"\")]";

		if(whispering())
			ausgabe = ausgabe.replace("[r", "[r,g");
		return ausgabe;
	}


	public static String ReplaceUmlauteOhneHtml(String bezeichnung) {
		String result = "";

		for (int i = 0; i < bezeichnung.length(); i++) {
			if (bezeichnung.charAt(i) == 'ü') {
				result += "ue";
			}else if (bezeichnung.charAt(i) == 'ö') {
				result+= "oe";
			}else if (bezeichnung.charAt(i) == 'ä') {
				result+= "ae";
			}else if (bezeichnung.charAt(i) == 'Ö') {
				result+= "Oe";
			}else if (bezeichnung.charAt(i) == 'Ä') {
				result+= "Ae";
			}else if (bezeichnung.charAt(i) == 'Ü') {
				result+= "Ue";
			}else if(bezeichnung.charAt(i) == ']'){
				//result += "&#93;";
				result += "[r: \"]\"]";
				//result += "&#x5D;";
			}else if(bezeichnung.charAt(i)== '['){
				//result += "&#91;";
				//result += "&#x5B;";
				result += "[r: \"[\"]";
			}else{
				result+= String.valueOf(bezeichnung.charAt(i));
			}
		}


		return result;
	}
}