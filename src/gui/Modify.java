package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import core.Database;

public class Modify extends JInternalFrame {
	private JPanel upperPanel, tablePanel, comboBoxPanel, labelPanel, textFieldPanel;
	
	private JComboBox relation;
	
	private JComboBox[] constrains;
	private JTextField[] modifications;
	
	private JTable matchTable;
	private Database db;
	private JButton delete, modify;
	private ArrayList<String> where;
	private String[] columns;

	public Modify(HashMap<String, String> schema, final Database db) {
		super("Delete", false, // resizable
				true, // closable
				false, // maximizable
				true);// iconifiable
		setVisible(true);
		this.db = db;
		// ///////////////////////////////////////////////TOP
		relation = new JComboBox(schema.keySet().toArray());
		final OptionListener optionListener = new OptionListener();
		relation.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				where = new ArrayList<String>();
				comboBoxPanel.removeAll();
				labelPanel.removeAll();
				textFieldPanel.removeAll();
				HashMap<String, ArrayList<String>> query = db.get("*", relation.getSelectedItem().toString());
				columns = query.keySet().toArray(new String[0]);
				constrains = new JComboBox[columns.length];
				JLabel[] labels = new JLabel[columns.length];
				modifications = new JTextField[columns.length];
				comboBoxPanel.setLayout(new GridLayout(1,columns.length));
				labelPanel.setLayout(new GridLayout(1,columns.length));
				textFieldPanel.setLayout(new GridLayout(1,columns.length));
				
				matchTable = new JTable(db.convert(query), query.keySet().toArray()) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				JScrollPane scrollPane = new JScrollPane(matchTable);
				scrollPane.setPreferredSize(new Dimension(720, 170));
				matchTable.setFillsViewportHeight(true);
				
				
				int i = 0;
				for(String column : columns){
					ArrayList<String> list = query.get(column);
					list.add("ALL");
					Collections.reverse(list);
					constrains[i] = new JComboBox(new LinkedHashSet(list).toArray());
					constrains[i].setSelectedItem("ALL");
					constrains[i].addActionListener(optionListener);
					labels[i] = new JLabel("Set "+columns[i]+":");
					modifications[i] = new JTextField();
					comboBoxPanel.add(constrains[i]);
					labelPanel.add(labels[i]);
					textFieldPanel.add(modifications[i]);
					i++;
				}

				tablePanel.removeAll();
				tablePanel.add(scrollPane);

				comboBoxPanel.updateUI();
				labelPanel.updateUI();
				textFieldPanel.updateUI();
				tablePanel.updateUI();
				if(!delete.isEnabled()) delete.setEnabled(true);
				if(!modify.isEnabled()) modify.setEnabled(true);
			}
		});
		
		delete = new JButton("Delete");
		delete.setEnabled(false);
		delete.addActionListener(new ButtonListener());
		
		modify = new JButton("Modify");
		modify.setEnabled(false);
		modify.addActionListener(new ButtonListener());

		upperPanel = new JPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
		upperPanel.add(new JLabel("Select the relation: "));
		upperPanel.add(relation);
		upperPanel.add(delete);
		upperPanel.add(modify);
		// ///////////////////////////////////////////////TOP
		// ///////////////////////////////////////////////MIDDLE
		tablePanel = new JPanel();
		matchTable = new JTable();
		comboBoxPanel = new JPanel();
		labelPanel = new JPanel();
		textFieldPanel = new JPanel();
		//comboBoxPanel.add(new JComboBox());

		JScrollPane scrollPane = new JScrollPane(matchTable);
		scrollPane.setPreferredSize(new Dimension(720, 170));
		matchTable.setFillsViewportHeight(true);
		comboBoxPanel.setPreferredSize(new Dimension(720, 50));
		labelPanel.setPreferredSize(new Dimension(720, 20));
		textFieldPanel.setPreferredSize(new Dimension(720, 20));
		tablePanel.add(scrollPane);
		//middlePanel.setSize(getSize());
		tablePanel.setPreferredSize(new Dimension(720, 220));
		// ///////////////////////////////////////////////MIDDLE

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setBounds(0, 0, 780, 440);
		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(720, 20));
		container.add(upperPanel);
		add(container);
		add(tablePanel);
		JPanel lower = new JPanel();
		lower.setPreferredSize(new Dimension(720, 90));
		lower.add(comboBoxPanel);
		lower.add(labelPanel);
		lower.add(textFieldPanel);
		add(lower);
	}
	
	private void reset(){
		this.dispose();
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if ((JButton) e.getSource() == delete) {
				int confirm = JOptionPane
						.showOptionDialog(
								null,
								"Are you sure you want to delete this/these entry/ies?",
								"Confirmation", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == 0) {
					int result = db.delete(relation.getSelectedItem()
							.toString(), where.toArray(new String[0]));
					if (result > 0) {
						JOptionPane
								.showMessageDialog(getContentPane(),
										"Successfully deleted "
												+ result
												+ " entries from "
												+ relation.getSelectedItem()
														.toString());
						reset();
					} else if (result == 0) {
						JOptionPane.showMessageDialog(null,
								"Not deleted! Error occurred!", "WARNING",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null,
								"Nothing to delete", "WARNING",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					//Not confirmed delete, do nothing!
				}
			} else if((JButton)e.getSource() == modify){
				ArrayList<String> set = new ArrayList<String>();
				for(int i =0;i<modifications.length;i++){
					String text = modifications[i].getText().replaceAll("[^a-zA-Z0-9/.-]|--", "");
					//String text = modifications[i].getText();
					if(!text.equals("")){
						set.add(columns[i]+" = '"+text+"'");
					}
				}
				int result = db.modify(relation.getSelectedItem().toString(), where.toArray(new String[0]), set.toArray(new String[0]));
				if (result > 0) {
					JOptionPane
							.showMessageDialog(getContentPane(),
									"Successfully modified "
											+ result
											+ " entries from "
											+ relation.getSelectedItem()
													.toString());
					reset();
				} else if (result == 0) {
					JOptionPane.showMessageDialog(null,
							"Nothing modified! Error occurred!", "WARNING",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (result == -2) {
					JOptionPane.showMessageDialog(null,
							"Nothing modified as no parameter was given!", "WARNING",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,
							"An error occoured!\n\n"
							+ "Please remember that only these types of values are admitted:\n"
							+ "- Dates in the form 'YYYY-MM-DD'\n"
							+ "- Integers in the form '123'\n"
							+ "- Floating point values in the form '12.3'\n"
							+ "- Alphanumerical literals in the form 'Abc12.3'\n\n"
							+ "Also note that you cannot change key values for more than one entry!", "WARNING",
							JOptionPane.ERROR_MESSAGE);
				}
				//Save has been pressed
			}
		}
		
	}
	private class OptionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			where = new ArrayList<String>();
			for(int i=0; i<constrains.length; i++){
				if(!constrains[i].getSelectedItem().toString().equals("ALL")){
					where.add(columns[i]+"='"+constrains[i].getSelectedItem().toString()+"'");
				}
				
			}
			HashMap<String, ArrayList<String>> query = db.get("*", relation.getSelectedItem().toString(),where.toArray(new String[0]));
			
			matchTable = new JTable(db.convert(query), query.keySet().toArray()) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			JScrollPane scrollPane = new JScrollPane(matchTable);
			scrollPane.setPreferredSize(new Dimension(720, 170));
			matchTable.setFillsViewportHeight(true);

			tablePanel.removeAll();
			tablePanel.add(scrollPane);
			tablePanel.updateUI();
		}
		
	}
}
