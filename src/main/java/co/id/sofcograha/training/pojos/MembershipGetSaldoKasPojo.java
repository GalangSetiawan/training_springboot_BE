package co.id.sofcograha.training.pojos;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.training.entities.MasterMembershipEntity;
import co.id.sofcograha.training.entities.MembershipGetSaldoKasEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.ALWAYS)
public class MembershipGetSaldoKasPojo {

	public String kodeMembership;
	public String namaMembership;
	public Double nilaiTitipan;
	public Integer nilaiPoint;
	public Long version;
	public String id;

    public MembershipGetSaldoKasEntity toEntity() {

		MembershipGetSaldoKasEntity entity = new MembershipGetSaldoKasEntity();
		entity.setKodeMembership(kodeMembership);
		entity.setNamaMembership(namaMembership);
		entity.setNilaiTitipan(nilaiTitipan);
		entity.setNilaiPoint(nilaiPoint);
		entity.setVersion(version);
		entity.setId(id);

  		return entity;
  	}
    
	public static MembershipGetSaldoKasPojo fromEntity (MembershipGetSaldoKasEntity entity) {
		return fromEntity(entity,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static MembershipGetSaldoKasPojo fromEntity(MembershipGetSaldoKasEntity entity, int depthLevel) {
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
		
		MembershipGetSaldoKasPojo pojo = new MembershipGetSaldoKasPojo();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.kodeMembership = entity.getKodeMembership();
			pojo.namaMembership = entity.getNamaMembership();
			pojo.nilaiTitipan = entity.getNilaiTitipan();
			pojo.nilaiPoint = entity.getNilaiPoint();
			pojo.version = entity.getVersion();
			pojo.id = entity.getId();

		}

		return pojo;
	}
	
	public static List<MembershipGetSaldoKasPojo> fromEntities(List<MembershipGetSaldoKasEntity> entities){
		return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static List<MembershipGetSaldoKasPojo> fromEntities(List<MembershipGetSaldoKasEntity> entities, int depthLevel){
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		
		List<MembershipGetSaldoKasPojo> pojos = new ArrayList<>();
		
		for (MembershipGetSaldoKasEntity entity : entities) {
			pojos.add(MembershipGetSaldoKasPojo.fromEntity(entity, depthLevel));
		}
		return pojos;
	}
	
	public static List<SearchFieldMapping> getFieldMappings() {
		List<SearchFieldMapping> mappings = new ArrayList<>();

		mappings.add(new SearchFieldMapping("namaMembership", "A.nama_member"));

		return mappings;
	} 
	
}
