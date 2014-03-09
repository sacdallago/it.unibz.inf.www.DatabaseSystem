package types;

public class Title {
	private Integer title_number;
	private String market_code;
	private String iban;
	private String bic;
	private Integer broker_number;
	private String created_day;
	private Double initial_value;
	private String initial_value_currency;
	
	public Title(Integer title_number, String market_code, String iban,
			String bic, Integer broker_number, String created_day,
			Double initial_value, String initial_value_currency) {
		this.title_number = title_number;
		this.market_code = market_code;
		this.iban = iban;
		this.bic = bic;
		this.broker_number = broker_number;
		this.created_day = created_day;
		this.initial_value = initial_value;
		this.initial_value_currency = initial_value_currency;
	}

	public Integer getTitle_number() {
		return title_number;
	}

	public String getMarket_code() {
		return market_code;
	}

	public String getIban() {
		return iban;
	}

	public String getBic() {
		return bic;
	}

	public Integer getBroker_number() {
		return broker_number;
	}

	public String getCreated_day() {
		return created_day;
	}

	public Double getInitial_value() {
		return initial_value;
	}

	public String getInitial_value_currency() {
		return initial_value_currency;
	}

	@Override
	public String toString() {
		return "Title [title_number=" + title_number + ", market_code="
				+ market_code + ", iban=" + iban + ", bic=" + bic
				+ ", broker_number=" + broker_number + ", created_day="
				+ created_day + ", initial_value=" + initial_value
				+ ", initial_value_currency=" + initial_value_currency + "]";
	}
}
