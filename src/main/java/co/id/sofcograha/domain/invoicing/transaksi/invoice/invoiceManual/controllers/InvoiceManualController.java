package co.id.sofcograha.domain.invoicing.transaksi.invoice.invoiceManual.controllers;

import java.text.ParseException;
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

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.ApiResponse;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities.EInvoiceHeader;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.pojos.InvoiceHeader;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.services.InvoiceHeaderService;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.invoiceManual.pojos.InvoiceManualComplete;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.invoiceManual.services.InvoiceManualCompleteService;

@RestController
@RequestMapping("/api/invoice-manual")
public class InvoiceManualController {
	
	public static final Logger logger = LoggerFactory.getLogger(InvoiceManualController.class);
	
	@Autowired private InvoiceHeaderService invoiceHeaderService;
	@Autowired private InvoiceManualCompleteService invoiceManualCompleteService;
	
	@GetMapping(value = "/search")
	public ApiResponse search(@RequestParam Map<String, String> searchParameter) {
		
		SearchResult<EInvoiceHeader> searchResult = invoiceHeaderService.search(SearchParameter.generate(searchParameter, 
				InvoiceHeader.getFieldMappings()));
		
		return ApiResponse.dataWithPaging("items", 
				InvoiceHeader.fromEntities(searchResult.getResult()), 
				searchResult.getPaging());
	}
	
	@GetMapping
	public ApiResponse get(@RequestParam String nomor) throws JsonProcessingException, ParseException {
		InvoiceManualComplete data = invoiceManualCompleteService.findByBk(nomor);
	    
	    return ApiResponse.data("item", data);
	}
	
	@PostMapping
	public ApiResponse add(@RequestBody InvoiceManualComplete pojo) {

		EInvoiceHeader header = invoiceManualCompleteService.add(pojo, BaseConstants.JENIS_TRX_INVOICE_MANUAL);
		
		return ApiResponse.data("item", InvoiceManualComplete.setFromEntity(header));
	}
	
	@PutMapping
	public ApiResponse edit(@RequestBody InvoiceManualComplete pojo) {
		
		EInvoiceHeader header = invoiceManualCompleteService.edit(pojo);
		
		return ApiResponse.data("item", InvoiceManualComplete.setFromEntity(header));
	}

	@DeleteMapping
	public ApiResponse delete(@RequestParam String id, @RequestParam(required = false) Long version) {
		
		invoiceManualCompleteService.delete(id, version);
		
		return ApiResponse.ok();
	}
	
}