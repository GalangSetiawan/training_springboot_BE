package co.id.sofcograha.base.exceptions;

public class ConversionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1611401679553715126L;
	
	public Object hint;
	
	public ConversionException(String message, Object... hint) {
		super(message);
		this.hint = hint;
	}
	
	public ConversionException(String message, Throwable cause, Object... hint) {
		super(message, cause);
		this.hint = hint;
	}
}