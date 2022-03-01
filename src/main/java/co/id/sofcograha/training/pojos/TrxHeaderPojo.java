package co.id.sofcograha.training.pojos;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.Message;
import co.id.sofcograha.base.utils.TimeUtil;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.training.entities.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@JsonInclude(Include.ALWAYS)
public class TrxHeaderPojo {

	public String id;
	public Date tanggalBon;
	public String nomorBon;
	public String namaPembeli;
	public Double totalPembelianBuku; // bruto
	public Integer discountHeader;
	public Double nilaiDiskonHeader;
	public Double DPP;
	public Double PPN;
	public Double netto;
	public Double totalPembayaran;
	public Double nilaiKembalian;
	public Boolean flagDapatPromo5Pertama;
	public String keterangan;

	public Long version;
	public MasterMembershipPojo dataMembership;
	public List<TrxDetailPembelianBukuPojo> listBuku;
	public MasterJenisTransaksiPojo dataJenisTransaksi;
	public List<TrxDetailPembayaranPojo> listPembayaran;

    public TrxHeaderEntity toEntity() {

		TrxHeaderEntity entity = new TrxHeaderEntity();
  		
  	    entity.setId(id);
		entity.setTanggalBon(TimeUtil.getSystemDate());
		entity.setNomorBon(nomorBon);
  		entity.setNamaPembeli(namaPembeli);
  		entity.setDiscountHeader(discountHeader);
		entity.setNilaiKembalian(nilaiKembalian);
		entity.setTotalPembayaran(totalPembayaran);
		entity.setTotalPembelianBuku(totalPembelianBuku);
		entity.setNilaiKembalian(nilaiDiskonHeader);
		entity.setFlagDapatPromo5Pertama(flagDapatPromo5Pertama);
		entity.setNilaiKembalian(nilaiKembalian);
		entity.setPPN(PPN);
		entity.setKeterangan(keterangan);
		entity.setDPP(DPP);
		entity.setNetto(netto);
  		entity.setVersion(version);

		if(dataMembership != null && !dataMembership.id.equals("")){
			MasterMembershipEntity entityRef = new MasterMembershipEntity();
			entityRef.setId(dataMembership.id);
			entity.setDataMembership(entityRef);
		}else{
			entity.setDataMembership(null);
		}

  		return entity;
  	}
    
	public static TrxHeaderPojo fromEntity (TrxHeaderEntity entity) {
		return fromEntity(entity,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static TrxHeaderPojo fromEntity(TrxHeaderEntity entity, int depthLevel) {
		if (entity == null) return null;
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;	
		
		TrxHeaderPojo pojo = new TrxHeaderPojo();
		
		pojo.id = entity.getId();
		if (depthLevel > 0) {
			depthLevel--;

			pojo.id = entity.getId();
			pojo.tanggalBon = entity.getTanggalBon();
			pojo.nomorBon = entity.getNomorBon();
			pojo.namaPembeli = entity.getNamaPembeli();
			pojo.discountHeader = entity.getDiscountHeader();
			pojo.nilaiKembalian = entity.getNilaiKembalian();
			pojo.totalPembayaran = entity.getTotalPembayaran();
			pojo.totalPembelianBuku = entity.getTotalPembelianBuku();
			pojo.nilaiDiskonHeader = entity.getNilaiDiskonHeader();
			pojo.flagDapatPromo5Pertama = entity.getFlagDapatPromo5Pertama();
			pojo.PPN = entity.getPPN();
			pojo.keterangan = entity.getKeterangan();
			pojo.DPP = entity.getDPP();
			pojo.netto = entity.getNetto();
			pojo.version = entity.getVersion();
			pojo.dataMembership = MasterMembershipPojo.fromEntity(entity.getDataMembership());

//			pojo.listBuku = TrxDetailPembelianBukuPojo.fromEntities(entity.getTrxDetailPembelianBuku(),1);

		}

		return pojo;
	}
	
	public static List<TrxHeaderPojo> fromEntities(List<TrxHeaderEntity> entities){
		return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH);
	}
	
	public static List<TrxHeaderPojo> fromEntities(List<TrxHeaderEntity> entities, int depthLevel){
		if (entities == null) return new ArrayList<>();
		if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;
		
		List<TrxHeaderPojo> pojos = new ArrayList<>();
		
		for (TrxHeaderEntity entity : entities) {
			pojos.add(TrxHeaderPojo.fromEntity(entity, depthLevel));
		}
		return pojos;
	}
	
	public static List<SearchFieldMapping> getFieldMappings() {
		List<SearchFieldMapping> mappings = new ArrayList<>();
	
		mappings.add(new SearchFieldMapping("nomorBon", "nomorBon"));
		mappings.add(new SearchFieldMapping("namaPembeli", "namaPembeli"));

	
		return mappings;
	} 
	
}
