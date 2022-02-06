package co.id.sofcograha.base.exceptions;

public class CustomSearchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5304919321619326052L;
	public Object hint;

	public CustomSearchException(String message, Object... hint) {
		super(message);
		this.hint = hint;
	}

	public CustomSearchException(String message, Throwable cause, Object... hint) {
		super(message, cause);
		this.hint = hint;
	}
}
