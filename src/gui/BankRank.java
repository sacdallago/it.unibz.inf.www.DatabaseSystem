package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import utilities.Utilities;
import core.Database;

public class BankRank extends JInternalFrame {
	private JPanel middlePanel;

	public BankRank(final Database db, String what) {
		super(what+" Ranking", false, // resizable
				true, // closable
				false, // maximizable
				true);// iconifiable
		setVisible(true);
		middlePanel = new JPanel();
		middlePanel.setPreferredSize(new Dimension(620, 220));
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		// ///////////////////////////////////////////////TOP

		if (what.equals("Banks")) {
			TreeMap<String, LinkedList<String>> query = db.getBanksRanking();
			try {
				Object[][] table = new Object[query.size()][4];
				int i = 0;
				for (String key : query.keySet()) {
					LinkedList<String> t = query.get(key);
					table[i][0] = t.get(0);
					table[i][1] = t.get(1);
					table[i][2] = t.get(2);
					table[i][3] = t.get(3);
					i++;
				}
				JTable matchTable = new JTable(table, new Object[] { "Rating",
						"Brand Name", "Market Code", "Open Accounts" }) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				JScrollPane scrollPane = new JScrollPane(matchTable);
				scrollPane.setPreferredSize(new Dimension(600, 170));
				matchTable.setFillsViewportHeight(true);
				middlePanel.removeAll();
				middlePanel.add(Utilities.drawCompanyRankBarChart(query));
				middlePanel.add(scrollPane);
				middlePanel.updateUI();
			} catch (Exception ex) {
				System.out
						.println("Wohops! Check currencies and conversions!!!!");
				ex.printStackTrace();
			}
		} else if (what.equals("Non-Banks")) {
			TreeMap<String, LinkedList<String>> query = db.getNonBanksRanking();
			try {
				Object[][] table = new Object[query.size()][3];
				int i = 0;
				for (String key : query.keySet()) {
					LinkedList<String> t = query.get(key);
					table[i][0] = t.get(0);
					table[i][1] = t.get(1);
					table[i][2] = t.get(2);
					i++;
				}
				JTable matchTable = new JTable(table, new Object[] { "Rating",
						"Brand Name", "Market Code" }) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				JScrollPane scrollPane = new JScrollPane(matchTable);
				scrollPane.setPreferredSize(new Dimension(600, 170));
				matchTable.setFillsViewportHeight(true);
				middlePanel.removeAll();
				middlePanel.add(Utilities.drawCompanyRankBarChart(query));
				middlePanel.add(scrollPane);
				middlePanel.updateUI();
			} catch (Exception ex) {
				System.out
						.println("Wohops! Check currencies and conversions!!!!");
				ex.printStackTrace();
			}
		} else {
			TreeMap<String, LinkedList<String>> query = db
					.getCompaniesRanking();
			try {
				Object[][] table = new Object[query.size()][3];
				int i = 0;
				for (String key : query.keySet()) {
					LinkedList<String> t = query.get(key);
					table[i][0] = t.get(0);
					table[i][1] = t.get(1);
					table[i][2] = t.get(2);
					i++;
				}
				JTable matchTable = new JTable(table, new Object[] { "Rating",
						"Brand Name", "Market Code" }) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				JScrollPane scrollPane = new JScrollPane(matchTable);
				scrollPane.setPreferredSize(new Dimension(600, 170));
				matchTable.setFillsViewportHeight(true);
				middlePanel.removeAll();
				middlePanel.add(Utilities.drawCompanyRankBarChart(query));
				middlePanel.add(scrollPane);
				middlePanel.updateUI();
			} catch (Exception ex) {
				System.out
						.println("Wohops! Check currencies and conversions!!!!");
				ex.printStackTrace();
			}
		}
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setBounds(0, 0, 790, 700);
		add(middlePanel);
	}
	
}
