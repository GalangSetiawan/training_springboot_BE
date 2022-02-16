package co.id.sofcograha.training.controllers;

import co.id.sofcograha.base.utils.ApiResponse;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.MasterMembershipEntity;
import co.id.sofcograha.training.pojos.MasterMembershipPojo;
import co.id.sofcograha.training.services.MasterMembershipService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/membership")
public class MasterMembershipController {

	public static final Logger logger = LoggerFactory.getLogger(MasterMembershipController.class);

	@Autowired
	private MasterMembershipService masterMembershipService;
	

	// -------------------Retrieve Some ---------------------------------------------
	@GetMapping(value = "/search")
	public ApiResponse search(@RequestParam Map<String, String> searchParameter) {
		
		SearchResult<MasterMembershipEntity> searchResult = masterMembershipService.search(SearchParameter.generate(searchParameter, MasterMembershipPojo.getFieldMappings()));

		return ApiResponse.dataWithPaging("items", MasterMembershipPojo.fromEntities(searchResult.getResult(), 2),
				searchResult.getPaging());
	}

	// -------------------Retrieve Single Data ------------------------------------------
	@GetMapping
	public ApiResponse get(@RequestParam String nama) throws JsonProcessingException {

		MasterMembershipEntity entity = masterMembershipService.findByBK(nama);
		return ApiResponse.data("item", MasterMembershipPojo.fromEntity(entity));
		
	}

    @PostMapping
    public ApiResponse add(@RequestBody MasterMembershipPojo pojo) {

		MasterMembershipEntity result = masterMembershipService.add(pojo);
		return ApiResponse.data("item", MasterMembershipPojo.fromEntity(result));
    }
    
	@PutMapping
	public ApiResponse edit(@RequestBody MasterMembershipPojo pojo) {

		MasterMembershipEntity result = masterMembershipService.edit(pojo.toEntity());
		return ApiResponse.data("item", MasterMembershipPojo.fromEntity(result));
		
	}

	@DeleteMapping
	public ApiResponse delete(@RequestParam String id, @RequestParam(required = false) Long version) {
		masterMembershipService.delete(id, version);
		return ApiResponse.ok();
	}

//	@GetMapping(value = "/get-by-nama")
//	public ApiResponse getByNama(@RequestParam String nama) throws JsonProcessingException {
//		MasterGenreEntity data = masterGenreService.findByNama(nama);
//		return ApiResponse.data("item", data);
//	}
}