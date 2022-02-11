package co.id.sofcograha.training.controllers;

import co.id.sofcograha.base.utils.ApiResponse;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.domain.invoicing.masters.customer.entities.ECustomerGajiId;
import co.id.sofcograha.domain.invoicing.masters.customer.pojos.CustomerGajiId;
import co.id.sofcograha.training.entities.MasterBukuEntity;
import co.id.sofcograha.training.pojos.MasterBukuPojo;
import co.id.sofcograha.training.services.MasterBukuService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/buku")
public class MasterBukuController {

	public static final Logger logger = LoggerFactory.getLogger(MasterBukuController.class);

	@Autowired
	private MasterBukuService masterBukuService;
	

	// -------------------Retrieve Some ---------------------------------------------
	@GetMapping(value = "/search")
	public ApiResponse search(@RequestParam Map<String, String> searchParameter) {
		
		SearchResult<ECustomerGajiId> searchResult = masterBukuService.search(SearchParameter.generate(searchParameter, CustomerGajiId.getFieldMappings()));
	    
		return ApiResponse.dataWithPaging("items", CustomerGajiId.fromEntities(searchResult.getResult()),
				searchResult.getPaging());
	}

	// -------------------Retrieve Single Data ------------------------------------------
	@GetMapping
	public ApiResponse get(@RequestParam String nama) throws JsonProcessingException {

		ECustomerGajiId entity = masterBukuService.findByBk(nama);
		
		return ApiResponse.data("item", CustomerGajiId.fromEntity(entity));
		
	}

    @PostMapping
    public ApiResponse add(@RequestBody MasterBukuPojo pojo) {
		
		MasterBukuEntity result = masterBukuService.add(pojo);
		return ApiResponse.data("item", MasterBukuPojo.fromEntity(result));
    }
    
	@PutMapping
	public ApiResponse edit(@RequestBody MasterBukuPojo pojo) {

		MasterBukuEntity result = masterBukuService.edit(pojo);
		return ApiResponse.data("item", MasterBukuPojo.fromEntity(result));
		
	}

	@DeleteMapping
	public ApiResponse delete(@RequestParam String id, @RequestParam(required = false) Long version) {
		masterBukuService.delete(id, version);
		return ApiResponse.ok();
	}

	@GetMapping(value = "/get-by-nama-buku")
	public ApiResponse getByNama(@RequestParam String nama) throws JsonProcessingException {
		MasterBukuPojo data = masterBukuService.findByNamaBuku(nama);
		return ApiResponse.data("item", data);
	}
}