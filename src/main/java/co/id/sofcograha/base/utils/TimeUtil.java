package co.id.sofcograha.base.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.util.ISO8601Utils;

import co.id.sofcograha.base.exceptions.ConversionException;

enum PatternDDMMYYYY { 
    ddMMyyyy     ("dd/MM/yyyy"),
    MMddyyyy     ("MM/dd/yyyy")
    ;
	
    private String pattern;

    public String getPattern() {
        return pattern;
    }

    private PatternDDMMYYYY(String pattern) {
        this.pattern = pattern;
    }

    public static PatternDDMMYYYY getDefault() { return MMddyyyy; }
}

//TODO: SimpleDateFormat is not thread safe!
public class TimeUtil {
	
	private static Map<String, PatternDDMMYYYY> patternDDMMYYYY = new HashMap<>();

    static {
    	patternDDMMYYYY.put("en", PatternDDMMYYYY.MMddyyyy);
    	patternDDMMYYYY.put("id", PatternDDMMYYYY.ddMMyyyy);
        // any locale not listed here will get the default pattern
    }	
	
    private static String getPattern (String localeCode) {      
        if (patternDDMMYYYY.get(localeCode)!=null) {
            return patternDDMMYYYY.get(localeCode).getPattern();
        } else {
            return PatternDDMMYYYY.getDefault().getPattern();
        }
    }
    
	private static LocalDate fromDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static boolean isBefore(Date d1, Date d2) {
		return fromDate(d1).isBefore(fromDate(d2));
	}
	
	public static boolean isBeforeOrEqual(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			return false;
		} else {
			return (fromDate(d1).isBefore(fromDate(d2)) || fromDate(d1).isEqual(fromDate(d2)));			
		}
	}

	public static boolean isAfter(Date d1, Date d2) {
		return fromDate(d1).isAfter(fromDate(d2));
	}
	
	public static boolean isAfterOrEqual(Date d1, Date d2) {
		return (fromDate(d1).isAfter(fromDate(d2)) || fromDate(d1).isEqual(fromDate(d2)));
	}

	public static boolean isBetween(Date date, Date minDate, Date maxDate) {
		LocalDate d = fromDate(date);
		
		if (d.isBefore(fromDate(minDate))) return false;
		if (d.isAfter(fromDate(maxDate))) return false;
		return true;
	}
	
	public static Date addMonths(Date date, int months) {
	  if (date == null) return null;
	  
	  LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	  localDateTime = localDateTime.plusMonths(months);
	  return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	   
	public static Date addDays(Date date, int days) {
		if (date == null) return null;
		
		LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		localDateTime = localDateTime.plusDays(days);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date addSeconds(Date date, long seconds) {
		if (date == null) return null;
		
		LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		localDateTime = localDateTime.plusSeconds(seconds);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date getDate(int year, int month, int dayOfMonth) {
		Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
		calendar.setLenient(false);
		return calendar.getTime();
	}
	
	public static Date getDate(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
		Calendar calendar = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
		calendar.setLenient(false);
		return calendar.getTime();
	}
	
	public static Date getDate(String yyyyMMdd) {
		
		if (yyyyMMdd != null) {
			int year = Integer.valueOf(yyyyMMdd.substring(0, 4));
			int month = Integer.valueOf(yyyyMMdd.substring(4, 6));
			int dayOfMonth = Integer.valueOf(yyyyMMdd.substring(6, 8));

			Calendar calendar = new GregorianCalendar(year, month - 1, dayOfMonth);
			calendar.setLenient(false);
			return calendar.getTime();			
		} else {
			return null;
		}
	}
	
	public static Date getSystemDate() {
		Calendar calendar = new GregorianCalendar();
		return getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	public static Date getSystemDateTime() {
		Calendar calendar = new GregorianCalendar();
		return getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
	}
	
	public static Date getMinDate() {
		return getDate(1900, Calendar.JANUARY, 01);
	}
	
	public static Date getMaxDate() {
		return getDate(2999, Calendar.DECEMBER, 31);
	}
	
	public static Date setTimeToZero(Date date) {
		if (date == null) return null;
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date setDate(Date date, int year, int month, int dayOfMonth) {
		if (date == null) return null;
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		return calendar.getTime();
	}
	
	public static Date setMonth(Date date, int month) {
      if (date == null) return null;
      
      Calendar calendar = new GregorianCalendar();
      calendar.setTime(date);
      calendar.set(Calendar.MONTH, month);
      return calendar.getTime();
    }
	
	public static Date setTime(Date date, int hourOfDay, int minute, int second) {
		if (date == null) return null;
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date convertIso8601ToDate(String iso8601) {
		if (iso8601 == null) return null;
		
		Date dateTime = null;
		try {
			iso8601 = iso8601.replace(" ", "+");
			dateTime = ISO8601Utils.parse(iso8601, new ParsePosition(0));
		} catch (ParseException e) {
			throw new ConversionException("invalid.format.datetime",
					"Supported format: yyyy-MM-ddTHH:mm:ss.SSSXXX (example: 2017-05-30T23:45:56.987+07:00)");
		}
		
		return dateTime;
	}
	
	public static String convertDateToString(Date date) {
		if (date == null) return null;
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	public static String convertDateToString(Date date, String pattern) {
		if (date == null) return null;
		
		return DateTimeFormatter.ofPattern(pattern)
				.withZone(ZoneId.systemDefault())
				.format(date.toInstant());
	}
	
	public static String convertStringDateToFormattedDateString(String yyyyMMdd, String pattern) {
		
		Date date = getDate(yyyyMMdd);
		if (date == null) return null;
		
		return DateTimeFormatter.ofPattern(pattern)
				.withZone(ZoneId.systemDefault())
				.format(date.toInstant());
	}
	
	public static String getFourDigitYear(Date date) {
		if (date == null) return null;
		return new SimpleDateFormat("yyyy").format(date);
	}
	
	public static String getTwoDigitYear(Date date) {
		if (date == null) return null;
		return new SimpleDateFormat("yy").format(date);
	}
	
	public static String getTwoDigitMonth(Date date) {
		if (date == null) return null;
		return new SimpleDateFormat("MM").format(date);
	}
	
	public static String getMonthName(Date date) {
		if (date == null) return null;
		return new SimpleDateFormat("MMM").format(date);
	}
	
	public static String getDayInMonth(Date date) {
		if (date == null) return null;
		return new SimpleDateFormat("dd").format(date);
	}
	
	public static String getMonthToRoman(Date date) {
		if (date == null) return null;
		
		String romanMonth = null;
		Month month = fromDate(date).getMonth();
		
		switch (month) {
			case JANUARY	: romanMonth = "I";		break;
			case FEBRUARY	: romanMonth = "II";	break;
			case MARCH		: romanMonth = "III";	break;
			case APRIL		: romanMonth = "IV";	break;
			case MAY		: romanMonth = "V";		break;
			case JUNE		: romanMonth = "VI";	break;
			case JULY		: romanMonth = "VII";	break;
			case AUGUST		: romanMonth = "VIII";	break;
			case SEPTEMBER	: romanMonth = "IX";	break;
			case OCTOBER	: romanMonth = "X";		break;
			case NOVEMBER	: romanMonth = "XI";	break;
			case DECEMBER	: romanMonth = "XII";	break;
		}
		
		return romanMonth;
	}
	
	public static String getMonthToIndonesian(Date date) {
		if (date == null) return null;
		
		String indonesianMonth = null;
		Month month = fromDate(date).getMonth();
		
		switch (month) {
			case JANUARY	: indonesianMonth = "Januari";	break;
			case FEBRUARY	: indonesianMonth = "Pebruari";	break;
			case MARCH		: indonesianMonth = "Maret";	break;
			case APRIL		: indonesianMonth = "April";	break;
			case MAY		: indonesianMonth = "Mei";		break;
			case JUNE		: indonesianMonth = "Juni";		break;
			case JULY		: indonesianMonth = "Juli";		break;
			case AUGUST		: indonesianMonth = "Agustus";	break;
			case SEPTEMBER	: indonesianMonth = "September"; break;
			case OCTOBER	: indonesianMonth = "Oktober";	break;
			case NOVEMBER	: indonesianMonth = "November";	break;
			case DECEMBER	: indonesianMonth = "Desember";	break;
		}
		
		return indonesianMonth;
	}
	
	public static Date getLastDateInMonth(Date date) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return c.getTime();
	}
	
	public static Date getFirstDateInMonth(Date date) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		
		return c.getTime();
	}
	
	public static String getDateFormWithIndonesianMonthName(Date date) {
		return getDayInMonth(date) + " " + getMonthToIndonesian(date) + " " + getFourDigitYear(date);
	}
	
	public static String convertDateToStringByLocaleCode(Date date, String localeCode) {
		if (date == null) return null;
		
		return DateTimeFormatter.ofPattern(getPattern(localeCode))
				.withZone(ZoneId.systemDefault())
				.format(date.toInstant());
	}
	
	public static String convertDateToYyyyMmDd(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);		
	}
	
	public static String convertDateToHhMmSs(Date date) {
		return new SimpleDateFormat("HHmmss").format(date);		
	}
	
	public static Date getLastDateInMonth(String yyyyMMdd) {
		
    	LocalDate convertedDate = LocalDate.parse(yyyyMMdd, DateTimeFormatter.ofPattern("yyyyMMdd"));
    	convertedDate = convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
    	
		return Date.from(convertedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	public static String getStrLastDateInMonth(String yyyyMMdd) {
		
    	LocalDate convertedDate = LocalDate.parse(yyyyMMdd, DateTimeFormatter.ofPattern("yyyyMMdd"));
    	convertedDate = convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
    	
		return convertDateToYyyyMmDd(Date.from(convertedDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}
	
	public static boolean isValidDate(String yyyyMMdd) {
		
    	DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;
    	boolean hasil = true;
    	
    	try {
            LocalDate.parse(yyyyMMdd, dateFormatter);
        } catch (DateTimeParseException e) {
            hasil = false;
        }
    	
    	return hasil;
	}

	public static String getStringDay(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		String strDay = "000" + String.valueOf(localDate.getDayOfMonth());
		
		return strDay.substring(strDay.length() - 2, strDay.length());		
	}
	
	public static String getStringMonth(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		String strMonth = "000" + String.valueOf(localDate.getMonthValue());
		
		return strMonth.substring(strMonth.length() - 2, strMonth.length());		
	}
	
	public static String getStringYear(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		return String.valueOf(localDate.getYear());		
	}
	
	public static int getIntYear(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		return localDate.getYear();		
	}
	
	public static int subtractDates(Date startDate, Date enddDate) {

    	long diff = enddDate.getTime() - startDate.getTime();

    	TimeUnit time = TimeUnit.DAYS; 
    	long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
		
    	return (int) diffrence;
	}
	
	public static String getNextMonth(String tahunBulan) {
		
		Integer intTahun = Integer.valueOf(tahunBulan.substring(0, 4));
		Integer intBulan = Integer.valueOf(tahunBulan.substring(4, 6));
		
		if (intBulan == 12) {
			intBulan = 1;
			intTahun = intTahun + 1;
		} else {
			intBulan = intBulan + 1;
		}
		
		String strTahun = String.valueOf(intTahun);
		String strBulan = "00" + String.valueOf(intBulan);
		strBulan = strBulan.substring(strBulan.length() - 2, strBulan.length());
		
		return strTahun + strBulan;
	}

	public static String getPrevMonth(String tahunBulan) {
		
		Integer intTahun = Integer.valueOf(tahunBulan.substring(0, 4));
		Integer intBulan = Integer.valueOf(tahunBulan.substring(4, 6));
		
		if (intBulan == 1) {
			intBulan = 12;
			intTahun = intTahun - 1;
		} else {
			intBulan = intBulan - 1;
		}
		
		String strTahun = String.valueOf(intTahun);
		String strBulan = "00" + String.valueOf(intBulan);
		strBulan = strBulan.substring(strBulan.length() - 2, strBulan.length());
		
		return strTahun + strBulan;
	}
	
	public static String getMonthToIndonesian(String month) {
		
		String indonesianMonth = "";
		Integer intMonth = Integer.valueOf(month);
		
		switch (intMonth) {
			case 1	: indonesianMonth = "Januari";	break;
			case 2	: indonesianMonth = "Pebruari";	break;
			case 3  : indonesianMonth = "Maret";	break;
			case 4	: indonesianMonth = "April";	break;
			case 5  : indonesianMonth = "Mei";		break;
			case 6	: indonesianMonth = "Juni";		break;
			case 7	: indonesianMonth = "Juli";		break;
			case 8	: indonesianMonth = "Agustus";	break;
			case 9	: indonesianMonth = "September"; break;
			case 10	: indonesianMonth = "Oktober";	break;
			case 11	: indonesianMonth = "November";	break;
			case 12	: indonesianMonth = "Desember";	break;
		}
		
		return indonesianMonth;
	}	
}