package co.id.sofcograha.training.pojos;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.training.entities.MasterMembershipEntity;
import co.id.sofcograha.training.entities.SaldoKasTitipanEntity;
import co.id.sofcograha.training.entities.TrxDetailPembayaran;
import co.id.sofcograha.training.entities.TrxHeaderEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.List;


@JsonInclude(Include.ALWAYS)
public class SaldoKasTitipanPojo {

	public String id;
	public Double nilaiTitipan;
	public Integer nilaiPoint;
	public Long version;

	public MasterMembershipPojo masterMembershipPojo;

	public boolean isSelect;

    public SaldoKasTitipanEntity toEntity() {

		SaldoKasTitipanEntity entity = new SaldoKasTitipanEntity();
  		
  	    entity.setId(id);
		entity.setNilaiTitipan(nilaiTitipan);
  		entity.setNilaiPoint(nilaiPoint);
  		entity.setVersion(version);

		if(masterMembershipPojo != null && !masterMembershipPojo.id.equals("")){
			MasterMembershipEntity entityRef = new MasterMembershipEntity();
			entityRef.setId(masterMembershipPojo.id);
			entity.setDataMembership(entityRef);
		}else{
			entity.setDataMembership(null);
		}

  		return entity;
  	}
    
	public static SaldoKasTitipanPojo fromEntity (SaldoKasTitipanEntity entity) {
		return fromEntity(entity,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static SaldoKasTitipanPojo fromEntity(SaldoKasTitipanEntity entity, int depthLevel) {
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
		
		SaldoKasTitipanPojo pojo = new SaldoKasTitipanPojo();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.id = entity.getId();
			pojo.nilaiTitipan = entity.getNilaiTitipan();
			pojo.nilaiPoint = entity.getNilaiPoint();
			pojo.version = entity.getVersion();
			pojo.masterMembershipPojo = MasterMembershipPojo.fromEntity(entity.getDataMembership());

		}

		return pojo;
	}
	
	public static List<SaldoKasTitipanPojo> fromEntities(List<SaldoKasTitipanEntity> entities){
		return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static List<SaldoKasTitipanPojo> fromEntities(List<SaldoKasTitipanEntity> entities, int depthLevel){
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		
		List<SaldoKasTitipanPojo> pojos = new ArrayList<>();
		
		for (SaldoKasTitipanEntity entity : entities) {
			pojos.add(SaldoKasTitipanPojo.fromEntity(entity, depthLevel));
		}
		return pojos;
	}
	
	public static List<SearchFieldMapping> getFieldMappings() {
		List<SearchFieldMapping> mappings = new ArrayList<>();
	
		mappings.add(new SearchFieldMapping("id", "id"));
//		mappings.add(new SearchFieldMapping("namaBuku", "namaBuku"));

	
		return mappings;
	} 
	
}
