package co.id.sofcograha.base.exceptions;

import java.util.ArrayList;

public class BatchBusinessExceptionWrapper {
	private String name;
	private ArrayList<BatchBusinessException> batchBusinessExceptions;

	public BatchBusinessExceptionWrapper() {
		name = "defaultName";
		batchBusinessExceptions = new ArrayList<BatchBusinessException>();
	}

	public static BatchBusinessExceptionWrapper create() {
		return new BatchBusinessExceptionWrapper();
	}

	public BatchBusinessExceptionWrapper addBatchBusinessException(BatchBusinessException e) {
		this.batchBusinessExceptions.add(e);
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<BatchBusinessException> getBatchBusinessExceptions() {
		return batchBusinessExceptions;
	}

	public void setBatchBusinessExceptions(ArrayList<BatchBusinessException> batchBusinessExceptions) {
		this.batchBusinessExceptions = batchBusinessExceptions;
	}
}
