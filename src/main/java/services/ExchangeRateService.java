package services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import dao.ExchangeRateDao;
import dto.ExchangeRateDTO;
import exceptions.ExceptionMessage;
import exceptions.UserException;
import models.ExchangeRate;

public class ExchangeRateService {
	
	ExchangeRateDao ExRDao;
	
	public ExchangeRateService(ExchangeRateDao ExDao) {
		this.ExRDao=ExDao;
	}
	
	public ExchangeRateDTO create(String baseCode, String targetCode, BigDecimal Rate) {
			ExRDao.create(new ExchangeRate(baseCode, targetCode, Rate));
			return get(baseCode, targetCode);		
	}
	
	public ExchangeRateDTO get(String baseCode, String targetCode) {
		ExchangeRateDTO exchangeRate = ExRDao.get(baseCode, targetCode).orElse(null);
		if(exchangeRate!=null) {
			return exchangeRate;
		}
		BigDecimal Rate = new BigDecimal(0);
		if(!(Rate=rvrsRateSearch(baseCode, targetCode)).equals(BigDecimal.ZERO)) {
			return  create(baseCode, targetCode, Rate);
		}
		if(!(Rate=ByUSDRateSearch(baseCode,targetCode)).equals(BigDecimal.ZERO)) {
			return  create(baseCode, targetCode, Rate);
		}
		throw new UserException(ExceptionMessage.EXCHANGE_RATE_NOT_FOUND);	
	}
	
	public List<ExchangeRateDTO> getAll() {
		List<ExchangeRateDTO> exchangeRates = ExRDao.getAll();
		if(exchangeRates.size()==0) {
			throw new UserException(ExceptionMessage.EXCHANGE_RATE_NOT_FOUND);
		}
			return exchangeRates;
	}
	
	public ExchangeRateDTO update(String baseCode, String targetCode, BigDecimal rate) {	
		ExRDao.update(new ExchangeRate(baseCode, targetCode, rate));
		return get(baseCode, targetCode);
	}

	public BigDecimal rvrsRateSearch(String baseCode, String targetCode) {
		ExchangeRateDTO exchangeRate;
		if((exchangeRate = ExRDao.get(targetCode, baseCode).orElse(null))!=null) {
			return BigDecimal.ONE.divide(exchangeRate.getRate(), MathContext.DECIMAL128);
		}
		return BigDecimal.ZERO;
	}
	
	public BigDecimal ByUSDRateSearch(String baseCode, String targetCode) {
		ExchangeRateDTO USD_A_ExR=ExRDao.get("USD", baseCode).orElse(null);
		ExchangeRateDTO USD_B_ExR=ExRDao.get("USD", targetCode).orElse(null);
		if(USD_A_ExR!=null&&USD_B_ExR!=null) {
			return USD_A_ExR.getRate().divide(USD_B_ExR.getRate(), MathContext.DECIMAL128);
		} else return BigDecimal.ZERO;
		
	}
}
