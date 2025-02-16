package db;

public interface ExchangeRateQueries {
	
	public static final String CREATE_EXCHANGE_RATE = "INSERT INTO ExchangeRates (Rate, BaseCurrencyID, TargetCurrencyID) VALUES "
			+ " (?, (SELECT ID FROM Currencies WHERE Code = ?),"
			+ " (SELECT ID FROM Currencies WHERE Code = ?))";
	public static final String GET_ALL_EXCHANGE_RATES = 
		    "SELECT Ex_ID, " +
		    "base.ID AS BaseID, base.Code AS BaseCode, base.FullName AS BaseFullName, base.Sign AS BaseSign, " +
		    "target.ID AS TargetID, target.Code AS TargetCode, target.FullName AS TargetFullName, target.Sign AS TargetSign, " +
		    "Rate " +
		    "FROM ExchangeRates " +
		    "JOIN Currencies base ON ExchangeRates.BaseCurrencyID = base.ID " +
		    "JOIN Currencies target ON ExchangeRates.TargetCurrencyID = target.ID";
	public static final String GET_EXCHANGE_RATE = GET_ALL_EXCHANGE_RATES + " WHERE base.Code = ? AND target.Code = ?";		
	public static final String UPDATE_EXCHANGE_RATE ="UPDATE ExchangeRates SET Rate = ? "
			+ " WHERE ExchangeRates.BaseCurrencyID = (SELECT ID FROM Currencies WHERE Code = ?) "
			+ " AND ExchangeRates.TargetCurrencyID = (SELECT ID FROM Currencies WHERE Code = ?)";
}
