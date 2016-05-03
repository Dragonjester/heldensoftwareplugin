package gui;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class StatistikMainFrame extends JFrame{


	/**
	 * int mu 
		int kl
		int intu 
		int ch 
		int ff
		int ge
		int ko
		int kk
	 */
	public StatistikMainFrame(HashMap<String, Integer> merkmale, int[] result) {
		JPanel flaeche = new JPanel();
		flaeche.setLayout(new GridLayout(8 + merkmale.size(), 2));
		String[] eigenschaften = {"Mut", "Klugheit", "Intuition", "Charisma", "Fingerfertigkeit", "Gewandheit", "Konstitution", "Körperkraft"};
		for(int i = 0; i < eigenschaften.length; i++){
			flaeche.add(new JLabel(eigenschaften[i]));
			flaeche.add(new JLabel(result[i] + ""));
		}

		Iterator it = merkmale.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			flaeche.add(new JLabel("Merkmal " + pairs.getKey()));
			flaeche.add(new JLabel(pairs.getValue() + ""));
			it.remove(); // avoids a ConcurrentModificationException
		}
		
		JScrollPane auswahlScroller = new JScrollPane(flaeche);
		this.add(auswahlScroller);

		this.setSize(420, 500);

		this.setVisible(true);
	}

}
