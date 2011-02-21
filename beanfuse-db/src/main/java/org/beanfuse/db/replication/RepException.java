package org.beanfuse.db.replication;

public class RepException extends RuntimeException {

	public RepException() {
		super();
	}

	public RepException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepException(String message) {
		super(message);
	}

	public RepException(Throwable cause) {
		super(cause);
	}

}
