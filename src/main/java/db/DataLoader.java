package db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import dao.CurrencyDao;
import dao.ExchangeRateDao;
import models.Currency;
import models.ExchangeRate;


public class DataLoader {
	
	CurrencyDao curDao;
	ExchangeRateDao exRDao;
	
	public DataLoader(CurrencyDao curDao, ExchangeRateDao exRDao) {
		this.curDao=curDao;
		this.exRDao=exRDao;
	}
	
	public void loadData() throws SQLException {
		try (
				Connection connection = DataSourceUtil.getConnection();
				Statement stmt = connection.createStatement();
				)
			{
				stmt.execute(TableQueries.CREATE_CURRENCY_TABLE);
				stmt.execute(TableQueries.CREATE_CODE_INDEX);
				stmt.execute(TableQueries.CREATE_EXCHANGE_RATE_TABLE);
				stmt.execute(TableQueries.CREATE_BASE_TARGET_CURRENCY_INDEX);
				
				if(curDao.getAll().isEmpty()) {
					Arrays.asList(
						new Currency("AUD", "Australian Dollar", "A$"),
						new Currency("EUR", "Euro", "€"),
						new Currency("USD", "United States Dollar", "$"),
						new Currency("RUB", "Russian Ruble", "₽"),
						new Currency("CNY", "Yuan Renminbi", "¥"),
						new Currency("JPY", "Yen", "¥"),
						new Currency("KRW", "North Korean Won", "₩")
					).forEach(curDao::create);
				}
				if(exRDao.getAll().isEmpty()) {
	                Arrays.asList(
	                	new ExchangeRate("USD", "AUD", new BigDecimal("1.61")),
	                    new ExchangeRate("USD", "EUR", new BigDecimal("0.97")),
	                    new ExchangeRate("USD", "RUB", new BigDecimal("102.29")),
	                    new ExchangeRate("USD", "CNY", new BigDecimal("7.47")),
	                    new ExchangeRate("USD", "JPY", new BigDecimal("158.33")),
	                    new ExchangeRate("USD", "KRW", new BigDecimal("1453.20"))
	                ).forEach(exRDao::create);
				}
			}
	}
}