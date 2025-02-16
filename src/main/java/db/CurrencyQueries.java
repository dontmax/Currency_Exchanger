package db;

public interface CurrencyQueries {
	
	public static final String CREATE_CURRENCY = "INSERT INTO Currencies (Code, FullName, Sign) VALUES (?,?,?)";
	public static final String GET_CURRENCY_BY_CODE = "SELECT * FROM Currencies WHERE Code = ?";	
	public static final String GET_ALL_CURRENCIES = "SELECT * FROM Currencies";	
}

