package co.id.sofcograha.training.pojos;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.training.entities.MasterBukuEntity;
import co.id.sofcograha.training.entities.MasterGenreEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@JsonInclude(Include.ALWAYS)
public class MasterBukuPojo {

	public String id;
	public String kodeBuku;
	public String namaBuku;
	public Double hargaBuku;
	public Integer stockBuku;
	public Boolean active;
	public Long version;
	public MasterGenrePojo dataGenre;
    
    public MasterBukuEntity toEntity() {

		MasterBukuEntity entity = new MasterBukuEntity();
  		
  	    entity.setId(id);
  		entity.setKodeBuku(kodeBuku);
  		entity.setNamaBuku(namaBuku);
  		entity.setHargaBuku(hargaBuku);
  		entity.setStockBuku(stockBuku);
  		entity.setFlagActive(active);
  		entity.setVersion(version);

		if(dataGenre != null && !dataGenre.id.equals("")){
			MasterGenreEntity entityRef = new MasterGenreEntity();
			entityRef.setId(dataGenre.id);
			entity.setGenreBuku(entityRef);

		}else{
			entity.setGenreBuku(null);
		}
          
  		return entity;
  	}
    
	public static MasterBukuPojo fromEntity (MasterBukuEntity entity) {
		return fromEntity(entity,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static MasterBukuPojo fromEntity(MasterBukuEntity entity, int depthLevel) {
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
		
		MasterBukuPojo pojo = new MasterBukuPojo();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.namaBuku = entity.getNamaBuku();
			pojo.kodeBuku = entity.getKodeBuku();
			pojo.hargaBuku = entity.getHargaBuku();
			pojo.stockBuku = entity.getStockBuku();
			pojo.active = entity.getFlagActive();
			pojo.version = entity.getVersion();

			pojo.dataGenre = MasterGenrePojo.fromEntity(entity.getGenreBuku());


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

	public static List<MasterBukuEntity> toEntities(List<MasterBukuPojo> pojos){
		List<MasterBukuEntity> resultPojos = new ArrayList<>();
		for(MasterBukuPojo eachPojo : pojos){
			resultPojos.add(eachPojo.toEntity());
		}
		return resultPojos;
	}
	
	public static List<MasterBukuPojo> fromEntities(List<MasterBukuEntity> entities){
		return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static List<MasterBukuPojo> fromEntities(List<MasterBukuEntity> entities, int depthLevel){
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		
		List<MasterBukuPojo> pojos = new ArrayList<>();
		
		for (MasterBukuEntity entity : entities) {
			pojos.add(MasterBukuPojo.fromEntity(entity, depthLevel));
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
