package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import core.Database;
import types.*;

public class InsertPanels {

	public static DatabaseType insertBankAccount(JFrame frame){
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(8, 1, 2, 2));
        label.add(new JLabel("Relation Number", SwingConstants.RIGHT));
        label.add(new JLabel("IBAN", SwingConstants.RIGHT));
        label.add(new JLabel("BIC/SWIFT", SwingConstants.RIGHT));
        label.add(new JLabel("Type", SwingConstants.RIGHT));
        label.add(new JLabel("Balance", SwingConstants.RIGHT));
        label.add(new JLabel("Account Currency", SwingConstants.RIGHT));
        label.add(new JLabel("Local Currency", SwingConstants.RIGHT));
        label.add(new JLabel("Bank market code", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(8, 1, 2, 2));
        
        JTextField relationF = new JTextField();
        relationF.setToolTipText("Insert the relation, which should be an integer number!");
        TextPrompt relationP = new TextPrompt("12345", relationF);
        relationP.setForeground( Color.RED );
        relationP.changeAlpha(0.5f);
        relationP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(relationF);
        
        JTextField ibanF = new JTextField();
        ibanF.setToolTipText("Insert the IBAN code, which should be alphanumerical");
        TextPrompt ibanP = new TextPrompt("GB30SBVC18523767901629", ibanF);
        ibanP.setForeground( Color.RED );
        ibanP.changeAlpha(0.5f);
        ibanP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(ibanF);
        
        JTextField bicF = new JTextField();
        bicF.setToolTipText("Insert the BIC/SWIFT code, which should be alphanumerical");
        TextPrompt bicP = new TextPrompt("RBS2343X", bicF);
        bicP.setForeground( Color.RED );
        bicP.changeAlpha(0.5f);
        bicP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(bicF);
        
        JTextField typeF = new JTextField();
        typeF.setToolTipText("Insert the type of account, either 'saving' or 'checking'");
        TextPrompt typeP = new TextPrompt("checking", typeF);
        typeP.setForeground( Color.RED );
        typeP.changeAlpha(0.5f);
        typeP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(typeF);
        
        JTextField balanceF = new JTextField();
        balanceF.setToolTipText("Insert the balance of the account as a floating point number. If you have 5647.94eur in your account, type 5647.94");
        TextPrompt balanceP = new TextPrompt("55654.56", balanceF);
        balanceP.setForeground( Color.RED );
        balanceP.changeAlpha(0.5f);
        balanceP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(balanceF);
        
        JTextField account_currencyF = new JTextField();
        account_currencyF.setToolTipText("Insert the currency of the account in three letters. If you have 5647.94eur in your account, type EUR");
        TextPrompt account_currencyP = new TextPrompt("GBP", account_currencyF);
        account_currencyP.setForeground( Color.RED );
        account_currencyP.changeAlpha(0.5f);
        account_currencyP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(account_currencyF);
        
        JTextField local_currencyF = new JTextField();
        local_currencyF.setToolTipText("Type the currency of the state you live in.");
        TextPrompt local_currencyP = new TextPrompt("EUR", local_currencyF);
        local_currencyP.setForeground( Color.RED );
        local_currencyP.changeAlpha(0.5f);
        local_currencyP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(local_currencyF);
        
        JTextField market_codeF = new JTextField();
        market_codeF.setToolTipText("Type the market code of the bank where this account is stored at. Like RT.b");
        TextPrompt market_codeP = new TextPrompt("RBS.L", market_codeF);
        market_codeP.setForeground( Color.RED );
        market_codeP.changeAlpha(0.5f);
        market_codeP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(market_codeF);
        
        
        controls.setPreferredSize(new Dimension(300, 300));
        panel.add(controls, BorderLayout.CENTER);

        while(JOptionPane.showConfirmDialog(frame, panel, "Add", JOptionPane.OK_CANCEL_OPTION) == 0){
        	String relation = relationF.getText();
        	String iban = ibanF.getText().toUpperCase();
        	String bic = bicF.getText().toUpperCase();
        	String type = typeF.getText();
        	String balance = balanceF.getText();
        	String account_currency = account_currencyF.getText().toUpperCase();
        	String local_currency = local_currencyF.getText().toUpperCase();
        	String market_code = market_codeF.getText().toUpperCase();
        	
        	//Checks
        	boolean validData = true;
        	try{
        		Integer.parseInt(relation);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Relation must be integer number!");
        	}
        	try{
        		Double.parseDouble(balance);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Balance must be floating point number!");
        	}
        	if(iban.length()>31 || !iban.matches("[A-z|0-9]+")){
        		JOptionPane.showMessageDialog(frame, "Iban too long or not alphanumerical!");
        		validData = false;
        	}
        	if(bic.length()>11 || !bic.matches("[A-z|0-9]+")){
        		JOptionPane.showMessageDialog(frame, "Bic/swift too long or not alphanumerical!");
        		validData = false;
        	}
        	if(!type.equals("saving") && !type.equals("checking") ){
        		JOptionPane.showMessageDialog(frame, "Type must be saving or checking!");
        		validData = false;
        	}
        	if(account_currency.length()>3 || !account_currency.matches("[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Currency should be 3 char max. and only literals, such as EUR or GBP!");
        		validData = false;
        	}
        	if(local_currency.length()>3 || !local_currency.matches("[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Currency should be 3 char max. and only literals, such as EUR or GBP!");
        		validData = false;
        	}
        	if(market_code.length()>10 || !market_code.matches("[A-z]+|[A-z]+.[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Market code maximum allowed 10 alphabetical characters + the dot (.) sign!");
        		validData = false;
        	}
        	
        	//If entered data is valid, return account type.
        	if(validData){
        		return new BankAccount(Integer.parseInt(relation), iban, bic, type, Double.parseDouble(balance), account_currency, local_currency, market_code);
        	}
        }
        return null;
	}

	public static DatabaseType insertBroker(JFrame frame){
		JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(3, 1, 2, 2));
        label.add(new JLabel("Broker Number", SwingConstants.RIGHT));
        label.add(new JLabel("Rating", SwingConstants.RIGHT));
        label.add(new JLabel("Bank market code", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(3, 1, 2, 2));
        
        JTextField relationF = new JTextField();
        relationF.setToolTipText("Insert the broker's id, which should be an integer number!");
        TextPrompt relationP = new TextPrompt("12345", relationF);
        relationP.setForeground( Color.RED );
        relationP.changeAlpha(0.5f);
        relationP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(relationF);
        
        JTextField ratingF = new JTextField();
        ratingF.setToolTipText("Insert the rating for the Broker, which should be an integer between 0 and 10");
        TextPrompt ratingP = new TextPrompt("7", ratingF);
        ratingP.setForeground( Color.RED );
        ratingP.changeAlpha(0.5f);
        ratingP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(ratingF);
        
        JTextField market_codeF = new JTextField();
        market_codeF.setToolTipText("Type the market code of the bank where this account is stored at. Like RT.b");
        TextPrompt market_codeP = new TextPrompt("RBS.L", market_codeF);
        market_codeP.setForeground( Color.RED );
        market_codeP.changeAlpha(0.5f);
        market_codeP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(market_codeF);
        
        
        controls.setPreferredSize(new Dimension(300, 100));
        panel.add(controls, BorderLayout.CENTER);

        while(JOptionPane.showConfirmDialog(frame, panel, "Add", JOptionPane.OK_CANCEL_OPTION) == 0){
        	String id = relationF.getText();
        	String rating = ratingF.getText();
        	String market_code = market_codeF.getText().toUpperCase();
        	
        	//Checks
        	boolean validData = true;
        	try{
        		Integer.parseInt(id);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Broker id must be integer number!");
        	}
        	try{
        		int i = Integer.parseInt(rating);
        		if(i<0 || i>10) throw new Exception();
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Rating should be integer between 0 and 10!");
        	}
        	if(market_code.length()>10 || !market_code.matches("[A-z]+|[A-z]+.[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Market code maximum allowed 10 alphabetical characters + the dot (.) sign!");
        		validData = false;
        	}
        	
        	//If entered data is valid, return broker type.
        	if(validData){
        		return new Broker(Integer.parseInt(id), Integer.parseInt(rating), market_code);
        	}
        }
        return null;
	}

	public static DatabaseType insertCompany(JFrame frame){
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(5, 1, 2, 2));
        label.add(new JLabel("Company market code", SwingConstants.RIGHT));
        label.add(new JLabel("Brand Name", SwingConstants.RIGHT));
        label.add(new JLabel("Rating", SwingConstants.RIGHT));
        label.add(new JLabel("Capital (in B)", SwingConstants.RIGHT));
        label.add(new JLabel("Currency", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(5, 1, 2, 2));
        
        JTextField market_codeF = new JTextField();
        market_codeF.setToolTipText("Type the market code of the company or bank. Like RT.b");
        TextPrompt market_codeP = new TextPrompt("RBS.L", market_codeF);
        market_codeP.setForeground( Color.RED );
        market_codeP.changeAlpha(0.5f);
        market_codeP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(market_codeF);
        
        JTextField brand_nameF = new JTextField();
        brand_nameF.setToolTipText("Insert the brand name of the company.");
        TextPrompt brand_nameP = new TextPrompt("Lloyd LTD", brand_nameF);
        brand_nameP.setForeground( Color.RED );
        brand_nameP.changeAlpha(0.5f);
        brand_nameP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(brand_nameF);
        
        JTextField ratingF = new JTextField();
        ratingF.setToolTipText("Insert the rating for the Company, which should be an integer between 0 and 10");
        TextPrompt ratingP = new TextPrompt("7", ratingF);
        ratingP.setForeground( Color.RED );
        ratingP.changeAlpha(0.5f);
        ratingP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(ratingF);
        
        JTextField capitalF = new JTextField();
        capitalF.setToolTipText("Insert the capital of the company, as a floating point number in, B.");
        TextPrompt capitalP = new TextPrompt("54.56", capitalF);
        capitalP.setForeground( Color.RED );
        capitalP.changeAlpha(0.5f);
        capitalP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(capitalF);
        
        JTextField currencyF = new JTextField();
        currencyF.setToolTipText("Type the currency of the state you live in.");
        TextPrompt currencyP = new TextPrompt("EUR", currencyF);
        currencyP.setForeground( Color.RED );
        currencyP.changeAlpha(0.5f);
        currencyP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(currencyF);
        
        
        controls.setPreferredSize(new Dimension(300, 180));
        panel.add(controls, BorderLayout.CENTER);

        while(JOptionPane.showConfirmDialog(frame, panel, "Add", JOptionPane.OK_CANCEL_OPTION) == 0){
        	String market_code = market_codeF.getText().toUpperCase();
        	String brand = brand_nameF.getText().toUpperCase();
        	String rating = ratingF.getText().toUpperCase();
        	String capital = capitalF.getText();
        	String currency = currencyF.getText().toUpperCase();
        	
        	
        	//Checks
        	boolean validData = true;
        	try{
        		int i = Integer.parseInt(rating);
        		if(i<0 || i>10) throw new Exception();
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Rating should be integer between 0 and 10!");
        	}
        	try{
        		Double.parseDouble(capital);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Balance must be floating point number!");
        	}
        	if(market_code.length()>10 || !market_code.matches("[A-z]+|[A-z]+.[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Market code maximum allowed 10 alphabetical characters + the dot (.) sign!");
        		validData = false;
        	}
        	if(currency.length()>3 || !currency.matches("[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Currency should be 3 char max. and only literals, such as EUR or GBP!");
        		validData = false;
        	}
        	//If entered data is valid, return company type.
        	if(validData){
        		return new Company(market_code, brand, Integer.parseInt(rating), Double.parseDouble(capital), currency);
        	}
        }
        return null;
	}
	
	public static DatabaseType insertStockValue(JFrame frame){
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(5, 1, 2, 2));
        label.add(new JLabel("Time", SwingConstants.RIGHT));
        label.add(new JLabel("Company market code", SwingConstants.RIGHT));
        label.add(new JLabel("Title number", SwingConstants.RIGHT));
        label.add(new JLabel("Current value", SwingConstants.RIGHT));
        label.add(new JLabel("Currency", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(5, 1, 2, 2));
        
        JTextField timeF = new JTextField();
        timeF.setToolTipText("Insert the time, in the form YYYY-MM-DD!");
        TextPrompt timeP = new TextPrompt("2013-08-20", timeF);
        timeP.setForeground( Color.RED );
        timeP.changeAlpha(0.5f);
        timeP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(timeF);
        
        JTextField market_codeF = new JTextField();
        market_codeF.setToolTipText("Type the market code of the bank where this account is stored at. Like RT.b");
        TextPrompt market_codeP = new TextPrompt("RBS.L", market_codeF);
        market_codeP.setForeground( Color.RED );
        market_codeP.changeAlpha(0.5f);
        market_codeP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(market_codeF);
        
        JTextField titleF = new JTextField();
        titleF.setToolTipText("Insert the title number, which should be an integer number!");
        TextPrompt titleP = new TextPrompt("12345", titleF);
        titleP.setForeground( Color.RED );
        titleP.changeAlpha(0.5f);
        titleP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(titleF);
        
        JTextField valueF = new JTextField();
        valueF.setToolTipText("Insert the value at that time, as a floating point number.");
        TextPrompt valueP = new TextPrompt("54.56", valueF);
        valueP.setForeground( Color.RED );
        valueP.changeAlpha(0.5f);
        valueP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(valueF);
        
        JTextField currencyF = new JTextField();
        currencyF.setToolTipText("Type the currency of the state you live in.");
        TextPrompt currencyP = new TextPrompt("EUR", currencyF);
        currencyP.setForeground( Color.RED );
        currencyP.changeAlpha(0.5f);
        currencyP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(currencyF);
        
        
        controls.setPreferredSize(new Dimension(300, 180));
        panel.add(controls, BorderLayout.CENTER);

        while(JOptionPane.showConfirmDialog(frame, panel, "Add", JOptionPane.OK_CANCEL_OPTION) == 0){
        	String time = timeF.getText();
        	String market_code = market_codeF.getText().toUpperCase();
        	String title = titleF.getText();
        	String value = valueF.getText();
        	String currency = currencyF.getText().toUpperCase();
        	
        	//Checks
        	boolean validData = true;
        	try{
        		Double.parseDouble(value);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Value must be floating point number!");
        	}
        	try{
        		Integer.parseInt(title);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Title number must be integer value!");
        	}
        	if(!time.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")){
        		JOptionPane.showMessageDialog(frame, "Time should be of the format 'YYYY-MM-DD'");
        		validData = false;
        	}
        	if(market_code.length()>10 || !market_code.matches("[A-z]+|[A-z]+.[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Market code maximum allowed 10 alphabetical characters + the dot (.) sign!");
        		validData = false;
        	}
        	if(currency.length()>3 || !currency.matches("[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Currency should be 3 char max. and only literals, such as EUR or GBP!");
        		validData = false;
        	}
        	
        	//If entered data is valid, return account type.
        	if(validData){
        		return new StockValue(time, market_code, Integer.parseInt(title), Double.parseDouble(value), currency);
        	}
        }
        return null;
	}

	public static DatabaseType insertTitle(JFrame frame){
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(8, 1, 2, 2));
        label.add(new JLabel("Title Number", SwingConstants.RIGHT));
        label.add(new JLabel("Market code", SwingConstants.RIGHT));
        label.add(new JLabel("IBAN", SwingConstants.RIGHT));
        label.add(new JLabel("BIC/SWIFT", SwingConstants.RIGHT));
        label.add(new JLabel("Broker number", SwingConstants.RIGHT));
        label.add(new JLabel("Created day", SwingConstants.RIGHT));
        label.add(new JLabel("Value", SwingConstants.RIGHT));
        label.add(new JLabel("Currency", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(8, 1, 2, 2));
        
        JTextField titleF = new JTextField();
        titleF.setToolTipText("Insert the Title number, which should be an integer value!");
        TextPrompt titleP = new TextPrompt("12345", titleF);
        titleP.setForeground( Color.RED );
        titleP.changeAlpha(0.5f);
        titleP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(titleF);
        
        JTextField market_codeF = new JTextField();
        market_codeF.setToolTipText("Type the market code of the company the title belonges to. Like RT.b");
        TextPrompt market_codeP = new TextPrompt("RBS.L", market_codeF);
        market_codeP.setForeground( Color.RED );
        market_codeP.changeAlpha(0.5f);
        market_codeP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(market_codeF);
        
        JTextField ibanF = new JTextField();
        ibanF.setToolTipText("Insert the IBAN code of the account where the title is stored at, which should be alphanumerical");
        TextPrompt ibanP = new TextPrompt("GB30SBVC18523767901629", ibanF);
        ibanP.setForeground( Color.RED );
        ibanP.changeAlpha(0.5f);
        ibanP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(ibanF);
        
        JTextField bicF = new JTextField();
        bicF.setToolTipText("Insert the BIC/SWIFT code of the account where the title is stored at, which should be alphanumerical");
        TextPrompt bicP = new TextPrompt("RBS2343X", bicF);
        bicP.setForeground( Color.RED );
        bicP.changeAlpha(0.5f);
        bicP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(bicF);
        
        JTextField brokerF = new JTextField();
        brokerF.setToolTipText("Insert the broker's id that buyed the title for you, if any.");
        TextPrompt brokerP = new TextPrompt("12345", brokerF);
        brokerP.setForeground( Color.RED );
        brokerP.changeAlpha(0.5f);
        brokerP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(brokerF);
        
        JTextField timeF = new JTextField();
        timeF.setToolTipText("Insert the time at which the title was created, in the form YYYY-MM-DD!");
        TextPrompt timeP = new TextPrompt("2013-08-20", timeF);
        timeP.setForeground( Color.RED );
        timeP.changeAlpha(0.5f);
        timeP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(timeF);
        
        JTextField valueF = new JTextField();
        valueF.setToolTipText("Insert the value at the creation day.");
        TextPrompt valueP = new TextPrompt("55654.56", valueF);
        valueP.setForeground( Color.RED );
        valueP.changeAlpha(0.5f);
        valueP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(valueF);
        
        JTextField currencyF = new JTextField();
        currencyF.setToolTipText("Insert the currency of the account in three letters. If you have 5647.94eur in your account, type EUR");
        TextPrompt currencyP = new TextPrompt("GBP", currencyF);
        currencyP.setForeground( Color.RED );
        currencyP.changeAlpha(0.5f);
        currencyP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(currencyF);
        
        controls.setPreferredSize(new Dimension(300, 300));
        panel.add(controls, BorderLayout.CENTER);

        while(JOptionPane.showConfirmDialog(frame, panel, "Add", JOptionPane.OK_CANCEL_OPTION) == 0){
        	String title = titleF.getText();
        	String market_code = market_codeF.getText().toUpperCase();
        	String iban = ibanF.getText().toUpperCase();
        	String bic = bicF.getText().toUpperCase();
        	String broker = brokerF.getText();
        	String time = timeF.getText();
        	String value = valueF.getText();
        	String currency = currencyF.getText().toUpperCase();
        	
        	//Checks
        	
        	boolean validData = true;
        	boolean hasBroker = false;
        	try{
        		Integer.parseInt(title);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Title number must be integer value!");
        	}
        	if(!broker.equals("")){
        		try{
            		Integer.parseInt(broker);
            		hasBroker = true;
            	} catch (Exception e){
            		validData = false;
            		JOptionPane.showMessageDialog(frame, "Broker number must be integer or empty!");
            	}
        	}
        	try{
        		Double.parseDouble(value);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Value must be floating point number!");
        	}
        	if(iban.length()>31 || !iban.matches("[A-z|0-9]+")){
        		JOptionPane.showMessageDialog(frame, "Iban too long or not alphanumerical!");
        		validData = false;
        	}
        	if(bic.length()>11 || !bic.matches("[A-z|0-9]+")){
        		JOptionPane.showMessageDialog(frame, "Bic/swift too long or not alphanumerical!");
        		validData = false;
        	}
        	if(!time.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")){
        		JOptionPane.showMessageDialog(frame, "Time should be of the format 'YYYY-MM-DD'");
        		validData = false;
        	}
        	if(currency.length()>3 || !currency.matches("[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Currency should be 3 char max. and only literals, such as EUR or GBP!");
        		validData = false;
        	}
        	if(market_code.length()>10 || !market_code.matches("[A-z]+|[A-z]+.[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Market code maximum allowed 10 alphabetical characters + the dot (.) sign!");
        		validData = false;
        	}
        	
        	//If entered data is valid, return account type.
        	if(validData){
        		return new Title(Integer.parseInt(title), market_code, iban, bic, (hasBroker?Integer.parseInt(broker):null), time, Double.parseDouble(value), currency);
        	}
        }
        return null;
	}

	public static DatabaseType insertAutocompleteBankAccount(JFrame frame, Database db){
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(8, 1, 2, 2));
        label.add(new JLabel("Relation Number", SwingConstants.RIGHT));
        label.add(new JLabel("IBAN", SwingConstants.RIGHT));
        label.add(new JLabel("BIC/SWIFT", SwingConstants.RIGHT));
        label.add(new JLabel("Type", SwingConstants.RIGHT));
        label.add(new JLabel("Balance", SwingConstants.RIGHT));
        label.add(new JLabel("Account Currency", SwingConstants.RIGHT));
        label.add(new JLabel("Local Currency", SwingConstants.RIGHT));
        label.add(new JLabel("Bank market code", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(8, 1, 2, 2));
        
        JTextField relationF = new JTextField();
        relationF.setToolTipText("Insert the relation, which should be an integer number!");
        TextPrompt relationP = new TextPrompt("12345", relationF);
        relationP.setForeground( Color.RED );
        relationP.changeAlpha(0.5f);
        relationP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(relationF);
        
        JTextField ibanF = new JTextField();
        ibanF.setToolTipText("Insert the IBAN code, which should be alphanumerical");
        TextPrompt ibanP = new TextPrompt("GB30SBVC18523767901629", ibanF);
        ibanP.setForeground( Color.RED );
        ibanP.changeAlpha(0.5f);
        ibanP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(ibanF);
        
        JTextField bicF = new JTextField();
        bicF.setToolTipText("Insert the BIC/SWIFT code, which should be alphanumerical");
        TextPrompt bicP = new TextPrompt("RBS2343X", bicF);
        bicP.setForeground( Color.RED );
        bicP.changeAlpha(0.5f);
        bicP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(bicF);
        
        JTextField typeF = new JTextField();
        typeF.setToolTipText("Insert the type of account, either 'saving' or 'checking'");
        TextPrompt typeP = new TextPrompt("checking", typeF);
        typeP.setForeground( Color.RED );
        typeP.changeAlpha(0.5f);
        typeP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(typeF);
        
        JTextField balanceF = new JTextField();
        balanceF.setToolTipText("Insert the balance of the account as a floating point number. If you have 5647.94eur in your account, type 5647.94");
        TextPrompt balanceP = new TextPrompt("55654.56", balanceF);
        balanceP.setForeground( Color.RED );
        balanceP.changeAlpha(0.5f);
        balanceP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(balanceF);
        
        JTextField account_currencyF = new JTextField();
        account_currencyF.setToolTipText("Insert the currency of the account in three letters. If you have 5647.94eur in your account, type EUR");
        TextPrompt account_currencyP = new TextPrompt("GBP", account_currencyF);
        account_currencyP.setForeground( Color.RED );
        account_currencyP.changeAlpha(0.5f);
        account_currencyP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(account_currencyF);
        
        JTextField local_currencyF = new JTextField();
        local_currencyF.setToolTipText("Type the currency of the state you live in.");
        TextPrompt local_currencyP = new TextPrompt("EUR", local_currencyF);
        local_currencyP.setForeground( Color.RED );
        local_currencyP.changeAlpha(0.5f);
        local_currencyP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(local_currencyF);
        
        JComboBox<String> market_codeF = new JComboBox<String>(db.get("market_code", "company").get("market_code").toArray(new String[0]));
        controls.add(market_codeF);
        
        
        controls.setPreferredSize(new Dimension(300, 300));
        panel.add(controls, BorderLayout.CENTER);

        while(JOptionPane.showConfirmDialog(frame, panel, "Add", JOptionPane.OK_CANCEL_OPTION) == 0){
        	String relation = relationF.getText();
        	String iban = ibanF.getText().toUpperCase();
        	String bic = bicF.getText().toUpperCase();
        	String type = typeF.getText();
        	String balance = balanceF.getText();
        	String account_currency = account_currencyF.getText().toUpperCase();
        	String local_currency = local_currencyF.getText().toUpperCase();
        	String market_code = market_codeF.getSelectedItem().toString();
        	
        	//Checks
        	boolean validData = true;
        	try{
        		Integer.parseInt(relation);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Relation must be integer number!");
        	}
        	try{
        		Double.parseDouble(balance);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Balance must be floating point number!");
        	}
        	if(iban.length()>31 || !iban.matches("[A-z|0-9]+")){
        		JOptionPane.showMessageDialog(frame, "Iban too long or not alphanumerical!");
        		validData = false;
        	}
        	if(bic.length()>11 || !bic.matches("[A-z|0-9]+")){
        		JOptionPane.showMessageDialog(frame, "Bic/swift too long or not alphanumerical!");
        		validData = false;
        	}
        	if(!type.equals("saving") && !type.equals("checking") ){
        		JOptionPane.showMessageDialog(frame, "Type must be saving or checking!");
        		validData = false;
        	}
        	if(account_currency.length()>3 || !account_currency.matches("[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Currency should be 3 char max. and only literals, such as EUR or GBP!");
        		validData = false;
        	}
        	if(local_currency.length()>3 || !local_currency.matches("[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Currency should be 3 char max. and only literals, such as EUR or GBP!");
        		validData = false;
        	}
        	
        	//If entered data is valid, return account type.
        	if(validData){
        		return new BankAccount(Integer.parseInt(relation), iban, bic, type, Double.parseDouble(balance), account_currency, local_currency, market_code);
        	}
        }
        return null;
	}

	public static DatabaseType insertAutocompleteBroker(JFrame frame, Database db){
		JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(3, 1, 2, 2));
        label.add(new JLabel("Broker Number", SwingConstants.RIGHT));
        label.add(new JLabel("Rating", SwingConstants.RIGHT));
        label.add(new JLabel("Bank market code", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(3, 1, 2, 2));
        
        JTextField relationF = new JTextField();
        relationF.setToolTipText("insert the broker's id, which should be an integer number!");
        TextPrompt relationP = new TextPrompt("12345", relationF);
        relationP.setForeground( Color.RED );
        relationP.changeAlpha(0.5f);
        relationP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(relationF);
        
        JComboBox<String> ratingF = new JComboBox<String>(new String[] {"0","1","2","3","4","5","6","7","8","9","10"});
        controls.add(ratingF);
        
        JComboBox<String> market_codeF = new JComboBox<String>(db.get("market_code", "company").get("market_code").toArray(new String[0]));
        controls.add(market_codeF);
        
        
        controls.setPreferredSize(new Dimension(300, 100));
        panel.add(controls, BorderLayout.CENTER);

        while(JOptionPane.showConfirmDialog(frame, panel, "Add", JOptionPane.OK_CANCEL_OPTION) == 0){
        	String id = relationF.getText();
        	String rating = ratingF.getSelectedItem().toString();
        	String market_code = market_codeF.getSelectedItem().toString();
        	
        	//Checks
        	boolean validData = true;
        	try{
        		Integer.parseInt(id);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Broker id must be integer number!");
        	}
        	
        	//If entered data is valid, return broker type.
        	if(validData){
        		return new Broker(Integer.parseInt(id), Integer.parseInt(rating), market_code);
        	}
        }
        return null;
	}

	public static DatabaseType insertAutocompleteCompany(JFrame frame, Database db){
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(5, 1, 2, 2));
        label.add(new JLabel("Company market code", SwingConstants.RIGHT));
        label.add(new JLabel("Brand Name", SwingConstants.RIGHT));
        label.add(new JLabel("Rating", SwingConstants.RIGHT));
        label.add(new JLabel("Capital (in B)", SwingConstants.RIGHT));
        label.add(new JLabel("Currency", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(5, 1, 2, 2));
        
        JTextField market_codeF = new JTextField();
        market_codeF.setToolTipText("Type the market code of the company or bank. Like RT.b");
        TextPrompt market_codeP = new TextPrompt("RBS.L", market_codeF);
        market_codeP.setForeground( Color.RED );
        market_codeP.changeAlpha(0.5f);
        market_codeP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(market_codeF);
        
        JTextField brand_nameF = new JTextField();
        brand_nameF.setToolTipText("Inser the brand name of the company.");
        TextPrompt brand_nameP = new TextPrompt("Lloyd LTD", brand_nameF);
        brand_nameP.setForeground( Color.RED );
        brand_nameP.changeAlpha(0.5f);
        brand_nameP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(brand_nameF);
        
        JComboBox<String> ratingF = new JComboBox<String>(new String[] {"0","1","2","3","4","5","6","7","8","9","10"});
        controls.add(ratingF);
        
        JTextField capitalF = new JTextField();
        capitalF.setToolTipText("Insert the capital of the company, as a floating point number in, B.");
        TextPrompt capitalP = new TextPrompt("54.56", capitalF);
        capitalP.setForeground( Color.RED );
        capitalP.changeAlpha(0.5f);
        capitalP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(capitalF);
        
        JTextField currencyF = new JTextField();
        currencyF.setToolTipText("Type the currency of the state you live in.");
        TextPrompt currencyP = new TextPrompt("EUR", currencyF);
        currencyP.setForeground( Color.RED );
        currencyP.changeAlpha(0.5f);
        currencyP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(currencyF);
        
        
        controls.setPreferredSize(new Dimension(300, 180));
        panel.add(controls, BorderLayout.CENTER);

        while(JOptionPane.showConfirmDialog(frame, panel, "Add", JOptionPane.OK_CANCEL_OPTION) == 0){
        	String market_code = market_codeF.getText().toUpperCase();
        	String brand = brand_nameF.getText().toUpperCase();
        	String rating = ratingF.getSelectedItem().toString();
        	String capital = capitalF.getText();
        	String currency = currencyF.getText().toUpperCase();
        	
        	
        	//Checks
        	boolean validData = true;
        	try{
        		Double.parseDouble(capital);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Balance must be floating point number!");
        	}
        	if(market_code.length()>10 || !market_code.matches("[A-z]+|[A-z]+.[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Market code maximum allowed 10 alphabetical characters + the dot (.) sign!");
        		validData = false;
        	}
        	if(currency.length()>3 || !currency.matches("[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Currency should be 3 char max. and only literals, such as EUR or GBP!");
        		validData = false;
        	}
        	//If entered data is valid, return company type.
        	if(validData){
        		return new Company(market_code, brand, Integer.parseInt(rating), Double.parseDouble(capital), currency);
        	}
        }
        return null;
	}
	
	public static DatabaseType insertAutocompleteStockValue(JFrame frame, Database db){
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(5, 1, 2, 2));
        label.add(new JLabel("Time", SwingConstants.RIGHT));
        label.add(new JLabel("Company market code", SwingConstants.RIGHT));
        label.add(new JLabel("Title number", SwingConstants.RIGHT));
        label.add(new JLabel("Current value", SwingConstants.RIGHT));
        label.add(new JLabel("Currency", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(5, 1, 2, 2));
        
        JTextField timeF = new JTextField();
        timeF.setToolTipText("Insert the time, in the form YYYY-MM-DD!");
        TextPrompt timeP = new TextPrompt("2013-08-20", timeF);
        timeP.setForeground( Color.RED );
        timeP.changeAlpha(0.5f);
        timeP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(timeF);
        
        JComboBox<String> market_codeF = new JComboBox<String>(db.get("market_code", "company").get("market_code").toArray(new String[0]));
        controls.add(market_codeF);
        
        JComboBox<String> titleF = new JComboBox<String>(db.get("title_number", "title").get("title_number").toArray(new String[0]));
        controls.add(titleF);
        
        JTextField valueF = new JTextField();
        valueF.setToolTipText("Insert the value at that time, as a floating point number.");
        TextPrompt valueP = new TextPrompt("54.56", valueF);
        valueP.setForeground( Color.RED );
        valueP.changeAlpha(0.5f);
        valueP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(valueF);
        
        JTextField currencyF = new JTextField();
        currencyF.setToolTipText("Type the currency of the state you live in.");
        TextPrompt currencyP = new TextPrompt("EUR", currencyF);
        currencyP.setForeground( Color.RED );
        currencyP.changeAlpha(0.5f);
        currencyP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(currencyF);
        
        
        controls.setPreferredSize(new Dimension(300, 180));
        panel.add(controls, BorderLayout.CENTER);

        while(JOptionPane.showConfirmDialog(frame, panel, "Add", JOptionPane.OK_CANCEL_OPTION) == 0){
        	String time = timeF.getText();
        	String market_code = market_codeF.getSelectedItem().toString();
        	String title = titleF.getSelectedItem().toString();
        	String value = valueF.getText();
        	String currency = currencyF.getText().toUpperCase();
        	
        	//Checks
        	boolean validData = true;
        	try{
        		Double.parseDouble(value);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Value must be floating point number!");
        	}
        	if(!time.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")){
        		JOptionPane.showMessageDialog(frame, "Time should be of the format 'YYYY-MM-DD'");
        		validData = false;
        	}
        	if(currency.length()>3 || !currency.matches("[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Currency should be 3 char max. and only literals, such as EUR or GBP!");
        		validData = false;
        	}
        	
        	//If entered data is valid, return account type.
        	if(validData){
        		return new StockValue(time, market_code, Integer.parseInt(title), Double.parseDouble(value), currency);
        	}
        }
        return null;
	}

	public static DatabaseType insertAutocompleteTitle(JFrame frame, Database db){
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(8, 1, 2, 2));
        label.add(new JLabel("Title Number", SwingConstants.RIGHT));
        label.add(new JLabel("Market code", SwingConstants.RIGHT));
        label.add(new JLabel("IBAN", SwingConstants.RIGHT));
        label.add(new JLabel("BIC/SWIFT", SwingConstants.RIGHT));
        label.add(new JLabel("Broker number", SwingConstants.RIGHT));
        label.add(new JLabel("Created day", SwingConstants.RIGHT));
        label.add(new JLabel("Value", SwingConstants.RIGHT));
        label.add(new JLabel("Currency", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(8, 1, 2, 2));
        
        JTextField titleF = new JTextField();
        titleF.setToolTipText("Insert the Title number, which should be an integer value!");
        TextPrompt titleP = new TextPrompt("12345", titleF);
        titleP.setForeground( Color.RED );
        titleP.changeAlpha(0.5f);
        titleP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(titleF);
        
        JComboBox<String> market_codeF = new JComboBox<String>(db.get("market_code", "company").get("market_code").toArray(new String[0]));
        controls.add(market_codeF);
        
        JComboBox<String> ibanF = new JComboBox<String>(db.get("iban", "bank_account").get("iban").toArray(new String[0]));
        controls.add(ibanF);
        
        JComboBox<String> bicF = new JComboBox<String>(db.get("bic", "bank_account").get("bic").toArray(new String[0]));
        controls.add(bicF);
        
        ArrayList<String> l = db.get("broker_number", "broker").get("broker_number");
        l.add("NONE");
        JComboBox<String> brokerF = new JComboBox<String>(l.toArray(new String[0]));
        controls.add(brokerF);
        
        JTextField timeF = new JTextField();
        timeF.setToolTipText("Insert the time at which the title was created, in the form YYYY-MM-DD!");
        TextPrompt timeP = new TextPrompt("2013-08-20", timeF);
        timeP.setForeground( Color.RED );
        timeP.changeAlpha(0.5f);
        timeP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(timeF);
        
        JTextField valueF = new JTextField();
        valueF.setToolTipText("Insert the value at the creation day.");
        TextPrompt valueP = new TextPrompt("55654.56", valueF);
        valueP.setForeground( Color.RED );
        valueP.changeAlpha(0.5f);
        valueP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(valueF);
        
        JTextField currencyF = new JTextField();
        currencyF.setToolTipText("Insert the currency of the account in three letters. If you have 5647.94eur in your account, type EUR");
        TextPrompt currencyP = new TextPrompt("GBP", currencyF);
        currencyP.setForeground( Color.RED );
        currencyP.changeAlpha(0.5f);
        currencyP.changeStyle(Font.BOLD + Font.ITALIC);
        controls.add(currencyF);
        
        controls.setPreferredSize(new Dimension(300, 300));
        panel.add(controls, BorderLayout.CENTER);

        while(JOptionPane.showConfirmDialog(frame, panel, "Add", JOptionPane.OK_CANCEL_OPTION) == 0){
        	String title = titleF.getText();
        	String market_code = market_codeF.getSelectedItem().toString();
        	String iban = ibanF.getSelectedItem().toString();
        	String bic = bicF.getSelectedItem().toString();
        	String broker = brokerF.getSelectedItem().toString();
        	String time = timeF.getText();
        	String value = valueF.getText();
        	String currency = currencyF.getText().toUpperCase();
        	
        	//Checks
        	
        	boolean validData = true;
        	boolean hasBroker = false;
        	if(broker.equals("NONE")){
        		hasBroker = false;
        	}
        	try{
        		Double.parseDouble(value);
        	} catch (Exception e){
        		validData = false;
        		JOptionPane.showMessageDialog(frame, "Value must be floating point number!");
        	}
        	if(!time.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")){
        		JOptionPane.showMessageDialog(frame, "Time should be of the format 'YYYY-MM-DD'");
        		validData = false;
        	}
        	if(currency.length()>3 || !currency.matches("[A-z]+")){
        		JOptionPane.showMessageDialog(frame, "Currency should be 3 char max. and only literals, such as EUR or GBP!");
        		validData = false;
        	}
        	
        	//If entered data is valid, return account type.
        	if(validData){
        		return new Title(Integer.parseInt(title), market_code, iban, bic, (hasBroker?Integer.parseInt(broker):null), time, Double.parseDouble(value), currency);
        	}
        }
        return null;
	}

}
