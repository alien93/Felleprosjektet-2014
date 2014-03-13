package gui;

import java.awt.Color;

import javax.swing.JFrame;

public class ParticipantAppointmentPanel extends AppointmentPanel {

	public ParticipantAppointmentPanel(JFrame jf) {
		super(jf);
		this.nameField.setEditable(false);
		this.dateChooser.getDateEditor().setEnabled(false);
		//this.dateChooser.getDateEditor().se
		this.starTimeHourPropertyComponent.setEnabled(false);
		this.starTimeMinutesPropertyComponent.setEnabled(false);
		this.endTimeHourPropertyComponent.setEnabled(false);
		this.endTimeMinutePropertyComponent.setEnabled(false);
		this.roomPropertyComponent.setEnabled(false);;
		this.addExternal.setEnabled(false);
		this.addButton.setEnabled(false);
		this.calender.setEnabled(false);
		this.emailField.setEnabled(false);
		
		
	}
	
	public static void main(String args[]){
		JFrame frame = new JFrame("Ny avtale");
		frame.getContentPane().add(new ParticipantAppointmentPanel(frame));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}

