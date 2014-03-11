package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
	
	public Participants() {
		setLayout(new GridBagLayout());
		
		searchInput = new JTextField(25);
		searchResultLabel = new JLabel("Searchresult");
		attendingLabel = new JLabel("Attending");
		searchResult = new JList<Person>();
		attendingList = new JList<Person>();
		attendButton = new JButton(">");
		undoAttendButton = new JButton("<");
		
		searchResult.setCellRenderer(new PersonRenderer()); // Add list renderers
		attendingList.setCellRenderer(new PersonRenderer());
		
		JScrollPane searchResultPane = new JScrollPane(searchResult); // Add lists to scrollpanes
		JScrollPane attendingListPane = new JScrollPane(attendingList);
		
		searchResultPane.setPreferredSize(new Dimension(150, 150)); // Set pane sizes
		attendingListPane.setPreferredSize(new Dimension(150, 150));
		
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

		DefaultListModel<Person> model = new DefaultListModel<Person>();
		searchResult.setModel(model);
		attendingList.setModel(model);						// ONLY USED FOR TESTING
		model.addElement(new Person("Per"));
		
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
		undoAttendConstraint.gridx = 1;
		undoAttendConstraint.gridy = 3;
		add(undoAttendButton, undoAttendConstraint);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("M�tedeltagere");
		frame.getContentPane().add(new Participants());
		frame.pack();
		frame.setVisible(true);
	}

}
