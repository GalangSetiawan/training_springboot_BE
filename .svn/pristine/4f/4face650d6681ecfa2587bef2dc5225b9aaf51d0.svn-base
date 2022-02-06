package co.id.sofcograha.domain.invoicing.commonMasters.comboConstants.pojos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.domain.invoicing.commonMasters.comboConstants.entities.EComboConstants;

@JsonInclude(Include.ALWAYS)
public class ComboConstants {

    public String id;
    public String rectyp;
    public String reccode;
    public String rectxt;
    public String flkakt;
    public Integer version;
	
	public EComboConstants toEntity() {
		
	  EComboConstants entity = new EComboConstants();
		
	    entity.setId(id);
	    entity.setRectyp(rectyp);
	    entity.setReccode(reccode);
	    entity.setRectxt(rectxt);
	    entity.setFlkakt(flkakt);
        entity.setVersion(version);
        
		return entity;
	}
	
	public static ComboConstants fromEntity (EComboConstants entity) {
		return fromEntity(entity, BaseConstants.DEFAULT_QUERY_DEPTH, BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH);
	}
	
	public static ComboConstants fromEntity (EComboConstants entity, int depthLevel) {
		return fromEntity(entity, depthLevel, BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH);
	}
	
	public static ComboConstants fromEntity(EComboConstants entity, int depthLevel, int detailDepthLevel) {
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
	    if (detailDepthLevel < 0) detailDepthLevel = BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH;
		
		ComboConstants pojo = new ComboConstants();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;
			
			pojo.rectyp = entity.getRectyp();
			pojo.reccode = entity.getReccode();
			pojo.rectxt = entity.getRectxt();
			pojo.flkakt = entity.getFlkakt();
	        pojo.version = entity.getVersion();
			
			if (detailDepthLevel > 0) {
				detailDepthLevel--;
				
			}
		}
		
		return pojo;
	}
	
	public static List<ComboConstants> fromEntities(List<EComboConstants> entities){
		return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH, BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH);
	}
	
	public static List<ComboConstants> fromEntities(List<EComboConstants> entities, int depthLevel, int detailDepthLevel){
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		if (detailDepthLevel < 0) detailDepthLevel = BaseConstants.DEFAULT_DETAIL_QUERY_DEPTH;
		
		List<ComboConstants> pojos = new ArrayList<>();
		
		for (EComboConstants entity : entities) {
			pojos.add(ComboConstants.fromEntity(entity, depthLevel, detailDepthLevel));
		}
		return pojos;
	}
	
	public static List<SearchFieldMapping> getFieldMappings() {
		List<SearchFieldMapping> mappings = new ArrayList<>();
		
//		mappings.add(new SearchFieldMapping("widgetId", "widget.id").searchOnly());
		mappings.add(new SearchFieldMapping("reccode", "reccode"));
		mappings.add(new SearchFieldMapping("rectyp", "rectyp"));
		mappings.add(new SearchFieldMapping("rectxt", "rectxt"));
//		mappings.add(new SearchFieldMapping("orderNumber", "orderNumber", Integer.class).sortOnly());
		
		return mappings;
	}
}
