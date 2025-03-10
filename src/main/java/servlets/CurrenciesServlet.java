package servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.CurrencyService;
import utils.ExceptionHandler;
import utils.UserValidation;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import exceptions.DatabaseException;
import exceptions.ExceptionMessage;
import exceptions.UserException;


@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    CurrencyService curService;
    ServletContext context;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public CurrenciesServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	context = getServletContext();
    	curService =(CurrencyService) context.getAttribute("curService");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String json = gson.toJson(curService.getAll());
			response.getWriter().write(json);			
		} catch (UserException |DatabaseException e) {
			ExceptionHandler.sendError(e.getStatus(), e.getMessage(), response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		String fullName = request.getParameter("name");
		String sign = request.getParameter("sign");
		try {
			if(!UserValidation.isCode(code)) {
				throw new UserException(ExceptionMessage.WRONG_CODE);
			}
			if(!UserValidation.isFullName(fullName)){
				throw new UserException(ExceptionMessage.WRONG_FULL_NAME);
			}
			if(!UserValidation.isSign(sign)) {
				throw new UserException(ExceptionMessage.WRONG_SIGN);
			}
			String json = gson.toJson(curService.create(code, fullName, sign));
			response.getWriter().write(json);	
		} catch (UserException |DatabaseException e) {
			ExceptionHandler.sendError(e.getStatus(), e.getMessage(), response);
		} 
	}
}
