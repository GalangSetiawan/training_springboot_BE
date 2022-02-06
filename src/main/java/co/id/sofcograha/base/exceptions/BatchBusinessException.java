package co.id.sofcograha.base.exceptions;

import java.util.ArrayList;

public class BatchBusinessException extends ParentBusinessException {
	
	private static final long serialVersionUID = 6596681048701298511L;
	
	private ArrayList<BusinessException> businessExceptions;
	private Object data;

	public BatchBusinessException() {
		businessExceptions = new ArrayList<BusinessException>();
	}

	public BatchBusinessException(Object data) {
		businessExceptions = new ArrayList<BusinessException>();
		this.data = data;
	}

	public static BatchBusinessException create() {
		return new BatchBusinessException();
	}

	public BatchBusinessException addBusinessException(BusinessException e) {
		this.businessExceptions.add(e);
		return this;
	}

	public void throwBatchBusinessException(){
		if (!businessExceptions.isEmpty()) {
			throw this;
		}
	}

	public ArrayList<BusinessException> getBusinessExceptions() {
		return businessExceptions;
	}

	public void setBusinessExceptions(ArrayList<BusinessException> businessExceptions) {
		this.businessExceptions = businessExceptions;
	}

	public Object getData() {
		return data;
	}

}