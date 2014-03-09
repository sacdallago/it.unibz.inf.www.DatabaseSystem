package gui;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.TableColumn;

import core.Container;

public class Companies extends JInternalFrame {
	private JPanel panel = new JPanel();
	private Container con;
	private String[] columnNames = {
			"Market code",
			"Brand",
			"Rating",
			"Capital in B",
			"Currency",
			};

	public Companies(Container con) {
		super("Companies", false, // resizable
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

	private void refresh() {
		Object[][] data = new Object[con.getCompanies().size()][5];
		for (int i = 0; i < con.getCompanies().size(); i++) {
			data[i][0] = con.getCompanies().get(i).getMarket_code();
			data[i][1] = con.getCompanies().get(i).getBrand_name();
			data[i][2] = con.getCompanies().get(i).getRating();
			data[i][3] = con.getCompanies().get(i).getCapital();
			data[i][4] = con.getCompanies().get(i).getCurrency();
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
		
		panel.add(scrollPane);
		panel.updateUI();
	}
}
