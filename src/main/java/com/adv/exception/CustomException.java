package com.adv.exception;

public class CustomException extends Throwable{

	private static final long serialVersionUID = 1L;

	public CustomException(String string) {
		super(string);
	}

	public CustomException(String string, Exception e) {
		super(string, e);
	}
}