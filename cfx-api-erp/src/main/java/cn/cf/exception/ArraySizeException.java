package cn.cf.exception;

public class ArraySizeException extends RuntimeException{
	
	
	
	private static final long serialVersionUID = -5893591181036967054L;

	public ArraySizeException() {
	}

	public ArraySizeException(String message) {
		super(message);
	}

	public ArraySizeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArraySizeException(Throwable cause) {
		super(cause);
	}
}
