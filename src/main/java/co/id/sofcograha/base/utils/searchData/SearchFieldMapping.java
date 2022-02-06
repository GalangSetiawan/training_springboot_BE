package co.id.sofcograha.base.utils.searchData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchFieldMapping {
	public String pojo;
	
	public String filter;
	public Class<?> filterType;
	
	public boolean sortable;
	public boolean searchable;
	
	public SearchFieldMapping(String pojo, String entity) {
		this(pojo, entity, String.class);
	}
	
	public SearchFieldMapping(String pojo, String entity, Class<?> type) {
		this.setPojoInfo(pojo);
		this.setEntityInfo(entity, type);
		this.searchable = true;
		this.sortable = true;
	}
	
	public SearchFieldMapping searchOnly() {
		this.searchable = true;
		this.sortable = false;
		return this;
	}
	
	public SearchFieldMapping sortOnly() {
		this.searchable = false;
		this.sortable = true;
		return this;
	}
	
	@JsonIgnore 
	private SearchFieldMapping setPojoInfo(String field) { this.pojo = field;
		return this;
	}
	
	@JsonIgnore
	private SearchFieldMapping setEntityInfo(String field, Class<?> type) {
		this.filter = field;
		this.filterType = type;
		return this;
	}
}

