package com.schoolregistration.exception;

public abstract class ConsumerException extends RuntimeException {

	private static final long serialVersionUID = -4175761837286179566L;

	public ConsumerException(final String message) {
		super(message);
	}
	
}
