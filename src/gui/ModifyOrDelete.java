package gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import core.Database;

public class ModifyOrDelete extends JInternalFrame {
	private JPanel panel = new JPanel();
	public ModifyOrDelete(String table, String attributes, String values, Database db){
		super("Modify " + values, false, // resizable
				false, // closable
				false, // maximizable
				false);// iconifiable
		setVisible(true);
		
		attributes = attributes.replaceAll(" ", "");
		String[] columns = attributes.split(",");
		values = values.replaceAll(" ", "");
		String[] condition = values.split(",");
		String[] where = new String[condition.length];
		if(condition.length != columns.length){
			JOptionPane.showMessageDialog(null, "Damn, something went wrong!");
		}
		for(int i = 0 ; i<condition.length;i++){
			where[i] = columns[i]+"='"+condition[i]+"'";
		}
		
		HashMap<String,ArrayList<String>> query = db.get("*", table, where);
		
		JTable t = new JTable(db.convert(query), query.keySet().toArray()) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane scrollPane = new JScrollPane(t);
		scrollPane.setPreferredSize(new Dimension(720, 200));
		t.setFillsViewportHeight(true);
		// table.setBounds(10, 10, 600, 600);
		
		panel.add(scrollPane);
		panel.updateUI();
		
		panel.setSize(getSize());
		setBounds(0, 0, 800, 280);
		add(panel);
	}
}
