package gui;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.TableColumn;

import core.Container;

public class Brokers extends JInternalFrame {
	private JPanel panel = new JPanel();
	private Container con;
	private String[] columnNames = {
			"Broker number",
			"Rating",
			"Bank market code"
			};

	public Brokers(Container con) {
		super("Brokers", false, // resizable
				false, // closable
				false, // maximizable
				false);// iconifiable
		setVisible(true);
		this.con = con;
		//////////////////////////////////////////////
		refresh();
		//////////////////////////////////////////////

		panel.setSize(getSize());
		setBounds(0, 0, 375, 280);
		add(panel);
	}

	public void refresh() {
		con.setBrokers();
		Object[][] data = new Object[con.getBrokers().size()][3];
		for (int i = 0; i < con.getBrokers().size(); i++) {
			data[i][0] = con.getBrokers().get(i).getBroker_number();
			data[i][1] = con.getBrokers().get(i).getRating();
			data[i][2] = con.getBrokers().get(i).getMarket_code();
		}

		JTable table = new JTable(data, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(300, 200));
		table.setFillsViewportHeight(true);
		// table.setBounds(10, 10, 600, 600);
		TableColumn column = null;
		for (int i = 0; i < 3; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 2) {
				column.setPreferredWidth(75);
			} else {
				column.setPreferredWidth(50);
			}
		}
		panel.removeAll();
		panel.add(scrollPane);
		panel.updateUI();
	}
}
