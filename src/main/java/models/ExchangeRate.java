package models;

import java.math.BigDecimal;

public class ExchangeRate {
	
	private int id;
	private String baseCode;
	private String targetCode;
	private BigDecimal rate;
	
	public ExchangeRate(int id, String baseCode, String targetCode, BigDecimal rate) {
		this.id=id;
		this.baseCode=baseCode;
		this.targetCode=targetCode;
		this.rate=rate;
	}
	
	public ExchangeRate(String baseCode, String targetCode, BigDecimal rate) {
		this.baseCode=baseCode;
		this.targetCode=targetCode;
		this.rate=rate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBaseCode() {
		return baseCode;
	}

	public void setBaseCode(String baseCode) {
		this.baseCode = baseCode;
	}

	public String getTargetCode() {
		return targetCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
}
