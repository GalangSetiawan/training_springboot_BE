package co.id.sofcograha.training.controllers;

import co.id.sofcograha.base.utils.ApiResponse;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.MasterGenreEntity;
import co.id.sofcograha.training.pojos.MasterGenrePojo;
import co.id.sofcograha.training.services.MasterGenreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/genre")
public class MasterGenreController {

	public static final Logger logger = LoggerFactory.getLogger(MasterGenreController.class);

	@Autowired
	private MasterGenreService masterGenreService;
	

	// -------------------Retrieve Some ---------------------------------------------
	@GetMapping(value = "/search")
	public ApiResponse search(@RequestParam Map<String, String> searchParameter) {
		
		SearchResult<MasterGenreEntity> searchResult = masterGenreService.search(SearchParameter.generate(searchParameter, MasterGenrePojo.getFieldMappings()));

		return ApiResponse.dataWithPaging("items", MasterGenrePojo.fromEntities(searchResult.getResult()),
				searchResult.getPaging());
	}

	// -------------------Retrieve Single Data ------------------------------------------
	@GetMapping
	public ApiResponse get(@RequestParam String nama) throws JsonProcessingException {

		MasterGenreEntity entity = masterGenreService.findByBk(nama);
		return ApiResponse.data("item", MasterGenrePojo.fromEntity(entity));
		
	}

    @PostMapping
    public ApiResponse add(@RequestBody MasterGenrePojo pojo) {

		MasterGenreEntity result = masterGenreService.add(pojo);
		return ApiResponse.data("item", MasterGenrePojo.fromEntity(result));
    }
    
	@PutMapping
	public ApiResponse edit(@RequestBody MasterGenrePojo pojo) {

		MasterGenreEntity result = masterGenreService.edit(pojo.toEntity());
		return ApiResponse.data("item", MasterGenrePojo.fromEntity(result));
		
	}

	@DeleteMapping
	public ApiResponse delete(@RequestParam String id, @RequestParam(required = false) Long version) {
		masterGenreService.delete(id, version);
		return ApiResponse.ok();
	}

//	@GetMapping(value = "/get-by-nama")
//	public ApiResponse getByNama(@RequestParam String nama) throws JsonProcessingException {
//		MasterGenreEntity data = masterGenreService.findByNama(nama);
//		return ApiResponse.data("item", data);
//	}
}