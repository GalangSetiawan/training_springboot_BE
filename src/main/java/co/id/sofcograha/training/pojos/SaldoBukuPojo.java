package co.id.sofcograha.training.pojos;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.training.Entity.MasterBukuEntity;
import co.id.sofcograha.training.Entity.MasterGenreEntity;
import co.id.sofcograha.training.entities.SaldoBukuEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.ALWAYS)
public class SaldoBukuPojo {

	public String id;
	public Double saldoBuku;
	public Long version;
	public MasterBukuPojo id_buku;
    
    public SaldoBukuEntity toEntity() {

		SaldoBukuEntity entity = new SaldoBukuEntity();
  		
  	    entity.setId(id);
  		entity.setSaldoBuku(saldoBuku);

  		entity.setVersion(version);

		if(id_buku != null && !id_buku.id.equals("")){
			MasterBukuEntity entityRef = new MasterBukuEntity();
			entityRef.setId(id_buku.id);
			entity.setDataBuku(entityRef);
		}else{
			entity.setDataBuku(null);
		}
    		
//        entity.setUsrcrt(usrcrt);
//
//		if (tglcrt != null) {
//			entity.setTglcrt(TimeUtil.convertDateToYyyyMmDd(tglcrt));
//		}
//
//        entity.setJamcrt(jamcrt);
//        entity.setUsrupd(usrupd);
//
//		if (tglupd != null) {
//			entity.setTglupd(TimeUtil.convertDateToYyyyMmDd(tglupd));
//		}
//
//        entity.setJamupd(jamupd);
          
  		return entity;
  	}
    
	public static SaldoBukuPojo fromEntity (SaldoBukuEntity entity) {
		return fromEntity(entity,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static SaldoBukuPojo fromEntity(SaldoBukuEntity entity, int depthLevel) {
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
		
		SaldoBukuPojo pojo = new SaldoBukuPojo();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.id = entity.getId();
			pojo.saldoBuku = entity.getSaldoBuku();
			pojo.version = entity.getVersion();

			pojo.id_buku = MasterBukuPojo.fromEntity(entity.getDataBuku());

//	        pojo.usrcrt = entity.getUsrcrt();
//
//			if (entity.getTglcrt() != null && !entity.getTglcrt().trim().equals("")) {
//				pojo.tglcrt = TimeUtil.getDate(entity.getTglcrt());
//			} else {
//				pojo.tglcrt = null;
//			}
//
//	        pojo.jamcrt = entity.getJamcrt();
//	        pojo.usrupd = entity.getUsrupd();
//
//			if (entity.getTglupd() != null && !entity.getTglupd().trim().equals("")) {
//				pojo.tglupd = TimeUtil.getDate(entity.getTglupd());
//			} else {
//				pojo.tglupd = null;
//			}
//
//	        pojo.jamupd = entity.getJamupd();
		}

		return pojo;
	}
	
	public static List<SaldoBukuPojo> fromEntities(List<SaldoBukuEntity> entities){
		return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static List<SaldoBukuPojo> fromEntities(List<SaldoBukuEntity> entities, int depthLevel){
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		
		List<SaldoBukuPojo> pojos = new ArrayList<>();
		
		for (SaldoBukuEntity entity : entities) {
			pojos.add(SaldoBukuPojo.fromEntity(entity, depthLevel));
		}
		return pojos;
	}
	
	public static List<SearchFieldMapping> getFieldMappings() {
		List<SearchFieldMapping> mappings = new ArrayList<>();
	
		mappings.add(new SearchFieldMapping("kodeBuku", "kodeBuku"));
		mappings.add(new SearchFieldMapping("namaBuku", "namaBuku"));

	
		return mappings;
	} 
	
}
