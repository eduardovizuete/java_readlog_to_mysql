package com.ef;

public class ParseLogException extends Exception {

	private static final long serialVersionUID = 1L;

	public ParseLogException() {}

	public ParseLogException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseLogException(String message) {
		super(message);
	}

	public ParseLogException(Throwable cause) {
		super(cause);
	}

}
