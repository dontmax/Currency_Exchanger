package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.CurrencyQueries;
import db.DataSourceUtil;
import exceptions.DatabaseException;
import exceptions.ExceptionMessage;
import models.Currency;

public class CurrencyDaoImpl implements CurrencyDao{//Exceptions and Return Statements
	
	public CurrencyDaoImpl() {}
	
	
	@Override
	public void create(Currency currency) {
		try(Connection conn = DataSourceUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CurrencyQueries.CREATE_CURRENCY);
			){
			setStmt(pstmt,currency);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(ExceptionMessage.DATABASE_EXCEPTION);
		}
	}
	
	public Optional<Currency> get(int id) {
		try(Connection conn = DataSourceUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CurrencyQueries.GET_CURRENCY_BY_ID);
			){
			pstmt.setInt(1, id);
			Currency currency = null;
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				currency = getFrmRs(pstmt.executeQuery());	
			}
			return Optional.ofNullable(currency);
		} catch(SQLException e) {
			throw new DatabaseException(ExceptionMessage.DATABASE_EXCEPTION);
		}
	}
	
	@Override
	public Optional<Currency> get(String Code) {
		try(Connection conn = DataSourceUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CurrencyQueries.GET_CURRENCY_BY_CODE);
			){
			pstmt.setString(1, Code);
			Currency currency = null;
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				currency=getFrmRs(rs);
			}
			return Optional.ofNullable(currency);
		} catch (SQLException e) {
			throw new DatabaseException(ExceptionMessage.DATABASE_EXCEPTION);
		}
	}

	@Override
	public List<Currency> getAll() {
		try(Connection conn = DataSourceUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CurrencyQueries.GET_ALL_CURRENCIES);
			){
			List<Currency> currencies= new ArrayList<>();
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				currencies.add(getFrmRs(rs));
			}
			return currencies;
			} catch (SQLException e) {
				throw new DatabaseException(ExceptionMessage.DATABASE_EXCEPTION);
			}
	}

	@Override
	public void delete(String Code) {
		try(Connection conn = DataSourceUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CurrencyQueries.DELETE_CURRENCY);
			){
			pstmt.setString(1, Code);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(ExceptionMessage.DATABASE_EXCEPTION);
		}
	}
	
	private void setStmt(PreparedStatement pstmt, Currency currency) throws SQLException {
		pstmt.setString(1, currency.getCode());
		pstmt.setString(2, currency.getFullName());
		pstmt.setString(3, currency.getSign());
	}
	
	private Currency getFrmRs(ResultSet rs) throws SQLException {
		return new Currency(rs.getInt("ID"),
							rs.getString("Code"),
							rs.getString("FullName"),
							rs.getString("Sign"));
		}

}
