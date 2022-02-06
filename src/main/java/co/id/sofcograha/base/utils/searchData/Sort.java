package co.id.sofcograha.base.utils.searchData;

import java.util.ArrayList;
import java.util.List;

public class Sort {

	private List<SortPojo> sort;
	private static final String multiSortDelimiter = ";";
	
	public Sort() {
		sort = new ArrayList<SortPojo>();
	}
	
	public static Sort multiSort(String rawSorts) {
		return multiSortWithFieldMapping(rawSorts, null);
	}
	
	public static Sort multiSortWithFieldMapping(String rawSorts, List<SearchFieldMapping> fieldMappings) {
		Sort sort = new Sort();
		List<SortPojo> sorts = new ArrayList<SortPojo>();
		
		if (rawSorts != null) {
			String[] rawSortz = rawSorts.split(multiSortDelimiter);
			
			for (String rawSort : rawSortz) {
				sorts.add(SortPojo.sortWithFieldMapping(rawSort, fieldMappings));
			}
		}
		sort.setSort(sorts);
		
		return sort;
	}

	public boolean sortable() {
		return sort.size() > 0;
	}

	public List<SortPojo> getSort() {
		return sort;
	}

	public void setSort(List<SortPojo> sort) {
		this.sort = sort;
	}
}
