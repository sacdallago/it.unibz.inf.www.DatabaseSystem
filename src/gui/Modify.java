package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import core.Database;

public class Modify extends JInternalFrame {
	private JPanel panel = new JPanel();
	private JComboBox one, two;
	private JTable t;
	private Database db;

	public Modify(HashMap<String, String> schema, Database db) {
		super("Modify", false, // resizable
				false, // closable
				false, // maximizable
				false);// iconifiable
		setVisible(true);
		this.db = db;

		final Database inner = db;
		final HashMap<String, String> innerMap = schema;

		one = new JComboBox(schema.keySet().toArray());
		two = new JComboBox();

		final JPanel upper = new JPanel();
		upper.setLayout(new GridLayout(0, 4));
		upper.add(one);
		upper.add(two);

		one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashMap<String, ArrayList<String>> query = inner.get(innerMap
						.get(one.getSelectedItem().toString()), one
						.getSelectedItem().toString());
				two = new JComboBox(inner.getAsString(query).toArray());
				two.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						refresh(one.getSelectedItem().toString(),
								innerMap.get(one.getSelectedItem().toString()),
								two.getSelectedItem().toString());
					}
				});
				upper.remove(1);
				upper.add(two);
				upper.updateUI();
			}
		});

		t = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane scrollPane = new JScrollPane(t);
		scrollPane.setPreferredSize(new Dimension(720, 200));
		t.setFillsViewportHeight(true);

		panel.add(scrollPane);

		panel.setSize(getSize());
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setBounds(0, 0, 800, 300);
		add(upper);
		add(panel);
	}

	private void refresh(String table, String attributes, String values) {
		attributes = attributes.replaceAll(" ", "");
		String[] columns = attributes.split(",");
		values = values.replaceAll(" ", "");
		String[] condition = values.split(",");
		String[] where = new String[condition.length];
		if (condition.length != columns.length) {
			JOptionPane.showMessageDialog(null, "Damn, something went wrong!");
			return;
		}
		for (int i = 0; i < condition.length; i++) {
			where[i] = columns[i] + "='" + condition[i] + "'";
		}

		HashMap<String, ArrayList<String>> query = db.get("*", table, where);

		t = new JTable(db.convert(query), query.keySet().toArray()) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane scrollPane = new JScrollPane(t);
		scrollPane.setPreferredSize(new Dimension(720, 200));
		t.setFillsViewportHeight(true);

		panel.remove(0);
		panel.add(scrollPane);

		panel.updateUI();
	}
}
