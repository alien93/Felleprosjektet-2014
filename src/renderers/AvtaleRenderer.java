package renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import appointment.Appointment;


public class AvtaleRenderer extends JPanel implements ListCellRenderer<Appointment>{
	JLabel nameText = new JLabel("");
	JLabel tidText = new JLabel("");
	JLabel romText = new JLabel("");
	JLabel vertText = new JLabel("");
	Border line;
	
	public AvtaleRenderer(){
		setLayout(new GridBagLayout());
		setSize(200, 100);
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(1, 5, 1, 5); // Padding
		gc.anchor = GridBagConstraints.EAST; // Alignment
		
		gc.gridx = 0; // Column
		gc.gridy = 0; // Row
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(nameText, gc);
		nameText.setFont(new Font(nameText.getFont().getName(), Font.BOLD, 16));
		
		gc.gridx = 0; // Column
		gc.gridy = 1; // Row
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(tidText, gc);
		
		gc.gridx = 1; // Column
		gc.gridy = 1; // Row
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(romText, gc);
		
		gc.gridx = 0; // Column
		gc.gridy = 2; // Row
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(vertText, gc);
		
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Appointment> avtaler, Appointment avtale,
			int index, boolean isSelected, boolean hasFocus) {

		nameText.setText(avtale.getName());
		tidText.setText(avtale.getStartTime() + " - " + avtale.getEndTime());
		romText.setText("Rom: " + String.valueOf(avtale.getMeetingRoomNr()));
		vertText.setText("Vert: " + "TODO");
		
	
		avtaler.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		line = BorderFactory.createRaisedBevelBorder();
		setBackground(new Color(255, 200, 200));
		setBorder(line);
        return this;
	}

}
