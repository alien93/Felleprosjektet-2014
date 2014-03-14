package renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import models.Appointment;



public class AvtaleRenderer extends JPanel implements ListCellRenderer<Appointment>{
	JLabel nameText = new JLabel("");
	JLabel tidText = new JLabel("");
	JLabel romText = new JLabel("");
	JLabel vertText = new JLabel("");
	JLabel varselText = new JLabel("");
	Border line;
	
	public AvtaleRenderer(){
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(175, 60));
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(1, 5, 1, 5); // Padding
		gc.anchor = GridBagConstraints.WEST; // Alignment
		
		gc.gridx = 0; // Column
		gc.gridy = 0; // Row
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(nameText, gc);
		nameText.setFont(new Font(nameText.getFont().getName(), Font.BOLD, 14));
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.EAST;
		gc.fill = GridBagConstraints.REMAINDER;
		add(varselText, gc);
		varselText.setFont(new Font("Times New Roman", Font.BOLD, 20));
		//varselText.setForeground(new Color(255, 230, 130));
		varselText.setForeground(Color.BLACK);
		gc.anchor = GridBagConstraints.WEST;
		
		gc.gridx = 0; // Column
		gc.gridy = 1; // Row
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(tidText, gc);
		tidText.setFont(new Font(nameText.getFont().getName(), Font.PLAIN, 11));
		
		gc.gridx = 1; // Column
		gc.gridy = 2; // Row
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.fill = GridBagConstraints.REMAINDER;
		add(romText, gc);
		romText.setFont(new Font(nameText.getFont().getName(), Font.PLAIN, 11));
		
		gc.gridx = 0; // Column
		gc.gridy = 2; // Row
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(vertText, gc);
		vertText.setFont(new Font(nameText.getFont().getName(), Font.PLAIN, 11));
		
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Appointment> avtaler, Appointment avtale,
			int index, boolean isSelected, boolean hasFocus) {

		nameText.setText(avtale.getName());
		tidText.setText(avtale.getStartTime().substring(0, 5) + " - " + avtale.getEndTime().substring(0, 5));
		romText.setText("Rom: " + String.valueOf(avtale.getMeetingRoomNr()));
		vertText.setText("Vert: " + "TODO");
		varselText.setText(avtale.isEdited()? "*" : "");
		
		
	
		avtaler.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Color color = Color.LIGHT_GRAY;
		//TODO forandre color til riktig farge basert på status
		String status = avtale.getStatus();
		/*try{
			status = avtale.getStatus();
		}catch (RuntimeException e){
			status = "gjest";
		}*/
		switch(status){
			case Appointment.DECLINED: color = new Color(255, 175, 175); break;
			case Appointment.CONFIRMED: color = new Color(175, 255, 175); break;
			case Appointment.HOST: color = new Color(175, 175, 255); break;
			default: color = new Color(255, 100, 255); break;
		}
		line = BorderFactory.createRaisedBevelBorder();
		setBackground(color);
		setBorder(line);
        return this;
	}

}
