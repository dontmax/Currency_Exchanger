package dto;

import java.math.BigDecimal;

import models.Currency;

public class ExchangeRateDTO {
	
	private int id;
	private Currency baseCurrency;
	private Currency targetCurrency;
	private BigDecimal rate;
	
	public ExchangeRateDTO(int id, Currency baseCurrency, Currency targetCurrency, BigDecimal rate) {
		this.id=id;
		this.baseCurrency=baseCurrency;
		this.targetCurrency=targetCurrency;
		this.rate=rate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Currency getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(Currency baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public Currency getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(Currency targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
}
