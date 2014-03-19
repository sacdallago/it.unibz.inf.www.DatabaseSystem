package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class InsertPanels {

	public static void insertBankAccount(JFrame frame){

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
        account_currencyF.setToolTipText("Type the currency of the state you live in.");
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

        if(JOptionPane.showConfirmDialog(frame, panel, "Add", JOptionPane.OK_CANCEL_OPTION) == 0){
        	System.out.println(relationF.getText());
        	/*
            logininformation.add(relationF.getText());
            logininformation.add(dbname.getText());
            logininformation.add(username.getText());
            logininformation.add(new String(password.getPassword()));
            */
        }
	}
}
