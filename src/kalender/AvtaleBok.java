package kalender;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AvtaleBok extends JPanel {
	
	public AvtaleBok() {
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		add(new JButton("<"), constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 0;
		add(new JLabel("Uke"), constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 0;
		add(new JButton(">"), constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(new JLabel("Mandag"), constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		add(new JLabel("Tirsdag"), constraints);

		constraints.gridx = 2;
		constraints.gridy = 1;
		add(new JLabel("Onsdag"), constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 1;
		add(new JLabel("Torsdag"), constraints);
		
		constraints.gridx = 4;
		constraints.gridy = 1;
		add(new JLabel("Fredag"), constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 3;
		add(new JButton("Ny avtale"), constraints);
		
		constraints.gridx = 4;
		constraints.gridy = 3;
		add(new JButton("Legg til/ fjern ansatt"), constraints);
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new AvtaleBok());
		//frame.setBounds(100, 100, 800, 600);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
