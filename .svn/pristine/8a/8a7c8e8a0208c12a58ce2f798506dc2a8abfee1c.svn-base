package co.id.sofcograha.base.utils.searchData;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.id.sofcograha.base.exceptions.CustomSearchParameterException;
import co.id.sofcograha.base.utils.TimeUtil;

//TODO vint: consider rewrite for clean code..
//TODO vint: consider performance, skip parameter to field checking when no parameter is present!
public class SearchParameter{
	
	private Map<String, Object> mappedParam;
	private Map<String, String> paramIn;
	private List<SearchFieldMapping> fieldMappings;
	private Sort sort;
	private Pagination pagination;

	private static final String keyPage = "page";
	private static final String keyPerPage = "perPage";
	private static final String keySort = "sort";

	public SearchParameter() {
		paramIn = new HashMap<String, String>();
	}

	public static SearchParameter generate(Map<String, String> paramIn, List<SearchFieldMapping> fieldMappings) {
		SearchParameter searchParameter = generate(paramIn);
		searchParameter.withMappedFields(fieldMappings);
		searchParameter.withSort();
		searchParameter.withPagination();
		return searchParameter;
	}

	private static SearchParameter generate(Map<String, String> paramIn) {
		SearchParameter searchParameter = new SearchParameter();
		searchParameter.setParamIn(paramIn);
		return searchParameter;
	}

	private SearchParameter withMappedFields(List<SearchFieldMapping> fieldMappings) {
		this.mappedParam = new HashMap<String, Object>();
		this.fieldMappings = new ArrayList<SearchFieldMapping>();
		this.setFieldMappings(fieldMappings);
		this.setMappedParam();
		return this;
	}

	private SearchParameter withPagination() {
		this.pagination = new Pagination();
		this.setPagination();
		return this;
	}

	private SearchParameter withSort() {
		this.sort = new Sort();
		this.setSort();
		return this;
	}

	private SearchParameter setPagination() {
		Long page = null;
		Long perPage = null;
		valParamForPagination();

		try {
			// data diambil untuk page yang ke berapa, ada di paramIn dengan key "page" 
			page = paramIn.get("page") == null? null : Long.valueOf(paramIn.get("page"));

			// berapa banyak data yang diambil untuk page tersebut, ada di paramIn dengan key "perPage" 
			perPage = paramIn.get("perPage") == null? null : Long.valueOf(paramIn.get("perPage"));
		} catch (NumberFormatException e) {
			paginationError();
		}

		pagination = Pagination.paging(page, perPage);
		return this;
	}

	private void valParamForPagination() {
		// must be paired or none at all
		if (paramIn.get(keyPage) != null && paramIn.get(keyPerPage) == null) {
			paginationError();
		}
		if (paramIn.get(keyPerPage) != null && paramIn.get(keyPage) == null) {
			paginationError();
		}
	}

	private void paginationError() {
		String message = "Parameter for pagination is either invalid or incomplete!";
		String hint = "Please provide '" + keyPage + "' and '" + keyPerPage + "' parameter with natural number w/o zero '0' in your request param";
		error(message, hint);
	}

	private SearchParameter setSort() {
		// validasi apakah field-field sort yang dikirimkan cocok dengan daftar field yang bisa di sort 
		// yang telah ditetapkan sebelumnya
		valFieldNotSortable(generateValidFieldsForSortFromFieldMappings(fieldMappings));
		
		// field-field untuk sorting ada di paramIn dengan key : "sort" dan value : "field1;field2;field3"
		sort = Sort.multiSortWithFieldMapping(paramIn.get(keySort), fieldMappings); 
		
		// ini menghasilkan object sort yang intinya berisi list of object SortPojo
		// dimana object sort pojo berisi nama field dan tipenya
		this.setSort(sort);   

		return this;
	}

	private void valFieldNotSortable(List<String> sortableFields) {
		// val with pojo-ed fields
		boolean isError = false;
		List<String> unsearchableFields = new ArrayList<String>();
		List<String> in = new ArrayList<String>();
		sort = Sort.multiSort(paramIn.get(keySort));
		sort.getSort().forEach(sortz -> {
			in.add(sortz.getField());
		});

		for (int i = 0; i < in.size(); i++) {
			String s = in.get(i);
			if (!sortableFields.contains(s)) {
				unsearchableFields.add(s);
				isError = true;
			}
		}

		String unsearchableFieldsForErrorInfo = String.join(", ", unsearchableFields);
		String sortableFieldsForErrorInfo = String.join(", ", sortableFields);
		if (isError) sortError(unsearchableFieldsForErrorInfo, sortableFieldsForErrorInfo);
	}

	private void sortError(String unsearchableFieldsForErrorInfo, String sortableFieldsForErrorInfo) {
		String message = "Parameter for sort is invalid or not found!";
		String hint = "Your invalid param(s): " + unsearchableFieldsForErrorInfo + "\nSortable param(s): " + sortableFieldsForErrorInfo + "\nCase-Sensitive!";
		error(message, hint);
	}

	private SearchParameter setMappedParam() {
		valFieldNotSearchable();

		// value untuk masing-masing field yang ada di fieldMappings dicari di paramIn dan diletakkan di mappedParam
		fieldMappings.forEach(fieldMapping -> {
			this.mappedParam.put(fieldMapping.filter, castValueFromString(fieldMapping, paramIn.get(fieldMapping.pojo)));
		});

		return this;
	}

	private void valFieldNotSearchable() {
		List<String> unsearchableFields = new ArrayList<String>();
		List<String> searchableFields = generateValidFieldsForSearchFromFieldMappings(fieldMappings);

		paramIn.forEach((key, value) -> {
			if (!(key.equals(keyPage) || key.equals(keyPerPage) || key.equals(keySort))) {
				if (!searchableFields.contains(key)) {
					unsearchableFields.add(key);
				}
			}
		});

		String unsearchableFieldsForErrorInfo = String.join(", ", unsearchableFields);

		String searchableFieldsForErrorInfo = String.join(", ", searchableFields);
		if (unsearchableFields.size() > 0) unsearchableError(searchableFieldsForErrorInfo, unsearchableFieldsForErrorInfo);
	}

	private List<String> generateValidFieldsForSearchFromFieldMappings(List<SearchFieldMapping> fieldMappings) {
		List<String> searchableFields = new ArrayList<String>();
		fieldMappings.forEach(fieldMapping -> {
			if (fieldMapping.searchable) {
				searchableFields.add(fieldMapping.pojo);
			}
		});
		return searchableFields;
	}

	private List<String> generateValidFieldsForSortFromFieldMappings(List<SearchFieldMapping> fieldMappings) {
		List<String> searchableFields = new ArrayList<String>();
		fieldMappings.forEach(fieldMapping -> {
			if (fieldMapping.sortable) {
				searchableFields.add(fieldMapping.pojo);
			}
		});
		return searchableFields;
	}

	private void unsearchableError(String searchableFieldsForErrorInfo, String unsearchableFieldsForErrorInfo) {
		String message = "One or more parameters for filter can't be use!";
		String hint = "Your invalid param(s): " + unsearchableFieldsForErrorInfo + "\nSearchable param(s): " + searchableFieldsForErrorInfo + "\nCase-Sensitive!";
		error(message, hint);
	}

	protected Object castValueFromString(SearchFieldMapping mapInfo, String value) {
		if (value == null) return null;

		Class<?> expectedType = mapInfo.filterType;

		if (value.getClass().equals(expectedType)) return value; //no need to convert

		Object castedValue = null;

		if (expectedType.equals(Double.class)) {
			try {
				return Double.valueOf((String) value);
			} catch (NumberFormatException e) {
				castError(expectedType.getSimpleName(), mapInfo.pojo, value.toString());
			}
		} 
		
		if (expectedType.equals(Integer.class)) {
			try {
				return Integer.valueOf((String) value);
			} catch (NumberFormatException e) {
				castError(expectedType.getSimpleName(), mapInfo.pojo, value.toString());
			}
		} 
		
		if (expectedType.equals(Date.class)) {
			return TimeUtil.convertIso8601ToDate((String) value);
		}
		
		if (expectedType.equals(LocalTime.class)) {
			return LocalTime.parse(value);
		}
		
		if (expectedType.equals(Boolean.class)) {
			if (value.equalsIgnoreCase("true") || value.toString().equalsIgnoreCase("false")) {
				return Boolean.valueOf(value);
			} else {
				castError(expectedType.getSimpleName(), mapInfo.pojo, value.toString());
			}
		}
		
//		if (expectedType.isEnum()) {
//			if (expectedType.equals(DayOfWeek.class)) {
//				return DayOfWeek.of(Integer.valueOf(value));
//			}
//			
//			return PojoUtil.string2RawEnum((String)value, expectedType);
//		} 
		
		castedValue = value; 
		return castedValue;
	}

	public Object getValueFromMappedParam(String mappedParamName) {
		return getMappedParam().get(mappedParamName);
	}

	public Object getValueFromParamIn(String paramInName) {
		return getParamIn().get(paramInName);
	}

	private void castError(String classTypeName, String key, String value) {
		String message = classTypeName + " conversion failed!";
		String hint = "Param name: '" + key + "'\nParam value: '" + value;
		error(message, hint);
	}

	protected void error(String msg, String hint) { throw new CustomSearchParameterException(msg, hint); }

	public Map<String, Object> getMappedParam() {
		return mappedParam;
	}
	public void setMappedParam(Map<String, Object> params) {
		this.mappedParam = params;
	}
	public List<SearchFieldMapping> getFieldMappings() {
		return fieldMappings;
	}
	public void setFieldMappings(List<SearchFieldMapping> fieldMappings) {
		this.fieldMappings = fieldMappings;
	}

	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination paging) {
		this.pagination = paging;
	}
	public Map<String, String> getParamIn() {
		return paramIn;
	}
	public void setParamIn(Map<String, String> paramIn) {
		this.paramIn = paramIn;
	}
}
