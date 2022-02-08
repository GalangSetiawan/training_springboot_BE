package co.id.sofcograha.training.Pojo;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.training.Entity.MasterJenisTransaksiEntity;
import co.id.sofcograha.training.Entity.MasterMembershipEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.ALWAYS)
public class MasterJenisTransaksiPojo {

	public String kodeJenisTransaksi;
	public String namaTransaksi;
	public String id;
    
    public MasterJenisTransaksiEntity toEntity() {

		MasterJenisTransaksiEntity entity = new MasterJenisTransaksiEntity();
		entity.setKodeJenisTransaksi(kodeJenisTransaksi);
		entity.setNamaTransaksi(namaTransaksi);
		entity.setId(id);

  		return entity;
  	}
    
	public static MasterJenisTransaksiPojo fromEntity (MasterJenisTransaksiEntity entity) {
		return fromEntity(entity,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static MasterJenisTransaksiPojo fromEntity(MasterJenisTransaksiEntity entity, int depthLevel) {
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
		
		MasterJenisTransaksiPojo pojo = new MasterJenisTransaksiPojo();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.kodeJenisTransaksi = entity.getKodeJenisTransaksi();
			pojo.namaTransaksi = entity.getNamaTransaksi();
			pojo.id = entity.getId();
		}

		return pojo;
	}
	
	public static List<MasterJenisTransaksiPojo> fromEntities(List<MasterJenisTransaksiEntity> entities){
		return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static List<MasterJenisTransaksiPojo> fromEntities(List<MasterJenisTransaksiEntity> entities, int depthLevel){
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		
		List<MasterJenisTransaksiPojo> pojos = new ArrayList<>();
		
		for (MasterJenisTransaksiEntity entity : entities) {
			pojos.add(MasterJenisTransaksiPojo.fromEntity(entity, depthLevel));
		}
		return pojos;
	}
	
	public static List<SearchFieldMapping> getFieldMappings() {
		List<SearchFieldMapping> mappings = new ArrayList<>();
	
		mappings.add(new SearchFieldMapping("kodeJenisTransaksi", "kodeJenisTransaksi"));
		mappings.add(new SearchFieldMapping("namaTransaksi", "namaTransaksi"));

	
		return mappings;
	} 
	
}
