package core;

import java.util.ArrayList;

import types.*;

public class Container {
	private ArrayList<BankAccount> bankAccounts;
	private ArrayList<Broker> brokers;
	private ArrayList<Company> companies;
	private ArrayList<StockValue> stockValues;
	private ArrayList<Title> titles;
	
	public Container(Database db){
		setBankAccounts(db);
		setBrokers(db);
		setCompanies(db);
		setStockValues(db);
		setTitles(db);
	}
	
	private Container(){
		
	}
	
	public final ArrayList<BankAccount> getBankAccounts() {
		return bankAccounts;
	}
	public final ArrayList<Broker> getBrokers() {
		return brokers;
	}
	public final ArrayList<Company> getCompanies() {
		return companies;
	}
	public final ArrayList<StockValue> getStockValues() {
		return stockValues;
	}
	public final ArrayList<Title> getTitles() {
		return titles;
	}
	
	
	public void setBankAccounts(Database db) {
		this.bankAccounts = db.loadBankAccounts();
	}
	public void setBrokers(Database db) {
		this.brokers = db.loadBrokers();
	}
	public void setCompanies(Database db) {
		this.companies = db.loadCompanies();
	}
	public void setStockValues(Database db) {
		this.stockValues = db.loadStockValues();
	}
	public void setTitles(Database db) {
		this.titles = db.loadTitles();
	}
	
	public void setAll(Database db) {
		setBankAccounts(db);
		setBrokers(db);
		setCompanies(db);
		setStockValues(db);
		setTitles(db);
	}
	
	
	public String printBankAccounts() {
		String result = "";
		for (BankAccount a : bankAccounts){
			result += a.toString() +"\n";
		}
		return result;
	}
	public String printBrokers() {
		String result = "";
		for (Broker a : brokers){
			result += a.toString() +"\n";
		}
		return result;
	}
	public String printCompanies() {
		String result = "";
		for (Company a : companies){
			result += a.toString() +"\n";
		}
		return result;
	}
	public String printStockValues() {
		String result = "";
		for (StockValue a : stockValues){
			result += a.toString() +"\n";
		}
		return result;
	}
	public String printTitles() {
		String result = "";
		for (Title a : titles){
			result += a.toString() +"\n";
		}
		return result;
	}
}
