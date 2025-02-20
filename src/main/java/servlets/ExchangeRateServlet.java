package servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
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


@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ServletContext context;
    ExchangeRateService exService;
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
    public ExchangeRateServlet() {
        super();
    }
	
	public void init() throws ServletException {
		context = getServletContext();
		exService =(ExchangeRateService)context.getAttribute("exService");
	}

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase("PATCH")) {
        	doPatch(req,resp);
        } else {
            super.service(req, resp);
        }

    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String Codes = request.getPathInfo().substring(1);
		String BaseCode = Codes.substring(0, 3);
		String TargetCode = Codes.substring(3);
		try {		
			if(!UserValidation.isCode(BaseCode)||!UserValidation.isCode(TargetCode)) {
				throw new UserException(ExceptionMessage.WRONG_CODE);
			}
				String json = gson.toJson(exService.get(BaseCode, TargetCode));
				response.getWriter().write(json);
		} catch(UserException|DatabaseException e) {
			ExceptionHandler.sendError(e.getStatus(), e.getMessage(), response);
		}
	}
	
	protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String Codes =request.getPathInfo().substring(1);
		String BaseCode = Codes.substring(0, 3);
		String TargetCode = Codes.substring(3);
		StringBuilder stringBuilder = new StringBuilder();
		int line;
		ServletInputStream reader = request.getInputStream();
		while((line = reader.read())!= -1) {
			stringBuilder.append((char)line);
		}
		String Rate = stringBuilder.toString().substring(stringBuilder.indexOf("=")+1);
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
			String json = gson.toJson(exService.update(BaseCode, TargetCode, new BigDecimal(Rate)));
			response.getWriter().write(json);
		} catch(UserException|DatabaseException e) {
			ExceptionHandler.sendError(e.getStatus(), e.getMessage(), response);
		}
		
	}
	
	
}
