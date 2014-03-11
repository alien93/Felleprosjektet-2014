package renderers;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import models.Person;


public class AvtaleRenderer extends JPanel implements ListCellRenderer<Appointment>{

	@Override
	public Component getListCellRendererComponent(JList<? extends Appointment> avtaler, Appointment avtale,
			int index, boolean isSelected, boolean hasFocus) {
		
	
		avtaler.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 if (isSelected && hasFocus) {
			 setBackground(avtaler.getSelectionBackground());
             setForeground(avtaler.getSelectionForeground());
         }
         else {
               setBackground(avtaler.getBackground());
               setForeground(avtaler.getForeground());
           }
         setEnabled(avtaler.isEnabled());
         setFont(avtaler.getFont());
         setOpaque(true);
         return this;
	}

}
