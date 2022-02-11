package co.id.sofcograha.training.pojos;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.training.entities.MasterGenreEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.ALWAYS)
public class MasterGenrePojo {

	public String kodeGenre;
	public String namaGenre;
	public Double diskonGenre;
	public Long version;
	public String id;
    
    public MasterGenreEntity toEntity() {

		MasterGenreEntity entity = new MasterGenreEntity();
		entity.setKodeGenre(kodeGenre);
		entity.setNamaGenre(namaGenre);
		entity.setDiskonGenre(diskonGenre);
		entity.setVersion(version);
		entity.setId(id);

  		return entity;
  	}
    
	public static MasterGenrePojo fromEntity (MasterGenreEntity entity) {
		return fromEntity(entity,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static MasterGenrePojo fromEntity(MasterGenreEntity entity, int depthLevel) {
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
		
		MasterGenrePojo pojo = new MasterGenrePojo();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.kodeGenre = entity.getKodeGenre();
			pojo.namaGenre = entity.getNamaGenre();
			pojo.diskonGenre = entity.getDiskonGenre();
			pojo.version = entity.getVersion();
			pojo.id = entity.getId();

		}

		return pojo;
	}
	
	public static List<MasterGenrePojo> fromEntities(List<MasterGenreEntity> entities){
		return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static List<MasterGenrePojo> fromEntities(List<MasterGenreEntity> entities, int depthLevel){
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		
		List<MasterGenrePojo> pojos = new ArrayList<>();
		
		for (MasterGenreEntity entity : entities) {
			pojos.add(MasterGenrePojo.fromEntity(entity, depthLevel));
		}
		return pojos;
	}
	
	public static List<SearchFieldMapping> getFieldMappings() {
		List<SearchFieldMapping> mappings = new ArrayList<>();
	
		mappings.add(new SearchFieldMapping("kodeGenre", "kodeGenre"));
		mappings.add(new SearchFieldMapping("namaGenre", "namaGenre"));


	
		return mappings;
	} 
	
}
