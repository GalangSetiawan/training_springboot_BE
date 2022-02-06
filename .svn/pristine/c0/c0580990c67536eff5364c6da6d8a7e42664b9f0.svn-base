package co.id.sofcograha.base.exceptions;

import java.util.LinkedHashMap;

public class CustomMultiUpdateException extends BatchBusinessException {

	private static final long serialVersionUID = -5980615353053823937L;

	private LinkedHashMap<BusinessException, BatchBusinessException> multiException;
	
	public boolean hasErrors() { 
		return multiException != null;
	}

	private void createIfEmpty() {
		if (!hasErrors()) {
			multiException = new LinkedHashMap<>();
		}
	}
	
	public void addException(int rowNumber, BatchBusinessException bbe) {
		createIfEmpty();
		multiException.put(new BusinessException("multiupdate.error", rowNumber), bbe);
	}

	public LinkedHashMap<BusinessException, BatchBusinessException> getMultiException() {
		return multiException;
	}

	public void setMultiException(LinkedHashMap<BusinessException, BatchBusinessException> multiException) {
		this.multiException = multiException;
	}
}
