package exception;

public class SessionExpiredException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SessionExpiredException() {
		super("세션 만료");
	}

	public SessionExpiredException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public SessionExpiredException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SessionExpiredException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SessionExpiredException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
}
