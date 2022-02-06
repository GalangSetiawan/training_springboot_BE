package co.id.sofcograha.domain.invoicing.commonMasters.comboConstants.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.domain.invoicing.commonMasters.comboConstants.entities.EComboConstants;
import co.id.sofcograha.domain.invoicing.commonMasters.comboConstants.repositories.EComboConstantsRepository;

@Service("comboConstantsService")
public class ComboConstantsService {
    
	@Autowired
	private EComboConstantsRepository eComboConstantsRepository;	
	
	public EComboConstants findByBk(String id, String rectyp, String reccode) {
		return eComboConstantsRepository.findByBK(id, rectyp, reccode);
	}
	
	public List<EComboConstants> getByType(String rectyp) {
		return eComboConstantsRepository.getByType(rectyp);
	}
	
	public List<EComboConstants> getKomponenNoOtomatisExcludeCounter() {
		return eComboConstantsRepository.getKomponenNoOtomatisExcludeCounter();
	}
	
	public SearchResult<EComboConstants> search(SearchParameter searchParameter) {
		return eComboConstantsRepository.search(searchParameter);
	}
}
