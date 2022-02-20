package co.id.sofcograha.training.pojos;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.training.entities.MasterBukuEntity;
import co.id.sofcograha.training.entities.TrxDetailPembelianBukuEntity;
import co.id.sofcograha.training.entities.TrxHeaderEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.List;


@JsonInclude(Include.ALWAYS)
public class TrxDetailPembelianBukuPojo {

	public String id;
	public Long version;
	public Integer qty;
	public Integer persenDiscBuku;
	public Double totalHarga;
	public Integer persenDiscGenre;
	public Double nilaiDiscGenre;
	public Double hargaSetelahDiscGenre;
	public Double nilaiDiscHeader;
	public Double hargaSetelahDiscHeader;
	public Double nilaiDiscProposional;
	public MasterBukuPojo dataBuku;
	public TrxHeaderPojo dataTrxHeader;
    
    public TrxDetailPembelianBukuEntity toEntity() {

		TrxDetailPembelianBukuEntity entity = new TrxDetailPembelianBukuEntity();
  		
  	    entity.setId(id);
		entity.setVersion(version);
		entity.setQty(qty);
		entity.setPersenDiscBuku(persenDiscBuku);
		entity.setTotalHarga(totalHarga);
		entity.setPersenDiscGenre(persenDiscGenre);
		entity.setNilaiDiscGenre(nilaiDiscGenre);
		entity.setHargaSetelahDiscGenre(hargaSetelahDiscGenre);
		entity.setNilaiDiscHeader(nilaiDiscHeader);
		entity.setHargaSetelahDiscHeader(hargaSetelahDiscHeader);
		entity.setNilaiDiscProposional(nilaiDiscProposional);

		if(dataBuku != null && !dataBuku.id.equals("")){
			MasterBukuEntity entityRef = new MasterBukuEntity();
			entityRef.setId(dataBuku.id);
			entity.setDataBuku(entityRef);
		}else{
			entity.setDataBuku(null);
		}

		if(dataTrxHeader != null && !dataTrxHeader.id.equals("")){
			TrxHeaderEntity entityRef = new TrxHeaderEntity();
			entityRef.setId(dataTrxHeader.id);
			entity.setDataHeader(entityRef);
		}else{
			entity.setDataHeader(null);
		}
          
  		return entity;
  	}
    
	public static TrxDetailPembelianBukuPojo fromEntity (TrxDetailPembelianBukuEntity entity) {
		return fromEntity(entity,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static TrxDetailPembelianBukuPojo fromEntity(TrxDetailPembelianBukuEntity entity, int depthLevel) {
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
		
		TrxDetailPembelianBukuPojo pojo = new TrxDetailPembelianBukuPojo();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.qty = entity.getQty();
			pojo.totalHarga = entity.getTotalHarga();
			pojo.persenDiscGenre = entity.getPersenDiscGenre();
			pojo.persenDiscBuku = entity.getPersenDiscBuku();
			pojo.nilaiDiscGenre = entity.getNilaiDiscGenre();
			pojo.hargaSetelahDiscGenre = entity.getHargaSetelahDiscGenre();
			pojo.nilaiDiscHeader = entity.getNilaiDiscHeader();
			pojo.hargaSetelahDiscHeader = entity.getHargaSetelahDiscHeader();
			pojo.nilaiDiscProposional = entity.getNilaiDiscProposional();
			pojo.version = entity.getVersion();
			pojo.dataBuku = MasterBukuPojo.fromEntity(entity.getDataBuku(),1);
			pojo.dataTrxHeader = TrxHeaderPojo.fromEntity(entity.getDataHeader(),1);

		}

		return pojo;
	}
	
	public static List<TrxDetailPembelianBukuPojo> fromEntities(List<TrxDetailPembelianBukuEntity> entities){
		return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static List<TrxDetailPembelianBukuPojo> fromEntities(List<TrxDetailPembelianBukuEntity> entities, int depthLevel){
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		
		List<TrxDetailPembelianBukuPojo> pojos = new ArrayList<>();
		
		for (TrxDetailPembelianBukuEntity entity : entities) {
			pojos.add(TrxDetailPembelianBukuPojo.fromEntity(entity, depthLevel));
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
