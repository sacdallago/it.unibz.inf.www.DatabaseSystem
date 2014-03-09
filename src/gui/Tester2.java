package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import core.Container;
import core.Database;

public class Tester2 {
	private static JMenuItem accountTable, brokerTable, companyTable, stockTable, titleTable;
	private static JMenuItem newAccount, newBroker, newCompany, newStock, newTitle;
	private static JMenuItem delete;
    private static MyFrame frame;
    private static JDesktopPane desktopFrame;
    private static Database db;
    private static Container con;
    private static HashMap<String, String> schema;
    
    private static HashMap<String, JInternalFrame> tables = new HashMap<String, JInternalFrame>();

    public static void main(String[] args) {
    	schema = new HashMap<String,String>();
    	
    	schema.put("company", "market_code");
    	schema.put("bank_account", "iban,bic");
    	schema.put("broker", "broker_number");
    	schema.put("title", "title_number,market_code");
    	schema.put("stock_value", "time,title_number,market_code");
    	
        JMenuBar bar = new JMenuBar();
        
        JMenu mainMenu = new JMenu("Menu");
        JMenu tablesMenu = new JMenu("Tables");
        JMenu newMenu = new JMenu("New");
        
        bar.add(mainMenu);
        mainMenu.add(newMenu);
        bar.add(tablesMenu);
        
        db = new Database("172.16.9.182", "db", "sacdallago", "ciaociao3");
        con = new Container(db);
        
        desktopFrame = new JDesktopPane();
        
        /////////TABLES
        accountTable = new JMenuItem("Show Accounts");
        tablesMenu.add(accountTable);
        accountTable.addActionListener(new Listener());

        brokerTable = new JMenuItem("Show Brokers");
        tablesMenu.add(brokerTable);
        brokerTable.addActionListener(new Listener());
        
        companyTable = new JMenuItem("Show Companies");
        tablesMenu.add(companyTable);
        companyTable.addActionListener(new Listener());
        
        stockTable = new JMenuItem("Show Stock Values");
        tablesMenu.add(stockTable);
        stockTable.addActionListener(new Listener());
        
        titleTable = new JMenuItem("Show Titles");
        tablesMenu.add(titleTable);
        titleTable.addActionListener(new Listener());
        
        
        /////////INSERT INTO DB
        newAccount = new JMenuItem("Insert New Account");
        newMenu.add(newAccount);
        newAccount.addActionListener(new Listener());
        
        newBroker = new JMenuItem("Insert New Broker");
        newMenu.add(newBroker);
        newBroker.addActionListener(new Listener());
        
        newCompany = new JMenuItem("Insert New Company");
        newMenu.add(newCompany);
        newCompany.addActionListener(new Listener());
        
        newStock = new JMenuItem("Insert New Stock Value");
        newMenu.add(newStock);
        newStock.addActionListener(new Listener());
        
        newTitle = new JMenuItem("Insert New Title");
        newMenu.add(newTitle);
        newTitle.addActionListener(new Listener());
        
        /////////MAIN
        
        delete = new JMenuItem("Delete");
        mainMenu.add(delete);
        delete.addActionListener(new Listener());

        /////////NAMING
        tables.put("accounts",new BankAccounts(con));
        tables.put("brokers",new Brokers(con));
        tables.put("companies",new Companies(con));
        tables.put("stockvalues",new StockValues(con));
        tables.put("titles",new Titles(con));
        
        desktopFrame.add(tables.get("accounts"));
        desktopFrame.add(tables.get("brokers"));
        desktopFrame.add(tables.get("companies"));
        desktopFrame.add(tables.get("stockvalues"));
        desktopFrame.add(tables.get("titles"));
        
        for(JInternalFrame a : tables.values()){
        	a.hide();
        }

        frame = new MyFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(bar);
        frame.setContentPane(desktopFrame);
        frame.pack();
        frame.setBounds(200, 200, 1000, 1000);
        frame.setVisible(true);
        //db.close();
    }
    
    protected static void close(){
    	db.close();
    }
    
    protected static void add(Component a){
    	desktopFrame.add(a);
    }

    private static class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
        	if (accountTable.isArmed()) {
        		if(tables.get("accounts").isVisible()){
        			accountTable.setText("Show Accounts");
        			tables.get("accounts").hide();
        		} else {
        			accountTable.setText("Hide Accounts");
        			tables.get("accounts").show();
        		}
            }
        	if (brokerTable.isArmed()) {
        		if(tables.get("brokers").isVisible()){
        			brokerTable.setText("Show Brokers");
        			tables.get("brokers").hide();
        		} else {
        			brokerTable.setText("Hide Brokers");
        			tables.get("brokers").show();
        		}
            }
        	if (companyTable.isArmed()) {
        		if(tables.get("companies").isVisible()){
        			companyTable.setText("Show Companies");
        			tables.get("companies").hide();
        		} else {
        			companyTable.setText("Hide Companies");
        			tables.get("companies").show();
        		}
            }
        	if (stockTable.isArmed()) {
        		if(tables.get("stockvalues").isVisible()){
        			stockTable.setText("Show Stock Values");
        			tables.get("stockvalues").hide();
        		} else {
        			stockTable.setText("Hide Stock Values");
        			tables.get("stockvalues").show();
        		}
            }
        	if (titleTable.isArmed()) {
        		if(tables.get("titles").isVisible()){
        			titleTable.setText("Show Titles");
        			tables.get("titles").hide();
        		} else {
        			titleTable.setText("Hide Titles");
        			tables.get("titles").show();
        		}
            }
        	if (delete.isArmed()) {
        		desktopFrame.add(new Modify(schema,db));
        		String s = (String)JOptionPane.showInputDialog(
                        frame,
                        "Select the table:",
                        "Table selector",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        schema.keySet().toArray(),
                        null);

        		//If a string was returned, say so.  db.getAsString(schema.get(s), s).toArray()
        		if ((s != null) && (s.length() > 0)) {
        			String q = (String)JOptionPane.showInputDialog(
                            frame,
                            "Select the relation:",
                            "Relation selector",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            db.getAsString(schema.get(s), s).toArray(),
                            null);
        			if ((q != null) && (q.length() > 0)) {
            			desktopFrame.add(new ModifyOrDelete(s,schema.get(s),q,db));
            		}
        		}
            }
        }
    }
}
