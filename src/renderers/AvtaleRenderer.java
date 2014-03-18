package renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import db.DBConnection;

import models.Appointment;
import models.Person;



public class AvtaleRenderer extends JPanel implements ListCellRenderer<Appointment>{
	public static final Color TEXT_COLOR = Color.WHITE;
	
	JLabel nameText = new JLabel("");
	JLabel tidText = new JLabel("");
	JLabel romText = new JLabel("");
	JLabel vertText = new JLabel("");
	JLabel varselText = new JLabel("");
	Border line;
	
	public AvtaleRenderer(){
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(175, 60));
		setMinimumSize(new Dimension(125, 55));
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(1, 5, 1, 5); // Padding
		gc.anchor = GridBagConstraints.WEST; // Alignment
		
		gc.gridx = 0; // Column
		gc.gridy = 0; // Row
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(nameText, gc);
		nameText.setFont(new Font(nameText.getFont().getName(), Font.BOLD, 14));
		nameText.setMinimumSize(new Dimension(100, 22));
		nameText.setPreferredSize(new Dimension(100, 22));
		nameText.setForeground(TEXT_COLOR);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.EAST;
		gc.fill = GridBagConstraints.REMAINDER;
		add(varselText, gc);
		varselText.setFont(new Font("Times New Roman", Font.BOLD, 20));
		//varselText.setForeground(new Color(255, 230, 130));
		varselText.setForeground(TEXT_COLOR);
		gc.anchor = GridBagConstraints.WEST;
		
		gc.gridx = 0; // Column
		gc.gridy = 1; // Row
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(tidText, gc);
		tidText.setFont(new Font(nameText.getFont().getName(), Font.PLAIN, 11));
		tidText.setForeground(TEXT_COLOR);
		
		gc.gridx = 1; // Column
		gc.gridy = 2; // Row
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.fill = GridBagConstraints.REMAINDER;
		add(romText, gc);
		romText.setFont(new Font(nameText.getFont().getName(), Font.PLAIN, 11));
		romText.setMinimumSize(new Dimension(50, 15));
		romText.setPreferredSize(new Dimension(65, 15));
		romText.setForeground(TEXT_COLOR);
		
		gc.gridx = 0; // Column
		gc.gridy = 2; // Row
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(vertText, gc);
		vertText.setFont(new Font(nameText.getFont().getName(), Font.PLAIN, 11));
		vertText.setForeground(TEXT_COLOR);
		
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Appointment> avtaler, Appointment avtale,
			int index, boolean isSelected, boolean hasFocus) {

		nameText.setText(avtale.getName());
		tidText.setText(avtale.getStartTime().substring(0, 5) + " - " + avtale.getEndTime().substring(0, 5));
		romText.setText("Rom: " + String.valueOf(avtale.getMeetingRoomNr()));
		try{
			vertText.setText("Vert: " + avtale.getHost().getUsername());//TODO
		}catch (NullPointerException npe){
			DBConnection con = new DBConnection("src/db/props.properties", true);
			try {

				ResultSet rsAtLoad = con.smallSELECT("SELECT Username, Status FROM employeeappointmentalarm WHERE AppointmentNumber = " + avtale.getId());
				while (rsAtLoad.next()) {
					if (rsAtLoad.getString("Status").equals("host")) {
						Person thisHost = new Person(rsAtLoad.getString("Username"));
						avtale.setHost(thisHost);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.close();
		}
		varselText.setText(avtale.isEdited()? "*" : "");
		
		
	
		avtaler.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Color color = new Color(118, 118, 118);
		//TODO forandre color til riktig farge basert p� status
		String status = avtale.getStatus();
		switch(status){
			case Appointment.DECLINED: 	color = new Color(255, 100, 100); break;
			case Appointment.CONFIRMED: color = new Color(100, 255, 100); break;
			case Appointment.HOST: 		color = new Color(000, 200, 255); break;
			case Appointment.GJEST: 	color = new Color(255, 175, 255); break;
		}
		//line = BorderFactory.createRaisedBevelBorder();
		line = BorderFactory.createLineBorder(Color.WHITE, 1);
		setBackground(color);
		setBorder(line);
        return this;
	}

}
