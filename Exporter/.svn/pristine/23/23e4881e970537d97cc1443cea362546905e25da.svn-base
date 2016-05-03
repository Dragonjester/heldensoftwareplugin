package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import logic.chareinlesen;
import logic.talent;

import org.w3c.dom.Document;

import de.CAA.utils.msgbox;


public class StatistikAuswahlFrame extends JFrame{
	chareinlesen derChar;
	final LinkedList<JCheckBox>[] boxen;

	public StatistikAuswahlFrame(Document ret){
		super("CAAs Statistik-Tool");

		derChar = new chareinlesen();
		derChar.Dateiauswahl(ret);
		JPanel auswahlFlaeche = new JPanel();


		//oben drüber ist ja immer das ____KategorieName___ - das soll weg, das brauchen wir hier überhaupt nicht...
		//die if Abfrage, damit das nicht bei kaputt geht, wenn sich das irgendwann mal ändern sollte
		for(int i = 0; i < derChar.talenteNachKategorien.length; i++)
			if(derChar.talenteNachKategorien[i].get(0).bezeichnung.contains("_"))
				derChar.talenteNachKategorien[i].remove(0);

		int anz = 0;
		for(int i = 1; i < derChar.talenteNachKategorien.length; i++)
			anz += derChar.talenteNachKategorien[i].size();


		auswahlFlaeche.setLayout(new GridLayout(anz, 1));
		boxen = new LinkedList[12];

		//initialisieren der listen, sonst werden die CheckBoxen im Null gespeichert, was dafür sorgt dass die auch nimmer auf der GUI sind :D
		for(int i = 0; i < 12; i++)
			boxen[i] = new LinkedList<JCheckBox>();


		for(int i = 1; i < derChar.talenteNachKategorien.length; i++)
			for(int k = 0; k < derChar.talenteNachKategorien[i].size(); k++){
				JCheckBox temp = new JCheckBox(derChar.talenteNachKategorien[i].get(k).bezeichnung);	
				auswahlFlaeche.add(temp);
				boxen[i].add(temp);
			}

		JScrollPane auswahlScroller = new JScrollPane(auswahlFlaeche);

		this.setLayout(new BorderLayout());
		this.add(auswahlScroller, BorderLayout.CENTER);


		JPanel buttons = new JPanel();

		anz = 0;
		for(int i = 1; i < derChar.talenteNachKategorien.length; i++)
			if(derChar.talenteNachKategorien[i].size() > 0)
				anz++;

		buttons.setLayout(new GridLayout(anz+1,1));


		JButton btnAll = new JButton("Alle");
		btnAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < boxen.length; i++)
					for(int x = 0; x < boxen[i].size(); x++){
						if(boxen[i].get(x).isSelected())
							boxen[i].get(x).setSelected(false);
						else
							boxen[i].get(x).setSelected(true);
					}

			}
		});

		buttons.add(btnAll);

		String[] texte = {"", "Körperlich", "Gesellschaftlich", "Natur", "Wissen", "Sprachen", "Schriften", "Handwerk", "Metatalente", "Zauber", "Gaben", "Liturgien"} ;
		for(int i = 1; i < texte.length; i++){
			if(derChar.talenteNachKategorien[i].size() > 0){
				final int stand = i;
				JButton temp = new JButton(texte[i]);
				temp.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						for(int x = 0; x < boxen[stand].size(); x++){
							if(boxen[stand].get(x).isSelected())
								boxen[stand].get(x).setSelected(false);
							else
								boxen[stand].get(x).setSelected(true);
						}

					}
				});

				buttons.add(temp);
			}
		}


		this.add(buttons, BorderLayout.EAST);

		JButton weiter = new JButton("Weiter");
		this.add(weiter, BorderLayout.SOUTH);


		weiter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				buttonclick();
			}	
		});

		//die 2 Leerzeichen sind, weil es sonst irgendwie komisch aussieht, dass der Text direkt am Rand startet...
		JLabel labelTop = new JLabel("  welche Talente sollen für die Statistiken berücksichtigt werden?");
		this.add(labelTop, BorderLayout.NORTH);

		this.setSize(420, 500);
		this.setVisible(true);
	}

	protected void buttonclick() {
		int mu = 0;
		int kl = 0;
		int intu = 0;
		int ch = 0;
		int ff = 0;
		int ge = 0;
		int ko = 0;
		int kk = 0;


		HashMap<String, Integer> merkmale = new HashMap<String, Integer>();

		int size = 0;

		for(int i = 0; i < boxen.length; i++)
			for(int x = 0; x < boxen[i].size(); x++)
				if(boxen[i].get(x).isSelected()){
					String bezeichnung = boxen[i].get(x).getText();

					for(int k = 0; k < derChar.talenteNachKategorien.length; k++)
						for(int j = 0; j < derChar.talenteNachKategorien[k].size(); j++){
							talent temp = derChar.talenteNachKategorien[k].get(j);
							if(temp.bezeichnung.equals(bezeichnung)){
								String eigenschaften[] = {temp.probe.substring(0,2), temp.probe.substring(3,5) ,temp.probe.substring(6,8) };

								for(int c = 0; c < 3; c++){
									if(eigenschaften[c].equals("MU"))
										mu++;
									else if(eigenschaften[c].equals("KL"))
										kl++;
									else if(eigenschaften[c].equals("IN"))
										intu++;
									else if(eigenschaften[c].equals("CH"))
										ch++;
									else if(eigenschaften[c].equals("FF"))
										ff++;
									else if(eigenschaften[c].equals("GE"))
										ge++;
									else if(eigenschaften[c].equals("KO"))
										ko++;
									else if(eigenschaften[c].equals("KK"))
										kk++;
								}

								String merkmal = temp.merkmale;
								if(!merkmal.equals("")){
									String speicher = "";

									for(int q = 0; q < merkmal.length(); q++)
										if(merkmal.charAt(q) != ','){
											speicher += merkmal.charAt(q) + "";
										}else{
											if(merkmale.containsKey(speicher)){
												merkmale.put(speicher, new Integer( merkmale.get(speicher).intValue()+1));
											}else{
												merkmale.put(speicher, new Integer(1));
											}
											q++;
											speicher = "";
										}
									//bei nur einem Merkmal ist gar kein "," drin... und selbst bei mehreren ist zum schluss ja eins, wo kein "," folgt...
									if(merkmale.containsKey(speicher)){
										merkmale.put(speicher, new Integer( merkmale.get(speicher).intValue()+1));
									}else{
										merkmale.put(speicher, new Integer(1));
									}
								}


							}
						}							
				}
		int[] result = {mu, kl, intu, ch, ff, ge, ko, kk};
		new StatistikMainFrame(merkmale, result);
		this.setVisible(false);
	}
}
