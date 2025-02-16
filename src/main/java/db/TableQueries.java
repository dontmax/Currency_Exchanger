package db;

public interface TableQueries {
	
	public static final String CREATE_CURRENCY_TABLE = 
			"CREATE TABLE IF NOT EXISTS Currencies ("
			+ "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "Code TEXT UNIQUE NOT NULL,"
			+ "FullName TEXT UNIQUE NOT NULL,"
			+ "Sign TEXT NOT NULL);";
	public static final String CREATE_CODE_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS Code ON currencies (Code)";
	public static final String CREATE_EXCHANGE_RATE_TABLE = 
			"CREATE TABLE IF NOT EXISTS ExchangeRates ("
			+ "Ex_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "BaseCurrencyID INTEGER NOT NULL,"
			+ "TargetCurrencyID INTEGER NOT NULL,"
			+ "Rate NUMERIC,"
			+ "FOREIGN KEY (BaseCurrencyID) REFERENCES currencies (ID),"
			+ "FOREIGN KEY (TargetCurrencyID) REFERENCES currencies (ID))";
	public static final String CREATE_BASE_TARGET_CURRENCY_INDEX = 
			"CREATE UNIQUE INDEX IF NOT EXISTS "
			+ "BaseTargetCurrenciesIndex ON ExchangeRates (BaseCurrencyID, TargetCurrencyID)";
	
	
	
}
