package utils;


public class NumberRound {
	
	public static String round(String number) {
		return (number.contains(".")&&(number.indexOf(".")+1)>2)?
				number.substring(0, number.indexOf(".")+2):
				number;
	}
}
