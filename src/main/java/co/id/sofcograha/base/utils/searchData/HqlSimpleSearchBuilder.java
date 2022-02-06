package co.id.sofcograha.base.utils.searchData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import co.id.sofcograha.base.constants.enums.SortType;
import co.id.sofcograha.base.exceptions.CustomSearchException;

public class HqlSimpleSearchBuilder<T> {

	private static final String newLine = "\n";

	private EntityManager em;
	private Class<T> domainClass;
	private List<String> wheres;
	private HashMap<String, Object> parameters;
	private Sort sort;
	private Sort injectedSort;
	private Pagination paging;

	public HqlSimpleSearchBuilder(Class<T> domainClass, EntityManager em) {
		this.em = em;
		this.domainClass = domainClass;
		this.wheres = new ArrayList<String>();
		this.parameters = new HashMap<String, Object>();
		this.sort = new Sort();
		this.paging = new Pagination();
	}

	public SearchResult<T> getSearchResult() {
		boolean pageable = paging == null? false : paging.pageable();
		boolean sortable = injectedSort == null? sort == null? false : sort.sortable() : injectedSort.sortable();

		TypedQuery<T> result;

		StringBuffer query = new StringBuffer();
		StringBuffer queryCount = new StringBuffer();

		buildQueryStatement(pageable, query, queryCount);

		if (sortable) {
			addSortToQuery(query);
		} else {
			addOrderBy(query);
			query.append("e.id");
		}

		result = em.createQuery(query.toString(), domainClass);

		if (pageable) {
			configurePagination(result, queryCount);
		} else {
			paging = null;
		}

		parameters.forEach((key, value) -> {
			result.setParameter(key, value);
		});
		List<T> results = result.getResultList();
		SearchResult<T> searchResults = new SearchResult<T>(results, paging);

		return searchResults;
	}

	private void buildQueryStatement(boolean pageable, StringBuffer query, StringBuffer queryCount) {
		query.append(newLine + "FROM " + domainClass.getSimpleName() + " e WHERE 1 = 1 ");
		queryCount.append(newLine + "SELECT COUNT(1) FROM " + domainClass.getSimpleName() + " e WHERE 1 = 1 ");

		wheres.forEach((where) -> {
			query.append(newLine + where);
			if (pageable) {
				queryCount.append(newLine + where);
			}
		});
	}

	private void configurePagination(TypedQuery<T> result, StringBuffer queryCount) {
		Long page = paging.getPage();
		Long perPage = paging.getPerPage();

		if (page.intValue() <= 0 || perPage.intValue() <= 0) {
			throw new CustomSearchException("invalid search parameter", "page and perPage must be > 0");
		}
		int firstResult = (page.intValue() - 1) * perPage.intValue();
		int maxResult = perPage.intValue();
		result.setFirstResult(firstResult).setMaxResults(maxResult);

		Query count = em.createQuery(queryCount.toString());

		parameters.forEach((key, value) -> {
			count.setParameter(key, value);
		});

		Long maxData = (long) count.getSingleResult();
		Long pages = (long) Math.ceil(maxData.doubleValue() / perPage.doubleValue());
		paging.setDataCount(maxData);
		paging.setPages(pages);
	}

	private void addOrderBy(StringBuffer query) {
		query.append("ORDER BY ");
	}

	private void addSortToQuery(StringBuffer query) {
		addOrderBy(query);
		appendInjectedSort(query);
		appendSort(query);
		query.append(", e.id ");
	}

	public void addWhere(String where) {
		this.wheres.add(where);
	}

	public void addParameter(String key, Object value) {
		this.parameters.put(key, value);
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public void setPaging(Pagination paging) {
		this.paging = paging;
	}
	
	public void setInjectedSort(Sort sort) {
		this.injectedSort = sort;
	}

	private void appendSort(StringBuffer query) {
		sort.getSort().forEach(sortz -> {
			query.append(sortz.getField() + " " + sortz.getType().toUpperCase());
			if (sort.getSort().indexOf(sortz) + 1 != sort.getSort().size()) {
				query.append(", ");
			}
		});
	}

	private void appendInjectedSort(StringBuffer query) {
		if (injectedSort != null) {
			injectedSort.getSort().forEach(sortz -> {
				query.append(sortz.getField() + " " + sortz.getType().toUpperCase());
				if (injectedSort.getSort().indexOf(sortz) + 1 != injectedSort.getSort().size()) {
					query.append(", ");
				}
			});
		}
	}

	public void injectSort(String sortField, SortType sortType) {
		List<SortPojo> finalSorts = injectedSort == null? new ArrayList<>() : injectedSort.getSort();
		Sort sort = injectedSort == null? new Sort() : injectedSort;
		
		SortPojo injectedSort = new SortPojo();
		injectedSort.setField(sortField);
		injectedSort.setType(sortType.name());
		finalSorts.add(injectedSort);
		
		sort.setSort(finalSorts);
		setInjectedSort(sort);
	}
}
