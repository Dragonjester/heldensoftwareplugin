package logic;

public class Waffe {
	public String bezeichnung = "";
	public String AT = "0";
	public String PA = "0";
	public String dmg = "";
	public String TP = "";
	public String TPKK = "";
	public String DK = "";
	public String INI = "";
	public String BF = ""; 

	public Waffe(String nick, String at, String pa, String dmge, String dk, String ini, String bf){
		bezeichnung = nick;
		AT = at;
		PA = pa;
		DK = dk;
		INI = ini;
		BF = bf;
		
		for(int i = 0; i < dmge.length(); i++)
			if(dmge.charAt(i) == 'W' || dmge.charAt(i) == 'w')
				dmg+= "d6";
			else			
				dmg += (dmge.charAt(i) + "");

	}
	
	public Waffe(String nick, String at, String pa, String dmge, String tp, String tpkk, String dk, String ini, String bf){
		this(nick, at, pa, dmge, dk, ini, bf);
		TP = tp;
		TPKK = tpkk;
	}
}