package co.id.sofcograha.base.exceptions;

import java.util.ArrayList;

public class MultiBatchBusinessException extends ParentBusinessException {
	
	private static final long serialVersionUID = 599176019839175639L;
	private ArrayList<BatchBusinessExceptionWrapper> batchBusinessExceptionWrappers;

	public MultiBatchBusinessException() {
		batchBusinessExceptionWrappers = new ArrayList<BatchBusinessExceptionWrapper>();
	}

	public static MultiBatchBusinessException create() {
		return new MultiBatchBusinessException();
	}

	public MultiBatchBusinessException addBatchBusinessException(String name, BatchBusinessException e) {
		boolean isDetailExist = false;
		for (BatchBusinessExceptionWrapper wrapper : batchBusinessExceptionWrappers) {
			if (wrapper.getName().equals(name)) {
				isDetailExist = true;
				wrapper.addBatchBusinessException(e);
			}
		}
		
		if (!isDetailExist) {
			BatchBusinessExceptionWrapper newDetail = new BatchBusinessExceptionWrapper();
			newDetail.setName(name);
			newDetail.addBatchBusinessException(e);
			batchBusinessExceptionWrappers.add(newDetail);
		}

		return this;
	}

	public void throwMultiBatchBusinessException(){
		for (BatchBusinessExceptionWrapper wrapper : batchBusinessExceptionWrappers) {
			for (BatchBusinessException bbe : wrapper.getBatchBusinessExceptions()) {
				if (!bbe.getBusinessExceptions().isEmpty()) {
					throw this;
				}
			}
		}
	}

	public ArrayList<BatchBusinessExceptionWrapper> getBatchBusinessExceptionWrappers() {
		return batchBusinessExceptionWrappers;
	}

	public void setBatchBusinessExceptionWrappers(ArrayList<BatchBusinessExceptionWrapper> batchBusinessExceptionWrappers) {
		this.batchBusinessExceptionWrappers = batchBusinessExceptionWrappers;
	}
}
