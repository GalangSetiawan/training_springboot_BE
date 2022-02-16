package co.id.sofcograha.training.controllers;

import co.id.sofcograha.base.utils.ApiResponse;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.RangePointEntity;
import co.id.sofcograha.training.pojos.RangePointPojo;
import co.id.sofcograha.training.services.RangePointService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rangepoint")
public class RangePointController {

	public static final Logger logger = LoggerFactory.getLogger(RangePointController.class);

	@Autowired
	private RangePointService rangePointService;
	

	// -------------------Retrieve Some ---------------------------------------------
	@GetMapping(value = "/search")
	public ApiResponse search(@RequestParam Map<String, String> searchParameter) {
		
		SearchResult<RangePointEntity> searchResult = rangePointService.search(SearchParameter.generate(searchParameter, RangePointPojo.getFieldMappings()));

		return ApiResponse.dataWithPaging("items", RangePointPojo.fromEntities(searchResult.getResult()),
				searchResult.getPaging());
	}

	// -------------------Retrieve Single Data ------------------------------------------
	@GetMapping
	public ApiResponse get(@RequestParam Double totalBayar, Boolean flagPoint) throws JsonProcessingException {

		RangePointEntity entity = rangePointService.findByTotalBayar(totalBayar, flagPoint);
		return ApiResponse.data("item", RangePointPojo.fromEntity(entity));
		
	}


//	@GetMapping(value = "/get-by-nama")
//	public ApiResponse getByNama(@RequestParam String nama) throws JsonProcessingException {
//		MasterGenreEntity data = masterGenreService.findByNama(nama);
//		return ApiResponse.data("item", data);
//	}
}