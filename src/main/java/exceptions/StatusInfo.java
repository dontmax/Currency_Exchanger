package exceptions;

import java.util.Map;
import java.util.HashMap;

public interface StatusInfo {
	Map<String, String> message = new HashMap<String,String>();
	public int getStatus();
}
