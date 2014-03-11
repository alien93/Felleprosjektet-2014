package kalender;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AvtaleBok extends JPanel {
	
	private JButton prevWeek, nextWeek, newAppointment, addRemove;
	private AvtaleBokModel model;
	private final String[] days = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};
	private GridBagConstraints constraints;
	private JLabel[] dateLabels = new JLabel[7];
	private JLabel ukeLabel;
	
	public AvtaleBok() {
		setLayout(new GridBagLayout());
		model = new AvtaleBokModel();
		constraints = new GridBagConstraints();
		

		prevWeek = new JButton("<<");
		prevWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setWeek(model.getWeek() - 1);
				updateAvtaleBok();
			}
		});

		nextWeek = new JButton(">>");
		nextWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setWeek(model.getWeek() + 1);
				updateAvtaleBok();
			}
		});
		

		JButton newAppointment = new JButton("Ny avtale");
		newAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Du trykka paa knappen ;)");
			}
		});
		
		JButton addRemove = new JButton("Legg til/ fjern ansatt");
		addRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Du trykka paa den andre knappen :D");
			}
		});
		
		

		constraints.gridx = 1;
		constraints.gridy = 0;
		add(prevWeek, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 0;
		ukeLabel = new JLabel("Uke " + model.getWeek());
		add(ukeLabel, constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 0;
		add(nextWeek, constraints);
		
		constraints.gridx = 5;
		constraints.gridy = 3;
		add(newAppointment, constraints);
		
		constraints.gridx = 6;
		constraints.gridy = 3;
		
		add(addRemove, constraints);
		

		Date dates = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy w u");
		for (int i = 0; i < days.length; i++) {
			constraints.gridx = i;
			try {
				dates = df.parse(model.getYear() + " " + model.getWeek() + " " + (i % 8 + 1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dateLabels[i] = new JLabel(new SimpleDateFormat("dd.MM.yy").format(dates));
			constraints.gridy = 2;
			add(dateLabels[i], constraints);
			JLabel weekDay = new JLabel(days[i]);
			constraints.gridy = 1;
			add(weekDay, constraints);
		}
		
		updateAvtaleBok();
	}
	
	public void updateAvtaleBok() {
		Date dates = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy w u");
		for (int i = 0; i < days.length; i++) {
			constraints.gridx = i;
			try {
				dates = df.parse(model.getYear() + " " + model.getWeek() + " " + (i % 8 + 1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dateLabels[i].setText((new SimpleDateFormat("dd.MM.yy").format(dates)));
		}
		ukeLabel.setText("Uke " + model.getWeek());
		
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new AvtaleBok());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
