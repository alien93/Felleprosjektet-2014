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
	private final String[] days = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag"};
	private GridBagConstraints constraints;
	
	public AvtaleBok() {
		setLayout(new GridBagLayout());
		model = new AvtaleBokModel();
		constraints = new GridBagConstraints();
		updateAvtaleBok();
	}
	
	public void updateAvtaleBok() {
		removeAll();
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		prevWeek = new JButton("<<");
		prevWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setWeek(model.getWeek() - 1);
				updateAvtaleBok();
			}
		});
		add(prevWeek, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 0;
		add(new JLabel("Uke " + model.getWeek()), constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 0;
		nextWeek = new JButton(">>");
		nextWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setWeek(model.getWeek() + 1);
				updateAvtaleBok();
			}
		});
		add(nextWeek, constraints);
		
		Date dates = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy w u");
		
		for (int i = 0; i < days.length; i++) {
			constraints.gridx = i;
			try {
				dates = df.parse(model.getYear() + " " + model.getWeek() + " " + (i % 8 + 1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			JLabel dateLabel = new JLabel(new SimpleDateFormat("dd.MM.yy").format(dates));
			constraints.gridy = 1;
			add(dateLabel, constraints);
			JLabel weekDay = new JLabel(days[i]);
			constraints.gridy = 2;
			add(weekDay, constraints);
		}
		
		constraints.gridx = 3;
		constraints.gridy = 3;
		JButton newAppointment = new JButton("Ny avtale");
		newAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Du trykka paa knappen ;)");
			}
		});
		add(newAppointment, constraints);
		
		constraints.gridx = 4;
		constraints.gridy = 3;
		JButton addRemove = new JButton("Legg til/ fjern ansatt");
		addRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Du trykka paa den andre knappen :D");
			}
		});
		add(addRemove, constraints);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new AvtaleBok());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
