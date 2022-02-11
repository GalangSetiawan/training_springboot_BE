package co.id.sofcograha.training.pojos;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.training.entities.MasterMembershipEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.ALWAYS)
public class MasterMembershipPojo {

	public String kodeMembership;
	public String namaMembership;
	public Long version;
	public String id;
    
    public MasterMembershipEntity toEntity() {

		MasterMembershipEntity entity = new MasterMembershipEntity();
		entity.setKodeMembership(kodeMembership);
		entity.setNamaMembership(namaMembership);
		entity.setVersion(version);
		entity.setId(id);

  		return entity;
  	}
    
	public static MasterMembershipPojo fromEntity (MasterMembershipEntity entity) {
		return fromEntity(entity,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static MasterMembershipPojo fromEntity(MasterMembershipEntity entity, int depthLevel) {
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
		
		MasterMembershipPojo pojo = new MasterMembershipPojo();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.kodeMembership = entity.getKodeMembership();
			pojo.namaMembership = entity.getNamaMembership();
			pojo.version = entity.getVersion();
			pojo.id = entity.getId();

		}

		return pojo;
	}
	
	public static List<MasterMembershipPojo> fromEntities(List<MasterMembershipEntity> entities){
		return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static List<MasterMembershipPojo> fromEntities(List<MasterMembershipEntity> entities, int depthLevel){
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		
		List<MasterMembershipPojo> pojos = new ArrayList<>();
		
		for (MasterMembershipEntity entity : entities) {
			pojos.add(MasterMembershipPojo.fromEntity(entity, depthLevel));
		}
		return pojos;
	}
	
	public static List<SearchFieldMapping> getFieldMappings() {
		List<SearchFieldMapping> mappings = new ArrayList<>();
	
		mappings.add(new SearchFieldMapping("kodeMembership", "kodeMembership"));
		mappings.add(new SearchFieldMapping("namaMembership", "namaMembership"));


	
		return mappings;
	} 
	
}
