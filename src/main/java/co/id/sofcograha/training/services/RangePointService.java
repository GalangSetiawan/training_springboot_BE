package co.id.sofcograha.training.services;

import co.id.sofcograha.base.extendables.BaseService;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.RangePointEntity;
import co.id.sofcograha.training.repositories.RangePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rangePointService")
public class RangePointService extends BaseService {
	
	@Autowired private RangePointRepository repo;
	
	public RangePointEntity findByBk(String namaMembership) {
		return repo.findByBK(namaMembership);
	}

	public RangePointEntity findByTotalBayar(Double totalBayar, Boolean flagPoint) {
		return repo.findByTotalBayar(totalBayar, flagPoint);
	}
	
	public RangePointEntity findById(final String id) {
		return repo.getOne(id);
	}

	public SearchResult<RangePointEntity> search(SearchParameter searchParameter) {
		return repo.search(searchParameter);
	}

	public RangePointEntity get(String id) {
		return repo.getOne(id);
	}
}
