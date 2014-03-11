package core;

import java.util.ArrayList;

import types.*;

public class Container {
	private ArrayList<BankAccount> bankAccounts;
	private ArrayList<Broker> brokers;
	private ArrayList<Company> companies;
	private ArrayList<StockValue> stockValues;
	private ArrayList<Title> titles;
	
	private Database db;
	
	public Container(Database db){
		this.db = db;
	}
	
	private Container(){}
	
	public void initialize(){
		setBankAccounts();
		setBrokers();
		setCompanies();
		setStockValues();
		setTitles();
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
	
	
	public void setBankAccounts() {
		this.bankAccounts = db.loadBankAccounts();
	}
	public void setBrokers() {
		this.brokers = db.loadBrokers();
	}
	public void setCompanies() {
		this.companies = db.loadCompanies();
	}
	public void setStockValues() {
		this.stockValues = db.loadStockValues();
	}
	public void setTitles() {
		this.titles = db.loadTitles();
	}
	
	public String printAndUpdateBankAccounts() {
		setBankAccounts();
		String result = "";
		for (BankAccount a : bankAccounts){
			result += a.toString() +"\n";
		}
		return result;
	}
	public String printAndUpdateBrokers() {
		setBrokers();
		String result = "";
		for (Broker a : brokers){
			result += a.toString() +"\n";
		}
		return result;
	}
	public String printAndUpdateCompanies() {
		setCompanies();
		String result = "";
		for (Company a : companies){
			result += a.toString() +"\n";
		}
		return result;
	}
	public String printAndUpdateStockValues() {
		setStockValues();
		String result = "";
		for (StockValue a : stockValues){
			result += a.toString() +"\n";
		}
		return result;
	}
	public String printAndUpdateTitles() {
		setTitles();
		String result = "";
		for (Title a : titles){
			result += a.toString() +"\n";
		}
		return result;
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
