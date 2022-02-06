package co.id.sofcograha.base.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.SerializationUtils;

@SuppressWarnings("unchecked")
public class ObjectUtil {

	public static String objectToString(Serializable object) {
		byte[] bytes = SerializationUtils.serialize(object);
	    return DatatypeConverter.printBase64Binary(bytes);
	}

	public static <E> E stringToObject(String string, Class<E> type) {
		byte[] decoded = DatatypeConverter.parseBase64Binary(string);
		return (E)SerializationUtils.deserialize(decoded);
	}
	
	public static <E extends Object> List<Object[]> convertListPojoToListObjectArray(List<E> entities) {
		List<Object[]> pojosAsObjectArray = new ArrayList<>();
		for (Iterator<E> iterator = entities.iterator(); iterator.hasNext();) {
			Object entity = iterator.next();
			Class<?> runtimeClass = entity.getClass();
			
			Object[] pojoAsObjectArray = new Object[runtimeClass.getDeclaredFields().length];
			int counter = 0;
			for (Field field : runtimeClass.getDeclaredFields()) {
				field.setAccessible(true);
				try {
					pojoAsObjectArray[counter] = field.get(entity);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				counter++;
			}
			pojosAsObjectArray.add(pojoAsObjectArray);
		}
		return pojosAsObjectArray;
	}

}
