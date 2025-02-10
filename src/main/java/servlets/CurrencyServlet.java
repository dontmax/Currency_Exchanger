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

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//CurrencyDaoImpl curDao;
    ServletContext context;
	CurrencyService curService;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public CurrencyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {
    	context = getServletContext();
    	curService = (CurrencyService)context.getAttribute("curService");
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String Code = request.getPathInfo().substring(1);
		try {
			if(!UserValidation.isCode(Code)) {
				throw new UserException(ExceptionMessage.WRONG_CODE);
			} else {
			String json = gson.toJson(curService.get(Code));
			response.getWriter().write(json);
			}
		} catch (UserException|DatabaseException e) {
			ExceptionHandler.sendError(e.getStatus(), e.getMessage(), response);
		}
	}

}
