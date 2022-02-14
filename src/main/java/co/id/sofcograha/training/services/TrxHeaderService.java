package co.id.sofcograha.training.services;

import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.extendables.BaseService;
import co.id.sofcograha.base.utils.VersionUtil;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.*;
import co.id.sofcograha.training.pojos.MasterBukuPojo;
import co.id.sofcograha.training.pojos.MasterMembershipPojo;
import co.id.sofcograha.training.pojos.TrxDetailBukuPojo;
import co.id.sofcograha.training.pojos.TrxHeaderPojo;
import co.id.sofcograha.training.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("trxHeaderService")
public class TrxHeaderService extends BaseService {

	@Autowired private TrxHeaderRepository repo;
	@Autowired private MasterBukuRepository repoMasterBuku;
	@Autowired private SaldoBukuRepository repoSaldoBuku;
	@Autowired private MasterGenreRepository repoGenre;
	@Autowired private TrxDetailBukuRepository repoTrxDetailBuku;
	@Autowired private TrxDetailPembayaranRepository trxDetailPembayaranRepository;
	@Autowired private SaldoKasTitipanRepository saldoKasTitipanRepository;

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
    public TrxHeaderEntity add(TrxHeaderPojo pojo) {

		TrxHeaderEntity entity = pojo.toEntity();

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

		TrxHeaderEntity addedEntity = repo.add(entity);

		Double totalHargaSetelahDiscGenre = 0.0;

		List<TrxDetailBukuEntity> listBukuAfterValidation = null;

		for (TrxDetailBukuPojo detailBukuPojo : pojo.dataBuku) {


			// inisialisasi detail entity
			TrxDetailBukuEntity detailEntity = detailBukuPojo.toEntity();
			detailEntity.getDataHeader().setId(addedEntity.getId());

			// validasi detail buku
			validasiDetailBuku(detailEntity);

			// hitung harga buku ( harga * qty )
			hitungHargaBukuVsQty(detailEntity);

			// hitung discount per genre
			hitungTotalHargaBukuVsDiscountGenre(detailEntity);

			// hitung total harga setelah disc genre
			totalHargaSetelahDiscGenre = totalHargaSetelahDiscGenre + detailEntity.getHargaSetelahDiscGenre();

			// tampung list Detail pembelian buku kedalam array untuk looping ke 2
			listBukuAfterValidation.add(detailEntity);

			// update saldo buku
			updateSaldoBuku(detailEntity, "kurang");
		}

		//loop ke 2
		for(TrxDetailBukuEntity detailBuku : listBukuAfterValidation){

			// hitung disc proposional
			hitungNilaiDiscountHeaderProposional(addedEntity, detailBuku, totalHargaSetelahDiscGenre);

			//save TrxDetail
			repoTrxDetailBuku.save(detailBuku);

		}

		// kalo dapet promo, kasih buku tulis + update saldo buku tulis

		// proses promosi
		if(addedEntity.getDataMembership().getId() != null){
			Boolean flagDapatPromo = checkLimaPembeliPertamaByNomorBonDanDate(addedEntity);

			if(flagDapatPromo){
				kurangiSaldoBukuTulis(addedEntity);
			}

			boolean isMember = true;

			addPointPembayaran(addedEntity, isMember);

			addKasTitipan();


		}



		throwBatchError();
		return addedEntity;	
		
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
//		if (entity.getFlakt() == null) entity.setFlakt(BaseConstants.YA);
		if (entity.getVersion() == null) entity.setVersion((long) 1);
	}
    
    protected void valRequiredValues(TrxHeaderEntity entity) {
		valRequiredString(entity.getNamaPembeli(), "trx.header.namaPembeli.required");
	}
    
    protected void manageMinMaxValues(TrxHeaderEntity entity) {
		valMaxString(entity.getNamaPembeli(), 200, "trx.header.kodeMembership.max.length");
//		valMaxString(entity.getNamaMembership(), 200, "trx.header.namaMembership.max.length");

	}
    
    protected void manageReferences(TrxHeaderEntity entity) {
		/*
		if (entity.getFunctionAccess() != null) {
			OptionalConsumerUtil.of(functionAccessService.find(entity.getFunctionAccess().getId()))
			.ifPresent(functionAccess -> {
				if (functionAccess.getActive()) {
					entity.setFunctionAccess(functionAccess);
				} else {
					batchError("widget.functionAccess.not.active");
				}
			})
			.ifNotPresent(() -> {
				batchError("widget.functionAccess.not.found");
			});
		}
		*/
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
		
		if (toBeSaved != null) {
			toBeSaved.setNomorBon(newValues.getNomorBon());
			toBeSaved.setTanggalBon(newValues.getTanggalBon());

			toBeSaved.setNamaPembeli(newValues.getNamaPembeli());
			toBeSaved.setDiscountHeader(newValues.getDiscountHeader());
			toBeSaved.setNilaiKembalian(newValues.getNilaiKembalian());
			toBeSaved.setTotalPembayaran(newValues.getTotalPembayaran()); // bukan nya ini bentuknya ga sesimpel ini? kan bayarnya gacuma bentuk duit, tp bisa duit & poin
			toBeSaved.setTotalPembelianBuku(newValues.getTotalPembelianBuku());
			toBeSaved.setNilaiDiskonHeader(newValues.getNilaiDiskonHeader());
			toBeSaved.setFlagDapatPromo5Pertama(newValues.getFlagDapatPromo5Pertama());
			toBeSaved.setDataMembership(newValues.getDataMembership());
			toBeSaved.setDataJenisTransaksi(newValues.getDataJenisTransaksi());


		}
		else if (toBeSaved == null) {
			defineDefaultValuesOnAdd(newValues);
		}
		
	}
	
	protected void valDelete(TrxHeaderEntity toBeDeleted) {	}
    
    
	public TrxHeaderEntity get(String id) {
		return repo.getOne(id);
	}

	public void validasiDetailBuku(TrxDetailBukuEntity trxDetailBuku){
		validasiBukuAdaDimasterDanAktif(trxDetailBuku);
		validasiBukuHarusAdaDisaldo(trxDetailBuku);
		validasiSaldoBukuMencukupi(trxDetailBuku);
	}

	public void validasiBukuAdaDimasterDanAktif(TrxDetailBukuEntity trxDetailBuku){
		MasterBukuEntity dataBuku = new MasterBukuEntity();
		dataBuku = repoMasterBuku.findByNamaBuku(trxDetailBuku.getDataBuku().getNamaBuku());

		if( dataBuku == null){
			throw new BusinessException("buku.yang.diinput.tidak.ada.pada.database", trxDetailBuku.getDataBuku().getNamaBuku());
		}
	}

	public void validasiBukuHarusAdaDisaldo(TrxDetailBukuEntity trxDetailBuku){
		MasterBukuEntity dataBuku = null;
		dataBuku = repoMasterBuku.findByNamaBuku(trxDetailBuku.getDataBuku().getNamaBuku());

		SaldoBukuEntity saldoBuku = null;
		saldoBuku = repoSaldoBuku.findByIdBuku(trxDetailBuku.getDataBuku().getId());

		if(saldoBuku == null){
			throw new BusinessException("tidak.terdapat.saldo.pada.buku", trxDetailBuku.getDataBuku().getNamaBuku());

		}


	}

	public void validasiSaldoBukuMencukupi(TrxDetailBukuEntity trxDetailBuku){
		MasterBukuEntity dataBuku = null;
		dataBuku = repoMasterBuku.findByNamaBuku(trxDetailBuku.getDataBuku().getNamaBuku());

		SaldoBukuEntity saldoBuku = null;
		saldoBuku = repoSaldoBuku.findByIdBuku(trxDetailBuku.getDataBuku().getId());

		if(saldoBuku.getSaldoBuku() - trxDetailBuku.getQty() <= 0){
			throw new BusinessException(
				"saldo.buku.tidak.mencukupi,.sisa.saldo.buku.saat.ini",
				trxDetailBuku.getDataBuku().getNamaBuku(),
				saldoBuku.getSaldoBuku()
			);
		}
	}

	public void hitungHargaBukuVsQty(TrxDetailBukuEntity trxDetailBuku){
		Double totalHarga = 0.0;
		Double hargaBuku = trxDetailBuku.getDataBuku().getHargaBuku();
		Integer qty = trxDetailBuku.getQty();
		totalHarga = hargaBuku * qty;

		trxDetailBuku.setTotalHarga(totalHarga);
	}

	public void hitungTotalHargaBukuVsDiscountGenre(TrxDetailBukuEntity trxDetailBuku){
		MasterBukuEntity dataBuku = null;
		dataBuku = repoMasterBuku.findByNamaBuku(trxDetailBuku.getDataBuku().getNamaBuku());

		MasterGenreEntity dataGenre = null;
		dataGenre = repoGenre.getOne(dataBuku.getGenreBuku().getId());

		Integer persenDiscGenre = dataGenre.getDiskonGenre();
		Double totalHarga = trxDetailBuku.getTotalHarga();
		Double nilaiDiscGenre = ( totalHarga * persenDiscGenre ) / 100;
		Double hargaSetelahDiscGenre = totalHarga - nilaiDiscGenre;

		trxDetailBuku.setNilaiDiscGenre(nilaiDiscGenre);
		trxDetailBuku.setHargaSetelahDiscGenre(hargaSetelahDiscGenre);

	}

	public void updateSaldoBuku(TrxDetailBukuEntity trxDetailBuku, String jenisUpdate){
		MasterBukuEntity dataBuku = null;
		dataBuku = repoMasterBuku.findByNamaBuku(trxDetailBuku.getDataBuku().getNamaBuku());

		SaldoBukuEntity saldoBuku = null;
		saldoBuku = repoSaldoBuku.findByIdBuku(dataBuku.getId());

		Integer sisaSaldoBukuTersedia = saldoBuku.getSaldoBuku();
		if(jenisUpdate == "tambah"){
			sisaSaldoBukuTersedia = saldoBuku.getSaldoBuku() + trxDetailBuku.getQty();
		}else if(jenisUpdate == "kurang"){
			sisaSaldoBukuTersedia = saldoBuku.getSaldoBuku() - trxDetailBuku.getQty();
		}

		saldoBuku.setSaldoBuku(sisaSaldoBukuTersedia);

		repoSaldoBuku.save(saldoBuku);
	}

	public void hitungNilaiDiscountHeaderProposional(TrxHeaderEntity header, TrxDetailBukuEntity trxDetailBuku, Double totalHargaSetelahDiscGenre){
		Integer persenDiscHeader = header.getDiscountHeader();
		Double nilaiDiscHeader = (totalHargaSetelahDiscGenre * persenDiscHeader) / 100;
		Double hargaSetelahDiscGenre = trxDetailBuku.getHargaSetelahDiscGenre();
		Double nilaiDiscProposional = ( hargaSetelahDiscGenre / totalHargaSetelahDiscGenre ) * nilaiDiscHeader;

		trxDetailBuku.setNilaiDiscHeader(nilaiDiscHeader);
		trxDetailBuku.setNilaiDiscProposional(nilaiDiscProposional);
	}

	private Boolean checkLimaPembeliPertamaByNomorBonDanDate(TrxHeaderEntity headerEntity) {
		List <TrxHeaderEntity> listPembeli = repo.get5DataPertamaByTanggalTrx(headerEntity.getTanggalBon());

		Boolean flagDapatpromo = true;

		if(listPembeli.size() > 5 ){
			flagDapatpromo = false;
		}else{
			for(TrxHeaderEntity eachData : listPembeli){
				if(eachData.getDataMembership().getId().equals(headerEntity.getDataMembership().getId())){
					flagDapatpromo = false;
				}
			}
		}

		return flagDapatpromo;
	}

	public void kurangiSaldoBukuTulis(TrxHeaderEntity trxHeaderEntity){
		// get data buku tulis
		MasterBukuEntity masterBukuEntity = new MasterBukuEntity();
		masterBukuEntity = repoMasterBuku.findByNamaBuku("Buku tulis");

		// inisialisasi trx detail
		TrxDetailBukuEntity trxDetailEntity = new TrxDetailBukuEntity();
		trxDetailEntity.getDataHeader().setId(trxHeaderEntity.getId());
		trxDetailEntity.getDataBuku().setId(masterBukuEntity.getId());

		updateSaldoBuku(trxDetailEntity, "tambah");

	}


	private void addPointPembayaran(TrxHeaderEntity entityHeader, boolean isMember){
		SaldoKasTitipanEntity saldoKasTitipanEntity =saldoKasTitipanRepository.findByBK(entityHeader.getDataMembership().getId());
		Integer rangePoint = saldoKasTitipanEntity.getNilaiPoint();
		Double rangeNilai = saldoKasTitipanEntity.getNilaiTitipan();

	}

	private void addKasTitipan(){

	}
}
