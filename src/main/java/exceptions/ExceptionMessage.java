package exceptions;

import jakarta.servlet.http.HttpServletResponse;

public enum ExceptionMessage {
	
	SUCCESS("Success",HttpServletResponse.SC_OK),
	EMPTY_FORM("Requared form field is missing",HttpServletResponse.SC_BAD_REQUEST),//400
	CURRENCY_NOT_FOUND("Currency Not Found", HttpServletResponse.SC_NOT_FOUND),//404
	EXCHANGE_RATE_NOT_FOUND("ExchangeRate Not Found",HttpServletResponse.SC_NOT_FOUND),//404
	CURRENCY_EXISTS("Currency already exists",  HttpServletResponse.SC_CONFLICT),//409
	EXCHANGE_RATE_EXISTS("ExchangeRate already exists",HttpServletResponse.SC_CONFLICT),//409
	DATABASE_EXCEPTION("Database is not answering",HttpServletResponse.SC_INTERNAL_SERVER_ERROR),//500
	WRONG_CODE("Code is not matching ISO 4217 standart",HttpServletResponse.SC_BAD_REQUEST),//400
	WRONG_FULL_NAME("FullName begins with upperCase and contains no more then 50 symbols",HttpServletResponse.SC_BAD_REQUEST),
	WRONG_SIGN("Sign is not correct",HttpServletResponse.SC_BAD_REQUEST),
	WRONG_RATE("Invalid Rate value",HttpServletResponse.SC_BAD_REQUEST),
	WRONG_VALUE("Invalid amount value",HttpServletResponse.SC_BAD_REQUEST);
	private final String message;
	private final int status;
	ExceptionMessage(String message, int status) {
		this.message=message;
		this.status=status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getStatus() {
		return status;
	}
	
}
