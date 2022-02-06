package co.id.sofcograha.base.utils.searchData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.ALWAYS)
public class Pagination {

	private Long page;
	private Long perPage;
	private Long pages;
	private Long dataCount;

	public Pagination() {}

	public static Pagination paging(Long page, Long perPage) {
		Pagination pagination = new Pagination();

		pagination.pages = null;
		pagination.dataCount = null;
		pagination.page = page;
		pagination.perPage = perPage;

		return pagination;
	}

	public boolean pageable() {
		if (page != null && perPage != null) {
			return true;
		} else {
			return false;
		}
	}

	public Long getPage() {
		return page;
	}
	public void setPage(Long page) {
		this.page = page;
	}
	public Long getPerPage() {
		return perPage;
	}
	public void setPerPage(Long perPage) {
		this.perPage = perPage;
	}
	public Long getPages() {
		return pages;
	}
	public void setPages(Long pages) {
		this.pages = pages;
	}
	public Long getDataCount() {
		return dataCount;
	}
	public void setDataCount(Long dataCount) {
		this.dataCount = dataCount;
	}
}

