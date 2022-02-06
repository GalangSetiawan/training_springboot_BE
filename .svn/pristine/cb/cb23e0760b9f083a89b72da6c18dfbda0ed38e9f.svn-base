package co.id.sofcograha.base.utils.exceptionHandler;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.QueryException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.exceptions.BatchBusinessException;
import co.id.sofcograha.base.exceptions.BatchBusinessExceptionWrapper;
import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.exceptions.ConversionException;
import co.id.sofcograha.base.exceptions.CustomMultiUpdateException;
import co.id.sofcograha.base.exceptions.CustomSearchException;
import co.id.sofcograha.base.exceptions.CustomSearchParameterException;
import co.id.sofcograha.base.exceptions.CustomSerializationException;
import co.id.sofcograha.base.exceptions.CustomUploadException;
import co.id.sofcograha.base.exceptions.DevException;
import co.id.sofcograha.base.exceptions.MultiBatchBusinessException;
import co.id.sofcograha.base.message.MultiMessage;
import co.id.sofcograha.base.utils.ApiResponse;
import co.id.sofcograha.base.utils.Message;
import co.id.sofcograha.base.utils.threadlocals.LocalErrors;

//TODO vint: find better error code, not like: method.not.allowed 
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final String ERR_MNA = "method.not.allowed";
  private static final String ERR_ISE = "internal.server.exception";
  private static final String ERR_DEV = "developer.exception";
  private static final String ERR_BR = "bad.request";
  private static final String ERR_QE = "query.exception";
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  MessageService messageService;

  @ExceptionHandler(CustomUploadException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ArrayList<ApiResponse> batchBusinessx(HttpServletRequest req, CustomUploadException ex) {
    printStackTrace(ex);
    ArrayList<ApiResponse> apiResponses = new ArrayList<>();

    ex.getUploadException().forEach((businessException, batchBusinessException) -> {
      ArrayList<Message> errors = new ArrayList<Message>();
      errors.add(getMessageForBusinessException(businessException));
      batchBusinessException.getBusinessExceptions().forEach(businessExceptionz -> {
        errors.add(getMessageForBusinessException(businessExceptionz));
      });
      apiResponses.add(ApiResponse.errors(errors));
    });

    removeLocalErrors();
    return apiResponses;
  }

  /*
  @ExceptionHandler(BatchBusinessException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResponse batchBusiness(HttpServletRequest req, BatchBusinessException ex) {
    printStackTrace(ex);
    ArrayList<Message> errors = new ArrayList<Message>();
    ex.getBusinessExceptions().forEach(businessException -> {
      errors.add(messageService.getMessageForBusinessException(businessException));
    });
    removeLocalErrors();
    return ApiResponse.errors(errors);
  }
  */

	@ExceptionHandler(BatchBusinessException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ApiResponse batchBusiness(HttpServletRequest req, BatchBusinessException ex) {
		printStackTrace(ex);
		ArrayList<Message> errors = new ArrayList<Message>();
		ex.getBusinessExceptions().forEach(
				businessException -> {
					errors.add(messageService.getMessageForBusinessException(businessException));
				});
		removeLocalErrors();
		
		// oom
		ApiResponse apiResponse = ApiResponse.errors(errors);
		apiResponse.addData("item", ex.getData());
		
		return apiResponse;
	}
  
  @ExceptionHandler(MultiBatchBusinessException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResponse multiBatchBusiness(HttpServletRequest req, MultiBatchBusinessException ex) {
    printStackTrace(ex);

    ArrayList<MultiMessage> multiErrors = new ArrayList<>();
    for (BatchBusinessExceptionWrapper wrapper : ex.getBatchBusinessExceptionWrappers()) {
      ArrayList<ArrayList<Message>> errorMessages = new ArrayList<>();
      for (BatchBusinessException batchBusinessException : wrapper.getBatchBusinessExceptions()) {
        ArrayList<Message> errors = new ArrayList<>();
        for (BusinessException businessException : batchBusinessException.getBusinessExceptions()) {
          errors.add(getMessageForBusinessException(businessException));
        }

        errorMessages.add(errors);
      }

      MultiMessage multiErrorMessage = new MultiMessage();
      multiErrorMessage.setName(wrapper.getName());
      multiErrorMessage.setMessages(errorMessages);
      multiErrors.add(multiErrorMessage);
    }

    removeLocalErrors();

    return ApiResponse.multiErrors(multiErrors);
  }

  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResponse business(HttpServletRequest req, BusinessException ex) {
    printStackTrace(ex);
    return ApiResponse.error(getMessageForBusinessException(ex));
  }

  @ExceptionHandler(DevException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  protected ApiResponse devs(HttpServletRequest req, DevException ex) {
    String logCode = logError(ERR_DEV, req, ex);
    return ApiResponse.error(Message.create().setCode(ERR_DEV).setDesc(getDevErrorMessage(logCode, ex.getMessage())));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResponse others(HttpServletRequest req, HttpMessageNotReadableException ex) {
    String desc = getDescForJmeErrors();
    if (ex.getCause() != null && ex.getCause().getClass().equals(JsonMappingException.class)) {
      if (ex.getCause().getCause().equals(CustomSerializationException.class)) {
        return ApiResponse.error(Message.create().setCode(ERR_DEV).setDesc(ex.getCause().getCause().getMessage()));
      }
    }
    if (ex.getCause() != null && ex.getCause().getClass().equals(InvalidFormatException.class)) {
      if (ex.getCause().getMessage().contains("Can not deserialize value of type")) {
        desc = "Json convert failure! Please check your param type and value";
        return ApiResponse.error(Message.create().setCode(ERR_DEV).setDesc(desc));
      }
      return ApiResponse.error(Message.create().setCode(ERR_DEV).setDesc(ex.getCause().getCause().getMessage()));
    }
    printStackTrace(ex);
    return ApiResponse.error(Message.create().setCode(ERR_DEV).setDesc(desc));
  }

  // enums
  @ExceptionHandler(CustomSerializationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResponse others(HttpServletRequest req, CustomSerializationException ex) {
    return ApiResponse.error(Message.create().setCode(ERR_DEV).setDesc(ex.getMessage()));
  }

  @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResponse uniqueResultError(HttpServletRequest req, IncorrectResultSizeDataAccessException ex) {
    if (ex.getCause().getClass().equals(NonUniqueResultException.class)) {
      return ApiResponse.error(Message.create().setCode(ERR_DEV).setDesc(getDescForNonUniqueResultErrors()));
    }
    String logCode = logError(ERR_DEV, req, ex);
    return ApiResponse.error(Message.create().setCode(ERR_ISE).setDesc(getDescForUncaughtErrors(logCode)));
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected ApiResponse unsupportedMediaType(HttpServletRequest req, HttpMediaTypeNotSupportedException ex) {
    return ApiResponse.error(Message.create().setCode("CORE.HTTP400.001")
        .setDesc(messageService.get("CORE.HTTP400.001", ex.getContentType().toString())));
  }

  // TODO vint: experimental!
  @ExceptionHandler(JDBCConnectionException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected ApiResponse jdbcError(HttpServletRequest req, JDBCConnectionException ex) {
    String logCode = logError(ERR_DEV, req, ex);
    return ApiResponse
        .error(Message.create().setCode("CORE.JDBC.001").setDesc(messageService.get("CORE.JDBC.001")).setArgs(logCode));
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected ApiResponse missingServletRequestParameter(HttpServletRequest req,
      MissingServletRequestParameterException ex) {
    if (ex.getCause().getClass().equals(IllegalArgumentException.class)) {
      return ApiResponse.error(Message.create().setCode(ERR_BR).setDesc(ex.getCause().getMessage()));
    }
    String logCode = logError(ERR_ISE, req, ex);
    return ApiResponse.error(Message.create().setCode(ERR_ISE).setDesc(getDescForUncaughtErrors(logCode)));
  }

  // request parameter conversion value
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected ApiResponse missingServletRequestParameter(HttpServletRequest req, MethodArgumentTypeMismatchException ex) {
    if (ex.getMessage().contains("Failed to convert value of type")) {
      if (ex.getCause().getClass().equals(IllegalArgumentException.class)) {
        if (ex.getCause().getMessage().contains("value")) {
          String desc = "Parameter conversion failed!";
          return ApiResponse.error(Message.create().setCode(ERR_BR).setDesc(desc).setHint(ex.getCause().getMessage()));
        }
      }
    }
    String logCode = logError(ERR_ISE, req, ex);
    return ApiResponse.error(Message.create().setCode(ERR_ISE).setDesc(getDescForUncaughtErrors(logCode)));
  }

  // this should handle search builder
  @ExceptionHandler(CustomSearchException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected ApiResponse customSearchException(HttpServletRequest req, CustomSearchException ex) {
    return ApiResponse.error(Message.create().setCode(ERR_BR).setDesc(ex.getMessage()).setHint(ex.hint));
  }

  // this should handle search parameter from request param
  @ExceptionHandler(CustomSearchParameterException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected ApiResponse customSearchParameterException(HttpServletRequest req, CustomSearchParameterException ex) {
    return ApiResponse.error(Message.create().setCode(ERR_BR).setDesc(ex.getMessage()).setHint(ex.hint));
  }

  // one or more of the sorting field does not exists in entity
  @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected ApiResponse queryException(HttpServletRequest req, InvalidDataAccessResourceUsageException ex) {
    if (ex.getCause().getClass().equals(SQLGrammarException.class)) {
      if (ex.getCause().getCause().getClass().equals(PSQLException.class)) {
        String errMsg = ex.getCause().getCause().getMessage();
        if (errMsg.contains("does not exist")) {
          String unexistedField = errMsg.split("[\\\"\\\"]")[1];
          String desc = "Query failed! field [" + unexistedField + "] does not match any field from the resource";
          return ApiResponse.error(Message.create().setCode(ERR_QE).setDesc(desc));
        }
      }
    }
    String logCode = logError(ERR_ISE, req, ex);
    return ApiResponse.error(Message.create().setCode(ERR_ISE).setDesc(getDescForUncaughtErrors(logCode)));
  }

  // one or more of the sorting field does not exists in entity v.2
  @ExceptionHandler(InvalidDataAccessApiUsageException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected ApiResponse queryException(HttpServletRequest req, InvalidDataAccessApiUsageException ex) {
    if (ex.getCause().getCause().getClass().equals(QueryException.class)) {
      String errMsg = ex.getCause().getCause().getMessage();
      if (errMsg.contains("Unable to resolve path")) {
        String unexpectedToken = errMsg.split("[\\[\\]]")[1];
        String desc = "Query failed! Field [" + unexpectedToken + "] does not match any field from the resource!";
        return ApiResponse.error(Message.create().setCode(ERR_QE).setDesc(desc));
      }
    }
    String logCode = logError(ERR_ISE, req, ex);
    return ApiResponse.error(Message.create().setCode(ERR_ISE).setDesc(getDescForUncaughtErrors(logCode)));
  }

  // Trying to delete referenced data
  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected ApiResponse dataIntegrityViolation(HttpServletRequest req, DataIntegrityViolationException ex) {
    if (ex.getCause().getClass().equals(ConstraintViolationException.class)) {
      if (ex.getCause().getCause().getClass().equals(PSQLException.class)) {
        if (ex.getCause().getCause().getMessage().contains("is still referenced from")) {
          String code = "delete.data.integrity.violation";
          String desc = messageService.get(code);
          return ApiResponse.error(Message.create().setCode(code).setDesc(desc));
        }
      }
    }
    String logCode = logError(ERR_ISE, req, ex);
    return ApiResponse.error(Message.create().setCode(ERR_ISE).setDesc(getDescForUncaughtErrors(logCode)));
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
  public ApiResponse others(HttpServletRequest req, HttpRequestMethodNotSupportedException ex) {
    String desc = getDescForHnsErrors(req);
    return ApiResponse.error(Message.create().setCode(ERR_MNA).setDesc(desc));
  }

  @ExceptionHandler(ConversionException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected ApiResponse conversionException(HttpServletRequest req, ConversionException ex) {
    return ApiResponse
        .error(Message.create().setCode(ex.getMessage()).setDesc(messageService.get(ex.getMessage())).setHint(ex.hint));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiResponse uncaughterror(HttpServletRequest req, Exception ex) {
    String logCode = logError(ERR_ISE, req, ex);
    return ApiResponse.error(Message.create().setCode(ERR_ISE).setDesc(getDescForUncaughtErrors(logCode)));
  }

	@ExceptionHandler(CustomMultiUpdateException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public List<ApiResponse> batchMultiUpdateException(HttpServletRequest req, CustomMultiUpdateException ex) {

		List<ApiResponse> apiResponses = new ArrayList<>();

		ex.getMultiException().forEach((businessException, batchBusinessException) -> {
			ArrayList<Message> errors = new ArrayList<>();
			errors.add(messageService.getMessageForBusinessException(businessException));
			batchBusinessException.getBusinessExceptions().forEach(businessExceptionz -> {
				errors.add(messageService.getMessageForBusinessException(businessExceptionz));
			});
			apiResponses.add(ApiResponse.errors(errors));
		});

		removeLocalErrors();

		return apiResponses;
	}
  
  private Message getMessageForBusinessException(BusinessException businessException) {
    Object[] parameters = businessException.getMessageParameters();
    Object[] newParameters;

    if (parameters == null || parameters.length == 0) {
      newParameters = new Object[0];
    } else {
      newParameters = new Object[parameters.length];
      for (int i = 0; i < parameters.length; i++) {
        if (parameters[i] == null) {
          newParameters[i] = BaseConstants.EMPTY;
        } else if (parameters[i] instanceof Date) {
          newParameters[i] = BaseConstants.JSON_DATE_FORMAT.format(parameters[i]);
        } else if (parameters[i] instanceof YearMonth) {
          newParameters[i] = parameters[i].toString();
        } else {
          newParameters[i] = parameters[i];
        }
      }
    }

    return Message.create().setCode(businessException.getMessageCode())
        .setDesc(messageService.get(businessException.getMessageCode(), newParameters))
        .setHint(businessException.getHint()).setArgs(newParameters);
  }

  private String getDescForHnsErrors(HttpServletRequest req) {
    return "Uri: " + req.getRequestURI() + ", Method: " + req.getMethod() + " not supported";
  }

  private String getDescForJmeErrors() {
    return "Request body not readable";
  }

  private String getDescForNonUniqueResultErrors() {
    return "Data's key anomaly! Wrong bk or wrong query?";
  }

  private String getDevErrorMessage(String logCode, String message) {
    return "Contact the dev if you see this! If you happened to be one of the dev(s), then please fix it "
        + "\nLogCode: '" + logCode + "'" + "\nMessage: '" + message + "'";
  }

  private String getDescForUncaughtErrors(String logCode) {
    return "Unexpected error occurred, please contact administrator and attach this code: " + logCode;
  }

  private String logError(String code, HttpServletRequest req, Exception e) {
    String logCode = code + System.currentTimeMillis();
    logger.error("=============================[" + logCode + "]=============================");
    logger.error("request: " + req.getRequestURL());
    logger.error("method: " + req.getMethod());
    printStackTrace(e);
    logger.error("=============================[" + logCode + "]=============================");
    return logCode;
  }

  private void printStackTrace(Exception e) {
    logger.debug("trace: ", e);
  }

  public static void removeLocalErrors() {
    LocalErrors.remove();
  }
}
