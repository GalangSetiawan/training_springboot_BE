package co.id.sofcograha.base.utils.searchData;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.ALWAYS)
public class SearchResult<T> {

	private List<T> result;
	private Pagination paging;

	public SearchResult(List<T> result, Pagination paging) {
		this.result = result;
		this.paging = paging;
	}

	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	public Pagination getPaging() {
		return paging;
	}
	public void setPaging(Pagination paging) {
		this.paging = paging;
	}
}

