package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
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

import core.Database;

public class Delete extends JInternalFrame {
	private JPanel upperPanel, middlePanel, comboBoxPanel;
	
	private JComboBox relation;
	
	private JComboBox[] constrains;
	
	private JTable matchTable;
	private Database db;
	private JButton delete;
	private ArrayList<String> where;
	private String[] columns;

	public Delete(HashMap<String, String> schema, final Database db) {
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
				HashMap<String, ArrayList<String>> query = db.get("*", relation.getSelectedItem().toString());
				columns = query.keySet().toArray(new String[0]);
				constrains = new JComboBox[columns.length];
				comboBoxPanel.setLayout(new GridLayout(1,columns.length));
				
				matchTable = new JTable(db.convert(query), query.keySet().toArray()) {
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
					comboBoxPanel.add(constrains[i]);
					i++;
				}

				middlePanel.removeAll();
				middlePanel.add(scrollPane);

				comboBoxPanel.updateUI();
				middlePanel.updateUI();
				if(!delete.isEnabled()) delete.setEnabled(true);
			}
		});
		
		delete = new JButton("Delete");
		delete.setEnabled(false);
		delete.addActionListener(new ButtonListener());

		upperPanel = new JPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
		upperPanel.add(relation);
		upperPanel.add(delete);
		// ///////////////////////////////////////////////TOP
		// ///////////////////////////////////////////////MIDDLE
		middlePanel = new JPanel();
		matchTable = new JTable();
		comboBoxPanel = new JPanel();
		//comboBoxPanel.add(new JComboBox());

		JScrollPane scrollPane = new JScrollPane(matchTable);
		scrollPane.setPreferredSize(new Dimension(720, 170));
		matchTable.setFillsViewportHeight(true);
		comboBoxPanel.setPreferredSize(new Dimension(720, 50));
		middlePanel.add(scrollPane);
		//middlePanel.setSize(getSize());
		middlePanel.setPreferredSize(new Dimension(720, 220));
		// ///////////////////////////////////////////////MIDDLE

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setBounds(0, 0, 780, 300);
		add(upperPanel);
		add(middlePanel);
		add(comboBoxPanel);
	}
	
	private void reset(){
		this.dispose();
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showOptionDialog(null,"Are you sure you want to delete this/these entry/ies?","Confirmation",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,null,null);
				if(confirm == 0){
					int result = db.delete(relation.getSelectedItem().toString(), where.toArray(new String[0]));
					if(result > 0){
						JOptionPane.showMessageDialog(getContentPane(), "Successfully deleted "+ result+" entries from "+ relation.getSelectedItem().toString());
						reset();
					} else if (result == 0){
						JOptionPane.showMessageDialog(null, "Not deleted! Error occurred!","WARNING", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Nothing to delete","WARNING", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					
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
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			JScrollPane scrollPane = new JScrollPane(matchTable);
			scrollPane.setPreferredSize(new Dimension(720, 170));
			matchTable.setFillsViewportHeight(true);

			middlePanel.removeAll();
			middlePanel.add(scrollPane);
			middlePanel.updateUI();
		}
		
	}
}
