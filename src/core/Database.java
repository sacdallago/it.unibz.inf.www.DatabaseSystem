package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import types.*;

public class Database {
		private Connection db;
		
		public Database(String url, String databseName, String username, String password){
			System.out.println("-------- PostgreSQL JDBC Connection Testing --------");
			
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
				//e.printStackTrace();
				return;
			}
	 
			System.out.println("-------- PostgreSQL JDBC Driver Registered! --------");
			
			try {
				//connection = DriverManager.getConnection("jdbc:postgresql://"+ args[0]+":5432/" + args[1], args[2], args[3]);
				db = DriverManager.getConnection("jdbc:postgresql://"+url+":5432/"+databseName, username, password);
				System.out.println("-------- PostgreSQL connection established! --------");
				//return;
			} catch (SQLException e) {
				System.err.println("Connection to DB Failed!");
				//e.printStackTrace();
				return;
			} catch (Exception e) {
				System.err.println("Call should be: \"filename.jar SERVERADDRESS DATABASENAME USERNAME PASSWORD\"");
				//e.printStackTrace();
				return;
			}
				//System.err.println("Not connected! Database unavailable or no internet connection!");
		}
		
		private String whereCreator(String conditions[]){
			String result = "";
			if(conditions.length == 0){
				return result;
			}
			result += "WHERE " + conditions[0] + " ";
			if(conditions.length > 1){
				for(int i = 1; i<conditions.length;i++){
					result += "AND " + conditions[i];
				}
			}
			return result;
		}
		
		public Object[][] convert(HashMap<String,ArrayList<String>> query){
			Object[][] result;
			int entries = query.get(query.keySet().iterator().next()).size();
			result = new Object[entries][query.keySet().size()];
			for (int i=0;i<entries;i++){
				int j = 0;
				for (String a : query.keySet()){
					result[i][j] = query.get(a).get(i);
					j++;
				}
			}
			return result;
		}
		
		/**
		 * Method to DELETE FROM a certain columns RELATION, WHERE there are constrains.
		 * 
		 * @param relation Is the name of the table where the deletion applies, such as "company"
		 * @param constrains Represent constrains in pure sql. eg.: "type='checking'","market_code='RBS.L'","relation_number=12345"
		 */
		public int delete(String relation, String...constrains){
			int itWorked = -1;
			Statement statement = null;
			String where = whereCreator(constrains);
			try {
				statement = db.createStatement();
				String query = "DELETE FROM "+relation+" "+ where +";";
				System.out.println(query);
				itWorked = statement.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
			    if (statement != null) {
			        try {
			        	statement.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
			return itWorked;
		}
		
		/**
		 * Method to SELECT certain columns from a RELATION, WHERE there are constrains.
		 * 
		 * @param select Should be something like "number,id,name" mind dividing the columns by comma only!
		 * @param relation Is the name of the table where the select applies, such as "company"
		 * @param constrains Represent constrains in pure sql. eg.: "type='checking'","market_code='RBS.L'","relation_number=12345"
		 */
		public HashMap<String,ArrayList<String>> get(String select, String relation, String...constrains){
			HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
			Statement statement = null;
			ResultSet resultSet = null;
			if(select.equals("*")){
				select = "";
				for(String s : getColumnNames(relation)){
					select += s + ",";
				}
				select = select.substring(0, select.length()-1);
			}
			select = select.replaceAll(" ", "");
			String[] selects = select.split(",");
			for (String a : selects){
				map.put(a, new ArrayList<String>());
			}
			String where = whereCreator(constrains);
			try {
				statement = db.createStatement();
				System.out.println("SELECT "+select+" FROM "+relation +" "+ where+";");
				resultSet = statement.executeQuery("SELECT "+select+" FROM "+relation +" "+ where+";");
				while (resultSet.next()){
					for (String a : selects){
						map.get(a).add(resultSet.getString(a));
					}
				}
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
			    if (statement != null) {
			        try {
			        	statement.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			    if (resultSet != null) {
			        try {
			        	resultSet.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
			return map;
		}
		
		/**
		 * Method to SELECT certain columns from a RELATION, WHERE there are constrains.
		 * 
		 * @param select Should be something like "number,id,name" mind dividing the columns by comma only!
		 * @param relation Is the name of the table where the select applies, such as "company"
		 * @param constrains Represent constrains in pure sql. eg.: "type='checking'","market_code='RBS.L'","relation_number=12345"
		 */
		public ArrayList<String> getAsString(String select, String relation, String...constrains){
			HashMap<String,ArrayList<String>> query;
			if(constrains.length == 0){
				query = get(select,relation);
			} else {
				query = get(select,relation,constrains);
			}
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
			return result;
		}
		
		/**
		 * Method to SELECT certain columns from a RELATION, WHERE there are constrains.
		 * 
		 * @param select Should be something like "number,id,name" mind dividing the columns by comma only!
		 * @param relation Is the name of the table where the select applies, such as "company"
		 * @param constrains Represent constrains in pure sql. eg.: "type='checking'","market_code='RBS.L'","relation_number=12345"
		 */
		public ArrayList<String> getAsString(HashMap<String,ArrayList<String>> query){
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
			return result;
		}
		
		/**
		 * Method to get all columns of bank account types. Can refine search by putting constrains.
		 * 
		 * @param constrains Represent constrains in pure sql. eg.: loadBankAccounts("type='checking'","market_code='RBS.L'","relation_number=12345")
		 */
		public ArrayList<BankAccount> loadBankAccounts(String ... constrains){
			ArrayList<BankAccount> list = new ArrayList<BankAccount>();
			Statement statement = null;
			ResultSet resultSet = null;
			String where = whereCreator(constrains);
			try {
				statement = db.createStatement();
				System.out.println("SELECT * FROM bank_account "+where+";");
				resultSet = statement.executeQuery("SELECT * FROM bank_account "+where+";");
				while (resultSet.next()){
					BankAccount item = new BankAccount(
							Integer.parseInt(resultSet.getString("relation_number")),
							resultSet.getString("iban"),
							resultSet.getString("bic"),
							resultSet.getString("type"),
							Double.parseDouble(resultSet.getString("balance")),
							resultSet.getString("account_currency"),
							resultSet.getString("local_currency"),
							resultSet.getString("market_code")
							);
					list.add(item);
				}
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
			    if (statement != null) {
			        try {
			        	statement.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			    if (resultSet != null) {
			        try {
			        	resultSet.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
			return list;
		}
		
		/**
		 * Method to get all columns of broker types. Can refine search by putting constrains.
		 * 
		 * @param constrains Represent constrains in pure sql. eg.: loadBrokers("type='checking'","market_code='RBS.L'","relation_number=12345")
		 */
		public ArrayList<Broker> loadBrokers(String ... constrains){
			ArrayList<Broker> list = new ArrayList<Broker>();
			Statement statement = null;
			ResultSet resultSet = null;
			String where = whereCreator(constrains);
			try {
				statement = db.createStatement();
				System.out.println("SELECT * FROM broker "+where+";");
				resultSet = statement.executeQuery("SELECT * FROM broker "+where+";");
				while (resultSet.next()){
					Broker item = new Broker(
							Integer.parseInt(resultSet.getString("broker_number")),
							Integer.parseInt(resultSet.getString("rating")),
							resultSet.getString("market_code")
							);
					list.add(item);
				}
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
			    if (statement != null) {
			        try {
			        	statement.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			    if (resultSet != null) {
			        try {
			        	resultSet.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
			return list;
		}
		
		/**
		 * Method to get all columns of company types. Can refine search by putting constrains.
		 * 
		 * @param constrains Represent constrains in pure sql. eg.: loadCompanies("type='checking'","market_code='RBS.L'","relation_number=12345")
		 */
		public ArrayList<Company> loadCompanies(String ... constrains){
			ArrayList<Company> list = new ArrayList<Company>();
			Statement statement = null;
			ResultSet resultSet = null;
			String where = whereCreator(constrains);
			try {
				statement = db.createStatement();
				System.out.println("SELECT * FROM company "+where+";");
				resultSet = statement.executeQuery("SELECT * FROM company "+where+";");
				while (resultSet.next()){
					Company item = new Company(
							resultSet.getString("market_code"),
							resultSet.getString("brand_name"),
							Integer.parseInt(resultSet.getString("rating")),
							Double.parseDouble(resultSet.getString("capital")),
							resultSet.getString("currency")
							);
					list.add(item);
				}
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
			    if (statement != null) {
			        try {
			        	statement.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			    if (resultSet != null) {
			        try {
			        	resultSet.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
			return list;
		}
		
		/**
		 * Method to get all columns of stock_value types. Can refine search by putting constrains.
		 * 
		 * @param constrains Represent constrains in pure sql. eg.: loadStockValues("type='checking'","market_code='RBS.L'","relation_number=12345")
		 */
		public ArrayList<StockValue> loadStockValues(String ... constrains){
			ArrayList<StockValue> list = new ArrayList<StockValue>();
			Statement statement = null;
			ResultSet resultSet = null;
			String where = whereCreator(constrains);
			try {
				statement = db.createStatement();
				System.out.println("SELECT * FROM stock_value "+where+";");
				resultSet = statement.executeQuery("SELECT * FROM stock_value "+where+";");
				while (resultSet.next()){
					StockValue item = new StockValue(
							resultSet.getString("time"),
							resultSet.getString("market_code"),
							Integer.parseInt(resultSet.getString("title_number")),
							Double.parseDouble(resultSet.getString("current_value")),
							resultSet.getString("currency")
							);
					list.add(item);
				}
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
			    if (statement != null) {
			        try {
			        	statement.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			    if (resultSet != null) {
			        try {
			        	resultSet.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
			return list;
		}
		
		/**
		 * Method to get all columns of title types. Can refine search by putting constrains.
		 * 
		 * @param constrains Represent constrains in pure sql. eg.: loadTitless("type='checking'","market_code='RBS.L'","relation_number=12345")
		 */
		public ArrayList<Title> loadTitles(String ... constrains){
			ArrayList<Title> list = new ArrayList<Title>();
			Statement statement = null;
			ResultSet resultSet = null;
			String where = whereCreator(constrains);
			try {
				statement = db.createStatement();
				System.out.println("SELECT * FROM title "+where+";");
				resultSet = statement.executeQuery("SELECT * FROM title "+where+";");
				while (resultSet.next()){
					Title item = new Title(
							Integer.parseInt(resultSet.getString("title_number")),
							resultSet.getString("market_code"),
							resultSet.getString("iban"),
							resultSet.getString("bic"),
							Integer.parseInt((resultSet.getString("broker_number")!=null ? resultSet.getString("broker_number") : "-1")),
							resultSet.getString("created_day"),
							Double.parseDouble(resultSet.getString("initial_value")),
							resultSet.getString("initial_value_currency")
							);
					list.add(item);
				}
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
			    if (statement != null) {
			        try {
			        	statement.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			    if (resultSet != null) {
			        try {
			        	resultSet.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
			return list;
		}
		
		public ArrayList<String> getColumnNames(String tableName) {
		    ArrayList<String> allFields = new ArrayList<String>();
		    ResultSet resultSet = null;
		    try {
		        resultSet = db.getMetaData().getColumns(null, null, tableName, null);
		        while (resultSet.next()) {
		        	allFields.add(resultSet.getString("COLUMN_NAME"));
		        	}
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
			    if (resultSet != null) {
			        try {
			        	resultSet.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
		    return allFields;
		}
		
		public ArrayList<String> getTableNames(){
			ArrayList<String> tables = new ArrayList<String>();
			ResultSet resultSet = null;
			try {
				resultSet = db.getMetaData().getTables(null, null, "%", null);
				while (resultSet.next()) {
					  tables.add(resultSet.getString(3));
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}  finally {
			    if (resultSet != null) {
			        try {
			        	resultSet.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
			return tables;
		}
		
		public void clearAllData(){
			SqlLoad runner = new SqlLoad(db, false, true);
			try {
				runner.runScript(new BufferedReader(new FileReader("sql/clear.sql")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("Some SQL file was not found!");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Some SQL file was not found!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void deleteSchema(){
			SqlLoad runner = new SqlLoad(db, false, true);
			try {
				
				runner.runScript(new BufferedReader(new FileReader("sql/clean.sql")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("Some SQL file was not found!");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Some SQL file was not found!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void loadMockData(){
			SqlLoad runner = new SqlLoad(db, false, true);
			try {
				runner.runScript(new BufferedReader(new FileReader("sql/load.sql")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("Some SQL file was not found!");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Some SQL file was not found!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void createSchema(){
			SqlLoad runner = new SqlLoad(db, false, true);
			try {
				runner.runScript(new BufferedReader(new FileReader("sql/create.sql")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("Some SQL file was not found!");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Some SQL file was not found!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void close(){
			boolean tester = true;
			while (db != null && tester){
				try {
					db.close();
					tester = false;
					System.out.println("--------    PostgreSQL connection closed!   --------");
				} catch (SQLException e) {
				}
			}
		}
}
