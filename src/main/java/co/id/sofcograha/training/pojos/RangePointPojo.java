package co.id.sofcograha.training.pojos;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.training.entities.RangePointEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.ALWAYS)
public class RangePointPojo {

	public Double minRange;
	public Double maxRange;
	public Integer point;
	public String id;
    
    public RangePointEntity toEntity() {

		RangePointEntity entity = new RangePointEntity();
		entity.setMinRange(minRange);
		entity.setMaxRange(maxRange);
		entity.setPoint(point);
		entity.setId(id);

  		return entity;
  	}
    
	public static RangePointPojo fromEntity (RangePointEntity entity) {
		return fromEntity(entity,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static RangePointPojo fromEntity(RangePointEntity entity, int depthLevel) {
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
		
		RangePointPojo pojo = new RangePointPojo();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.minRange = entity.getMinRange();
			pojo.maxRange = entity.getMaxRange();
			pojo.point = entity.getPoint();
			pojo.id = entity.getId();

		}

		return pojo;
	}
	
	public static List<RangePointPojo> fromEntities(List<RangePointEntity> entities){
		return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static List<RangePointPojo> fromEntities(List<RangePointEntity> entities, int depthLevel){
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		
		List<RangePointPojo> pojos = new ArrayList<>();
		
		for (RangePointEntity entity : entities) {
			pojos.add(RangePointPojo.fromEntity(entity, depthLevel));
		}
		return pojos;
	}
	
	public static List<SearchFieldMapping> getFieldMappings() {
		List<SearchFieldMapping> mappings = new ArrayList<>();
	
		mappings.add(new SearchFieldMapping("minRange", "minRange"));
		mappings.add(new SearchFieldMapping("maxRange", "maxRange"));


	
		return mappings;
	} 
	
}
