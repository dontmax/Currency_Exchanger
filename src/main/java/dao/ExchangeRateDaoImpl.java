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
import exceptions.DatabaseException;
import exceptions.ExceptionMessage;
import models.ExchangeRate;

import java.math.BigDecimal;

public class ExchangeRateDaoImpl implements ExchangeRateDao {
	
	public ExchangeRateDaoImpl() {}
	
	
	@Override
	public void create(ExchangeRate	exchangeRate) {
		try(Connection conn = DataSourceUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(ExchangeRateQueries.CREATE_EXCHANGE_RATE);
			){
			setStmt(pstmt, exchangeRate);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			throw new DatabaseException(ExceptionMessage.DATABASE_EXCEPTION);
		}
	}

	@Override
	public Optional<ExchangeRate> get(int BaseID, int TargetID) {
		try(Connection conn = DataSourceUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(ExchangeRateQueries.GET_EXCHANGE_RATE);
			){
			pstmt.setInt(1, BaseID);
			pstmt.setInt(2, TargetID);
			ExchangeRate exchangeRate = null;;
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
	public List<ExchangeRate> getAll(){
		try(Connection conn = DataSourceUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(ExchangeRateQueries.GET_ALL_EXCHANGE_RATES);
			){
			List<ExchangeRate> exchangeRates= new ArrayList<>();
			ResultSet rs = pstmt.executeQuery();
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

	@Override
	public void delete(int BaseID, int TargetID) {
		try(Connection conn = DataSourceUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(ExchangeRateQueries.DELETE_EXCHANGE_RATE);
			){
			pstmt.setInt(1, BaseID);
			pstmt.setInt(2, TargetID);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			throw new DatabaseException(ExceptionMessage.DATABASE_EXCEPTION);
		}
	}
	
	private void setStmt(PreparedStatement pstmt, ExchangeRate exchangeRate) throws SQLException {
		pstmt.setString(1, exchangeRate.getRate().toString());
		pstmt.setInt   (2, exchangeRate.getBaseID());
		pstmt.setInt   (3, exchangeRate.getTargetID());
	}
	
	private ExchangeRate getFrmRs(ResultSet rs) throws SQLException {
		return new ExchangeRate(rs.getInt("ID"),
								rs.getInt("BaseCurrencyID"),
								rs.getInt("TargetCurrencyID"),
				  new BigDecimal(rs.getString("Rate")));
	}


}
