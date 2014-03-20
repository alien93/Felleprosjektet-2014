package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import models.ParticipantEntity;
import db.ObjectFactory;

public class Participants extends JDialog {

	private JTextField searchInput;
	private JLabel searchResultLabel, attendingLabel;
	private JList<ParticipantEntity> searchResult, attendingList;
	private JButton attendButton, undoAttendButton, saveButton;
	private DefaultListModel<ParticipantEntity> unattendingEmployeesModel, attendingEmployeesModel;
	private ArrayList<ParticipantEntity> unattendingEmployees, attendingEmployees;
	private JScrollPane searchResultPane, attendingListPane;
	private HashMap<Integer, ArrayList<ParticipantEntity>> groups = null;
	private ParticipantEntity user;

	public Participants(final AppointmentPanel appointmentPanel, final ParticipantEntity person, final HashMap<String, String> attendingEmpAtLoad) {

		super(appointmentPanel, "Deltagere", true);
		user = person;

		attendingEmployees = new ArrayList<ParticipantEntity>();
		
		for (String user : attendingEmpAtLoad.keySet()) {
			attendingEmployees.add(new ParticipantEntity(user));
		}
		
		initGUI();
		
		groups = ObjectFactory.getAllGroups();
		updateGroups();
		
		updateAttendingEmployeesModel();
		updateUnattendingEmployeesModel();

		// Actionlisteners //
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				HashMap<String, String> attendingMap = new HashMap<String, String>(attendingEmpAtLoad);
				
				for (ParticipantEntity pers : unattendingEmployees) {
					if (attendingMap.containsKey(pers.getUsername())) {
						attendingMap.remove(pers.getUsername());
					}
				}
				
				for (ParticipantEntity pers : attendingEmployees) {
					if (!attendingMap.containsKey(pers.getUsername()))
						attendingMap.put(pers.getUsername(), "Avventer svar");
				}
				
				appointmentPanel.updateParticipantRows(attendingMap);
				dispose();
			}
			
		});

		addGUI(appointmentPanel);
	}
	
	public Participants(final MainFrame frame, final AvtaleBok avtaleBok, ArrayList<ParticipantEntity> employees) {
		super(frame, "Multivisning", true);
		user = employees.get(0);
		attendingEmployees = new ArrayList<ParticipantEntity>(employees);
		
		initGUI();
		unattendingEmployees.remove(user);
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				avtaleBok.addEmployees(attendingEmployees);
				avtaleBok.removeEmployees(unattendingEmployees);
				dispose();
			}
		});
		
		addGUI(frame);
	}

	public void updateUnattendingEmployeesModel() {
		unattendingEmployeesModel.clear(); // Clear model list
		for (ParticipantEntity p : unattendingEmployees) { // Iterate through selected employees
			if (! unattendingEmployeesModel.contains(p) && p.getUsername().toLowerCase().contains(searchInput.getText().toLowerCase())) {
				unattendingEmployeesModel.addElement(p); // Add employee to unattending list if the above requirements are fulfilled
			}
		}
	}

	public void updateAttendingEmployeesModel() {
		attendingEmployeesModel.clear();
		for (ParticipantEntity p : attendingEmployees) {
			attendingEmployeesModel.addElement(p);
		}
	}
	
	public void updateGroups() {
		if (groups != null) {
			for (int groupNumber : groups.keySet()) {
				ParticipantEntity potential = new ParticipantEntity("Gruppe " + groupNumber, groups.get(groupNumber));
				if (!unattendingEmployees.contains(potential)) {
					for (ParticipantEntity per : groups.get(groupNumber)) {
						if (unattendingEmployees.contains(per)) {
							unattendingEmployees.add(0, potential);
							break;
						}
					}
				}
				else {
					boolean existsInUnattending = false;
					for (ParticipantEntity per : groups.get(groupNumber)) {
						if (unattendingEmployees.contains(per)) {
							existsInUnattending = true;
							break;
						}
					}
					if (!existsInUnattending)
						unattendingEmployees.remove(potential);
				}
			}
		}
	}

	private void initGUI() {
		setLayout(new GridBagLayout());
		setSize(500, 300);

		searchInput = new JTextField(25);
		searchResultLabel = new JLabel("Ansatte");
		attendingLabel = new JLabel("Lagt til");
		searchResult = new JList<ParticipantEntity>() {
			@Override
			public String getToolTipText(MouseEvent event) {
				int index = locationToIndex(event.getPoint());
				if (index > -1 && unattendingEmployeesModel.get(index).getGroupMembers() != null)
					return unattendingEmployeesModel.get(index).getGMembersString();
				return null;
			}
		};
		attendingList = new JList<ParticipantEntity>();
		attendButton = new JButton(">");
		undoAttendButton = new JButton("<");
		saveButton = new JButton("Lagre");

		searchResultPane = new JScrollPane(searchResult); // Add lists to scrollpanes
		attendingListPane = new JScrollPane(attendingList);

		searchResultPane.setPreferredSize(new Dimension(150, 150)); // Set pane sizes
		attendingListPane.setPreferredSize(new Dimension(150, 150));

		unattendingEmployeesModel = new DefaultListModel<ParticipantEntity>(); // Create models
		attendingEmployeesModel = new DefaultListModel<ParticipantEntity>();

		searchResult.setModel(unattendingEmployeesModel); // Set JList models
		attendingList.setModel(attendingEmployeesModel);
		
		unattendingEmployees = ObjectFactory.getAllEmployees();

		unattendingEmployees.removeAll(attendingEmployees); // Remove all attending employees from not attending list
		
		unattendingEmployees.remove(user);
		attendingEmployees.remove(user); // Remove logged in user from appointment

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
				List<ParticipantEntity> selectedEmployees = searchResult.getSelectedValuesList();
				for (ParticipantEntity p : selectedEmployees) {
					if (p.getGroupMembers() != null) {
						for (ParticipantEntity per : p.getGroupMembers()) {
							if (!attendingEmployees.contains(per) && !per.equals(user)) {
								attendingEmployees.add(per);
								unattendingEmployees.remove(per);
							}
						}
					} else if (!attendingEmployees.contains(p)) {
						attendingEmployees.add(p); // Add employee to attending list
						unattendingEmployees.remove(p); // Remove employee from unattending list
					}
				}
				updateGroups();
				updateUnattendingEmployeesModel(); // Update models
				updateAttendingEmployeesModel();
			}
		});
		undoAttendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<ParticipantEntity> selectedEmployees = attendingList.getSelectedValuesList();
				for (ParticipantEntity p : selectedEmployees) {
					unattendingEmployees.add(p);
					attendingEmployees.remove(p);
				}
				updateGroups();
				updateUnattendingEmployeesModel();
				updateAttendingEmployeesModel();
			}
		});
	}

	private void addGUI(Component frame) {
		// Add elements to grid //
		GridBagConstraints inputConstraint = new GridBagConstraints();
		inputConstraint.gridwidth = 3;
		inputConstraint.insets = new Insets(0, 0, 10, 0);
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
		attendButtonConstraint.insets = new Insets(25, 5, 0, 5);
		add(attendButton, attendButtonConstraint);

		GridBagConstraints undoAttendConstraint = new GridBagConstraints();
		undoAttendConstraint.anchor = GridBagConstraints.SOUTH;
		undoAttendConstraint.gridx = 1;
		undoAttendConstraint.gridy = 3;
		undoAttendConstraint.insets = new Insets(0, 5, 25, 5);
		add(undoAttendButton, undoAttendConstraint);

		GridBagConstraints saveButtonConstraint = new GridBagConstraints();
		saveButtonConstraint.gridwidth = 3;
		saveButtonConstraint.gridx = 0;
		saveButtonConstraint.gridy = 4;
		saveButtonConstraint.insets = new Insets(10, 0, 0, 0);
		saveButtonConstraint.ipadx = 30;
		add(saveButton, saveButtonConstraint);
		

		setLocationRelativeTo(frame);
		this.setVisible(true);
	}

}
