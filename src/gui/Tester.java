package gui;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.postgresql.*;

import core.Database;

public class Tester {

	public static void main(String[] args) {
		/*
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream("output.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(out);
		*/
		
		//Database db = new Database("172.16.9.182", "db", "sacdallago", "ciaociao3");
		
		//db.cleanDB();
		//db.createDBStructure();
		//db.loadFakeData();
		
		//db.loadBankAccounts("type = \'checking\'", "market_code='RBS.L'");
		
		//db.loadAll();
		
		//System.out.println(db.getContainer().printBankAccounts());
		//System.out.println(db.getContainer().printBrokers());
		//System.out.println(db.getContainer().printCompanies());
		//System.out.println(db.getContainer().printStockValues());
		//System.out.println(db.getContainer().printTitles());
		
		/*
		HashMap<String, ArrayList<String>> query = db.get("time,title_number,market_code", "stock_value");
		ArrayList<String> result = new ArrayList<String>();
		boolean first = true;
		for (String a : query.keySet()){
			if (first){
				for (int i=0;i<query.get(a).size();i++){
					result.add(query.get(a).get(i));
				}
				first = false;
			} else {
				for (int i=0;i<query.get(a).size();i++){
					result.set(i, result.get(i)+", "+query.get(a).get(i));
				}
			}
		}
		for(String a : result){
			System.out.println(a);
		}
		*/
		
		//db.close();
		
		//System.out.println("TESTtest123.tes t--comment".replaceAll("[^a-zA-Z0-9/.]|--", ""));
		//System.out.println("2013-12-23".replaceAll("[^a-zA-Z0-9/.-]|--", ""));
		
		//InsertPanels.insertBankAccount(null);
		//InsertPanels.insertCompany(null);
		//InsertPanels.insertStockValue(null);
		//InsertPanels.insertTitle(null);
		//String iban = "1234-56-78";
		//String iban = "1234-5-7";
		//System.out.println((iban.length()>31) +" "+ iban.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}"));
		//String text = "Facebook 21 Inc.";
		//System.out.println(text.replaceAll("[^a-zA-Z/.-/ ]|-[-]+", "").replaceAll(" +", " "));
	}

}
