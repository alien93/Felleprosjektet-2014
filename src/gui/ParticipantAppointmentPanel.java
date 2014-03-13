package gui;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class ParticipantAppointmentPanel extends AppointmentPanel {

	public ParticipantAppointmentPanel(JFrame jf) {
		super(jf);
		DefaultTableModel tableModel = new DefaultTableModel(){
			 public boolean isCellEditable(int row, int column) {
			        //all cells false
			        return false;
			        }
			 };
	this.nameField.setEditable(false);
	this.nameField.setEnabled(false);
	
	}
}
