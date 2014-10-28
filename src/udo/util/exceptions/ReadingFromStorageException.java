package udo.util.exceptions;

public class ReadingFromStorageException extends Exception {
	
	private static final long serialVersionUID = -8581536426563241650L;
	
	public ReadingFromStorageException() {
	}

	public ReadingFromStorageException(String message) {
		super(message);

	}

	public ReadingFromStorageException(Throwable cause) {
		super(cause);

	}

	public ReadingFromStorageException(String message, Throwable cause) {
		super(message, cause);

	}

	public ReadingFromStorageException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
