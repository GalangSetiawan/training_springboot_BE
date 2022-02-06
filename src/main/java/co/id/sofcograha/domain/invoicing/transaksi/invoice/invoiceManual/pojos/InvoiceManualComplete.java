package co.id.sofcograha.domain.invoicing.transaksi.invoice.invoiceManual.pojos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities.EInvoiceHeader;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.pojos.InvoiceDetailLainLain;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.pojos.InvoiceHeader;

@JsonInclude(Include.ALWAYS)
public class InvoiceManualComplete {

	public InvoiceHeader header;
	public List<InvoiceDetailLainLain> detailLainLain;
	
	// untuk penanda apakah data ini tertandai dipilih untuk grid yang punya checkbox select
	// dipakai di proses bulanan (grid jurnal rutin)
	public boolean isSelect;

	@JsonIgnore
	public EInvoiceHeader getEntityHeader() {
		return header.toEntity();
	}

	public static InvoiceManualComplete setFromEntity(EInvoiceHeader entity) {
		
		InvoiceManualComplete pojo = new InvoiceManualComplete();
		pojo.header = InvoiceHeader.fromEntity(entity);

		// detail depth level diisi = 1, supaya sub detail ikutan diambil (karena detail punya sub detail0
		pojo.detailLainLain = InvoiceDetailLainLain.fromEntities(entity.getDetailLainLain(), BaseConstants.DEFAULT_QUERY_DEPTH, 1);
		
		pojo.isSelect = false;
		
		return pojo;
	}
	
	public static List<InvoiceManualComplete> fromEntities(List<EInvoiceHeader> entities){
	
		if (entities == null) return new ArrayList<>();
		
		List<InvoiceManualComplete> pojos = new ArrayList<>();
		
		for (EInvoiceHeader entity : entities) {
			pojos.add(setFromEntity(entity));
		}
		return pojos;
		
	}
	
}
