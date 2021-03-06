package co.id.sofcograha.domain.invoicing.transaksi.invoice.data.pojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.TimeUtil;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.domain.invoicing.masters.customer.entities.ECustomerGajiId;
import co.id.sofcograha.domain.invoicing.masters.customer.pojos.CustomerGajiId;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities.EInvoiceHeader;

@JsonInclude(Include.ALWAYS)
public class InvoiceHeader {
	
	public String id;
    public String nomor;
    public Date tgtrn;
    public String nmcust;
    public String nama;
    public String alamat;
    public String email;
    public String status;
	public Double bruto;
	public Double totdisc;
	public Double dpp;
	public Double ppn;
	public Double netto;
	public Double depused;
    public Boolean fltodep;
	public Double nildep;
    public Date tgjtemp;
    public Boolean flasli;
    public String notes;
	public Double pctdis;
	public Double nildis;
    public String ketdis;
    public String ketinv;
	public Double nilbyr;
    
    // field bantu untuk input sent/tidak
    public boolean flsent;
    // field bantu untuk input bayar/tidak
    public boolean flbayar;
    // field bantu untuk menunjukkan berapa hari menuju jatuh tempo (jthtempo = tanggal jatuh tempo - tanggal hari ini)
    public Integer jthtempo;
    // field bantu untuk zonaisasi
    // zona 4 = masih lama
    // zona 3 = kuning (jthtempo 35% dari customer.jthtempo) 
    // zona 2 = oranye (jthtempo 25% dari customer.jthtempo) 
    // zona 1 = merah  (jthtempo 0% dari customer.jthtempo) 
    public Integer zona;
    
	public Long version;  
    public String usrcrt;
    public Date tglcrt;
    public String jamcrt;
    public String usrupd;
    public Date tglupd;
    public String jamupd;
	
	public CustomerGajiId customer;
	public List<InvoiceDetailLainLain> detailLainLain;

	public EInvoiceHeader toEntity() {
		
		EInvoiceHeader entity = new EInvoiceHeader();

		entity.setId(id);
		entity.setNomor(nomor);

		if (tgtrn != null) {
			entity.setTgtrn(TimeUtil.convertDateToYyyyMmDd(tgtrn)); 
		}
		
		entity.setNmcust(nmcust);
		entity.setNama(nama);
		entity.setAlamat(alamat);
		entity.setEmail(email);
		entity.setStatus(status);
		entity.setBruto(bruto);
		entity.setTotdisc(totdisc);
		entity.setDpp(dpp);
		entity.setPpn(ppn);
		entity.setNetto(netto);
		entity.setDepused(depused);
		entity.setFltodep(fltodep ? "Y" : "T");
		entity.setNildep(nildep);

		if (tgjtemp != null) {
			entity.setTgjtemp(TimeUtil.convertDateToYyyyMmDd(tgjtemp)); 
		}
		
		entity.setFlasli(flasli ? "Y" : "T");
		entity.setNotes(notes);
		entity.setPctdis(pctdis);
		entity.setNildis(nildis);
		entity.setKetdis(ketdis);
		entity.setKetinv(ketinv);
		entity.setNilbyr(nilbyr);
		
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
		
		if (customer != null && !StringUtils.isEmpty(customer.id)) {
			ECustomerGajiId entityRef = new ECustomerGajiId();
		
			entityRef.setId(customer.id);
			entityRef.setVersion(customer.version);
		
			entity.setCustomer(entityRef);
		
		} else {
			entity.setCustomer(null);
		}
        
		return entity;
	}
	
	public static List<EInvoiceHeader> toEntities(List<InvoiceHeader> pojos) {
		if (pojos == null) return null;

		List<EInvoiceHeader> entities = new ArrayList<EInvoiceHeader>();
		for (InvoiceHeader pojo : pojos) {
			entities.add(pojo.toEntity());
		}

		return entities;
	}
	
	public static InvoiceHeader fromEntity (EInvoiceHeader entity) {
		return fromEntity(entity, BaseConstants.DEFAULT_QUERY_DEPTH, BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH);
	}
	
	public static InvoiceHeader fromEntity (EInvoiceHeader entity, int depthLevel) {
		return fromEntity(entity, depthLevel, BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH);
	}
	
	public static InvoiceHeader fromEntity(EInvoiceHeader entity, int depthLevel, int detailDepthLevel) {
		
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
	    if (detailDepthLevel < 0) detailDepthLevel = BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH;
		
		InvoiceHeader pojo = new InvoiceHeader();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.nomor = entity.getNomor();

			if (entity.getTgtrn() != null && !entity.getTgtrn().trim().equals("")) {
				pojo.tgtrn = TimeUtil.getDate(entity.getTgtrn());				
			} else {
				pojo.tgtrn = null;
			}
			
			pojo.nmcust = entity.getNmcust();
			pojo.nama = entity.getNama();
			pojo.alamat = entity.getAlamat();
			pojo.email = entity.getEmail();
			pojo.status = entity.getStatus();
			pojo.bruto = entity.getBruto();
			pojo.totdisc = entity.getTotdisc();
			pojo.dpp = entity.getDpp();
			pojo.ppn = entity.getPpn();
			pojo.netto = entity.getNetto();
			pojo.depused = entity.getDepused();
			pojo.fltodep = entity.getFltodep() == null ? false : entity.getFltodep().trim().equals("Y") ? true : false;
			pojo.nildep = entity.getNildep();

			if (entity.getTgjtemp() != null && !entity.getTgjtemp().trim().equals("")) {
				pojo.tgjtemp = TimeUtil.getDate(entity.getTgjtemp());				
			} else {
				pojo.tgjtemp = null;
			}
			
			pojo.flasli = entity.getFlasli() == null ? false : entity.getFlasli().trim().equals("Y") ? true : false;
			pojo.notes = entity.getNotes();
			
			pojo.jthtempo = TimeUtil.subtractDates(new Date(), TimeUtil.getDate(entity.getTgjtemp())) + 1;
			
			if (pojo.jthtempo <= BaseConstants.END_ZONA_MERAH) {
				pojo.zona = BaseConstants.JTH_TEMPO_ZONA_MERAH;
			} else if (pojo.jthtempo >= BaseConstants.END_ZONA_MERAH + 1 && pojo.jthtempo <= BaseConstants.END_ZONA_ORANGE) {
				pojo.zona = BaseConstants.JTH_TEMPO_ZONA_ORANGE; 
			} else if (pojo.jthtempo >= BaseConstants.END_ZONA_ORANGE + 1 && pojo.jthtempo <= BaseConstants.END_ZONA_KUNING) {
				pojo.zona = BaseConstants.JTH_TEMPO_ZONA_KUNING; 
			} else if (pojo.jthtempo >= BaseConstants.END_ZONA_KUNING + 1) {
				pojo.zona = BaseConstants.JTH_TEMPO_ZONA_HIJAU; 
			}
			
			pojo.pctdis = entity.getPctdis();
			pojo.nildis = entity.getNildis();
			pojo.ketdis = entity.getKetdis();
			pojo.ketinv = entity.getKetinv();
			pojo.nilbyr = entity.getNilbyr();
			
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
	      
	        pojo.flsent = entity.getStatus().equals(BaseConstants.STATUS_INV_KIRIM) ? true : false;
	        //pojo.flbayar = entity.getStatus().equals(BaseConstants.STATUS_INV_BAYAR) ? true : false;
	        if (entity.getStatus().equals(BaseConstants.STATUS_INV_BAYAR)) {
	        	pojo.flbayar = true;
	        	pojo.flsent = true;
	        } else {
	        	pojo.flbayar = false;
	        }
	        		
			pojo.customer = CustomerGajiId.fromEntity(entity.getCustomer());
			
			if (detailDepthLevel > 0) {
				detailDepthLevel--;
				
				pojo.detailLainLain = InvoiceDetailLainLain.fromEntities(entity.getDetailLainLain(), depthLevel + 1, detailDepthLevel);
			}
		}

		return pojo;
	}
	
	public static List<InvoiceHeader> fromEntities(List<EInvoiceHeader> entities){
		return fromEntities(entities, BaseConstants.DEFAULT_QUERY_DEPTH, BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH);
	}
	
	public static List<InvoiceHeader> fromEntities(List<EInvoiceHeader> entities, int depthLevel, int detailDepthLevel){
		
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		if (detailDepthLevel < 0) detailDepthLevel = BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH;
		
		List<InvoiceHeader> pojos = new ArrayList<>();
		
		for (EInvoiceHeader entity : entities) {
			pojos.add(InvoiceHeader.fromEntity(entity, depthLevel, detailDepthLevel));
		}
		return pojos;
	}

	public static List<SearchFieldMapping> getFieldMappings() {
		List<SearchFieldMapping> mappings = new ArrayList<>();
		
		mappings.add(new SearchFieldMapping("nomor", "nomor"));
		mappings.add(new SearchFieldMapping("nama", "nama"));
		mappings.add(new SearchFieldMapping("alamat", "alamat"));
		mappings.add(new SearchFieldMapping("email", "email"));
		mappings.add(new SearchFieldMapping("customer", "customer"));
		mappings.add(new SearchFieldMapping("status", "status"));
		mappings.add(new SearchFieldMapping("fltodep", "fltodep"));
		mappings.add(new SearchFieldMapping("tahun", "tahun"));
		mappings.add(new SearchFieldMapping("bulan", "bulan"));
		mappings.add(new SearchFieldMapping("idMi010", "idMi010"));
		mappings.add(new SearchFieldMapping("cust", "customer.nama", String.class).sortOnly());
		mappings.add(new SearchFieldMapping("tgtrn", "tgtrn", String.class).sortOnly());
		mappings.add(new SearchFieldMapping("tgjtemp", "tgjtemp", String.class).sortOnly());
		mappings.add(new SearchFieldMapping("dpp", "dpp", Double.class).sortOnly());
		mappings.add(new SearchFieldMapping("ppn", "ppn", Double.class).sortOnly());
		mappings.add(new SearchFieldMapping("netto", "netto", Double.class).sortOnly());
//		mappings.add(new SearchFieldMapping("widgetId", "widget.id").searchOnly());
//		mappings.add(new SearchFieldMapping("userId", "user.id"));
//		mappings.add(new SearchFieldMapping("orderNumber", "orderNumber", Integer.class).sortOnly());
		
		return mappings;
	}
}	
