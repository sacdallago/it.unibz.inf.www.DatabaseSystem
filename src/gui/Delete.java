package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
	private String[] where, value, columns;
	private String order;

	public Delete(HashMap<String, String> schema, final Database db) {
		super("Delete", false, // resizable
				true, // closable
				false, // maximizable
				true);// iconifiable
		setVisible(true);
		this.db = db;
		// ///////////////////////////////////////////////TOP
		relation = new JComboBox(schema.keySet().toArray());
		relation.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxPanel.removeAll();
				HashMap<String, ArrayList<String>> query = db.get("*", relation.getSelectedItem().toString());
				columns = query.keySet().toArray(new String[0]);
				constrains = new JComboBox[columns.length];
				comboBoxPanel.setLayout(new GridLayout(1,columns.length));
				int i = 0;
				for(String column : columns){
					constrains[i] = new JComboBox(new LinkedHashSet(query.get(column)).toArray());
					comboBoxPanel.add(constrains[i]);
					i++;
				}
				comboBoxPanel.updateUI();
				middlePanel.updateUI();
			}
		});
		
		delete = new JButton("Delete");
		delete.setEnabled(false);
		delete.addActionListener(new Listener());

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
		scrollPane.setPreferredSize(new Dimension(720, 50));
		matchTable.setFillsViewportHeight(true);
		comboBoxPanel.setPreferredSize(new Dimension(720, 50));
		middlePanel.add(scrollPane);
		middlePanel.add(comboBoxPanel);
		//middlePanel.setSize(getSize());
		middlePanel.setPreferredSize(new Dimension(720, 100));
		// ///////////////////////////////////////////////MIDDLE

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setBounds(0, 0, 850, 200);
		add(upperPanel);
		add(middlePanel);
	}
	
	private void reset(){
		
		matchTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(matchTable);
		scrollPane.setPreferredSize(new Dimension(720, 50));
		matchTable.setFillsViewportHeight(true);
		middlePanel.remove(0);
		comboBoxPanel.removeAll();
		middlePanel.add(scrollPane);
		
		delete.setEnabled(false);
		
		upperPanel.updateUI();
		middlePanel.updateUI();
	}

	private void refresh() {
		/*
		attributes = attributes.replaceAll(" ", "");
		String[] columns = attributes.split(",");
		values = values.replaceAll(" ", "");
		String[] condition = values.split(",");
		where = new String[condition.length];
		for (int i = 0; i < condition.length; i++) {
			where[i] = columns[i] + "='" + condition[i] + "'";
		}

		HashMap<String, ArrayList<String>> query = db.get("*", table, where);

		matchTable = new JTable(db.convert(query), query.keySet().toArray()) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane scrollPane = new JScrollPane(matchTable);
		scrollPane.setPreferredSize(new Dimension(720, 50));
		matchTable.setFillsViewportHeight(true);

		middlePanel.remove(0);
		middlePanel.add(scrollPane);

		middlePanel.updateUI();
		*/
	}
	
	private class Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(delete == (JButton) e.getSource()){
				int confirm = JOptionPane.showOptionDialog(null,"Are you sure you want to delete this entry?","Confirmation",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,null,null);
				if(confirm == 0){
					if(db.delete(relation.getSelectedItem().toString(), where)){
						System.out.println("Successfully deleted from "+ relation.getSelectedItem().toString());
						reset();
					} else {
						JOptionPane.showMessageDialog(null, "Not deleted! Error occurred!","WARNING", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					
				}
			}
		}
		
	}
}
