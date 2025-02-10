package utils;

import java.util.regex.Pattern;


public class UserValidation {

	
	public static boolean isCode(String input) {
		return Pattern.matches("^[A-Z]{3}$",input);
	}
	
	public static boolean isFullName(String input) {
		return Pattern.matches("^[A-Z][a-zA-Z -_]{1,50}", input);
	}
	
	public static boolean isSign(String input) {
		return Pattern.matches("^[^\\d]{1,4}$", input);
	}
	
	public static boolean isRate(String input) {
		return Pattern.matches("^[0-9]+(\\.[0-9]+)?$", input);
	}
	
	public static boolean isNumber(String input) {
		return Pattern.matches("^[0-9]+(\\.[0-9]+)?$", input);
	}
	
}
