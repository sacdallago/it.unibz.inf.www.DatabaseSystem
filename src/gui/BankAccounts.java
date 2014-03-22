package gui;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.TableColumn;

import core.Container;

public class BankAccounts extends JInternalFrame {
	private JPanel panel = new JPanel();
	private Container con;
	private String[] columnNames = {
			"Relation number",
			"IBAN",
			"BIC",
			"Type of account",
			"Balance",
			"Account currency",
			"Local currency",
			"Bank market code"
			};

	public BankAccounts(Container con) {
		super("Bank Accounts", false, // resizable
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
		con.setBankAccounts();
		Object[][] data = new Object[con.getBankAccounts().size()][8];
		for (int i = 0; i < con.getBankAccounts().size(); i++) {
			data[i][0] = con.getBankAccounts().get(i).getRelation_number();
			data[i][1] = con.getBankAccounts().get(i).getIban();
			data[i][2] = con.getBankAccounts().get(i).getBic();
			data[i][3] = con.getBankAccounts().get(i).getType();
			data[i][4] = con.getBankAccounts().get(i).getBalance();
			data[i][5] = con.getBankAccounts().get(i).getAccount_currency();
			data[i][6] = con.getBankAccounts().get(i).getLocal_currency();
			data[i][7] = con.getBankAccounts().get(i).getMarket_code();
		}

		JTable table = new JTable(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(720, 200));
		table.setFillsViewportHeight(true);
		// table.setBounds(10, 10, 600, 600);
		TableColumn column = null;
		for (int i = 0; i < 8; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(50); // third column is bigger
			} else if (i == 1) {
				column.setPreferredWidth(220);
			} else {
				column.setPreferredWidth(50);
			}
		}
		panel.removeAll();
		panel.add(scrollPane);
		panel.updateUI();
	}
}
