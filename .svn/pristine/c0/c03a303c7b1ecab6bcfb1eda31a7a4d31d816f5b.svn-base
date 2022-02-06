package co.id.sofcograha.base.exceptions;

public class BusinessException extends ParentBusinessException {

	private static final long serialVersionUID = 627510061490272053L;
	
	private String code;
	private Object[] args;
	private Object hint;

	public BusinessException(String code, Object... args) {
		super(code);
		this.code = code;
		this.args = args;
	}

	public String getMessageCode() {
		return this.code;
	}

	public Object[] getMessageParameters() {
		return this.args;
	}

	public BusinessException setHint(Object hint) {
		this.hint = hint;
		return this;
	}

	public Object getHint() {
		return this.hint;
	}
}
