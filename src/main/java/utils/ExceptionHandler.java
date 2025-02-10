package utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.http.HttpServletResponse;

public class ExceptionHandler {
	
	public static void sendError(int status,String message, HttpServletResponse response) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		response.setStatus(status);
		Map<String,String> exceptionResponse = new HashMap<>();
		exceptionResponse.put("message", message);
		response.getWriter().write( gson.toJson(exceptionResponse));
		
	}
}
