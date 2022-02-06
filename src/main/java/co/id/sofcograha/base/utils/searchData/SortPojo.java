package co.id.sofcograha.base.utils.searchData;

import java.util.List;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.exceptions.CustomSearchException;

public class SortPojo {

	private String field;
	private String type;
	private static final String sortDelimiter = "-";

	public SortPojo() {
		field = BaseConstants.EMPTY;
		type = BaseConstants.EMPTY;
	}

	public static SortPojo sort(String field, String type) {
		SortPojo sortPojo = new SortPojo();

		sortPojo.setField(field);
		sortPojo.setType(type);

		return sortPojo;
	}
	
	public static SortPojo sortWithFieldMapping(String rawSorts, List<SearchFieldMapping> fieldMappings) {
		String field = BaseConstants.EMPTY;
		String type = BaseConstants.EMPTY;
		try {
			field = rawSorts.substring(0, rawSorts.indexOf(sortDelimiter));
			if (fieldMappings != null) {
				for (SearchFieldMapping fieldMapping : fieldMappings) {
					if (fieldMapping.pojo.equals(field)) {
						field = fieldMapping.filter;
					}
				}
			}
			type = rawSorts.substring(rawSorts.indexOf(sortDelimiter) + 1,rawSorts.length());
		} catch (StringIndexOutOfBoundsException e) {
			throw new CustomSearchException("sort.parameter.format.invalid", "format is 'field-type;field-type'");
		}
		return sort(field, type);
	}
	
	public static SortPojo sort(String rawSorts) {
		return sortWithFieldMapping(rawSorts, null);
	}

	public boolean sortable() {
		if (field != null && type != null) {
			return true;
		} else {
			return false;
		}
	}

	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		if (type != null) {
			if (!(type.equalsIgnoreCase("asc") || type.equalsIgnoreCase("desc"))) {
				throw new CustomSearchException("sort.type.invalid", "only 'asc' or 'desc' is allowed, case insensitive");
			}
		}

		this.type = type;
	}
}
