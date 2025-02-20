package dao;

import java.util.List;
import java.util.Optional;

import dto.ExchangeRateDTO;
import models.ExchangeRate;

public interface  ExchangeRateDao {
	
	void create(ExchangeRate value);
	Optional<ExchangeRateDTO> get(String baseCode, String targetCode);
	List <ExchangeRateDTO> getAll();
	void update(ExchangeRate value);
}
