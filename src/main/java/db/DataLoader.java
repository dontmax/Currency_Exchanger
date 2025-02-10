package db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import dao.CurrencyDao;
import dao.ExchangeRateDao;
import models.Currency;
import models.ExchangeRate;


public class DataLoader {
	
	CurrencyDao curDao;
	ExchangeRateDao ExRDao;
	
	public DataLoader(CurrencyDao curDao, ExchangeRateDao ExRDao) {
		this.curDao=curDao;
		this.ExRDao=ExRDao;
	}
	
	public void createTables() throws SQLException {
		try (
				Connection connection = DataSourceUtil.getConnection();
				Statement stmt = connection.createStatement();
				)
			{
				stmt.execute(TableQueries.CREATE_CURRENCY_TABLE);
				stmt.execute(TableQueries.CREATE_CODE_INDEX);
				stmt.execute(TableQueries.CREATE_EXCHANGE_RATE_TABLE);
				stmt.execute(TableQueries.CREATE_BASE_TARGET_CURRENCY_INDEX);
			}
	}
	
	public void loadInitialData() throws SQLException {
		if(curDao.getAll().isEmpty()) {
			curDao.create(new Currency("AUD", "Australian Dollar", "A$"));
			curDao.create(new Currency("EUR", "Euro", "€"));
			curDao.create(new Currency("USD", "United States Dollar", "$"));
			curDao.create(new Currency("RUB", "Russian Ruble", "₽"));
			curDao.create(new Currency("CNY", "Yuan Renminbi", "¥"));
			curDao.create(new Currency("JPY", "Yen", "¥"));
			curDao.create(new Currency("KRW", "North Korean Won", "₩"));
		}
		if(ExRDao.getAll().isEmpty()) {
			ExRDao.create(new ExchangeRate(3,1,new BigDecimal(1.61)));
			ExRDao.create(new ExchangeRate(3,2,new BigDecimal(0.97)));
			ExRDao.create(new ExchangeRate(3,4,new BigDecimal(102.29)));
			ExRDao.create(new ExchangeRate(3,5,new BigDecimal(7.47)));
			ExRDao.create(new ExchangeRate(3,6,new BigDecimal(158.33)));
			ExRDao.create(new ExchangeRate(3,7,new BigDecimal(1453.20)));
		}
	}
}
