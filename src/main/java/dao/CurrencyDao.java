package dao;

import java.util.List;
import java.util.Optional;

import models.Currency;

public interface CurrencyDao {
	
	void create(Currency value);
	Optional<Currency> get(String code);
	List <Currency> getAll();
}
