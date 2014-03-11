package panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import models.Appointment;

import db.DBConnection;

import models.Person;
import renderers.PersonRenderer;

public class Participants extends JPanel {

	private JTextField searchInput;
	private JLabel searchResultLabel;
	private JLabel attendingLabel;
	private JList<Person> searchResult;
	private JList<Person> attendingList;
	private JButton attendButton;
	private JButton undoAttendButton;
	private JButton saveButton;
	private DefaultListModel<Person> unattendingEmployeesModel;
	private DefaultListModel<Person> attendingEmployeesModel;
	private ArrayList<Person> unattendingEmployees;
	private ArrayList<Person> attendingEmployees;

	public Participants(final Appointment ap) {
		setLayout(new GridBagLayout());

		searchInput = new JTextField(25);
		searchResultLabel = new JLabel("Employees");
		attendingLabel = new JLabel("Added");
		searchResult = new JList<Person>();
		attendingList = new JList<Person>();
		attendButton = new JButton(">");
		undoAttendButton = new JButton("<");
		saveButton = new JButton("Lagre");

		searchResult.setCellRenderer(new PersonRenderer()); // Add list renderers
		attendingList.setCellRenderer(new PersonRenderer());
		
		JScrollPane searchResultPane = new JScrollPane(searchResult); // Add lists to scrollpanes
		JScrollPane attendingListPane = new JScrollPane(attendingList);

		searchResultPane.setPreferredSize(new Dimension(150, 150)); // Set pane sizes
		attendingListPane.setPreferredSize(new Dimension(150, 150));
		
		unattendingEmployeesModel = new DefaultListModel<Person>(); // Create models
		attendingEmployeesModel = new DefaultListModel<Person>();
		searchResult.setModel(unattendingEmployeesModel); // Set JList models
		attendingList.setModel(attendingEmployeesModel);

		// ONLY USED FOR TESTING //
		unattendingEmployees = new ArrayList<Person>();
		attendingEmployees = new ArrayList<Person>();
		Person p1 = new Person("Per");
		Person p2 = new Person("Kari");
		Person p3 = new Person("Ola");
		Person p4 = new Person("Jon");
		Person p5 = new Person("Silje");
		unattendingEmployees.add(p1);
		unattendingEmployees.add(p2);
		unattendingEmployees.add(p3);
		unattendingEmployees.add(p4);
		unattendingEmployees.add(p5);
		attendingEmployees.add(p2);
		attendingEmployees.add(p3);
		
		unattendingEmployees.removeAll(attendingEmployees); // Remove all attending employees from not attending list
		updateAttendingEmployeesModel(); // Update attending employees model
		updateUnattendingEmployeesModel(); // Update unattending employees model
		
		// Actionlisteners //
		searchInput.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) { }
			public void keyReleased(KeyEvent e) {
				updateUnattendingEmployeesModel(); // Update unattending model
			}
			public void keyPressed(KeyEvent e) { }
		});
		
		attendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Person> selectedEmployees = searchResult.getSelectedValuesList();
				for (Person p : selectedEmployees) {
					attendingEmployees.add(p); // Add employee to attending list
					unattendingEmployees.remove(p); // Remove employee from unattending list
				}
				updateUnattendingEmployeesModel(); // Update models
				updateAttendingEmployeesModel();
			}
		});
		undoAttendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Person> selectedEmployees = attendingList.getSelectedValuesList();
				for (Person p : selectedEmployees) {
					unattendingEmployees.add(p);
					attendingEmployees.remove(p);
				}
				updateUnattendingEmployeesModel();
				updateAttendingEmployeesModel();
			}
		});
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DBConnection con = new DBConnection("src/db/props.properties");
				con.init();
				ResultSet old = getOldAttendingList(con);
				deleteParticipantsNotOnAttending(con, old);
				saveParticipantsOnAttending(con, old);
				con.close();
			}

			private ResultSet getOldAttendingList(DBConnection con2) {
				PreparedStatement prs;
				try {
					prs = con2.prepareStatement("SELECT Username FROM employeeappointmentalarm WHERE AppointmentNumber = " + ap.getId());
					return prs.executeQuery();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}

			private void deleteParticipantsNotOnAttending(DBConnection con2, ResultSet old) {
				PreparedStatement prs;
				try {
					prs = con2.prepareStatement("DELETE FROM employeeappointmentalarm WHERE Username = ?");
					while (old.next()) {
						if (!attendingEmployeesModel.contains(old.getString(1))) {
							prs.setString(1, old.getString(1));
							prs.executeUpdate();
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} 
				
			}

			private void saveParticipantsOnAttending(DBConnection con2, ResultSet old) {
				PreparedStatement prs;
				try {
					prs = con2.prepareStatement("INSERT INTO employeeappointmentalarm(Username, AppointmentNumber,Status,Hide,Edited)" +
												"VALUES (?,?,?,?,?)");
					old.beforeFirst();
					while (old.next()) {
						if (attendingEmployeesModel.contains(old.getString(1))) {
							
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		});

		// Add elements to grid //
		GridBagConstraints inputConstraint = new GridBagConstraints();
		inputConstraint.gridwidth = 3;
		add(searchInput, inputConstraint);

		GridBagConstraints resultLabelConstraint = new GridBagConstraints();
		resultLabelConstraint.gridx = 0;
		resultLabelConstraint.gridy = 1;
		add(searchResultLabel, resultLabelConstraint);

		GridBagConstraints attendLabelConstraint = new GridBagConstraints();
		attendLabelConstraint.gridx = 2;
		attendLabelConstraint.gridy = 1;
		add(attendingLabel, attendLabelConstraint);

		GridBagConstraints searchListConstraint = new GridBagConstraints();
		searchListConstraint.gridx = 0;
		searchListConstraint.gridy = 2;
		searchListConstraint.gridheight = 2;
		add(searchResultPane, searchListConstraint);


		GridBagConstraints attendingListConstraint = new GridBagConstraints();
		attendingListConstraint.gridx = 2;
		attendingListConstraint.gridy = 2;
		attendingListConstraint.gridheight = 2;
		add(attendingListPane, attendingListConstraint);

		GridBagConstraints attendButtonConstraint = new GridBagConstraints();
		attendButtonConstraint.gridx = 1;
		attendButtonConstraint.gridy = 2;
		add(attendButton, attendButtonConstraint);

		GridBagConstraints undoAttendConstraint = new GridBagConstraints();
		undoAttendConstraint.anchor = GridBagConstraints.SOUTH;
		undoAttendConstraint.gridx = 1;
		undoAttendConstraint.gridy = 3;
		add(undoAttendButton, undoAttendConstraint);
		
		GridBagConstraints saveButtonConstraint = new GridBagConstraints();
		saveButtonConstraint.gridy = 4;
		add(saveButton, saveButtonConstraint);
	}

	public void updateUnattendingEmployeesModel() {
		unattendingEmployeesModel.clear(); // Clear model list
		for (Person p : unattendingEmployees) { // Iterate through selected employees
			if (! unattendingEmployeesModel.contains(p) && p.getUsername().toLowerCase().contains(searchInput.getText().toLowerCase())) {
				unattendingEmployeesModel.addElement(p); // Add employee to unattending list if the above requirements are fulfilled
			}
		}
	}
	
	public void updateAttendingEmployeesModel() {
		attendingEmployeesModel.clear();
		for (Person p : attendingEmployees) {
			attendingEmployeesModel.addElement(p);
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Møtedeltagere");
		frame.setContentPane(new Participants(new Appointment(6)));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	}
}
