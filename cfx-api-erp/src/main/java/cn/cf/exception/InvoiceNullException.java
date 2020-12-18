package cn.cf.exception;

public class InvoiceNullException extends RuntimeException{

	
	private static final long serialVersionUID = -7365913596459555L;

	public InvoiceNullException() {
	}

	public InvoiceNullException(String message) {
		super(message);
	}

	public InvoiceNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvoiceNullException(Throwable cause) {
		super(cause);
	}
}
