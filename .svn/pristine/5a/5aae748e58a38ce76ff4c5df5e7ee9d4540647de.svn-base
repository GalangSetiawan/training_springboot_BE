package co.id.sofcograha.base.utils.exceptionHandler;

import java.time.YearMonth;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.utils.Message;

@Component
public class MessageService {

	@Autowired
	protected MessageSource messageSource;

	public String get(String code, Locale locale, Object... args) {
		return messageSource.getMessage(code, args, BaseConstants.DEFAULT_MESSAGE_DESC, locale);
	}

	public String get(String code, Object... args) {
		Locale local = LocaleContextHolder.getLocale();
		return get(code, local, args);
	}
	
	public Message getMessageForBusinessException(BusinessException businessException) {
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
		
		return Message.create()
				.setCode(businessException.getMessageCode())
				.setDesc(get(businessException.getMessageCode(), newParameters))
				.setHint(businessException.getHint())
				.setArgs(newParameters);
	}
}
