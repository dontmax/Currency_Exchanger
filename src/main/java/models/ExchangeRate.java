package models;

import java.math.BigDecimal;

public class ExchangeRate {
	
	private int id;
	private int BaseID;
	private int TargetID;
	private BigDecimal Rate;
	
	public ExchangeRate(int id, int BaseID, int TargetID, BigDecimal Rate) {
		this.id=id;
		this.BaseID=BaseID;
		this.TargetID=TargetID;
		this.Rate=Rate;
	}
	
	public ExchangeRate(int BaseID, int TargetID, BigDecimal Rate) {
		this.BaseID=BaseID;
		this.TargetID=TargetID;
		this.Rate=Rate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBaseID() {
		return BaseID;
	}

	public void setBaseID(int BaseID) {
		this.BaseID = BaseID;
	}

	public int getTargetID() {
		return TargetID;
	}

	public void setTargetID(int TargetID) {
		this.TargetID = TargetID;
	}

	public BigDecimal getRate() {
		return Rate;
	}

	public void setRate(BigDecimal Rate) {
		this.Rate = Rate;
	}
}
