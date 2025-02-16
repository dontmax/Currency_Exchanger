package dao;

import java.util.List;
import java.util.Optional;

import models.Currency;

public interface CurrencyDao {
	
	public void create(Currency value);
	public Optional<Currency> get(String code);
	public List <Currency> getAll();
}
