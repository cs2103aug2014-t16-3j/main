package udo.util.exceptions;

public class WritingToStorageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 76626833108912570L;

	public WritingToStorageException() {
	}

	public WritingToStorageException(String message) {
		super(message);

	}

	public WritingToStorageException(Throwable cause) {
		super(cause);

	}

	public WritingToStorageException(String message, Throwable cause) {
		super(message, cause);

	}

	public WritingToStorageException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
