package edu.mum.core.exception;

/**
 * 业务异常基类.
 * 
 * @author mz
 */
@SuppressWarnings("serial")
public class SystemException extends Exception {

	public SystemException() {
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}
}
