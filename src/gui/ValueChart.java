package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import utilities.Utilities;
import core.Database;

public class ValueChart extends JInternalFrame {
	private JPanel upperPanel, middlePanel, upperTwo;
	private JLabel variance;
	private JComboBox marketCode, titleNumber;

	public ValueChart(final Database db) {
		super("Value Chart", false, // resizable
				true, // closable
				false, // maximizable
				true);// iconifiable
		setVisible(true);
		// ///////////////////////////////////////////////TOP
		HashMap<String, ArrayList<String>> query = db.get("market_code",
				"company");
		marketCode = new JComboBox(query.get("market_code").toArray());
		titleNumber = new JComboBox();
		marketCode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<String, ArrayList<String>> query = db
						.get("title_number", "title", "market_code='"
								+ marketCode.getSelectedItem().toString() + "'");
				titleNumber = new JComboBox(query.get("title_number").toArray());
				titleNumber.setPreferredSize(new Dimension(50, 100));
				titleNumber.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							TreeMap<String, Double> query = db.getTitleValues(
									titleNumber.getSelectedItem().toString(),
									marketCode.getSelectedItem().toString(),
									"USD");
							Object[][] table = new Object[query.size()][2];
							int i = 0;
							for (String key : query.keySet()) {
								table[i][0] = key;
								table[i][1] = query.get(key);
								i++;
							}
							JTable matchTable = new JTable(table, new Object[] {"Date", "Value in USD" }) {
								@Override
								public boolean isCellEditable(int row,
										int column) {
									return false;
								}
							};
							JScrollPane scrollPane = new JScrollPane(matchTable);
							scrollPane.setPreferredSize(new Dimension(600, 170));
							matchTable.setFillsViewportHeight(true);
							middlePanel.removeAll();
							middlePanel.add(Utilities.drawCoolLineChart(query));
							middlePanel.add(scrollPane);
							middlePanel.updateUI();
							String s =String.format("%1$.2f", ((double)table[query.size()-1][1]-(double)table[0][1]));
							variance.setText(s+"$");
							upperTwo.updateUI();
						} catch (Exception ex) {
							System.out.println("Wohops! Check currencies and conversions!!!!");
						}
					}
				});
				upperPanel.remove(1);
				upperPanel.add(titleNumber);
				upperPanel.add(variance);
				upperPanel.updateUI();
			}
		});
		
		JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(3, 1, 2, 2));
        label.add(new JLabel("Company", SwingConstants.RIGHT));
        label.add(new JLabel("Title N.", SwingConstants.RIGHT));
        label.add(new JLabel("Variance", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        upperPanel = new JPanel(new GridLayout(3, 1, 2, 2));
        upperPanel.add(marketCode);
        upperPanel.add(titleNumber);
        variance = new JLabel(" ");
        upperTwo = new JPanel();
        upperTwo.add(variance);
        upperPanel.add(variance);
        upperPanel.setPreferredSize(new Dimension(50, 100));
        panel.add(upperPanel, BorderLayout.CENTER);
		// ///////////////////////////////////////////////TOP
		// ///////////////////////////////////////////////MIDDLE
		middlePanel = new JPanel();
		middlePanel.setSize(new Dimension(620, 220));
		// ///////////////////////////////////////////////MIDDLE

		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		setBounds(0, 0, 850, 700);
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(3,1));
		temp.setSize(new Dimension(260, 50));
		temp.setPreferredSize(new Dimension(260,50));
		temp.add(panel);
		temp.add(upperTwo);
		add(temp);
		add(middlePanel);
	}
}
