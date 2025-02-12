package servlets;

import java.sql.SQLException;

import dao.CurrencyDao;
import dao.CurrencyDaoImpl;
import dao.ExchangeRateDao;
import dao.ExchangeRateDaoImpl;
import db.DataLoader;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import services.CurrencyService;
import services.ExchangeRateService;

/**
 * Application Lifecycle Listener implementation class DataInitializer
 *
 */
@WebListener
public class DataInitializer implements ServletContextListener {
	
	
	
    public DataInitializer() {

		
    }

    public void contextInitialized(ServletContextEvent sce)  {
    	
        CurrencyDao curDao = new CurrencyDaoImpl();
		CurrencyService curService = new CurrencyService(curDao);
		ExchangeRateDao exDao = new ExchangeRateDaoImpl();
		DataLoader load = new DataLoader(curDao, exDao);
		try {
		load.createTables();
		load.loadInitialData();
		} catch(SQLException e) {
			
		}
		ExchangeRateService exService = new ExchangeRateService(curService, exDao);
		ServletContext context = sce.getServletContext();
		context.setAttribute("curService", curService);
		context.setAttribute("exService", exService);
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    }
	
}
