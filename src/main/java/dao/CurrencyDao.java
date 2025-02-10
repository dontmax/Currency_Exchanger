package dao;

import java.util.List;
import java.util.Optional;

import models.Currency;

public interface CurrencyDao {
	
	public void create(Currency value);
	public Optional<Currency> get(String value);
	public Optional<Currency> get(int id);
	public List <Currency> getAll();
	public void delete(String Code);
}
