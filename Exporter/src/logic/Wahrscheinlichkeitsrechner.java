package logic;

import de.CAA.utils.msgbox;

public class Wahrscheinlichkeitsrechner {

	static public WahrscheinlichkeitRueckgabe Wahrscheinlichkeit(int e1, int e2, int e3, int taw, int Mod){
		int trueTaw = taw;
		taw -= Mod; 

		if(e1 > 20) e1 = 20;
		if(e2 > 20) e2 = 20;
		if(e3 > 20) e3 = 20;
		
		int success, restTaP;
		float summe = 0.0F;
		if (taw < 0) return Wahrscheinlichkeit(e1+taw, e2+taw, e3+taw, 0, 0) ;

		success = 0;
		for (int w1=1;w1<= 20;w1++) {
			for (int w2=1;w2<= 20;w2++){
				for (int w3=1;w3<= 20;w3++){
					if (meisterhaft(w1,w2,w3)){
						success++;
						summe+= taw;
					}else{ 
						if (!patzer(w1,w2,w3)){
							// schauen, ob die Rest-TaP nicht unter 0 fallen
							restTaP = taw-Math.max(0, w1-e1)-Math.max(0, w2-e2)-Math.max(0, w3-e3);
							if(restTaP >= 0){
								if(restTaP == 0)
									restTaP = 1;
								summe+=Math.max(1, Math.min(restTaP, trueTaw));
								success++;
							}
						}
					}
				}
			}
		}

		float V = 1f/8000f*(success);
		V = Float.parseFloat("" + Math.round(V * 1000));
		V/=1000;

		summe/=success;


		summe = Float.parseFloat("" + Math.round(summe * 100))/100;

		return new WahrscheinlichkeitRueckgabe(V, summe);
	}

	private static boolean meisterhaft(int w1, int w2, int w3) {
		return (w1==1)&&(w2==1)||
				(w2==1)&&(w3==1)||
				(w1==1)&&(w3==1);
	}

	private static boolean patzer(int w1, int w2, int w3) {
		return (w1==20)&&(w2==20)||
				(w2==20)&&(w3==20)||
				(w1==20)&&(w3==20);
	}
}