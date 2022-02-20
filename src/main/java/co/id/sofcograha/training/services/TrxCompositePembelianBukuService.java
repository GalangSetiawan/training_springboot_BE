package co.id.sofcograha.training.services;

import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.extendables.BaseService;
import co.id.sofcograha.base.utils.Message;
import co.id.sofcograha.base.utils.VersionUtil;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.base.utils.threadlocals.LocalErrors;
import co.id.sofcograha.training.entities.*;
import co.id.sofcograha.training.pojos.TrxDetailPembelianBukuPojo;
import co.id.sofcograha.training.pojos.TrxDetailPembayaranPojo;
import co.id.sofcograha.training.pojos.TrxHeaderPojo;
import co.id.sofcograha.training.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("trxCompositePembelianBukuService")
public class TrxCompositePembelianBukuService extends BaseService {

	@Autowired private TrxHeaderRepository repo;
	@Autowired private TrxHeaderRepository repoTrxHeader;
	@Autowired private MasterBukuRepository repoMasterBuku;
	@Autowired private SaldoBukuRepository repoSaldoBuku;
	@Autowired private MasterGenreRepository repoGenre;
	@Autowired private TrxDetailPembelianBukuRepository repoTrxDetailBuku;
	@Autowired private TrxDetailPembayaranRepository trxDetailPembayaranRepository;
	@Autowired private SaldoKasTitipanRepository saldoKasTitipanRepository;
	@Autowired private RangePointRepository rangePointRepository;
	@Autowired private MasterMembershipRepository repoMember;

	@Autowired private TrxHeaderService trxHeaderService;
	@Autowired private TrxDetailPembelianBukuService trxDetailPembelianBukuService;

	private boolean isErrorDetail = false;
	
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

		TrxHeaderEntity entity = trxHeaderService.add(pojo);

		for(TrxDetailPembelianBukuPojo trxDetailPembelianBukuPojo: pojo.listBuku){

			TrxDetailPembelianBukuEntity trxDetailPembelianBukuEntity = trxDetailPembelianBukuPojo.toEntity();

			trxDetailPembelianBukuService.add(trxDetailPembelianBukuPojo);

		}


		TrxHeaderEntity addedHeaderEntity = repoTrxHeader.add(entity);

		// Galang
		hitungPembelianBuku(pojo ,addedHeaderEntity );

//		// Evi
//		hitungPembayaranBuku(addedHeaderEntity, pojo);

		throwBatchError();
		return addedHeaderEntity;
		
    }


	@Transactional
	public void hitungPembelianBuku (TrxHeaderPojo trxHeaderPojo ,TrxHeaderEntity addedHeaderEntity){

		Double totalHargaSetelahDiscGenre = 0.0;

		List<TrxDetailPembelianBukuEntity> listBukuAfterValidation = new ArrayList<>();

		for (TrxDetailPembelianBukuPojo detailBukuPojo : trxHeaderPojo.listBuku) {


			// inisialisasi detail entity
			TrxDetailPembelianBukuEntity detailEntity = detailBukuPojo.toEntity();

			detailEntity.setDataHeader(addedHeaderEntity);

			MasterBukuEntity dataBuku = null;
			dataBuku = repoMasterBuku.findById(detailEntity.getDataBuku().getId());
			detailEntity.setDataBuku(dataBuku);

			// validasi detail buku
			validasiDetailBuku(detailEntity);

			// hitung discount buku (harga - discount)
			hitungDiscountBuku(detailEntity);

			// hitung harga buku setelah disc buku ( harga * qty )
			hitungHargaSetelahDiscBukuVsQty(detailEntity);

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
		Double totalPembelianBuku = totalHargaSetelahDiscGenre;
		Double totalNilaiDiscountHeader = 0.0;

		for(TrxDetailPembelianBukuEntity detailBuku : listBukuAfterValidation){

			// hitung disc proposional
			hitungNilaiDiscountHeaderProposional(addedHeaderEntity, detailBuku, totalHargaSetelahDiscGenre);

			totalPembelianBuku = totalPembelianBuku - detailBuku.getNilaiDiscHeader();
			totalNilaiDiscountHeader = totalNilaiDiscountHeader + detailBuku.getNilaiDiscHeader();

			//save TrxDetail
			repoTrxDetailBuku.save(detailBuku);
		}

		addedHeaderEntity.setTotalPembelianBuku(totalPembelianBuku);
		addedHeaderEntity.setNilaiDiskonHeader(totalNilaiDiscountHeader);

		// proses promosi
		if(addedHeaderEntity.getDataMembership() != null){
			Boolean flagDapatPromo = checkLimaPembeliPertamaByNomorBonDanDate(addedHeaderEntity);

			if(flagDapatPromo == true){
				kurangiSaldoBukuTulis(addedHeaderEntity);
				tambahkanBukuTulisPadaDetailTransaksi(addedHeaderEntity);
				addedHeaderEntity.setFlagDapatPromo5Pertama(true);
			}else{
				addedHeaderEntity.setFlagDapatPromo5Pertama(false);
			}
		}else{
			addedHeaderEntity.setFlagDapatPromo5Pertama(false);
		}

//		List<TrxDetailPembelianBukuEntity> listTrxDetailPembelianBuku = repoTrxDetailBuku.findByIdHeader(addedHeaderEntity);
//		addedHeaderEntity.setTrxDetailPembelianBuku(listTrxDetailPembelianBuku);

		repoTrxHeader.edit(addedHeaderEntity);

	}



	@Transactional
	public void hitungPembayaranBuku (TrxHeaderEntity trxHeaderEntity, TrxHeaderPojo trxHeaderPojo){
		if(trxHeaderEntity.getDataMembership() != null){

				//tambah point untuk setiap pembelian buku
				addPointPembayaran(trxHeaderEntity);

			for(TrxDetailPembayaranPojo detailPembayaranPojo : trxHeaderPojo.trxDetailPembayaranPojo) {
				TrxDetailPembayaran entityDetailBayar = detailPembayaranPojo.toEntity();

					if(entityDetailBayar.getJenisPembayaran() == "Tunai"){
						//update kas titipan
						updateKasTitipan(trxHeaderEntity);
					}

					if(entityDetailBayar.getJenisPembayaran() == "Point"){
						//validasi point ada atau tidak
						validasiSaldoPointMencukupi(trxHeaderEntity, entityDetailBayar);

						//update kas titipan
						kurangiPoint(trxHeaderEntity, entityDetailBayar);
					}

					if(entityDetailBayar.getJenisPembayaran() == "Kas titipan"){
						//validasi kas titipan ada atau tidak
						validasiSaldoKasTitipanMencukupi(trxHeaderEntity, entityDetailBayar);

						//update kas titipan
						kurangiKasTitipan(trxHeaderEntity, entityDetailBayar);
					}

					if(entityDetailBayar.getJenisPembayaran() == "Transfer"){
						//update point
						updatePoint(trxHeaderEntity);
					}

				trxDetailPembayaranRepository.save(entityDetailBayar);

				if (isAnyBatchErrors()) {
					isErrorDetail = true;

					// ambil error-error yang sudah terkumpul di batchError, lalu masukkan ke errorMsg di pojo ini

					for (BusinessException businessException : LocalErrors.getErrors().getBusinessExceptions()) {

						// khusus untuk error yang muncul di grid
						Message message = new Message();
						ArrayList<Object> newParameters = new ArrayList<Object>();

						for (Object object : businessException.getMessageParameters()) {
							newParameters.add(object);
						}

						message.setCode(businessException.getMessageCode());
						message.setArgs(newParameters);

						if (detailPembayaranPojo.errorMsg == null) {
							detailPembayaranPojo.errorMsg = new ArrayList<Message>();
						}

						detailPembayaranPojo.errorMsg.clear();
						detailPembayaranPojo.errorMsg.add(message);
					}

					// bersihkan error yang ada di LocalError
					removeBatchErrors();

					continue;
					// ini artinya kalau ada error di detail sub detail tidak dijalankan (ngga apa apa sih)
					// tapi bagaimana bila sub detail yang ada error, apakah detail nya juga ditandai error agar tampilan di layar
					// nanti di 'depan' (display browse detail) ada tanda merah (sebab kan ngga lucu juga kalo user harus klik
					// satu satu sampai anak ter dalam
				}
			}

		}
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
			throw new BusinessException("trx.pembelian.buku.bk", addedEntity.getNomorBon());
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

	public void validasiDetailBuku(TrxDetailPembelianBukuEntity trxDetailBuku){
		validasiBukuAdaDimasterDanAktif(trxDetailBuku);
		validasiBukuHarusAdaDisaldo(trxDetailBuku);
		validasiSaldoBukuMencukupi(trxDetailBuku);
	}

	public void validasiBukuAdaDimasterDanAktif(TrxDetailPembelianBukuEntity trxDetailBuku){

		if( trxDetailBuku.getDataBuku() == null){
			throw new BusinessException("buku.yang.diinput.tidak.ada.pada.database", trxDetailBuku.getDataBuku().getNamaBuku());
		}
	}

	public void validasiBukuHarusAdaDisaldo(TrxDetailPembelianBukuEntity trxDetailBuku){
		SaldoBukuEntity saldoBuku = null;
		saldoBuku = repoSaldoBuku.findByDataBuku(trxDetailBuku.getDataBuku());

		if(saldoBuku == null){
			throw new BusinessException("tidak.terdapat.saldo.pada.buku", trxDetailBuku.getDataBuku().getNamaBuku());

		}
	}

	public void validasiSaldoBukuMencukupi(TrxDetailPembelianBukuEntity trxDetailBuku){
		SaldoBukuEntity saldoBuku = null;
		saldoBuku = repoSaldoBuku.findByDataBuku(trxDetailBuku.getDataBuku());

		if(saldoBuku.getSaldoBuku() - trxDetailBuku.getQty() <= 0){
			throw new BusinessException(
				"saldo.buku.tidak.mencukupi,.sisa.saldo.buku.saat.ini",
				trxDetailBuku.getDataBuku().getNamaBuku(),
				saldoBuku.getSaldoBuku()
			);
		}
	}

	public void hitungDiscountBuku(TrxDetailPembelianBukuEntity trxDetailBuku){
		Double hargaBuku = trxDetailBuku.getDataBuku().getHargaBuku();
		Integer persenDiscBuku = trxDetailBuku.getPersenDiscBuku();

		Double nilaiDiscBuku = ( hargaBuku * persenDiscBuku ) / 100;
		Double hargaSetelahDiscBuku = hargaBuku - nilaiDiscBuku;

		trxDetailBuku.setNilaiDiscBuku(nilaiDiscBuku);
		trxDetailBuku.setHargaSetelahDiscBuku(hargaSetelahDiscBuku);

	}

	public void hitungHargaSetelahDiscBukuVsQty(TrxDetailPembelianBukuEntity trxDetailBuku){
		Double hargaSetelahDiscBuku = trxDetailBuku.getHargaSetelahDiscBuku();
		Integer qty = trxDetailBuku.getQty();
		Double totalHarga = hargaSetelahDiscBuku * qty;

		trxDetailBuku.setTotalHarga(totalHarga);
	}



	public void hitungTotalHargaBukuVsDiscountGenre(TrxDetailPembelianBukuEntity trxDetailBuku){

		MasterGenreEntity dataGenre = null;
		dataGenre = repoGenre.getOne(trxDetailBuku.getDataBuku().getGenreBuku().getId());

		Integer persenDiscGenre = dataGenre.getDiskonGenre();
		Double totalHarga = trxDetailBuku.getTotalHarga();
		Double nilaiDiscGenre = ( totalHarga * persenDiscGenre ) / 100;
		Double hargaSetelahDiscGenre = totalHarga - nilaiDiscGenre;

		trxDetailBuku.setNilaiDiscGenre(nilaiDiscGenre);
		trxDetailBuku.setHargaSetelahDiscGenre(hargaSetelahDiscGenre);

	}

	public void updateSaldoBuku(TrxDetailPembelianBukuEntity trxDetailBuku, String jenisUpdate){
		SaldoBukuEntity saldoBuku = null;
		saldoBuku = repoSaldoBuku.findByDataBuku(trxDetailBuku.getDataBuku());

		Integer sisaSaldoBukuTersedia = saldoBuku.getSaldoBuku();
		if(jenisUpdate == "tambah"){
			sisaSaldoBukuTersedia = saldoBuku.getSaldoBuku() + trxDetailBuku.getQty();
		}else if(jenisUpdate == "kurang"){
			sisaSaldoBukuTersedia = saldoBuku.getSaldoBuku() - trxDetailBuku.getQty();
		}

		saldoBuku.setSaldoBuku(sisaSaldoBukuTersedia);

		repoSaldoBuku.save(saldoBuku);
	}

	public void hitungNilaiDiscountHeaderProposional(TrxHeaderEntity header, TrxDetailPembelianBukuEntity trxDetailBuku, Double totalHargaSetelahDiscGenre){
		Integer persenDiscHeader = header.getDiscountHeader();
		Double nilaiDiscHeader = (totalHargaSetelahDiscGenre * persenDiscHeader) / 100;
		Double hargaSetelahDiscGenre = trxDetailBuku.getHargaSetelahDiscGenre();
		Double nilaiDiscProposional = ( hargaSetelahDiscGenre / totalHargaSetelahDiscGenre ) * nilaiDiscHeader;

		trxDetailBuku.setNilaiDiscHeader(nilaiDiscHeader);
		trxDetailBuku.setNilaiDiscProposional(nilaiDiscProposional);
	}

	public Boolean checkLimaPembeliPertamaByNomorBonDanDate(TrxHeaderEntity headerEntity) {
		List <TrxHeaderEntity> listPembeli = repoTrxHeader.get5DataPertamaByTanggalTrx(headerEntity.getTanggalBon());

		Integer countGaDapatPromo = 0;
		Boolean flagDapatpromo = true;

		String currentInputMemberId = headerEntity.getDataMembership().getId();

		if(listPembeli.size() >= 5 ){
			countGaDapatPromo++;
		}else{
			for(TrxHeaderEntity eachData : listPembeli){
				if(!currentInputMemberId.equals(eachData.getDataMembership().getId())){
					if(eachData.getDataMembership().getId().equals(currentInputMemberId)){
						countGaDapatPromo++;
					}
				}
			}
		}

		if(countGaDapatPromo > 0){
			flagDapatpromo = false;
		}

		return flagDapatpromo;
	}

	public void kurangiSaldoBukuTulis(TrxHeaderEntity trxHeaderEntity){
		// get data buku tulis
		MasterBukuEntity masterBukuEntity = new MasterBukuEntity();
		masterBukuEntity = repoMasterBuku.findByBK("BukuTulis");

		// inisialisasi trx detail
		TrxDetailPembelianBukuEntity trxDetailEntity = new TrxDetailPembelianBukuEntity();
		trxDetailEntity.setDataHeader(trxHeaderEntity);
		trxDetailEntity.setDataBuku(masterBukuEntity);
		trxDetailEntity.setQty(1);
		updateSaldoBuku(trxDetailEntity, "kurang");

	}

	public void tambahkanBukuTulisPadaDetailTransaksi(TrxHeaderEntity trxHeaderEntity){
		TrxDetailPembelianBukuEntity trxDetailPembelianBukuEntity = new TrxDetailPembelianBukuEntity();

		MasterBukuEntity masterBukuEntity = new MasterBukuEntity();
		masterBukuEntity = repoMasterBuku.findByBK("BukuTulis");

		trxDetailPembelianBukuEntity.setDataBuku(masterBukuEntity);
		trxDetailPembelianBukuEntity.setDataHeader(trxHeaderEntity);
		trxDetailPembelianBukuEntity.setQty(1);
		trxDetailPembelianBukuEntity.setTotalHarga(0.0);

		repoTrxDetailBuku.save(trxDetailPembelianBukuEntity);

	}


	private void addPointPembayaran(TrxHeaderEntity entityHeader){
		RangePointEntity rangePoint =rangePointRepository.findByTotal(entityHeader.getTotalPembelianBuku());
		Integer point = rangePoint.getPoint();

		MasterMembershipEntity masterMembershipEntity =repoMember.findByPoint(entityHeader.getDataMembership().getNamaMembership());

		SaldoKasTitipanEntity saldoKasTitipanEntity =saldoKasTitipanRepository.findByBK(masterMembershipEntity.getId());
		saldoKasTitipanEntity.setNilaiPoint(point);

		saldoKasTitipanRepository.save(saldoKasTitipanEntity);
	}

	private void updateKasTitipan(TrxHeaderEntity entityHeader){
		MasterMembershipEntity masterMembershipEntity =repoMember.findByPoint(entityHeader.getDataMembership().getNamaMembership());

		SaldoKasTitipanEntity saldoKasTitipanEntity = saldoKasTitipanRepository.findByIdMember(masterMembershipEntity.getId());
		Double nilaiKembalian = entityHeader.getNilaiKembalian();

			saldoKasTitipanEntity.setNilaiTitipan(nilaiKembalian);

			entityHeader.setNilaiKembalian(0.0);

			RangePointEntity rangePoint =rangePointRepository.findByTotal(entityHeader.getTotalPembelianBuku());
			Integer point = rangePoint.getPoint();

			saldoKasTitipanEntity.setNilaiPoint(point);


		saldoKasTitipanRepository.save(saldoKasTitipanEntity);
	}

	public void validasiSaldoPointMencukupi(TrxHeaderEntity entityHeader, TrxDetailPembayaran entityDetailBayar){
		MasterMembershipEntity masterMembershipEntity =repoMember.findByPoint(entityHeader.getDataMembership().getNamaMembership());

		SaldoKasTitipanEntity saldoPoint = saldoKasTitipanRepository.findByIdMember(masterMembershipEntity.getId());

		if(saldoPoint.getNilaiPoint() - entityDetailBayar.getJumlahPoint() <= 0){
			throw new BusinessException(
					"saldo.point.tidak.mencukupi,.sisa.saldo.point.saat.ini",
					entityDetailBayar.getJumlahPoint(),
					saldoPoint.getNilaiPoint()
			);
		}
	}

	public void kurangiPoint(TrxHeaderEntity entityHeader, TrxDetailPembayaran entityDetailBayar){
		MasterMembershipEntity masterMembershipEntity =repoMember.findByPoint(entityHeader.getDataMembership().getNamaMembership());

		SaldoKasTitipanEntity saldoPoint =saldoKasTitipanRepository.findByIdMember(masterMembershipEntity.getId());
		Integer point = saldoPoint.getNilaiPoint();

		point = point - entityDetailBayar.getJumlahPoint();

		saldoPoint.setNilaiPoint(point);

		saldoKasTitipanRepository.save(saldoPoint);
	}

	public void validasiSaldoKasTitipanMencukupi(TrxHeaderEntity entityHeader, TrxDetailPembayaran entityDetailBayar){
		MasterMembershipEntity masterMembershipEntity =repoMember.findByPoint(entityHeader.getDataMembership().getNamaMembership());

		SaldoKasTitipanEntity saldoKasTitipan =saldoKasTitipanRepository.findByIdMember(masterMembershipEntity.getId());

		if(saldoKasTitipan.getNilaiTitipan() - entityDetailBayar.getNilaiRupiah() <= 0){
			throw new BusinessException(
					"saldo.kas.titipan.tidak.mencukupi,.sisa.saldo.kas.titipan.saat.ini",
					entityDetailBayar.getNilaiRupiah(),
					saldoKasTitipan.getNilaiTitipan()
			);
		}
	}

	public void kurangiKasTitipan(TrxHeaderEntity entityHeader, TrxDetailPembayaran entityDetailBayar){
		MasterMembershipEntity masterMembershipEntity =repoMember.findByPoint(entityHeader.getDataMembership().getNamaMembership());

		SaldoKasTitipanEntity saldoKasTitipan =saldoKasTitipanRepository.findByIdMember(masterMembershipEntity.getId());
		Double nilaiTitipan = saldoKasTitipan.getNilaiTitipan();

		nilaiTitipan = nilaiTitipan - entityDetailBayar.getNilaiRupiah();

		saldoKasTitipan.setNilaiTitipan(nilaiTitipan);

		saldoKasTitipanRepository.save(saldoKasTitipan);
	}

	private void updatePoint(TrxHeaderEntity entityHeader){
		MasterMembershipEntity masterMembershipEntity =repoMember.findByPoint(entityHeader.getDataMembership().getNamaMembership());

		SaldoKasTitipanEntity saldoKasTitipanEntity =saldoKasTitipanRepository.findByBK(masterMembershipEntity.getId());

		RangePointEntity rangePoint =rangePointRepository.findByTotal(entityHeader.getTotalPembelianBuku());
		Integer point = rangePoint.getPoint();

		saldoKasTitipanEntity.setNilaiPoint(point);

		saldoKasTitipanRepository.save(saldoKasTitipanEntity);
	}

}
