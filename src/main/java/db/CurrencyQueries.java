package db;

public interface CurrencyQueries {
	
	public static final String CREATE_CURRENCY = "INSERT OR IGNORE INTO Currencies (Code, FullName, Sign) VALUES (?,?,?)";
	public static final String GET_CURRENCY_BY_ID = "SELECT * FROM Currencies WHERE ID = ?";
	public static final String GET_CURRENCY_BY_CODE = "SELECT * FROM Currencies WHERE Code = ?";	
	public static final String GET_ALL_CURRENCIES = "SELECT * FROM Currencies";	
	public static final String DELETE_CURRENCY = "DELETE FROM Currencies WHERE Code = ?";
}

