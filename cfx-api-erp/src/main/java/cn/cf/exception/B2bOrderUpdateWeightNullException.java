package cn.cf.exception;

public class B2bOrderUpdateWeightNullException extends RuntimeException{
	
	
	
	private static final long serialVersionUID = -7209574991332330905L;

	public B2bOrderUpdateWeightNullException() {
	}

	public B2bOrderUpdateWeightNullException(String message) {
		super(message);
	}

	public B2bOrderUpdateWeightNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public B2bOrderUpdateWeightNullException(Throwable cause) {
		super(cause);
	}
}
