package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import core.Database;

public class Modify extends JInternalFrame {
	private JPanel middlePanel, bottomPanel;
	private JComboBox one, two;
	private JTable t;
	private Database db;
	private JButton delete, modify;
	private String[] where;
	private final JPanel upper;

	public Modify(HashMap<String, String> schema, Database db) {
		super("Modify or Delete", false, // resizable
				true, // closable
				false, // maximizable
				true);// iconifiable
		setVisible(true);
		this.db = db;
		// ///////////////////////////////////////////////TOP
		final Database inner = db;
		final HashMap<String, String> innerMap = schema;

		one = new JComboBox(schema.keySet().toArray());
		two = new JComboBox();

		upper = new JPanel();
		upper.setLayout(new GridLayout(0, 4));
		upper.add(one);
		upper.add(two);

		one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final HashMap<String, ArrayList<String>> query = inner.get(
						innerMap.get(one.getSelectedItem().toString()), one
								.getSelectedItem().toString());
				two = new JComboBox(inner.getAsString(query).toArray());
				two.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String order = "";
						for (String a : query.keySet()) {
							order += a + ",";
						}
						order = order.substring(0, order.length() - 1);
						refresh(one.getSelectedItem().toString(), order, two
								.getSelectedItem().toString());
						delete.setEnabled(true);
						modify.setEnabled(true);
						bottomPanel.updateUI();
					}
				});
				upper.remove(1);
				upper.add(two);
				upper.updateUI();
				delete.setEnabled(false);
				modify.setEnabled(false);
				bottomPanel.updateUI();
			}
		});
		// ///////////////////////////////////////////////TOP
		// ///////////////////////////////////////////////MIDDLE
		middlePanel = new JPanel();
		t = new JTable();

		JScrollPane scrollPane = new JScrollPane(t);
		scrollPane.setPreferredSize(new Dimension(720, 50));
		t.setFillsViewportHeight(true);
		middlePanel.add(scrollPane);
		middlePanel.setSize(getSize());
		// ///////////////////////////////////////////////MIDDLE
		// ///////////////////////////////////////////////BOTTOM
		bottomPanel = new JPanel();
		delete = new JButton("Delete");
		modify = new JButton("Modify");
		Listener listen = new Listener();
		delete.setEnabled(false);
		modify.setEnabled(false);
		delete.addActionListener(listen);
		modify.addActionListener(listen);
		bottomPanel.add(delete);
		bottomPanel.add(modify);
		// ///////////////////////////////////////////////BOTTOM

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setBounds(0, 0, 800, 200);
		add(upper);
		add(middlePanel);
		add(bottomPanel);
	}
	
	private void reset(){
		
		two = new JComboBox();
		upper.remove(1);
		upper.add(two);
		
		t = new JTable();
		JScrollPane scrollPane = new JScrollPane(t);
		scrollPane.setPreferredSize(new Dimension(720, 50));
		t.setFillsViewportHeight(true);
		middlePanel.remove(0);
		middlePanel.add(scrollPane);
		
		delete.setEnabled(false);
		modify.setEnabled(false);
		
		upper.updateUI();
		middlePanel.updateUI();
		bottomPanel.updateUI();
	}

	private void refresh(String table, String attributes, String values) {
		attributes = attributes.replaceAll(" ", "");
		String[] columns = attributes.split(",");
		values = values.replaceAll(" ", "");
		String[] condition = values.split(",");
		where = new String[condition.length];
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
		scrollPane.setPreferredSize(new Dimension(720, 50));
		t.setFillsViewportHeight(true);

		middlePanel.remove(0);
		middlePanel.add(scrollPane);

		middlePanel.updateUI();
	}
	
	private class Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(delete == (JButton) e.getSource()){
				int confirm = JOptionPane.showOptionDialog(null,"Are you sure you want to delete this entry?","Confirmation",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,null,null);
				if(confirm == 0){
					if(db.delete(one.getSelectedItem().toString(), where)){
						System.out.println("Successfully deleted from "+ one.getSelectedItem().toString());
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
