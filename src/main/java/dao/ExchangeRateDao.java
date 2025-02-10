package dao;

import java.util.List;
import java.util.Optional;

import models.ExchangeRate;

public interface  ExchangeRateDao {
	
	public void create(ExchangeRate value);
	public Optional<ExchangeRate> get(int BaseID, int TargetID);
	public List <ExchangeRate> getAll();
	public void update(ExchangeRate value);
	public void delete(int BaseID, int TargetID);
}
