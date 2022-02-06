package co.id.sofcograha.base.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.message.MultiMessage;
import co.id.sofcograha.base.utils.searchData.Pagination;
import co.id.sofcograha.base.utils.threadlocals.LocalErrors;
import co.id.sofcograha.base.utils.threadlocals.LocalNotices;
import co.id.sofcograha.base.utils.threadlocals.LocalWarnings;

@JsonInclude(Include.ALWAYS)
public class ApiResponse {

	private static final String status_ok = BaseConstants.STATUS_OK;
	private static final String status_warning = BaseConstants.STATUS_WARNING;
	private static final String status_error = BaseConstants.STATUS_ERROR;

	private HashMap<String, Object> data;
	private HashMap<String, Object> meta;
	private ArrayList<Message> errors;
	@JsonInclude(Include.NON_EMPTY)
	private ArrayList<MultiMessage> multiErrors;
	private ArrayList<Message> notices;
	private ArrayList<Message> warnings;
	private String status;

	public ApiResponse() {
		data = new HashMap<String, Object>();
		meta = new HashMap<String, Object>();
		errors = new ArrayList<Message>();
		notices = new ArrayList<Message>();
		warnings = new ArrayList<Message>();
	}

	public static ApiResponse data(String key, Object value){
		ApiResponse apiResponse = ApiResponse.create()
				.addData(key, value)
				.addNotices(LocalNotices.getNotices())
				.addWarnings(LocalWarnings.getWarnings());

//		removeLocalNoticesWarnings();
		removeThreadLocals();
		return apiResponse;
	}
	
	public static ApiResponse dataWithPaging(String key, Object value, Pagination paging){
		ApiResponse apiResponse = ApiResponse.create()
				.addData(key, value)
				.addNotices(LocalNotices.getNotices())
				.addWarnings(LocalWarnings.getWarnings());

		if (paging != null) {
			apiResponse.addMeta(BaseConstants.PAGINATION, paging);
		}
		
//		removeLocalNoticesWarnings();
		removeThreadLocals();
		return apiResponse;
	}

	public static ApiResponse error(Message error){
		ApiResponse apiResponse = ApiResponse.create()
				.addError(error)
				.changeStatus(status_error);

//		removeLocalNoticesWarnings();
		removeThreadLocals();
		return apiResponse;
	}

	public static ApiResponse errors(ArrayList<Message> errors){
		ApiResponse apiResponse = ApiResponse.create()
				.addErrors(errors)
				.changeStatus(status_error);

//		removeLocalNoticesWarnings();
		removeThreadLocals();
		return apiResponse;
	}

	public static ApiResponse multiErrors(ArrayList<MultiMessage> multiErrors){
		ApiResponse apiResponse = ApiResponse.create().changeStatus(status_error);
		apiResponse.setMultiErrors(multiErrors);
		
		removeThreadLocals();
		return apiResponse;
	}
	
	public static ApiResponse multi(int rows){
		ApiResponse apiResponse = ApiResponse.create()
				.addNotice(Message.create().setCode("multiupdate.success").setDesc("'" + rows + "' rows updated!").setArgs(rows))
				.changeStatus(status_ok);
		
		removeThreadLocals();
		return apiResponse;
	}

	public static ApiResponse upload(int rowsUploaded){
		ApiResponse apiResponse = ApiResponse.create()
				.addNotice(Message.create().setCode("upload.success").setDesc("'" + rowsUploaded + "' rows uploaded!").setArgs(rowsUploaded))
				.changeStatus(status_ok);
		
//		removeLocalNoticesWarnings();
		removeThreadLocals();
		return apiResponse;
	}

	public static ApiResponse ok(){
		ApiResponse apiResponse = ApiResponse.create().changeStatus(status_ok)
				.addNotices(LocalNotices.getNotices());

//		removeLocalNoticesWarnings();
		removeThreadLocals();
		return apiResponse;
	}

	public static void removeThreadLocals() {
		LocalNotices.remove();
		LocalWarnings.remove();
		LocalErrors.remove();
		LocalErrors.removeMulti();
	}
//	public static void removeLocalNoticesWarnings() {
//		LocalNotices.remove();
//		LocalWarnings.remove();
//	}

	public static ApiResponse create(){
		return new ApiResponse();
	}

	public ApiResponse addData(String key, Object value){
		this.data.put(key, value);
		this.changeStatus(status_ok);
		return this;
	}
	
	public ApiResponse addMeta(String key, Object value){
		this.meta.put(key, value);
		return this;
	}

	public ApiResponse addError(Message myMessage) {
		this.errors.add(myMessage);
		return this;
	}

	public ApiResponse addErrors(ArrayList<Message> errors) {
		errors.forEach(error -> {
			this.errors.add(error);
			this.changeStatus(status_error);
		});
		return this;
	}

	public ApiResponse addNotices(ArrayList<Message> notices) {
		notices.forEach(notice -> {
			this.notices.add(notice);
			this.changeStatus(status_ok);
		});
		return this;
	}

	public ApiResponse addNotice(Message myMessage) {
		this.notices.add(myMessage);
		return this;
	}

	public ApiResponse addWarnings(ArrayList<Message> warnings) {
		warnings.forEach(warning -> {
			this.warnings.add(warning);
			this.changeStatus(status_warning);
		});
		return this;
	}

	public ApiResponse addWarning(Message warning) {
		this.notices.add(warning);
		return this;
	}

	public ApiResponse changeStatus(String status){
		this.status = status;
		return this;
	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}
	
	public HashMap<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(HashMap<String, Object> meta) {
		this.meta = meta;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Message> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<Message> errors) {
		this.errors = errors;
	}

	public ArrayList<MultiMessage> getMultiErrors() {
		return multiErrors;
	}

	public void setMultiErrors(ArrayList<MultiMessage> multiErrors) {
		this.multiErrors = multiErrors;
	}
	
	public ArrayList<Message> getNotices() {
		return notices;
	}

	public ApiResponse setNotices(ArrayList<Message> notices) {
		this.notices = notices;
		return this;
	}

	public ArrayList<Message> getWarnings() {
		return warnings;
	}

	public ApiResponse setWarnings(ArrayList<Message> warnings) {
		this.warnings = warnings;
		return this;
	}

}
