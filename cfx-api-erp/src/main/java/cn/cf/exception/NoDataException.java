package cn.cf.exception;

public class NoDataException extends RuntimeException{

	
	private static final long serialVersionUID = 2809762358841152206L;
	
	public NoDataException(String message) {
		super(message);
	}

	public NoDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoDataException(Throwable cause) {
		super(cause);
	}
	
	
	
	public NoDataException() {
	}



}
