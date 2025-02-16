package dao;

import java.util.List;
import java.util.Optional;

import dto.ExchangeRateDTO;
import models.ExchangeRate;

public interface  ExchangeRateDao {
	
	public void create(ExchangeRate value);
	public Optional<ExchangeRateDTO> get(String baseCode, String targetCode);
	public List <ExchangeRateDTO> getAll();
	public void update(ExchangeRate value);
}
