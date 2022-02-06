package co.id.sofcograha.base.utils.searchData;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

// Copied from erp4-common NativeSearchBuilder
public class NativeSearchBuilder<T> {
	
	private EntityManager em;
	private Pagination pagination;
	private Sort sort;
	private Class<T> domainClass;
	private StringBuilder queryStringBuilder;
	private HashMap<String, Object> parameters;
	
	public NativeSearchBuilder(Class<T> domainClass, EntityManager em) {
		this.em = em;
		this.pagination = new Pagination();
		this.sort = new Sort();
		this.domainClass = domainClass;
		this.queryStringBuilder = new StringBuilder();
		this.parameters = new HashMap<>();
	}

	@SuppressWarnings("unchecked")

	public SearchResult<T> getSearchResult() {
		Query query;
		
//		setSortable();
		
		boolean pageable = pagination == null ? false : pagination.pageable();
		if (pageable)
			query = em.createNativeQuery("SELECT * FROM (SELECT ROWNUM RNUM, A.* FROM ( SELECT *, row_number() over () as ROWNUM FROM (" + queryStringBuilder.toString() +
					" ) X ) A WHERE ROWNUM <= :maxRowNum) B WHERE ROWNUM >= :startRowNum", domainClass);
		else {
			query = em.createNativeQuery(queryStringBuilder.toString(), domainClass);
			pagination = null;
		}
		
		parameters.forEach((key, value) -> {
			query.setParameter(key, value);
		});
		
		if (pageable) {
			Integer startIndex = (pagination.getPage().intValue() - 1) * pagination.getPerPage().intValue();
			query.setParameter("maxRowNum", startIndex + pagination.getPerPage());
			query.setParameter("startRowNum", startIndex + 1);
		}
		
		setPaginationDataCount();
		
		SearchResult<T> searchResults = new SearchResult<T>(query.getResultList(), pagination);

		return searchResults;
	}
	
	@SuppressWarnings("unchecked")
    public List<Object[]> getEntities() {
        Query query;
      
        setSortable();
      
        boolean pageable = pagination == null ? false : pagination.pageable();
        if (pageable)
            query = em.createNativeQuery("SELECT * FROM (SELECT ROWNUM RNUM, A.* FROM ( SELECT *, row_number() over () as ROWNUM FROM (" + queryStringBuilder.toString() +
                    " ) X ) A WHERE ROWNUM <= :maxRowNum) B WHERE ROWNUM >= :startRowNum");
        else {
            query = em.createNativeQuery(queryStringBuilder.toString());
            pagination = null;
        }
        
        parameters.forEach((key, value) -> {
            query.setParameter(key, value);
        });
        
        if (pageable) {
            Integer startIndex = (pagination.getPage().intValue() - 1) * pagination.getPerPage().intValue();
            query.setParameter("maxRowNum", startIndex + pagination.getPerPage());
            query.setParameter("startRowNum", startIndex + 1);
        }
        
        setPaginationDataCount();
        return query.getResultList();
    }
	
	public NativeSearchBuilder<T> setQueryString(StringBuilder queryStringBuilder) {
		this.queryStringBuilder = queryStringBuilder;
		
		return this;
	}
	
	public NativeSearchBuilder<T> addParameter(String key, Object value) {
		this.parameters.put(key, value);
		
		return this;
	}
	
	public NativeSearchBuilder<T> setPagination(Pagination pagination) {
		this.pagination = pagination;
		
		return this;
	}
	
	public NativeSearchBuilder<T> setSort(Sort sort) {
		this.sort = sort;
		
		return this;
	}
	
	private void setSortable() {
		boolean sortable = sort == null ? false : sort.sortable();
		if (sortable) {
			queryStringBuilder.append("ORDER BY ");

			for (int i = 0; i < sort.getSort().size(); i++) {
				queryStringBuilder.append(sort.getSort().get(i).getField() + " " + sort.getSort().get(i).getType().toUpperCase());
				if ((i + 1) != sort.getSort().size()) queryStringBuilder.append(", ");
			}
		}
	}
	
	private void setPaginationDataCount() {
		boolean pageable = pagination == null? false : pagination.pageable();
		if (pageable) {
			Query result = em.createNativeQuery("SELECT COUNT(1) FROM ( " + queryStringBuilder.toString() + " ) x");
			parameters.forEach((key, value) -> {
				result.setParameter(key, value);
			});
			
			Long maxData = ((BigInteger) result.getSingleResult()).longValue();

			pagination.setDataCount(maxData);
			
			Long pages = (long) Math.ceil(maxData.doubleValue() / pagination.getPerPage().doubleValue());
			pagination.setPages(pages);
		}
	}

    public Pagination getPagination() {
      return pagination;
    }
}
