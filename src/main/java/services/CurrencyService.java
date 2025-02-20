package services;

import java.util.List;

import dao.CurrencyDao;
import exceptions.ExceptionMessage;
import exceptions.UserException;
import models.Currency;

public class CurrencyService {
	
	CurrencyDao curDao;
	
	public CurrencyService(CurrencyDao curDao) {
		this.curDao = curDao;
	}
	
	
	public Currency create(String Code, String FullName, String Sign) {
		Currency currency = new Currency(Code,FullName,Sign);
		curDao.create(currency);
		return get(currency.getCode());
	}
	
	public Currency get(String Code) {
		Currency currency = curDao.get(Code).orElse(null);
		if(currency==null) {
			throw new UserException(ExceptionMessage.CURRENCY_NOT_FOUND);
		}
			return currency;
	}
	
	public List<Currency> getAll() {
		List<Currency> currencies = curDao.getAll();
		return currencies;		
	}
	
}
