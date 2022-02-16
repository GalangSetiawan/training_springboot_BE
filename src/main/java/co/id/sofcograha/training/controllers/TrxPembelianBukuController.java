package co.id.sofcograha.training.controllers;

import co.id.sofcograha.base.utils.ApiResponse;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.MasterGenreEntity;
import co.id.sofcograha.training.entities.TrxHeaderEntity;
import co.id.sofcograha.training.pojos.MasterGenrePojo;
import co.id.sofcograha.training.pojos.TrxHeaderPojo;
import co.id.sofcograha.training.services.MasterGenreService;
import co.id.sofcograha.training.services.TrxCompositePembelianBukuService;
import co.id.sofcograha.training.services.TrxHeaderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/beli-buku")
public class TrxPembelianBukuController {

	public static final Logger logger = LoggerFactory.getLogger(TrxPembelianBukuController.class);

	@Autowired
	private TrxHeaderService trxHeaderService;

	@Autowired
	private TrxCompositePembelianBukuService trxCompositePembelianBukuService;
	

	// -------------------Retrieve Some ---------------------------------------------
	@GetMapping(value = "/search")
	public ApiResponse search(@RequestParam Map<String, String> searchParameter) {

		SearchResult<TrxHeaderEntity> searchResult = trxCompositePembelianBukuService.search(SearchParameter.generate(searchParameter, MasterGenrePojo.getFieldMappings()));

		return ApiResponse.dataWithPaging("items", TrxHeaderPojo.fromEntities(searchResult.getResult()),
				searchResult.getPaging());
	}

	// -------------------Retrieve Single Data ------------------------------------------
	@GetMapping
	public ApiResponse get(@RequestParam String nama) throws JsonProcessingException {

		TrxHeaderEntity entity = trxHeaderService.findByBk(nama);
		return ApiResponse.data("item", TrxHeaderPojo.fromEntity(entity));

	}

    @PostMapping
    public ApiResponse add(@RequestBody TrxHeaderPojo pojo) {

		TrxHeaderEntity result = trxCompositePembelianBukuService.add(pojo);
		return ApiResponse.data("item", TrxHeaderPojo.fromEntity(result));
    }
    
	@PutMapping
	public ApiResponse edit(@RequestBody TrxHeaderPojo pojo) {

		TrxHeaderEntity result = trxHeaderService.edit(pojo.toEntity());
		return ApiResponse.data("item", TrxHeaderPojo.fromEntity(result));

	}

	@DeleteMapping
	public ApiResponse delete(@RequestParam String id, @RequestParam(required = false) Long version) {
		trxHeaderService.delete(id, version);
		return ApiResponse.ok();
	}

//	@GetMapping(value = "/get-by-nama")
//	public ApiResponse getByNama(@RequestParam String nama) throws JsonProcessingException {
//		TrxHeaderEntity data = trxHeaderService.findByNama(nama);
//		return ApiResponse.data("item", data);
//	}
}