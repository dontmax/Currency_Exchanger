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
		if(curDao.get(currency.getCode()).orElse(null)!=null) {
			throw new UserException(ExceptionMessage.CURRENCY_EXISTS);		
		}
		curDao.create(currency);
		return get(currency.getCode());
	}
	
	public Currency get(String Code) {System.out.println("currencyService: get method");
		Currency currency = curDao.get(Code).orElse(null);
		if(currency==null) {
			throw new UserException(ExceptionMessage.CURRENCY_NOT_FOUND);
		}
			return currency;
	}
	
	public Currency get(int id) {System.out.println("currencyService: get method");
		Currency currency = curDao.get(id).orElse(null);
		if(currency==null) {
			throw new UserException(ExceptionMessage.CURRENCY_NOT_FOUND);
		}
		return currency;
	}
	
	public List<Currency> getAll() {System.out.println("currencyService: getAll method");
		List<Currency> currencies = curDao.getAll();
		if(currencies.size()==0) {
			throw new UserException(ExceptionMessage.CURRENCY_NOT_FOUND);
		}
			return currencies;		
	}
	
	public void delete(String Code) {System.out.println("currencyService: Delete method");
		if(curDao.get(Code).orElse(null)!=null) {
			curDao.delete(Code);
		}
	}
}
