package servlets;

import java.sql.SQLException;

import dao.CurrencyDao;
import dao.CurrencyDaoImpl;
import dao.ExchangeRateDao;
import dao.ExchangeRateDaoImpl;
import db.DataLoader;
import db.DataSourceUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import services.CurrencyService;
import services.ExchangeRateService;

@WebListener
public class DataInitializer implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce)  {
    	
        CurrencyDao curDao = new CurrencyDaoImpl();
		CurrencyService curService = new CurrencyService(curDao);
		ExchangeRateDao exDao = new ExchangeRateDaoImpl();
		DataLoader load = new DataLoader(curDao, exDao);
		try {
			load.loadData();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		ExchangeRateService exService = new ExchangeRateService(exDao);
		ServletContext context = sce.getServletContext();
		context.setAttribute("curService", curService);
		context.setAttribute("exService", exService);
    }
    
    public void contextDestroyed(ServletContextEvent sce)  {
    	DataSourceUtil.shutdown();
    	DataSourceUtil.deregisterJdbcDrivers();
    }
	
}
