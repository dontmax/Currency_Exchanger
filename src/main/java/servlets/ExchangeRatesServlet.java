package servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ExchangeRateService;
import utils.ExceptionHandler;
import utils.UserValidation;

import java.io.IOException;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import exceptions.DatabaseException;
import exceptions.ExceptionMessage;
import exceptions.UserException;


@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    ServletContext context;
	private ExchangeRateService exService;
	
    public ExchangeRatesServlet() {
        super();
    }
    
    public void init() throws ServletException {
    	context = getServletContext();
		exService =(ExchangeRateService)context.getAttribute("exService");
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			String json = gson.toJson(exService.getAll());
			response.getWriter().write(json);
		} catch(UserException|DatabaseException e) {
			ExceptionHandler.sendError(e.getStatus(), e.getMessage(), response);
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String BaseCode = request.getParameter("baseCurrencyCode");
		String TargetCode = request.getParameter("targetCurrencyCode");
		String Rate = request.getParameter("rate");
		try {
		if(!UserValidation.isCode(BaseCode)) {
			throw new UserException(ExceptionMessage.WRONG_CODE);
		}
		if(!UserValidation.isCode(TargetCode)){
			throw new UserException(ExceptionMessage.WRONG_CODE);
		}
		if(!UserValidation.isRate(Rate)) {
			throw new UserException(ExceptionMessage.WRONG_RATE);
		}
			String json = gson.toJson(exService.create(BaseCode, TargetCode, new BigDecimal(Rate)));
			response.getWriter().write(json);
		} catch(UserException|DatabaseException e) {
			ExceptionHandler.sendError(e.getStatus(), e.getMessage(), response);
		}
	}

}
