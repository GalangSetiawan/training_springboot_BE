package co.id.sofcograha.domain.invoicing.commonMasters.comboConstants.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.id.sofcograha.base.utils.ApiResponse;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.domain.invoicing.commonMasters.comboConstants.entities.EComboConstants;
import co.id.sofcograha.domain.invoicing.commonMasters.comboConstants.pojos.ComboConstants;
import co.id.sofcograha.domain.invoicing.commonMasters.comboConstants.services.ComboConstantsService;

@RestController
@RequestMapping("/api/combo-constants")
public class ComboConstantsController {

	public static final Logger logger = LoggerFactory.getLogger(ComboConstantsController.class);

	@Autowired
	private ComboConstantsService comboConstantsService;
	
	// -------------------Retrieve Some ---------------------------------------------
	@GetMapping(value = "/search")
	public ApiResponse search(@RequestParam Map<String, String> searchParameter) {
		
		SearchResult<EComboConstants> searchResult = comboConstantsService.search(SearchParameter.generate(searchParameter, ComboConstants.getFieldMappings()));
	    
		return ApiResponse.dataWithPaging("items", ComboConstants.fromEntities(searchResult.getResult()),
				searchResult.getPaging());
	}

	@GetMapping(value = "/komp-nomor-otomatis-excl-counter")
	public ApiResponse kompNoOtomatisExcludeCounter() {
		
		List<ComboConstants> result = ComboConstants.fromEntities(comboConstantsService.getKomponenNoOtomatisExcludeCounter());
	    
		return ApiResponse.data("items", result);
	}

}