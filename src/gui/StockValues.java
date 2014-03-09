package gui;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.TableColumn;

import core.Container;

public class StockValues extends JInternalFrame {
	private JPanel panel = new JPanel();
	private Container con;
	private String[] columnNames = {
			"Time",
			"Market code",
			"Title Number",
			"Current Value",
			"Currency"
			};

	public StockValues(Container con) {
		super("Stock Values", false, // resizable
				false, // closable
				false, // maximizable
				false);// iconifiable
		setVisible(true);
		this.con = con;
		//////////////////////////////////////////////
		refresh();
		//////////////////////////////////////////////

		panel.setSize(getSize());
		setBounds(0, 0, 575, 280);
		add(panel);
	}

	private void refresh() {
		Object[][] data = new Object[con.getStockValues().size()][5];
		for (int i = 0; i < con.getStockValues().size(); i++) {
			data[i][0] = con.getStockValues().get(i).getTime();
			data[i][1] = con.getStockValues().get(i).getMarket_code();
			data[i][2] = con.getStockValues().get(i).getTitle_number();
			data[i][3] = con.getStockValues().get(i).getCurrent_value();
			data[i][4] = con.getStockValues().get(i).getCurrency();
		}

		JTable table = new JTable(data, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200));
		table.setFillsViewportHeight(true);
		// table.setBounds(10, 10, 600, 600);
		
		panel.add(scrollPane);
		panel.updateUI();
	}
}
