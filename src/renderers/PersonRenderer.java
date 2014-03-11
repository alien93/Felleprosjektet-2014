package renderers;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import models.Person;

public class PersonRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {

	@Override
	public Component getListCellRendererComponent(JList list, Object object,
			int index, boolean isSelected, boolean cellHasFocus) {

		super.getListCellRendererComponent(list, object, index, isSelected, cellHasFocus);
		Person person = (Person) object;
		setText(person.getUsername());

		return this;
	}
	
}
