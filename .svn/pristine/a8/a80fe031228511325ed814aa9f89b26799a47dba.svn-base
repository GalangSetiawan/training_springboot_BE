package co.id.sofcograha.base.utils.pojos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.exceptions.CustomSerializationException;

public class PojoUtil {

	public static Byte[] string2Base64ByteArray(String val) {
		if (val.length() < BaseConstants.MIN_IMAGE_SIZE_BYTES) {
			throw new CustomSerializationException("minimum byte size(s) is: " + BaseConstants.MIN_IMAGE_SIZE_BYTES + "bytes");
		}
		return ArrayUtils.toObject(Base64.getDecoder().decode(val));
	}
	
	public static List<Object[]> convertBigTypeToPrimitive(List<Object[]> resultList) {
    resultList.forEach(result -> {
      for (int i = 0; i < result.length; i++) {
        if (result[i] instanceof BigDecimal) result[i] = ((BigDecimal) result[i]).doubleValue();
        if (result[i] instanceof BigInteger) result[i] = ((BigInteger) result[i]).intValue();
      }
    });
    return resultList;
  }

	public static Object string2RawEnum(String rawStr, Class<?> enumClass) {
		String str = rawStr;
		if (str == null) return null;

		if (!enumClass.isEnum()) throw new CustomSerializationException("ENUMssssssss....\nWrong enum class");

		Object daEnum = null;
		str = str.trim().toLowerCase();
		Object[] enums = enumClass.getEnumConstants();
		List<String> availableEnums = new ArrayList<>();

		for (Object enumm : enums) {
			availableEnums.add(enumm.toString());
			if (enumm.toString().toLowerCase().equals(str)) daEnum = enumm;
			if (daEnum != null) break;
		}

		if (daEnum == null) throw new CustomSerializationException("ENUMssssssss....\nEnum Class: " + enumClass.getSimpleName() + 
				"\nAvailable: " + String.join(",", availableEnums) +"\nYour value: " + rawStr);

		return daEnum;
	}
	
	public static <E extends Enum<E>> E string2Enum(String rawStr , Class<E> enumClass) {
		String str = rawStr;
		if (str == null) return null;

		if (!enumClass.isEnum()) throw new CustomSerializationException("ENUMssssssss....\nWrong enum class");

		E daEnum = null;
		str = str.trim().toLowerCase();
		E[] enums = enumClass.getEnumConstants();
		List<String> availableEnums = new ArrayList<>();

		for (E enumm : enums) {
			availableEnums.add(enumm.toString());
			if (enumm.toString().toLowerCase().equals(str)) daEnum = enumm;
			if (daEnum != null) break;
		}

		// TODO jun : use jackson's filter >>> http://stackoverflow.com/a/12761116
		if (daEnum == null) throw new CustomSerializationException("ENUMssssssss....\nEnum Class: " + enumClass.getSimpleName() + 
				"\nAvailable: " + String.join(",", availableEnums) +"\nYour value: " + rawStr);

		return daEnum;
	}
}