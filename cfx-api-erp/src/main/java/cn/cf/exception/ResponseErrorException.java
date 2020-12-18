package cn.cf.exception;

public class ResponseErrorException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResponseErrorException() {
	}

	public ResponseErrorException(String message) {
		super(message);
	}

	public ResponseErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResponseErrorException(Throwable cause) {
		super(cause);
	}
}
