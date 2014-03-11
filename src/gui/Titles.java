package gui;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.TableColumn;

import core.Container;

public class Titles extends JInternalFrame {
	private JPanel panel = new JPanel();
	private Container con;
	private String[] columnNames = {
			"Title number",
			"Market code",
			"IBAN",
			"BIC",
			"Broker Number",
			"Created day",
			"Initial Value",
			"Currency"
			};

	public Titles(Container con) {
		super("Titles", false, // resizable
				false, // closable
				false, // maximizable
				false);// iconifiable
		setVisible(true);
		this.con = con;
		//////////////////////////////////////////////
		refresh();
		//////////////////////////////////////////////

		panel.setSize(getSize());
		setBounds(0, 0, 800, 280);
		add(panel);
	}

	public void refresh() {
		con.setTitles();
		Object[][] data = new Object[con.getTitles().size()][8];
		for (int i = 0; i < con.getTitles().size(); i++) {
			data[i][0] = con.getTitles().get(i).getTitle_number();
			data[i][1] = con.getTitles().get(i).getMarket_code();
			data[i][2] = con.getTitles().get(i).getIban();
			data[i][3] = con.getTitles().get(i).getBic();
			data[i][4] = con.getTitles().get(i).getBroker_number() == -1 ? "NONE" : con.getTitles().get(i).getBroker_number();
			data[i][5] = con.getTitles().get(i).getCreated_day();
			data[i][6] = con.getTitles().get(i).getInitial_value();
			data[i][7] = con.getTitles().get(i).getInitial_value_currency();
		}

		JTable table = new JTable(data, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(720, 200));
		table.setFillsViewportHeight(true);
		// table.setBounds(10, 10, 600, 600);
		panel.removeAll();
		panel.add(scrollPane);
		panel.updateUI();
	}
}
