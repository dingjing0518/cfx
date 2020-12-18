package cn.cf.exception;

public class FinanceRecordsNullException extends RuntimeException{

	
	private static final long serialVersionUID = -7365913596459555L;

	public FinanceRecordsNullException() {
	}

	public FinanceRecordsNullException(String message) {
		super(message);
	}

	public FinanceRecordsNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public FinanceRecordsNullException(Throwable cause) {
		super(cause);
	}
}
