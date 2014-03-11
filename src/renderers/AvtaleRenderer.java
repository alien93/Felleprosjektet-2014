package renderers;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import appointment.Appointment;


public class AvtaleRenderer extends JPanel implements ListCellRenderer<Appointment>{
	JLabel nameText = new JLabel("");
	JLabel tidText = new JLabel("");
	JLabel romText = new JLabel("");
	JLabel vertText = new JLabel("");
	
	public AvtaleRenderer(){
		setSize(200, 100);
		add(nameText);
		add(tidText);
		add(romText);
		add(vertText);
		
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Appointment> avtaler, Appointment avtale,
			int index, boolean isSelected, boolean hasFocus) {

		nameText.setText(avtale.getName());
		tidText.setText(avtale.getStartTime() + " - " + avtale.getEndTime());
		romText.setText("Rom: " + String.valueOf(avtale.getMeetingRoomNr()));
		vertText.setText("Vert: " + "TODO");
		
	
		avtaler.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 if (isSelected && hasFocus) {
			 setBackground(avtaler.getSelectionBackground());
             setForeground(avtaler.getSelectionForeground());
         }
         else {
               setBackground(avtaler.getBackground());
               setForeground(avtaler.getForeground());
           }
         setOpaque(true);
         return this;
	}

}
