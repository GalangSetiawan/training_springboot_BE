package co.id.sofcograha.base.exceptions;

import java.util.LinkedHashMap;

public class CustomUploadException extends BatchBusinessException{

	private static final long serialVersionUID = -1045301169223500126L;

	private LinkedHashMap<BusinessException, BatchBusinessException> uploadException;
	
	public boolean hasErrors() { 
		return uploadException != null;
	}

	private void createIfEmpty() {
		if (!hasErrors()) {
			uploadException = new LinkedHashMap<>();
		}
	}
	
	public void addException(int rowNumber, BatchBusinessException bbe) {
		createIfEmpty();
		uploadException.put(new BusinessException("upload.error", rowNumber), bbe);
	}

	public LinkedHashMap<BusinessException, BatchBusinessException> getUploadException() {
		return uploadException;
	}

	public void setUploadException(LinkedHashMap<BusinessException, BatchBusinessException> uploadException) {
		this.uploadException = uploadException;
	}
}
