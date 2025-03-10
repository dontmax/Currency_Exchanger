package exceptions;

public class UserException extends RuntimeException implements StatusInfo {
	
	private static final long serialVersionUID = 9197990357831161988L;
	private int status;

	public UserException(ExceptionMessage message) {
		super(message.getMessage());
		this.status=message.getStatus();
	}
	
	public UserException(String message, int status) {
		super(message);
		this.status=status;
	}
	
	public int getStatus() {
		return status;
	}
	
}
