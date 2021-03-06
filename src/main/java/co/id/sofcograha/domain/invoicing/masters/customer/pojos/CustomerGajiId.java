package co.id.sofcograha.domain.invoicing.masters.customer.pojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.TimeUtil;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.domain.invoicing.masters.customer.entities.ECustomerGajiId;

@JsonInclude(Include.ALWAYS)
public class CustomerGajiId {

    public String id;
	public String nama;
	public String picnama;
	public String picrole;
	public String picnumber;
	public String picemail;
	public String picalamat;
	public String billnama;
	public String billrole;
	public String billnumber;
	public String billemail;
	public String billalamat;
	public String billcust2;
	public String billnama2;
	public String billrole2;
	public String billnumber2;
	public String billemail2;
	public String billalamat2;
	public String vabca;
	public Boolean flakt;
	public Boolean flmainva;
    public Long version;
    public String usrcrt;
    public Date tglcrt;
    public String jamcrt;
    public String usrupd;
    public Date tglupd;
    public String jamupd;
    public boolean isDeleted;
    
    public ECustomerGajiId toEntity() {
		
    	ECustomerGajiId entity = new ECustomerGajiId();
  		
  	    entity.setId(id);
  		entity.setNama(nama);
  		entity.setPicnama(picnama);
  		entity.setPicrole(picrole);
  		entity.setPicnumber(picnumber);
  		entity.setPicemail(picemail);
  		entity.setPicalamat(picalamat);
  		entity.setBillnama(billnama);
  		entity.setBillrole(billrole);
  		entity.setBillnumber(billnumber);
  		entity.setBillemail(billemail);
  		entity.setBillalamat(billalamat);
  		entity.setBillcust2(billcust2);
  		entity.setBillnama2(billnama2);
  		entity.setBillrole2(billrole2);
  		entity.setBillnumber2(billnumber2);
  		entity.setBillemail2(billemail2);
  		entity.setBillalamat2(billalamat2);
  		entity.setVabca(vabca);
  		
  		if (flakt == true) {
  		  entity.setFlakt("Y");
  		} else {
  		  entity.setFlakt("T");
  		}
  		
  		if (flmainva == true) {
  			entity.setFlmainva("Y");
  		} else {
  			entity.setFlmainva("T");
  		}
    		
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
          
  		return entity;
  	}
    
	public static CustomerGajiId fromEntity (ECustomerGajiId entity) {
		return fromEntity(entity,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static CustomerGajiId fromEntity(ECustomerGajiId entity, int depthLevel) {
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
		
		CustomerGajiId pojo = new CustomerGajiId();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.nama = entity.getNama();
			pojo.picnama = entity.getPicnama();
			pojo.picrole = entity.getPicrole();
			pojo.picnumber = entity.getPicnumber();
			pojo.picemail = entity.getPicemail();
			pojo.picalamat = entity.getPicalamat();
			pojo.billnama = entity.getBillnama();
			pojo.billrole = entity.getBillrole();
			pojo.billnumber = entity.getBillnumber();
			pojo.billemail = entity.getBillemail();
			pojo.billalamat = entity.getBillalamat();
			pojo.billcust2 = entity.getBillcust2();
			pojo.billnama2 = entity.getBillnama2();
			pojo.billrole2 = entity.getBillrole2();
			pojo.billnumber2 = entity.getBillnumber2();
			pojo.billemail2 = entity.getBillemail2();
			pojo.billalamat2 = entity.getBillalamat2();
			pojo.vabca = entity.getVabca();
		    
		    if (entity.getFlakt().equals("Y")) {
		      pojo.flakt = true;
		    } else {
		      pojo.flakt = false;
		    }
		    
		    if (entity.getFlmainva().equals("Y")) {
		    	pojo.flmainva = true;
		    } else {
		    	pojo.flmainva = false;
		    }
			    
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
		}

		return pojo;
	}
	
	public static List<CustomerGajiId> fromEntities(List<ECustomerGajiId> entities){
		return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static List<CustomerGajiId> fromEntities(List<ECustomerGajiId> entities, int depthLevel){
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		
		List<CustomerGajiId> pojos = new ArrayList<>();
		
		for (ECustomerGajiId entity : entities) {
			pojos.add(CustomerGajiId.fromEntity(entity, depthLevel));
		}
		return pojos;
	}
	
	public static List<SearchFieldMapping> getFieldMappings() {
		List<SearchFieldMapping> mappings = new ArrayList<>();
	
		mappings.add(new SearchFieldMapping("nama", "nama"));
		mappings.add(new SearchFieldMapping("picnama", "picnama"));
		mappings.add(new SearchFieldMapping("picalamat", "picalamat"));
		mappings.add(new SearchFieldMapping("picemail", "picemail"));
		mappings.add(new SearchFieldMapping("billnama", "billnama"));
		mappings.add(new SearchFieldMapping("billalamat", "billalamat"));
		mappings.add(new SearchFieldMapping("billemail", "billemail"));
		mappings.add(new SearchFieldMapping("billcust2", "billcust2"));
		mappings.add(new SearchFieldMapping("billnama2", "billnama2"));
		mappings.add(new SearchFieldMapping("flakt", "flakt").searchOnly());
//		mappings.add(new SearchFieldMapping("urutan", "urutan", Integer.class).sortOnly());
	
		return mappings;
	} 
	
}
