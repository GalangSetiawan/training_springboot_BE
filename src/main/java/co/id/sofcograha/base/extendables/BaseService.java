package co.id.sofcograha.base.extendables;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.exceptions.BatchBusinessException;
import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.exceptions.CustomMultiUpdateException;
import co.id.sofcograha.base.utils.Message;
import co.id.sofcograha.base.utils.TimeUtil;
import co.id.sofcograha.base.utils.threadlocals.LocalErrors;
import co.id.sofcograha.base.utils.threadlocals.LocalNotices;
import co.id.sofcograha.base.utils.threadlocals.LocalWarnings;

public class BaseService {

	@Autowired private LocalErrors errorManager;
	@Autowired private LocalWarnings warningManager;
	@Autowired private LocalNotices noticeManager;

	private boolean permitLeadingAndTrailingSpace = false;

	protected void error(String code, Object... args) {
		throw getError(code, args);
	}
	
	/*
	 * Dummy method for bypass test coverage
	 */
	protected BusinessException getError(String code, Object... args) {
		return new BusinessException(code, args);
	}

	protected LocalErrors batchError(String code, Object... args) {
		errorManager.addError(code, args);
		return errorManager;
	}
	
	protected LocalErrors batchErrorWithData(String code, Object data) {
		errorManager.addErrorWithData(code, data);
		return errorManager;
	}
	
	protected void wrapBatchErrors() {
		wrapBatchErrors(BaseConstants.ERROR_DEFAULT_NAME);
	}
	
	protected void wrapBatchErrors(String name) {
		errorManager.wrapBatchErrors(name);
		LocalErrors.remove();
	}

	protected void removeBatchErrors() {
		LocalErrors.remove();
	}

	protected LocalErrors getLocalErrors() {
		return errorManager;
	}

	protected void throwBatchError() {
		errorManager.throwBatchError();
	}
	
	protected void throwMultiBatchError() {
		errorManager.throwMultiBatchError();
		LocalErrors.removeMulti();
	}
	
	protected boolean isAnyBatchErrors() {
		return LocalErrors.hasErrors();
	}

	protected LocalWarnings warning(String code, String desc, Object... args) {
		Message message = Message.create()
				.setCode(code)
				.setDesc(desc);
		warningManager.addWarning(message);
		return warningManager;
	}
	
	protected boolean isAnyBatchWarnings() {
		return LocalWarnings.hasWarnings();
	}

	protected LocalNotices notice(String code, String desc, Object... args) {
		Message message = Message.create()
				.setCode(code)
				.setDesc(desc);
		noticeManager.addNotice(message);
		return noticeManager;
	}

	protected void rollback() {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}

	protected void valIdVersionRequired(String id, Long version) {
		if (id == null || version == null) {
			throw new BusinessException("data.require.id.and.version");
		}
	}
	
	protected void valRequiredDate(Date d, String msgCode, Object... args) {
		if (d == null) { batchError(msgCode, args); return; }
	}

	protected void valRequiredString(String str, String msgCode, Object... args) {
		if (str == null) { batchError(msgCode, args); return; }

		String s = permitLeadingAndTrailingSpace ? str : str.trim();

		if (s.equals("")) { batchError(msgCode, args); return; }
	}

	protected void valRequiredDouble(Double d, String msgCode, Object... args) {
		if (d == null) { batchError(msgCode, args); return; }
	}

	protected void valRequiredLong(Long l, String msgCode, Object... args) {
		if (l == null) { batchError(msgCode, args); return; }
	}

	protected void valRequiredFile(Byte[] file, String msgCode, Object... args) {
		if (file == null) { batchError(msgCode, args); return; }
	}
	
	protected <E extends Enum<E>> void valRequiredEnum(E value, Class<E> clazz, String msgCode) {
		if (value == null) { batchError(msgCode); return; }
	}
	
	protected void valMinString(String str, int len, String msgCode) {
		if (str == null) return;

		String s = permitLeadingAndTrailingSpace ? str : str.trim();

		if (s.length() < len) { batchError(msgCode, len); return; }
	}

	protected void valMaxString(String str, int len, String msgCode) {
		if (str == null) return;

		String s = permitLeadingAndTrailingSpace ? str : str.trim();

		if (s.length() > len) { batchError(msgCode, len); return; }
	}

	protected void valMinMaxString(String str, int minLen, int maxLen, String msgCode) {
		if (str == null) return;

		String s = permitLeadingAndTrailingSpace ? str : str.trim();

		if (s.length() < minLen || s.length() > maxLen) { batchError(msgCode, minLen, maxLen); return; }
	}
	
	protected void valRequiredBoolean(Boolean b, String msgCode, Object... args) {
		if (b == null) { batchError(msgCode, args); return; }
	}
	
	protected void valMinDate(Date date, Date minDate, String msgCode) {
		valMinDate(date, minDate, msgCode, true);
	}

	protected void valMinDate(Date date, Date minDate, String msgCode, boolean isBatchError) {
		if (date == null) return;

		if (TimeUtil.isBefore(date, minDate)) {
			if (isBatchError) { batchError(msgCode, minDate); return; }
			else { error(msgCode, minDate); return; }
		}
	}
	
	protected void valMaxDate(Date date, Date maxDate, String msgCode) {
		if (date == null) return;

		if (TimeUtil.isAfter(date, maxDate)) { batchError(msgCode, maxDate); return; }
	}
	
	protected void valRequiredInteger(Integer i, String msgCode, Object... args) {
		if (i == null) { batchError(msgCode, args); return; }
	}
	
	protected void valMinInteger(Integer val, Integer minVal, String msgCode) {
		if (val == null) return;

		if (Integer.compare(val, minVal) < 0) { batchError(msgCode, minVal); return; }
	}

	protected void valMaxInteger(Integer val, Integer maxVal, String msgCode) {
		if (val == null) return;

		if (Integer.compare(val, maxVal) > 0) { batchError(msgCode, maxVal); return; }
	}
	
	protected void valMinDouble(Double val, Double minVal, String msgCode) {
		if (val == null) return;

		//		if (Math.abs(a -b))
		if (Double.compare(val, minVal) < 0) { batchError(msgCode, minVal); return; }
	}

	protected void valMaxDouble(Double val, Double maxVal, String msgCode) {
		if (val == null) return;

		if (Double.compare(val, maxVal) > 0) { batchError(msgCode, maxVal); return; }
	}
	
	/*
	protected <RE extends BaseEntity> void valRequiredReference(RE ref, String msgCode, Object... args) {
		if (ref == null) { batchError(msgCode, args); return; }
		if (ref.getId() == null) { batchError(msgCode, args); return; }
		if (ref.getId().equals("")) { batchError(msgCode, args); return; }
	}
	*/
	
	protected void valRequiredReference(Object ref, String id, String msgCode, Object... args) {
		if (ref == null) { batchError(msgCode, args); return; }
		if (id == null) { batchError(msgCode, args); return; }
		if (id.equals("")) { batchError(msgCode, args); return; }
	}
	
	protected String usingString(String str, String defaultVal) {
		if (str == null) return defaultVal;

		String s = permitLeadingAndTrailingSpace ? str : str.trim();

		if (s.equals("")) return defaultVal;

		return s;
	}

	protected void multiUpdateErrorHandling(CustomMultiUpdateException cmue, int rowNum, Exception e) {
		e.printStackTrace();
		if (e instanceof BatchBusinessException) {
			cmue.addException(rowNum, (BatchBusinessException) e);
		} else if (e instanceof BusinessException) {
			cmue.addException(rowNum, LocalErrors.getErrors().addBusinessException((BusinessException) e));
		} else {
			cmue.addException(rowNum, LocalErrors.getErrors().addBusinessException(new BusinessException(e.getMessage())));
		}
		LocalErrors.remove();
	}
}
