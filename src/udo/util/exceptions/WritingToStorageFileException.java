package udo.util.exceptions;

public class WritingToStorageFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 76626833108912570L;

	public WritingToStorageFileException() {
	}

	public WritingToStorageFileException(String message) {
		super(message);

	}

	public WritingToStorageFileException(Throwable cause) {
		super(cause);

	}

	public WritingToStorageFileException(String message, Throwable cause) {
		super(message, cause);

	}

	public WritingToStorageFileException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
