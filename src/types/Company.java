package types;

public class Company implements DatabaseType {
	private String market_code;
	private String brand_name;
	private Integer rating;
	private Double capital;
	private String currency;
	
	public Company(String market_code, String brand_name, Integer rating, Double capital, String currency){
		this.market_code = market_code;
		this.brand_name = brand_name;
		this.rating = rating;
		this.capital = capital;
		this.currency = currency;
	}

	public String getMarket_code() {
		return market_code;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public Integer getRating() {
		return rating;
	}

	public Double getCapital() {
		return capital;
	}

	public String getCurrency() {
		return currency;
	}

	@Override
	public String toString() {
		return "Company [market_code=" + market_code + ", brand_name="
				+ brand_name + ", rating=" + rating + ", capital=" + capital
				+ ", currency=" + currency + "]";
	}

	@Override
	public String convertToInsert() {
		// TODO Auto-generated method stub
		return null;
	}

}
