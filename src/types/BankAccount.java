package types;

public class BankAccount implements DatabaseType  {
	private Integer relation_number;
	private String iban;
	private String bic;
	private String type;
	private Double balance;
	private String account_currency;
	private String local_currency;
	private String market_code;
	
	public BankAccount(Integer relation_number, String iban, String bic, String type, Double balance, String account_currency,
			String local_currency, String market_code){
		this.relation_number = relation_number;
		this.iban = iban;
		this.bic = bic;
		this.type = type;
		this.balance = balance;
		this.account_currency = account_currency;
		this.local_currency = local_currency;
		this.market_code = market_code;
	}
	
	public Integer getRelation_number() {
		return relation_number;
	}

	public String getIban() {
		return iban;
	}

	public String getBic() {
		return bic;
	}

	public String getType() {
		return type;
	}

	public Double getBalance() {
		return balance;
	}

	public String getAccount_currency() {
		return account_currency;
	}

	public String getLocal_currency() {
		return local_currency;
	}

	public String getMarket_code() {
		return market_code;
	}

	@Override
	public String toString() {
		return "BankAccount [relation_number=" + relation_number + ", iban="
				+ iban + ", bic=" + bic + ", type=" + type + ", balance="
				+ balance + ", account_currency=" + account_currency
				+ ", local_currency=" + local_currency + ", market_code="
				+ market_code + "]";
	}

	@Override
	public String convertToInsert() {
		// TODO Auto-generated method stub
		return null;
	}
}
