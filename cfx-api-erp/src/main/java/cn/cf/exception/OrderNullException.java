package cn.cf.exception;

public class OrderNullException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5511914142315122919L;

	public OrderNullException() {
	}

	public OrderNullException(String message) {
		super(message);
	}

	public OrderNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderNullException(Throwable cause) {
		super(cause);
	}
}
