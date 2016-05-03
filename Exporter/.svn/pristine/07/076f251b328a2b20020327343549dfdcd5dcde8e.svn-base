package logic;

import java.io.Serializable;

public class talent implements Serializable{

	public String bezeichnung = "";
	public int tap = 0;
	public int wert1 = 0;
	public int wert2 = 0;
	public int wert3 = 0;
	public int erleichterung = 0;
	boolean iszauber = false;
	boolean isElf = false;
	public boolean hauszauber = false;
	public String probe;
	String sMod = "Modifikationen: ";
	public String merkmale = "";
	public float wahrscheinlichkeit = 1.0f;


	talent(String name, String tap_, String wert1, String wert2, String wert3, String probe, boolean iszauber, boolean isElf, boolean haus, String merkmal) {
		merkmale = merkmal;
		bezeichnung = name;
		this.probe = probe;
		this.isElf = isElf;
		this.iszauber = iszauber;
		hauszauber = haus;
		try{
			
			//bei manchen zaubern wird ab 1 zeichen zufrüh eingelesen... bis ich den fehler gefunden habe, sollte dass
			//hier den fehler beheben...
			this.tap = Integer.valueOf(tap_);
			if(wert1.charAt(0) == '\"') wert1 = String.valueOf(wert1.charAt(1));
			this.wert1 = Integer.valueOf(wert1);
			if(wert2.charAt(0) == '\"') wert2 = String.valueOf(wert2.charAt(1));
			this.wert2 = Integer.valueOf(wert2);
			if(wert3.charAt(0) == '\"') wert3 = String.valueOf(wert3.charAt(1));
			this.wert3 = Integer.valueOf(wert3);
			
			
		}catch(Exception e){
			//e.printStackTrace();
		}
	}
	
	talent(String name, String tap_, String wert1, String wert2, String wert3, String probe, boolean iszauber, boolean isElf) {
		bezeichnung = name;
		this.probe = probe;
		this.isElf = isElf;
		this.iszauber = iszauber;
		try{
			
			//bei manchen zaubern wird ab 1 zeichen zufrüh eingelesen... bis ich den fehler gefunden habe, sollte dass
			//hier den fehler beheben...
			this.tap = Integer.valueOf(tap_);
			if(wert1.charAt(0) == '\"') wert1 = String.valueOf(wert1.charAt(1));
			this.wert1 = Integer.valueOf(wert1);
			if(wert2.charAt(0) == '\"') wert2 = String.valueOf(wert2.charAt(1));
			this.wert2 = Integer.valueOf(wert2);
			if(wert3.charAt(0) == '\"') wert3 = String.valueOf(wert3.charAt(1));
			this.wert3 = Integer.valueOf(wert3);
			
			
		}catch(Exception e){
			//e.printStackTrace();
		}
	}



	public String toString()
	{
		return bezeichnung + "  " + probe;
		
	}
}