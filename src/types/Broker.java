package types;

public class Broker {
	private Integer broker_number;
	private Integer rating;
	private String market_code;
	
	public Broker(Integer broker_number, Integer rating, String market_code){
		this.broker_number = broker_number;
		this.rating = rating;
		this.market_code = market_code;
	}

	public Integer getBroker_number() {
		return broker_number;
	}

	public Integer getRating() {
		return rating;
	}

	public String getMarket_code() {
		return market_code;
	}

	@Override
	public String toString() {
		return "Broker [broker_number=" + broker_number + ", rating=" + rating
				+ ", market_code=" + market_code + "]";
	}
}
