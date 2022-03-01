package co.id.sofcograha.training.services;

import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.extendables.BaseService;
import co.id.sofcograha.base.utils.TimeUtil;
import co.id.sofcograha.base.utils.VersionUtil;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.*;
import co.id.sofcograha.training.pojos.MasterMembershipPojo;
import co.id.sofcograha.training.pojos.TrxHeaderPojo;
import co.id.sofcograha.training.pojos.TrxPembelianBukuPromoPojo;
import co.id.sofcograha.training.repositories.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("trxHeaderService")
public class TrxHeaderService extends BaseService {

	@Autowired private TrxHeaderRepository repo;
	@Autowired private MasterMembershipRepository masterMembershipRepository;
	@Autowired private TrxCompositePembelianBukuService trxCompositePembelianBukuService;
	@Autowired private MasterJenisTransaksiRepository masterJenisTransaksiRepository;

	public TrxHeaderEntity findByBk(String nomorTrxHeader) {
		return repo.findByBK(nomorTrxHeader);
	}

	public TrxHeaderEntity findById(final String id) {
		return repo.getOne(id);
	}

	public SearchResult<TrxHeaderEntity> search(SearchParameter searchParameter) {
		return repo.search(searchParameter);
	}

	public TrxHeaderPojo findByNomorBon(String nomorBon) {
		return TrxHeaderPojo.fromEntity(repo.findByNomorBon(nomorBon));
	}


	@Transactional
    public TrxHeaderEntity add(TrxHeaderEntity entity) {

		entity.setId(null);

		defineDefaultValuesOnAdd(entity);

		valRequiredValues(entity);
		throwBatchError();

		manageMinMaxValues(entity);
		throwBatchError();

		manageReferences(entity);
		throwBatchError();

		valUniquenessOnAdd(entity);
		throwBatchError();

		throwBatchError();

		entity = repo.save(entity);

		return entity;
    }
       
	@Transactional
	public TrxHeaderEntity edit(TrxHeaderEntity entity) {
		
		valIdVersionRequired(entity.getId(), entity.getVersion());
		valVersion(entity.getId(), entity.getVersion(), entity.getClass().getSimpleName());
		throwBatchError();
		
		valRequiredValues(entity);
		throwBatchError();
		
		manageMinMaxValues(entity);
		throwBatchError();
		
		manageReferences(entity);
		throwBatchError();
		
		valUniquenessOnEdit(entity);
		throwBatchError();

		TrxHeaderEntity toBeSaved = repo.getOne(entity.getId());
		//ECustomer oldEntity = (ECustomer) toBeSaved.clone();
		
		defineEditableValues(entity, toBeSaved);
		
		throwBatchError();
		return toBeSaved;
		
	}

	@Transactional
	public void delete (String id, Long version) {
		
		valIdVersionRequired(id, version);
		valVersion(id, version, MasterGenreEntity.class.getSimpleName());
		throwBatchError();

		TrxHeaderEntity toBeDeleted = repo.getOne(id);
		
		valDelete(toBeDeleted);
		throwBatchError();
		
		repo.delete(toBeDeleted.getId());
		
		throwBatchError();
	}

    protected void defineDefaultValuesOnAdd(TrxHeaderEntity entity) {
		// generate nomor bon
		generateNomorBon(entity);

		//Patch tanggal bon
		entity.setTanggalBon(TimeUtil.getSystemDateTime());

		// version
		if (entity.getVersion() == null) {
			entity.setVersion(1L);
		}
	}
    
    protected void valRequiredValues(TrxHeaderEntity entity) {
		valRequiredString(entity.getNamaPembeli(), "trx.header.namaPembeli.required");
	}
    
    protected void manageMinMaxValues(TrxHeaderEntity entity) {
		valMaxString(entity.getNamaPembeli(), 200, "trx.header.kodeMembership.max.length");
//		valMaxString(entity.getNamaMembership(), 200, "trx.header.namaMembership.max.length");

	}
    
    protected void manageReferences(TrxHeaderEntity entity) {

		if(entity.getDataMembership() != null){
			MasterMembershipEntity dataMember = masterMembershipRepository.getOne(entity.getDataMembership().getId());
			entity.setDataMembership(dataMember);
		}

		MasterJenisTransaksiEntity jenisTransaksiEntity = masterJenisTransaksiRepository.findOne("1");
		entity.setDataJenisTransaksi(jenisTransaksiEntity);

	}

    protected void valUniquenessOnAdd(TrxHeaderEntity addedEntity) {
		TrxHeaderEntity entityFromDb = repo.findByBK(addedEntity.getNomorBon());
		if (entityFromDb != null) {
			throw new BusinessException("master.membership.bk", addedEntity.getNomorBon());
		}
	}
    
    private void valVersion(String id, Long version, String entityClassName) {
		valEntityExists(id, entityClassName);
		TrxHeaderEntity dbEntity = repo.getOne(id);
		VersionUtil.check(version, dbEntity.getVersion());
	}
    
	private void valEntityExists(String id, String entityClassName) {
		if (repo.getOne(id) == null) {
			throw new BusinessException("data.not.found", entityClassName, id);
		}
	}
	
	protected void valUniquenessOnEdit(TrxHeaderEntity editedEntity) {
		TrxHeaderEntity entityFromDb = repo.findByBK(editedEntity.getNomorBon());
		if (entityFromDb != null) {
			if (!editedEntity.getId().equals(entityFromDb.getId())) {
				throw new BusinessException("master.membership.bk", editedEntity.getNomorBon());
			}
		}
	}
	
	protected void defineEditableValues(TrxHeaderEntity newValues, TrxHeaderEntity toBeSaved) {
		toBeSaved.setNomorBon(newValues.getNomorBon());
		toBeSaved.setTanggalBon(newValues.getTanggalBon());
		toBeSaved.setNamaPembeli(newValues.getNamaPembeli());
		toBeSaved.setDiscountHeader(newValues.getDiscountHeader());
		toBeSaved.setNilaiKembalian(newValues.getNilaiKembalian());
		toBeSaved.setTotalPembayaran(newValues.getTotalPembayaran());
		toBeSaved.setTotalPembelianBuku(newValues.getTotalPembelianBuku());
		toBeSaved.setNilaiDiskonHeader(newValues.getNilaiDiskonHeader());
		toBeSaved.setFlagDapatPromo5Pertama(newValues.getFlagDapatPromo5Pertama());
		toBeSaved.setFlagKembalian(newValues.getFlagKembalian());
		toBeSaved.setPPN(newValues.getPPN());
		toBeSaved.setKeterangan(newValues.getKeterangan());
		toBeSaved.setDPP(newValues.getDPP());
		toBeSaved.setDataMembership(newValues.getDataMembership());
		toBeSaved.setDataJenisTransaksi(newValues.getDataJenisTransaksi());
	}
	
	protected void valDelete(TrxHeaderEntity toBeDeleted) {	}

	public TrxHeaderEntity get(String id) {
		return repo.getOne(id);
	}

	public TrxPembelianBukuPromoPojo check5pembeliPertama(String idMembership) {
		TrxPembelianBukuPromoPojo result = new TrxPembelianBukuPromoPojo();
		MasterMembershipEntity dataMember = masterMembershipRepository.getOne(idMembership);

		if(dataMember != null){
			TrxHeaderEntity trxHeaderEntityDummy = new TrxHeaderEntity();
			trxHeaderEntityDummy.setTanggalBon(TimeUtil.getSystemDate());
			trxHeaderEntityDummy.setDataMembership(dataMember);

			result.flagDapatPromo5PembeliPertama = trxCompositePembelianBukuService.checkLimaPembeliPertamaByNomorBonDanDate(trxHeaderEntityDummy);
		} else {
			batchError("idMembership.tidak.ditemukan");
			result.flagDapatPromo5PembeliPertama = false;
		}

		return result;
	}

	public TrxHeaderEntity getLastData() {
		return repo.getLastData();
	}

	public void generateNomorBon(TrxHeaderEntity entity){
		String nomorBonGenerated = "TRX-" + getAlphaNumericString(6);
		entity.setNomorBon(nomorBonGenerated);
	}

	static String getAlphaNumericString(int n) {
		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789"
				+ "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index
					= (int)(AlphaNumericString.length()
					* Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString
					.charAt(index));
		}

		return sb.toString();
	}

}
