package cn.cf.exception;

public class NetWorkException extends RuntimeException {

	private static final long serialVersionUID = -9079010183705787652L;

	public NetWorkException() {
	}

	public NetWorkException(String message) {
		super(message);
	}

	public NetWorkException(String message, Throwable cause) {
		super(message, cause);
	}

	public NetWorkException(Throwable cause) {
		super(cause);
	}

}
