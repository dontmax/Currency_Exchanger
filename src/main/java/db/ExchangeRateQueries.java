package db;

public interface ExchangeRateQueries {
	
	public static final String CREATE_EXCHANGE_RATE = "INSERT OR IGNORE INTO ExchangeRates (Rate, BaseCurrencyID, TargetCurrencyID) VALUES (?,?,?)";
	public static final String GET_EXCHANGE_RATE = "SELECT * FROM ExchangeRates WHERE BaseCurrencyID = ? AND TargetCurrencyID = ?";
	public static final String GET_ALL_EXCHANGE_RATES = "SELECT * FROM ExchangeRates";		
	public static final String UPDATE_EXCHANGE_RATE ="UPDATE ExchangeRates SET Rate = ? WHERE BaseCurrencyID = ? AND TargetCurrencyID = ?";
	public static final String DELETE_EXCHANGE_RATE = "DELETE FROM ExchangeRates WHERE BaseCurrencyID = ? AND TargetCurrencyID = ?";
}
