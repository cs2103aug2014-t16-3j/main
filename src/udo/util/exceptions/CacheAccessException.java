package udo.util.exceptions;

public class CacheAccessException extends Exception {

	private static final long serialVersionUID = 6438275789275587451L;

	public CacheAccessException() {
	}

	public CacheAccessException(String message) {
		super(message);

	}

	public CacheAccessException(Throwable cause) {
		super(cause);

	}

	public CacheAccessException(String message, Throwable cause) {
		super(message, cause);

	}

	public CacheAccessException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
