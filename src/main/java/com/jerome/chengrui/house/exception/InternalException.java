package com.jerome.chengrui.house.exception;

public class InternalException extends Exception {

	private static final long serialVersionUID = 1L;

	public InternalException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalException(String message) {
		super(message);
	}

	public InternalException(Throwable cause) {
		super(cause);
	}
	
}
