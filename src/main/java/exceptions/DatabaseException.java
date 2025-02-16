package exceptions;

public class DatabaseException extends RuntimeException implements StatusInfo {
	
	private static final long serialVersionUID = -1122450044095711761L;
	private int status;
	
	public DatabaseException(ExceptionMessage message) {
		super(message.getMessage());
		this.status=message.getStatus();
	}
	
	public DatabaseException(ExceptionMessage message, Throwable cause) {
		super(message.getMessage(), cause);
		this.status=message.getStatus();
	}
	public int getStatus() {
		return status;
	}
	
}
