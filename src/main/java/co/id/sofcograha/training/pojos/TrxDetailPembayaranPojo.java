package co.id.sofcograha.training.pojos;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.Message;
import co.id.sofcograha.base.utils.TimeUtil;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.training.entities.MasterMembershipEntity;
import co.id.sofcograha.training.entities.TrxDetailPembayaran;
import co.id.sofcograha.training.entities.TrxHeaderEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@JsonInclude(Include.ALWAYS)
public class TrxDetailPembayaranPojo {

	public String id;
	public String jenisPembayaran;
	public Double nilaiRupiah;
	public Integer jumlahPoint;
	public Long version;

	public TrxHeaderPojo transaksiHeader;

	// untuk keperluan matching error message di front end (bila input di grid)
	public String keyIn;

	public boolean isSelect;

	// Untuk menampung message error di grid
	public ArrayList<Message> errorMsg;

    public TrxDetailPembayaran toEntity() {

		TrxDetailPembayaran entity = new TrxDetailPembayaran();
  		
  	    entity.setId(id);
		entity.setJenisPembayaran(jenisPembayaran);
  		entity.setNilaiRupiah(nilaiRupiah);
  		entity.setJumlahPoint(jumlahPoint);
  		entity.setVersion(version);

		if(transaksiHeader != null && !transaksiHeader.id.equals("")){
			TrxHeaderEntity entityRef = new TrxHeaderEntity();
			entityRef.setId(transaksiHeader.id);
			entity.setTransaksiHeader(entityRef);
		}else{
			entity.setTransaksiHeader(null);
		}

  		return entity;
  	}
    
	public static TrxDetailPembayaranPojo fromEntity (TrxDetailPembayaran entity) {
		return fromEntity(entity,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static TrxDetailPembayaranPojo fromEntity(TrxDetailPembayaran entity, int depthLevel) {
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
		
		TrxDetailPembayaranPojo pojo = new TrxDetailPembayaranPojo();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.id = entity.getId();
			pojo.jenisPembayaran = entity.getJenisPembayaran();
			pojo.nilaiRupiah = entity.getNilaiRupiah();
			pojo.jumlahPoint = entity.getJumlahPoint();
			pojo.version = entity.getVersion();
			pojo.transaksiHeader = TrxHeaderPojo.fromEntity(entity.getTransaksiHeader());

		}

		return pojo;
	}
	
	public static List<TrxDetailPembayaranPojo> fromEntities(List<TrxDetailPembayaran> entities){
		return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static List<TrxDetailPembayaranPojo> fromEntities(List<TrxDetailPembayaran> entities, int depthLevel){
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		
		List<TrxDetailPembayaranPojo> pojos = new ArrayList<>();
		
		for (TrxDetailPembayaran entity : entities) {
			pojos.add(TrxDetailPembayaranPojo.fromEntity(entity, depthLevel));
		}
		return pojos;
	}
	
	public static List<SearchFieldMapping> getFieldMappings() {
		List<SearchFieldMapping> mappings = new ArrayList<>();
	
		mappings.add(new SearchFieldMapping("id", "id"));
	
		return mappings;
	} 
	
}
