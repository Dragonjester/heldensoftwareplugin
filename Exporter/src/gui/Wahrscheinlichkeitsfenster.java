package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.WahrscheinlichkeitRueckgabe;
import logic.Wahrscheinlichkeitsrechner;

public class Wahrscheinlichkeitsfenster extends JFrame{
	JTextField[] Eigenschaften;
	JTextField TaW;
	JTextField Mod;
	JPanel center;
	JPanel southpanel;
	final JLabel South;
	JLabel[] a;
	public Wahrscheinlichkeitsfenster(){
		Eigenschaften = new JTextField[3];
		this.setLayout(new BorderLayout());
		center = new JPanel();
		center.setLayout(new GridLayout(5,5));
		southpanel = new JPanel();
		South = new JLabel();
		southpanel.setLayout(new GridLayout(1,3));
		southpanel.add(new JLabel(""));
		southpanel.add(South);
		southpanel.add(new JLabel(""));
		this.add(southpanel, BorderLayout.SOUTH);

		a = new JLabel[5];
		for(int i = 0; i < 3; i++){
			a[i] = new JLabel("Eigenschaft " + (i+1));
			Eigenschaften[i] = new JTextField("10");
			Eigenschaften[i].addKeyListener(new KeyListener(){
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
				}
				@Override
				public void keyReleased(KeyEvent arg0) {
					setWahrscheinlichkeit();	
				}
				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub	
				};
			});
		}
		a[3] = new JLabel("Taw");
		a[4] = new JLabel("Mod");

		TaW = new JTextField("0");
		TaW.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				setWahrscheinlichkeit();	
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub	
			};
		});


		Mod = new JTextField("0");
		Mod.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				setWahrscheinlichkeit();
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub	
			};
		});

		for(int i = 0; i < 3; i++){
			center.add(a[i]);
			center.add(Eigenschaften[i]);
		}

		center.add(a[3]);
		center.add(TaW);

		center.add(a[4]);
		center.add(Mod);


		this.add(center);
		setWahrscheinlichkeit();
		this.setSize(350,200);
		this.setLocation(400, 300);
		this.setTitle("CAAs Wahrscheinlichkeitsrechner");
		this.setVisible(true);
	}

	private void setWahrscheinlichkeit(){
		try{
			int e1,e2,e3,taw,mod;
			if(Eigenschaften[0].getText() == "") e1 = 0; else e1 = Integer.parseInt(Eigenschaften[0].getText());
			if(Eigenschaften[1].getText() == "") e2 = 0; else e2 = Integer.parseInt(Eigenschaften[1].getText());
			if(Eigenschaften[2].getText() == "") e3 = 0; else e3 = Integer.parseInt(Eigenschaften[2].getText());
			if(TaW.getText() == "") taw = 0; else taw = Integer.parseInt(TaW.getText());
			if(Mod.getText() == "") mod = 0; else mod = Integer.parseInt(Mod.getText());
			WahrscheinlichkeitRueckgabe result = Wahrscheinlichkeitsrechner.Wahrscheinlichkeit(e1, e2, e3, taw, mod);
			South.setText("   "+ result.Wahrscheinlichkeit + "%\n" + "   "+ result.durchschnittTap + "TaP*");
		}catch(Exception e){
			e.printStackTrace();
		}	

	}
}