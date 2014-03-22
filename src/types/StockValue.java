package types;

public class StockValue implements DatabaseType  {
	private String time;
	private String market_code;
	private Integer title_number;
	private Double current_value;
	private String currency;
	
	public StockValue(String time, String market_code, Integer title_number, Double current_value, String currency){
		this.time = time;
		this.market_code = market_code;
		this.title_number = title_number;
		this.current_value = current_value;
		this.currency = currency;
	}

	public String getTime() {
		return time;
	}

	public String getMarket_code() {
		return market_code;
	}

	public Integer getTitle_number() {
		return title_number;
	}

	public Double getCurrent_value() {
		return current_value;
	}

	public String getCurrency() {
		return currency;
	}

	@Override
	public String toString() {
		return "StockValue [time=" + time + ", market_code=" + market_code
				+ ", title_number=" + title_number + ", current_value="
				+ current_value + ", currency=" + currency + "]";
	}

	@Override
	public String convertToInsert() {
		String result = "INSERT INTO stock_value(time, market_code, title_number, current_value, currency)\n";
		result += "VALUES ('"+time+ "', '" + market_code+ "', " + title_number+ ", " + current_value+ ", '" + currency+"');";
		return result;
	}
}
