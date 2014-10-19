package udo.util.exceptions;

public class WritingToStorageFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 76626833108912570L;

	public WritingToStorageFailedException() {
	}

	public WritingToStorageFailedException(String message) {
		super(message);

	}

	public WritingToStorageFailedException(Throwable cause) {
		super(cause);

	}

	public WritingToStorageFailedException(String message, Throwable cause) {
		super(message, cause);

	}

	public WritingToStorageFailedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
