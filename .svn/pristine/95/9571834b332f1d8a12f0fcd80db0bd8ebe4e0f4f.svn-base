package co.id.sofcograha.base.utils;

import java.util.ArrayList;
import java.util.List;

import co.id.sofcograha.base.constants.BaseConstants;

public class StringUtil {

	public static boolean isBlank(String strngPVal) {
		if (strngPVal == null || strngPVal.trim().equals(BaseConstants.EMPTY)) {
			return true;
		} else {
			return false;
		}
	}

	public static String checkBlank(String strngPVal) {
		return isBlank(strngPVal)? BaseConstants.EMPTY : strngPVal.trim();
	}
	
	public static String objArrayToString(Object[] data) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			if (i != 0) {
				result.append(";");
			}
			result.append(data[i]);
		}
		
		return result.toString();
	}
	
	public static String arrayToString(String[] data) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			if (i != 0) {
				result.append(";");
			}
			result.append(data[i]);
		}
		
		return result.toString();
	}
	
	public static String listToString(List<String> data) {
		StringBuffer result = new StringBuffer();
		boolean first = true;
		for (String string : data) {
			if (!first) {
				result.append(";");
			}
			result.append(string);
			first = false;
		}
		
		return result.toString();
	}
	
	public static String[] stringToArray(String data) {
		return data.split(";");
	}
	
	public static List<String> stringToList(String data) {
		String[] stringToArray = stringToArray(data);
		List<String> result = new ArrayList<>();
		for (int i = 0; i < stringToArray.length; i++) {
			result.add(stringToArray[i]);
		}
		
		return result;
	}
	
	public static Boolean convertStringYesNoToBoolean(String data) {
		if (isBlank(data)) {
			return null;
		}
		
		if (data.equalsIgnoreCase("ya")) {
			return true;
		}
		if (data.equalsIgnoreCase("tidak")) {
			return false;
		}
		
		if (data.equalsIgnoreCase("Y")) {
			return true;
		}
		if (data.equalsIgnoreCase("T")) {
			return false;
		}
		
		if (data.equalsIgnoreCase("yes")) {
			return true;
		}
		if (data.equalsIgnoreCase("no")) {
			return false;
		}
		
		if (data.equalsIgnoreCase("Y")) {
			return true;
		}
		if (data.equalsIgnoreCase("N")) {
			return false;
		}
		
		return null;
	}
	
	/**
	 * Credit : https://codippa.com/how-to-convert-number-to-words-in-java/
	 */
	public static String convertCurrencyToCalculatedString(Double number) {
		String words = "";
		Integer convertedNumber = number.intValue();
		String unitsArray[] = { "nol", "satu", "dua", "tiga", "empat", "lima", "enam", 
								"tujuh", "delapan", "sembilan", "sepuluh", "sebelas", "dua belas", 
								"tiga belas", "empat belas", "lima belas", "enam belas", "tujuh belas", 
								"delapan belas", "sembilan belas" };
		String tensArray[] = { "nol", "sepuluh", "dua puluh", "tiga puluh", "empat puluh", "lima puluh", 
							   "enam puluh", "tujuh puluh", "delapan puluh", "sembilan puluh" };

		if (convertedNumber == 0) {
			return "nol";
		}
		// add minus before conversion if the number is less than 0
		if (convertedNumber < 0) { // convert the number to a string
			String numberStr = "" + convertedNumber;
			// remove minus before the number
			numberStr = numberStr.substring(1);
			// add minus before the number and convert the rest of number
			return "- " + convertCurrencyToCalculatedString(Double.parseDouble(numberStr));
		}
		// check if number is divisible by 1 million
		if ((convertedNumber / 1000000) > 0) {
			words += convertCurrencyToCalculatedString((convertedNumber / 1000000) * 1D) + "juta ";
			convertedNumber %= 1000000;
		}
		// check if number is divisible by 1 thousand
		if ((convertedNumber / 1000) > 0) {
			words += convertCurrencyToCalculatedString((convertedNumber / 1000) * 1D) + "ribu ";
			convertedNumber %= 1000;
		}
		// check if number is divisible by 1 hundred
		if ((convertedNumber / 100) > 0) {
			words += convertCurrencyToCalculatedString((convertedNumber / 100) * 1D) + "ratus ";
			convertedNumber %= 100;
		}

		if (convertedNumber > 0) {
			// check if number is within teens
			if (convertedNumber < 20) {
				// fetch the appropriate value from unit array
				words += unitsArray[convertedNumber] + " ";
			} else {
				// fetch the appropriate value from tens array
				words += tensArray[convertedNumber / 10] + " ";
				if ((number % 10) > 0) {
					words += unitsArray[convertedNumber % 10] + " ";
				}
			}
		}

		return words;
	}
	
    public static Object[] getWordByKey(String message, String key) {
    	
    	int posisi_awal = 0;
    	boolean terus = true;
		ArrayList<String> tmp_hasil = new ArrayList<String>();
		
    	while (terus) {
    		
    		int posisi_akhir = message.indexOf(key, posisi_awal);

        	if (posisi_akhir > 0) {
        		tmp_hasil.add(message.substring(posisi_awal, posisi_akhir));
        		posisi_awal = posisi_akhir + 1;
        	} else {
        		if (posisi_awal < message.length()) {
        			tmp_hasil.add(message.substring(posisi_awal, message.length()));        			
        		}
        		terus = false;
        	}
    	}
    	
    	return tmp_hasil.toArray();
    }
}
