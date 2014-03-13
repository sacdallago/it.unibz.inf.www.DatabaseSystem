package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import utilities.Timer;
import core.Container;
import core.Database;

public class Tester2 {
	private static JMenuItem accountTable, brokerTable, companyTable, stockTable, titleTable;
	private static JMenuItem newAccount, newBroker, newCompany, newStock, newTitle;
	private static JMenuItem createSchema, destroySchema, clearAllData, loadData;
	private static JMenuItem modify, delete;
    private static MyFrame frame;
    private static JDesktopPane desktopFrame;
    private static Database db;
    private static Container con;
    private static HashMap<String, String> schema;
    
    private static HashMap<String, JInternalFrame> tables = new HashMap<String, JInternalFrame>();

    public static void main(String[] args) {
    	final ArrayList<String> loginInfo = login(frame);
    	
        db = new Database(loginInfo.get(0), loginInfo.get(1), loginInfo.get(2), loginInfo.get(3));
        con = new Container(db);
        
    	schema = new HashMap<String,String>();
    	
    	schema.put("company", "market_code");
    	schema.put("bank_account","bic,iban");
    	schema.put("broker", "broker_number");
    	schema.put("title", "title_number,market_code");
    	schema.put("stock_value", "time,title_number,market_code");
    	
        JMenuBar bar = new JMenuBar();
        
        JMenu mainMenu = new JMenu("Menu");
        JMenu tablesMenu = new JMenu("Tables");
        JMenu newMenu = new JMenu("New");
        JMenu databaseMenu = new JMenu("Database");
        
        bar.add(mainMenu);
        bar.add(databaseMenu);
        mainMenu.add(newMenu);
        bar.add(tablesMenu);
        
        
        desktopFrame = new JDesktopPane();
        
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
        
        Listener listen = new Listener();
        
        /////////TABLES
        accountTable = new JMenuItem("Show Accounts");
        tablesMenu.add(accountTable);
        accountTable.addActionListener(listen);

        brokerTable = new JMenuItem("Show Brokers");
        tablesMenu.add(brokerTable);
        brokerTable.addActionListener(listen);
        
        companyTable = new JMenuItem("Show Companies");
        tablesMenu.add(companyTable);
        companyTable.addActionListener(listen);
        
        stockTable = new JMenuItem("Show Stock Values");
        tablesMenu.add(stockTable);
        stockTable.addActionListener(listen);
        
        titleTable = new JMenuItem("Show Titles");
        tablesMenu.add(titleTable);
        titleTable.addActionListener(listen);
        
        tablesMenu.addSeparator();
        
        final JMenuItem lastRefresh = new JMenuItem("Last Refresh: Never");
        lastRefresh.setEnabled(false);
        
        final JMenuItem autoRefreshMenu = new JMenuItem("Set autorefresh ON");
        tablesMenu.add(autoRefreshMenu);
        autoRefreshMenu.addActionListener(new ActionListener(){
        	boolean autoRefreshCheck = false;
        	Thread autosync;
        	
			public void actionPerformed(ActionEvent e) {
				if(autoRefreshCheck){
					autosync.stop();
					autoRefreshMenu.setText("Set autorefresh ON");
					autoRefreshCheck = false;
				} else {
					autosync = new Thread(){
		            	public void run(){
		            		while(true){
		            			try {
			    					Thread.sleep(30000);
			    					((BankAccounts) tables.get("accounts")).refresh();
			    					((Brokers) tables.get("brokers")).refresh();
			    					((Companies) tables.get("companies")).refresh();
			    					((StockValues) tables.get("stockvalues")).refresh();
			    					((Titles) tables.get("titles")).refresh();
			    					lastRefresh.setText("Last Refresh: "+Timer.getTime());
			    				} catch (InterruptedException e) {
			    				}
		            		}
		            	}
		            };
		            autosync.start();
					autoRefreshMenu.setText("Set autorefresh OFF");
					autoRefreshCheck = true;
				}
			}
        });
        
        final JMenuItem manualRefresh = new JMenuItem("Refresh Tables");
        tablesMenu.add(manualRefresh);
        manualRefresh.addActionListener(new ActionListener(){
        	
			public void actionPerformed(ActionEvent e) {
				((BankAccounts) tables.get("accounts")).refresh();
				((Brokers) tables.get("brokers")).refresh();
				((Companies) tables.get("companies")).refresh();
				((StockValues) tables.get("stockvalues")).refresh();
				((Titles) tables.get("titles")).refresh();
				lastRefresh.setText("Last Refresh: "+Timer.getTime());
			}
        });
        
        tablesMenu.add(lastRefresh);
        
        /////////INSERT INTO DB MENU
        newAccount = new JMenuItem("Insert New Account");
        newMenu.add(newAccount);
        newAccount.addActionListener(listen);
        
        newBroker = new JMenuItem("Insert New Broker");
        newMenu.add(newBroker);
        newBroker.addActionListener(listen);
        
        newCompany = new JMenuItem("Insert New Company");
        newMenu.add(newCompany);
        newCompany.addActionListener(listen);
        
        newStock = new JMenuItem("Insert New Stock Value");
        newMenu.add(newStock);
        newStock.addActionListener(listen);
        
        newTitle = new JMenuItem("Insert New Title");
        newMenu.add(newTitle);
        newTitle.addActionListener(listen);
        
        /////////DB MENU
        final JMenuItem advanced = new JMenuItem("Enable Database Operations");
        databaseMenu.add(advanced);
        advanced.addActionListener(new ActionListener(){
        	boolean check = false;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(check){
					createSchema.setEnabled(false);
					destroySchema.setEnabled(false);
					loadData.setEnabled(false);
					clearAllData.setEnabled(false);
					check = false;
					advanced.setText("Enable Database Operations");
				} else {
					int i = JOptionPane.showConfirmDialog(frame, "Are you sure you want to continue?\n\n"
							+ "If you are not aware of what you are doing, "
							+ "this can be very harmful to the data stored in the Database\n"
							+ "and cause errors in this program!","Warning",JOptionPane.YES_NO_OPTION);
					if(i==0){
						createSchema.setEnabled(true);
						destroySchema.setEnabled(true);
						loadData.setEnabled(true);
						clearAllData.setEnabled(true);
						check = true;
						advanced.setText("Disable Database Operations");
					}
				}
			}
        });
        databaseMenu.addSeparator();
        
        createSchema = new JMenuItem("Build schema");
        databaseMenu.add(createSchema);
        createSchema.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				db.createSchema();
			}
        });
        createSchema.setEnabled(false);
        
        destroySchema = new JMenuItem("Destroy schema");
        databaseMenu.add(destroySchema);
        destroySchema.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				db.deleteSchema();
			}
        });
        destroySchema.setEnabled(false);
        
        loadData = new JMenuItem("Load fake data");
        databaseMenu.add(loadData);
        loadData.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				db.loadMockData();;
			}
        });
        loadData.setEnabled(false);
        
        
        clearAllData = new JMenuItem("Clear all data");
        databaseMenu.add(clearAllData);
        clearAllData.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				db.clearAllData();
			}
        });
        clearAllData.setEnabled(false);
        
        /////////MAIN
        modify = new JMenuItem("Modify");
        mainMenu.add(modify);
        modify.addActionListener(listen);
        
        delete = new JMenuItem("Delete");
        mainMenu.add(delete);
        delete.addActionListener(listen);

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
        	if (modify.isArmed()) {
        		desktopFrame.add(new Modify(schema,db));
            }
        	if (delete.isArmed()) {
        		desktopFrame.add(new Delete(schema,db));
            }
        }
    }
    
    private static class MyFrame extends JFrame {
    	public MyFrame() {
    		super();
            addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                	close(); //Close connection when exiting!
                    System.exit(0);
                }
            });
        }
    }

    private static ArrayList<String> login(JFrame frame) {
    	ArrayList<String> logininformation = new ArrayList<String>();

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(4, 1, 2, 2));
        label.add(new JLabel("Database Address", SwingConstants.RIGHT));
        label.add(new JLabel("Database name", SwingConstants.RIGHT));
        label.add(new JLabel("Username", SwingConstants.RIGHT));
        label.add(new JLabel("Password", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(4, 1, 2, 2));
        JTextField url = new JTextField("172.16.9.182");
        controls.add(url);
        JTextField dbname = new JTextField("db");
        controls.add(dbname);
        JTextField username = new JTextField("sacdallago");
        controls.add(username);
        JPasswordField password = new JPasswordField("ciaociao3");
        controls.add(password);
        controls.setPreferredSize(new Dimension(200, 100));
        panel.add(controls, BorderLayout.CENTER);

        if(JOptionPane.showConfirmDialog(frame, panel, "Login", JOptionPane.OK_CANCEL_OPTION) != 0){
        	System.exit(0);
        }

        logininformation.add(url.getText());
        logininformation.add(dbname.getText());
        logininformation.add(username.getText());
        logininformation.add(new String(password.getPassword()));
        return logininformation;
    }
}
