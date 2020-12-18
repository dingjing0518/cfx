package cn.cf.exception;

public class ErrorParameterException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ErrorParameterException(){
		super();
	}

	public ErrorParameterException(String msg){
		super(msg);
	}

}
