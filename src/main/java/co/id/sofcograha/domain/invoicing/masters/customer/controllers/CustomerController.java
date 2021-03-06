package co.id.sofcograha.domain.invoicing.masters.customer.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.id.sofcograha.base.utils.ApiResponse;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.domain.invoicing.masters.customer.entities.ECustomerGajiId;
import co.id.sofcograha.domain.invoicing.masters.customer.pojos.CustomerGajiId;
import co.id.sofcograha.domain.invoicing.masters.customer.services.CustomerGajiIdService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	public static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerGajiIdService customerGajiIdService;
	

	// -------------------Retrieve Some ---------------------------------------------
	@GetMapping(value = "/search")
	public ApiResponse search(@RequestParam Map<String, String> searchParameter) {
		
		SearchResult<ECustomerGajiId> searchResult = customerGajiIdService.search(SearchParameter.generate(searchParameter, CustomerGajiId.getFieldMappings()));
	    
		return ApiResponse.dataWithPaging("items", CustomerGajiId.fromEntities(searchResult.getResult()),
				searchResult.getPaging());
	}

	// -------------------Retrieve Single Data ------------------------------------------
	@GetMapping
	public ApiResponse get(@RequestParam String nama) throws JsonProcessingException {

		ECustomerGajiId entity = customerGajiIdService.findByBk(nama);
		
		return ApiResponse.data("item", CustomerGajiId.fromEntity(entity));
		
	}

    @PostMapping
    public ApiResponse add(@RequestBody CustomerGajiId pojo) {
		
		ECustomerGajiId result = customerGajiIdService.add(pojo.toEntity());
		return ApiResponse.data("item", CustomerGajiId.fromEntity(result));
    }
    
	@PutMapping
	public ApiResponse edit(@RequestBody CustomerGajiId pojo) {
		
		ECustomerGajiId result = customerGajiIdService.edit(pojo.toEntity());
		return ApiResponse.data("item", CustomerGajiId.fromEntity(result));
		
	}

	@DeleteMapping
	public ApiResponse delete(@RequestParam String id, @RequestParam(required = false) Long version) {
		customerGajiIdService.delete(id, version);
		return ApiResponse.ok();
	}

	@GetMapping(value = "/get-by-nama")
	public ApiResponse getByNama(@RequestParam String nama) throws JsonProcessingException {
		CustomerGajiId data = customerGajiIdService.findByNama(nama);
		return ApiResponse.data("item", data);
	}
}