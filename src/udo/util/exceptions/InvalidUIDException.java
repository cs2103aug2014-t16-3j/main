//@author A0108358B
package udo.util.exceptions;

public class InvalidUIDException extends Exception {
	
	private static final long serialVersionUID = -4071625216471665775L;

	public InvalidUIDException() {
	}

	public InvalidUIDException(String message) {
		super(message);

	}

	public InvalidUIDException(Throwable cause) {
		super(cause);

	}

	public InvalidUIDException(String message, Throwable cause) {
		super(message, cause);

	}

	public InvalidUIDException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
