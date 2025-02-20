package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.DataSourceUtil;
import db.ExchangeRateQueries;
import dto.ExchangeRateDTO;
import exceptions.DatabaseException;
import exceptions.ExceptionMessage;
import exceptions.UserException;
import models.Currency;
import models.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeRateDaoImpl implements ExchangeRateDao {
	
	@Override
	public void create(ExchangeRate	exchangeRate) {
		try(Connection conn = DataSourceUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(ExchangeRateQueries.CREATE_EXCHANGE_RATE);
			){
			setStmt(pstmt, exchangeRate);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			if(e.getMessage().contains("UNIQUE constraint failed")) {
				throw new UserException(ExceptionMessage.EXCHANGE_RATE_EXISTS);
			}
			throw new DatabaseException(ExceptionMessage.DATABASE_EXCEPTION);
		}
	}

	@Override
	public Optional<ExchangeRateDTO> get(String baseCode, String targetCode) {
		try(Connection conn = DataSourceUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(ExchangeRateQueries.GET_EXCHANGE_RATE);
			){
			pstmt.setString(1, baseCode);
			pstmt.setString(2, targetCode);
			ExchangeRateDTO exchangeRate = null;;
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				exchangeRate=getFrmRs(rs);
			}
			return Optional.ofNullable(exchangeRate);
		} catch(SQLException e) {
			throw new DatabaseException(ExceptionMessage.DATABASE_EXCEPTION);
		}
	}

	@Override
	public List<ExchangeRateDTO> getAll(){
		try(Connection conn = DataSourceUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(ExchangeRateQueries.GET_ALL_EXCHANGE_RATES);
			ResultSet rs = pstmt.executeQuery();
			){
			List<ExchangeRateDTO> exchangeRates= new ArrayList<>();
			while(rs.next()) {
				exchangeRates.add(getFrmRs(rs));
			}
			return exchangeRates;
		} catch(SQLException e) {
			throw new DatabaseException(ExceptionMessage.DATABASE_EXCEPTION);
		}
	}

	@Override
	public void update(ExchangeRate exchangeRate){
		try(Connection conn = DataSourceUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(ExchangeRateQueries.UPDATE_EXCHANGE_RATE);
			){
			setStmt(pstmt, exchangeRate);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			throw new DatabaseException(ExceptionMessage.DATABASE_EXCEPTION);
		}
	}
	
	private void setStmt(PreparedStatement pstmt, ExchangeRate exchangeRate) throws SQLException {
		pstmt.setString(1, exchangeRate.getRate().toString());
		pstmt.setString(2, exchangeRate.getBaseCode());
		pstmt.setString(3, exchangeRate.getTargetCode());

	}
	
	private ExchangeRateDTO getFrmRs(ResultSet rs) throws SQLException {
	    return new ExchangeRateDTO(
	        rs.getInt("Ex_ID"),
	        new Currency(
	            rs.getInt("BaseID"),
	            rs.getString("BaseCode"),
	            rs.getString("BaseFullName"),
	            rs.getString("BaseSign")
	        ),
	        new Currency(
	            rs.getInt("TargetID"),
	            rs.getString("TargetCode"),
	            rs.getString("TargetFullName"),
	            rs.getString("TargetSign")
	        ),
	        new BigDecimal(rs.getString("Rate")).setScale(2,RoundingMode.HALF_DOWN)
	    );
	}
	
}
