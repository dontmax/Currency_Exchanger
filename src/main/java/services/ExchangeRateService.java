package services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import dao.ExchangeRateDao;
import dto.ExchangeRateDTO;
import exceptions.ExceptionMessage;
import exceptions.UserException;
import models.Currency;
import models.ExchangeRate;

public class ExchangeRateService {
	
	CurrencyService curService;
	ExchangeRateDao ExRDao;
	
	//WE SHOULD NOT CARE ABOUT CURRENCY EXCEPTIONS (CURRENCYSERVICE TAKES CARE OF IT)
	public ExchangeRateService(CurrencyService currencyService, ExchangeRateDao ExDao) {
		this.curService=currencyService;
		this.ExRDao=ExDao;
	}
	
	
	public ExchangeRateDTO create(String BaseCode, String TargetCode, BigDecimal Rate) {
		Currency BsCurrency = curService.get(BaseCode);
		Currency TrgtCurrency = curService.get(TargetCode);
		ExchangeRate exchangeRate = ExRDao.get(BsCurrency.getId(), TrgtCurrency.getId()).orElse(null);
		if(exchangeRate!=null) {
			throw new UserException(ExceptionMessage.EXCHANGE_RATE_EXISTS);
		}
			ExRDao.create(new ExchangeRate(BsCurrency.getId(),TrgtCurrency.getId(),Rate));
			return get(BaseCode, TargetCode);		
	}
	
	public ExchangeRateDTO get(String BaseCode, String TargetCode) {
		Currency BsCurrency = curService.get(BaseCode);
		Currency TrgtCurrency = curService.get(TargetCode);
		ExchangeRate exchangeRate = ExRDao.get(BsCurrency.getId(), TrgtCurrency.getId()).orElse(null);
		if(exchangeRate!=null) {
			return toDTO(exchangeRate,BsCurrency, TrgtCurrency);
		}
		BigDecimal Rate = new BigDecimal(0);
		if(!(Rate=rvrsRateSearch(BsCurrency.getId(),TrgtCurrency.getId())).equals(BigDecimal.ZERO)) {
			return  create(BaseCode, TargetCode, Rate);
		}
		if(!(Rate=ByUSDRateSearch(BsCurrency.getId(),TrgtCurrency.getId())).equals(BigDecimal.ZERO)) {
			return  create(BaseCode, TargetCode, Rate);
		}
		throw new UserException(ExceptionMessage.EXCHANGE_RATE_NOT_FOUND);	
	}
	
	public List<ExchangeRateDTO> getAll() {
		List<ExchangeRate> exchangeRates = ExRDao.getAll();

		if(exchangeRates.size()==0) {
			throw new UserException(ExceptionMessage.EXCHANGE_RATE_NOT_FOUND);
		}
		List<ExchangeRateDTO> exchangeRateDTOs = new ArrayList<>();	
		for(ExchangeRate exchangeRate:exchangeRates) {
			exchangeRateDTOs.add(new ExchangeRateDTO(exchangeRate.getId(),
					curService.get(exchangeRate.getBaseID()),
					curService.get(exchangeRate.getTargetID()),
					exchangeRate.getRate()));
		}
			return exchangeRateDTOs;
	}
	
	public ExchangeRateDTO update(String BaseCode, String TargetCode, BigDecimal Rate) {	
		ExchangeRateDTO exchangeRate = get(BaseCode, TargetCode);
		exchangeRate.setRate(Rate);	
		ExRDao.update(new ExchangeRate(
				exchangeRate.getId(),
				exchangeRate.getBaseCurrency().getId(),
				exchangeRate.getTargetCurrency().getId(),
				exchangeRate.getRate()));
		return get(BaseCode, TargetCode);
	}
	//Maybe better not to check for being, but catch deleteException
	public void delete(String BaseCode, String TargetCode) {
		ExchangeRateDTO exchangeRate = get(BaseCode, TargetCode);
		ExRDao.delete(exchangeRate.getBaseCurrency().getId(),
					  exchangeRate.getTargetCurrency().getId());
	}
	
	public ExchangeRateDTO toDTO(ExchangeRate exchangeRate, Currency BsCurrency, Currency TrgtCurrency) {
		return new ExchangeRateDTO(exchangeRate.getId(),BsCurrency,TrgtCurrency,exchangeRate.getRate());
	}
	
	public BigDecimal rvrsRateSearch(int BaseID, int TargetID) {
		ExchangeRate exchangeRate;
		if((exchangeRate = ExRDao.get(TargetID, BaseID).orElse(null))!=null) {
			return BigDecimal.ONE.divide(exchangeRate.getRate(), MathContext.DECIMAL128);
		}
		return BigDecimal.ZERO;
	}
	
	public BigDecimal ByUSDRateSearch(int BaseID, int TargetID) {
		int USD_ID = curService.get("USD").getId();
		ExchangeRate USD_A_ExR=ExRDao.get(USD_ID, BaseID).orElse(null);
		ExchangeRate USD_B_ExR=ExRDao.get(USD_ID, TargetID).orElse(null);
		if(USD_A_ExR!=null&&USD_B_ExR!=null) {
			return USD_A_ExR.getRate().divide(USD_B_ExR.getRate(), MathContext.DECIMAL128);
		} else return BigDecimal.ZERO;
		
	}
}
