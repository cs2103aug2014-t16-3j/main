//@author A0108358B
package udo.exceptions;

public class AddToCacheException extends Exception {

	private static final long serialVersionUID = 3432337392350347299L;

	public AddToCacheException() {
	}

	public AddToCacheException(String message) {
		super(message);

	}

	public AddToCacheException(Throwable cause) {
		super(cause);

	}

	public AddToCacheException(String message, Throwable cause) {
		super(message, cause);

	}

	public AddToCacheException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
