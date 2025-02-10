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

import dto.ExchangeDTO;
import dto.ExchangeRateDTO;
import exceptions.DatabaseException;
import exceptions.ExceptionMessage;
import exceptions.UserException;


@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ServletContext context;
    ExchangeRateService exService;
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ExchangeServlet() {
        super();
    }


    public void init() throws ServletException {
		context = getServletContext();
		exService =(ExchangeRateService)context.getAttribute("exService");
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String BaseCode = request.getParameter("from");
		String TargetCode = request.getParameter("to");
		String amount = request.getParameter("amount");
		try {
			if(!UserValidation.isCode(BaseCode)||!UserValidation.isFullName(TargetCode)) {
				throw new UserException(ExceptionMessage.WRONG_CODE);
			}
			if(!UserValidation.isNumber(amount)) {
				throw new UserException(ExceptionMessage.WRONG_VALUE);
			}
			ExchangeRateDTO exR = exService.get(BaseCode, TargetCode);
			ExchangeDTO dto = new ExchangeDTO(
					exR.getBaseCurrency(),
					exR.getTargetCurrency(),
					exR.getRate(),
					new BigDecimal(amount),
					new BigDecimal(amount).multiply(exR.getRate())
					);
			String json = gson.toJson(dto);
			response.getWriter().write(json);
		} catch (UserException|DatabaseException e) {
			ExceptionHandler.sendError(e.getStatus(), e.getMessage(), response);

		}
		
	}

}
