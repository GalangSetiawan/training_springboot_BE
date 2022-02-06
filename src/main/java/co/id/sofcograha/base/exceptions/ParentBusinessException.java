package co.id.sofcograha.base.exceptions;

public class ParentBusinessException extends RuntimeException {

	private static final long serialVersionUID = -7624946615707215106L;

	protected ParentBusinessException() {
		super();
	}

	protected ParentBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	protected ParentBusinessException(String message) {
		super(message);
	}

	protected ParentBusinessException(Throwable cause) {
		super(cause);
	}
}