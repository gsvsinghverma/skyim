package com.adv.exception;

public class DataNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String cause;

	public DataNotFoundException(String cause) {
		super(cause);
		this.cause = cause;
	}
	
	@Override
	public String getMessage() {
		return cause;
	}

}