package co.id.sofcograha.domain.invoicing.transaksi.invoice.data.pojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.Message;
import co.id.sofcograha.base.utils.TimeUtil;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities.EInvoiceDetailLainLain;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities.EInvoiceHeader;

@JsonInclude(Include.ALWAYS)
public class InvoiceDetailLainLain {
	
	public String id;
	public Integer nourut;  
	public String keterangan;
    public Double harga;
    public Double pctdisc;
    public Double nilpctdisc;
    public Double nildisc;
    public Double netto;
	
	public Long version;  
    public String usrcrt;
    public Date tglcrt;
    public String jamcrt;
    public String usrupd;
    public Date tglupd;
    public String jamupd;
	
	// untuk keperluan matching error message di front end (bila input di grid)
	public String keyIn;

	// untuk penanda apakah detail ini tertandai delete (dari front end)
	public boolean isSelect;
	
	public InvoiceHeader header;

	// Untuk menampung message error di grid
	public ArrayList<Message> errorMsg;
	
	public EInvoiceDetailLainLain toEntity() {
		
		EInvoiceDetailLainLain entity = new EInvoiceDetailLainLain();
		
		entity.setId(id);
		entity.setNourut(nourut);
		entity.setNourut(nourut);
		entity.setKeterangan(keterangan);
		entity.setHarga(harga);
		entity.setPctdisc(pctdisc);
		entity.setNilpctdisc(nilpctdisc);
		entity.setNildisc(nildisc);
		entity.setNetto(netto);
		
		entity.setVersion(version);
        entity.setUsrcrt(usrcrt);
        
		if (tglcrt != null) {
			entity.setTglcrt(TimeUtil.convertDateToYyyyMmDd(tglcrt)); 
		}
        
        entity.setJamcrt(jamcrt);
        entity.setUsrupd(usrupd);
        
		if (tglupd != null) {
			entity.setTglupd(TimeUtil.convertDateToYyyyMmDd(tglupd)); 
		}
        
        entity.setJamupd(jamupd);
		
		if (header != null && !StringUtils.isEmpty(header.id)) {
			EInvoiceHeader entityRef = new EInvoiceHeader();
		
			entityRef.setId(header.id);
			entityRef.setVersion(header.version);
		
			entity.setHeader(entityRef);
		
		} else {
			entity.setHeader(null);
		}

		return entity;
	}
		
	public static List<EInvoiceDetailLainLain> toEntities(final List<InvoiceDetailLainLain> pojos) {
		List<EInvoiceDetailLainLain> entities = new ArrayList<EInvoiceDetailLainLain>();
		for (InvoiceDetailLainLain pojo : pojos) {
			entities.add(pojo.toEntity());
		}

		return entities;
	}
	
	public static InvoiceDetailLainLain fromEntity(EInvoiceDetailLainLain entity) {
		return fromEntity(entity, BaseConstants.DEFAULT_QUERY_DEPTH, BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH);
	}
	
	public static InvoiceDetailLainLain fromEntity(EInvoiceDetailLainLain entity, int depthLevel) {
		return fromEntity(entity, depthLevel, BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH);
	}

	public static InvoiceDetailLainLain fromEntity(EInvoiceDetailLainLain entity, int depthLevel, int detailDepthLevel) {
		
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
		if (detailDepthLevel < 0) detailDepthLevel = BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH;
		
		InvoiceDetailLainLain pojo = new InvoiceDetailLainLain();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.nourut = entity.getNourut();
			pojo.keterangan = entity.getKeterangan();
			pojo.harga = entity.getHarga();
			pojo.pctdisc = entity.getPctdisc();
			pojo.nilpctdisc = entity.getNilpctdisc();
			pojo.nildisc = entity.getNildisc();
			pojo.netto = entity.getNetto();

			pojo.version = entity.getVersion();
	        pojo.usrcrt = entity.getUsrcrt();
	        
			if (entity.getTglcrt() != null && !entity.getTglcrt().trim().equals("")) {
				pojo.tglcrt = TimeUtil.getDate(entity.getTglcrt());				
			} else {
				pojo.tglcrt = null;
			}
	        
	        pojo.jamcrt = entity.getJamcrt();
	        pojo.usrupd = entity.getUsrupd();
	        
			if (entity.getTglupd() != null && !entity.getTglupd().trim().equals("")) {
				pojo.tglupd = TimeUtil.getDate(entity.getTglupd());				
			} else {
				pojo.tglupd = null;
			}
	        
	        pojo.jamupd = entity.getJamupd();
			
			pojo.header = InvoiceHeader.fromEntity(entity.getHeader(), depthLevel);
	        
			if (detailDepthLevel > 0) {
				detailDepthLevel--;
				
			}
			
			pojo.isSelect = false;
		}
				
		return pojo;
	}
	
	public static List<InvoiceDetailLainLain> fromEntities(List<EInvoiceDetailLainLain> entities){
		return fromEntities(entities, BaseConstants.DEFAULT_QUERY_DEPTH, BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH);
	}

	public static List<InvoiceDetailLainLain> fromEntities(final List<EInvoiceDetailLainLain> entities, int depthLevel) {
		return fromEntities(entities, depthLevel, BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH);
	}

	public static List<InvoiceDetailLainLain> fromEntities(List<EInvoiceDetailLainLain> entities, int depthLevel, int detailDepthLevel){
		
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
	    if (detailDepthLevel < 0) detailDepthLevel = BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH;
		
		List<InvoiceDetailLainLain> pojos = new ArrayList<>();
		
		for (EInvoiceDetailLainLain entity : entities) {
			pojos.add(InvoiceDetailLainLain.fromEntity(entity, depthLevel, detailDepthLevel));
		}
		return pojos;
	}
	
	public static List<SearchFieldMapping> getFieldMappings() {
		List<SearchFieldMapping> mappings = new ArrayList<>();
		
//		mappings.add(new SearchFieldMapping("widgetId", "widget.id").searchOnly());
//		mappings.add(new SearchFieldMapping("userId", "user.id"));
//		mappings.add(new SearchFieldMapping("orderNumber", "orderNumber", Integer.class).sortOnly());
		
		return mappings;
	}
}
