package gui;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.w3c.dom.Document;

import logic.WahrscheinlichkeitRueckgabe;
import logic.Wahrscheinlichkeitsrechner;
import logic.ausgabeErstellen;
import logic.chareinlesen;
import logic.eigenschaft;
import logic.talent;

@SuppressWarnings("serial")
public class JP_Maptooltool extends JPanel implements Serializable{
	JList TalentAuswahl = new JList();
	JList EigenschaftAuswahl = new JList();
	chareinlesen chars = new chareinlesen();
	final JScrollBar sb = new JScrollBar( Adjustable.HORIZONTAL, 25, 0, 0, 50); 
	final JTextField tf = new JTextField("Erschwerniss: "+0); 
	JLabel Wahrscheinlichkeit;
	public JP_Maptooltool(Document ret)
	{
		this.setLayout(new BorderLayout());

		//SOUTH
		JPanel P_South = new JPanel();

		P_South.setLayout(new GridLayout(3,1));

		P_South.add ( tf ); 
		P_South.add ( sb ); 
		JPanel P_Whsp = new JPanel();
		P_Whsp.setLayout(new BorderLayout());
		final JCheckBox JWhsp = new JCheckBox();
		JWhsp.setText("An den Meister würfeln");
		JWhsp.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ausgabeErstellen.setWhisper();
				if(!TalentAuswahl.isSelectionEmpty()){
					int index = TalentAuswahl.getSelectedIndex();
					int erschwerniss = sb.getValue()-25;
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ausgabeErstellen.Talentprobe((talent)chars.talente.get(index), erschwerniss)), null);
				}else if(!EigenschaftAuswahl.isSelectionEmpty()){
					int auswahl = EigenschaftAuswahl.getSelectedIndex();
					int erschwerniss = (sb.getValue()-25) ;
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ausgabeErstellen.Eigenschaftsprobe((eigenschaft)chars.eigenschaften.get(auswahl), erschwerniss)), null);
				}	
			}
		});
		P_Whsp.add(JWhsp, BorderLayout.EAST);
		Wahrscheinlichkeit = new JLabel();
		P_Whsp.add(Wahrscheinlichkeit, BorderLayout.CENTER);
		//P_South.add(Wahrscheinlichkeit);
		P_South.add(P_Whsp);
		tf.addActionListener( new ActionListener() { 
			@Override
			public void actionPerformed( ActionEvent e ) { 
				sb.setValue( Integer.parseInt(tf.getText()) ); 
			}
		} ); 

		sb.addAdjustmentListener( new AdjustmentListener() { 
			@Override 
			public void adjustmentValueChanged( AdjustmentEvent e ) {
				if(sb.getValue() >= 25)
				{
					tf.setText( "Erschwerniss: " + (sb.getValue()-25) );
				}else{
					tf.setText("Erleichterung: " + (sb.getValue()-25));
				}

				try{
					if(!TalentAuswahl.isSelectionEmpty()){
						int index = TalentAuswahl.getSelectedIndex();
						int erschwerniss = sb.getValue()-25;
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ausgabeErstellen.Talentprobe((talent)chars.talente.get(index), erschwerniss)), null);
						wahrscheinlichkeitTalent(index, erschwerniss);
					}else{
						int auswahl = EigenschaftAuswahl.getSelectedIndex();
						int erschwerniss = (sb.getValue()-25) ;
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ausgabeErstellen.Eigenschaftsprobe((eigenschaft)chars.eigenschaften.get(auswahl), erschwerniss)), null);
						wahrscheinlichkeitEigenschaft(auswahl, erschwerniss);
					}
				}catch(Exception as){}
			} 
		} ); 


		JButton Reg = new JButton("Regeneration");
		Reg.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ausgabeErstellen.Regeneration()), null);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
		JPanel P_North = new JPanel();
		P_North.setLayout(new GridLayout(2,1));
		JPanel P_North_Top = new JPanel();
		P_North_Top.setLayout(new GridLayout(1,6));

		for(int i = 1; i < 4; i++){
			JButton d6 = new JButton(i+"d6");
			final int iStand = i;
			d6.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ausgabeErstellen.anzD6(iStand),null);
				}
			});
			P_North_Top.add(d6);
		}

		JButton d20 = new JButton("1d20");
		d20.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ausgabeErstellen.anzD20(1),null);
			}
		});
		P_North_Top.add(d20);

		JButton at = new JButton("AT");
		at.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	}
			@Override
			public void mouseEntered(MouseEvent arg0) {	}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				try{
					if(arg0.getClickCount() == 2 || chars.at.equals("") || chars.atWert.equals("")){
						setWaffenWerteAT();
						if(!(chars.at.equals("") || chars.atWert.equals("")))
							Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ausgabeErstellen.angriff((sb.getValue()-25))), null);
					}else if(arg0.getClickCount() == 1){
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ausgabeErstellen.angriff((sb.getValue()-25))), null);
					}
				}
				catch(Exception e){}
			}
		});

		P_North_Top.add(at);
		JButton pa = new JButton("PA");

		pa.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				try{
					if(arg0.getClickCount() == 2 || chars.pa.equals("")){
						setWaffenWertePA();
						if(!chars.pa.equals(""))
							Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ausgabeErstellen.parade((sb.getValue()-25))), null);
					}else if(arg0.getClickCount() == 1){
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ausgabeErstellen.parade((sb.getValue()-25))), null);
					}
				}
				catch(Exception e){}

			}
		});

		P_North_Top.add(pa);

		P_North.add(P_North_Top);
		P_North.add(Reg);
		this.add(P_North, BorderLayout.NORTH);
		this.add(P_South, BorderLayout.SOUTH);

		//    CENTER
		JPanel P_Center = new JPanel();
		P_Center.setLayout(new GridLayout(1,2));

		TalentAuswahl.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				EigenschaftAuswahl.clearSelection();
				int index = TalentAuswahl.getSelectedIndex();
				int erschwerniss = (sb.getValue()-25);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ausgabeErstellen.Talentprobe((talent)chars.talente.get(index), erschwerniss)), null);
				wahrscheinlichkeitTalent(index, erschwerniss);	
				sb.setValue(25);
				tf.setText( "Erschwerniss: 0");
			}

		});
		/*
		TalentAuswahl.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					EigenschaftAuswahl.clearSelection();
					sb.setValue(25);
					tf.setText( "Erschwerniss: 0");
					int index = TalentAuswahl.getSelectedIndex();
					int erschwerniss = (sb.getValue()-25);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ausgabeErstellen.Talentprobe((talent)chars.talente.get(index), erschwerniss)), null);
					wahrscheinlichkeitTalent(index, erschwerniss);
				}catch(Exception e){}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		 */

		JScrollPane scrollingList = new JScrollPane(TalentAuswahl); 
		P_Center.add(scrollingList);
		/*
		EigenschaftAuswahl.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				sb.setValue(25);
				tf.setText( "Erschwerniss: 0");
				try{
					int auswahl = EigenschaftAuswahl.getSelectedIndex();
					int erschwerniss = (sb.getValue()-25) ;
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ausgabeErstellen.Eigenschaftsprobe((eigenschaft)chars.eigenschaften.get(auswahl), erschwerniss)), null);
					wahrscheinlichkeitEigenschaft(auswahl, erschwerniss);
				}catch(Exception e){}
				TalentAuswahl.clearSelection();
			}	
		});
		 */
		EigenschaftAuswahl.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					TalentAuswahl.clearSelection();
					sb.setValue(25);
					tf.setText( "Erschwerniss: 0");
					int auswahl = EigenschaftAuswahl.getSelectedIndex();
					int erschwerniss = (sb.getValue()-25) ;
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ausgabeErstellen.Eigenschaftsprobe((eigenschaft)chars.eigenschaften.get(auswahl), erschwerniss)), null);
					wahrscheinlichkeitEigenschaft(auswahl, erschwerniss);
				}catch(Exception e){}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});

		JScrollPane scrollingList2 = new JScrollPane(EigenschaftAuswahl);


		P_Center.add(scrollingList2);

		this.add(P_Center, BorderLayout.CENTER);

		chars.Dateiauswahl(ret);

		TalentAuswahl.setListData(chars.dataTalente);
		EigenschaftAuswahl.setListData(chars.dataEigenschaften);

		this.repaint();
	}

	public void wahrscheinlichkeitEigenschaft(int auswahl, int erschwerniss){
		float Wahrscheinlichkeit = Float.parseFloat(((eigenschaft)chars.eigenschaften.get(auswahl)).wert);
		Wahrscheinlichkeit-=erschwerniss;
		Wahrscheinlichkeit/=20;
		if(Wahrscheinlichkeit >= 1.0f) Wahrscheinlichkeit = 0.95f;
		if(Wahrscheinlichkeit <= 0.0f) Wahrscheinlichkeit = 0.05f;
		this.Wahrscheinlichkeit.setText("                                                                             " + Wahrscheinlichkeit + "");

	}

	private void setWaffenWerteAT() {
		Object[] options = chars.getWaffenAT();
		int n = JOptionPane.showOptionDialog(new JFrame(), "Choose wisely my friend", "Waffenauswahl", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,  options[0]);
		chars.setWaffeAT(n);
	}

	private void setWaffenWertePA() {
		Object[] options = chars.getWaffenPA();
		int n = JOptionPane.showOptionDialog(new JFrame(), "Choose wisely my friend", "Waffenauswahl", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,  options[0]);
		chars.setWaffePA(n);
	}


	public void wahrscheinlichkeitTalent(int selectedIndex, int i) {
		int e1 = ((talent)chars.talente.get(TalentAuswahl.getSelectedIndex())).wert1;
		int e2 = ((talent)chars.talente.get(TalentAuswahl.getSelectedIndex())).wert2;
		int e3 = ((talent)chars.talente.get(TalentAuswahl.getSelectedIndex())).wert3;
		int taw = ((talent)chars.talente.get(TalentAuswahl.getSelectedIndex())).tap;
		WahrscheinlichkeitRueckgabe result = Wahrscheinlichkeitsrechner.Wahrscheinlichkeit(e1, e2, e3, taw, i);
		this.Wahrscheinlichkeit.setText("                              " +  result.Wahrscheinlichkeit + "% und " + (char)216 + ": " + result.durchschnittTap + "Tap*");
	}	
}