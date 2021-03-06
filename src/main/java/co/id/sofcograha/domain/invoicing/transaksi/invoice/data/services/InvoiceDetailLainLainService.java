package co.id.sofcograha.domain.invoicing.transaksi.invoice.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.extendables.BaseService;
import co.id.sofcograha.base.utils.VersionUtil;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities.EInvoiceDetailLainLain;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.pojos.InvoiceDetailLainLain;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.repositories.EInvoiceDetailLainLainRepository;

@Service("invoiceDetailLainLainService")
public class InvoiceDetailLainLainService extends BaseService {
	
	@Autowired private EInvoiceDetailLainLainRepository repo;
	
	// sementara belum dipakai
	//@Autowired private ActionAuthorizationService actionAuthorizationService; 
	
	public InvoiceDetailLainLain findByBk(String idTi001, Integer nourut) {
		return InvoiceDetailLainLain.fromEntity(repo.findByBK(idTi001, nourut));
	}

	public SearchResult<EInvoiceDetailLainLain> search(SearchParameter searchParameter) {
		return repo.search(searchParameter);
	}
	
	@Transactional
	public EInvoiceDetailLainLain add(EInvoiceDetailLainLain entity) {
		
		entity.setId(null);
		
		defineDefaultValuesOnAdd(entity);
		
		valRequiredValues(entity);
		// tidak memanggil throwBatchError(), karena simpan transaksi jurnal menganut simpan keseluruhan dan menampilkan error
		// di grid tidak error yang muncul satu per satu.
		//throwBatchError();
		
		manageMinMaxValues(entity);
		//throwBatchError();
		
		manageReferences(entity);
		//throwBatchError();

		valUniquenessOnAdd(entity);
		//throwBatchError();
		
		if (!isAnyBatchErrors()) {
			preAdd(entity);
			EInvoiceDetailLainLain addedEntity = repo.add(entity);
			postAdd(addedEntity);
			
			return addedEntity;	
		}
			
		//throwBatchError();
		return entity;	

	}
	
	@Transactional
	public EInvoiceDetailLainLain edit(EInvoiceDetailLainLain entity) {
		
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
				
		EInvoiceDetailLainLain toBeSaved = repo.getOne(entity.getId());
		EInvoiceDetailLainLain oldEntity = (EInvoiceDetailLainLain) toBeSaved.clone();
		
		defineEditableValues(entity, toBeSaved);
    
		preEdit(toBeSaved, oldEntity);
		toBeSaved = repo.edit(toBeSaved);
		postEdit(toBeSaved, oldEntity);

		throwBatchError();
		return toBeSaved;	
		
	}

	@Transactional
	public void delete (String id, Long version) {
		
		valIdVersionRequired(id, version);
		valVersion(id, version, EInvoiceDetailLainLain.class.getSimpleName());
		// tidak memanggil throwBatchError(), karena simpan transaksi jurnal menganut simpan keseluruhan dan menampilkan error
		// di grid tidak error yang muncul satu per satu.
		//throwBatchError();
		
		EInvoiceDetailLainLain toBeDeleted = repo.getOne(id);
		
		valDelete(toBeDeleted);
		//throwBatchError();
		
		preDelete(toBeDeleted);
		repo.delete(toBeDeleted.getId());
		postDelete(toBeDeleted);
		
		//throwBatchError();
		
	}
	
	// terkait manipulasi data
	private void valVersion(String id, Long version, String entityClassName) {
		valEntityExists(id, entityClassName);
		EInvoiceDetailLainLain dbEntity = repo.getOne(id);
		VersionUtil.check(version, dbEntity.getVersion());
	}
	
	private void valEntityExists(String id, String entityClassName) {
		if (repo.getOne(id) == null) {
			throw new BusinessException("data.not.found", entityClassName, id);
		}
	}
	
	protected void defineDefaultValuesOnAdd(EInvoiceDetailLainLain entity) {
		if (entity.getVersion() == null) entity.setVersion((long) 1);
	}
	
	protected void defineEditableValues(EInvoiceDetailLainLain newValues, EInvoiceDetailLainLain toBeSaved) {

		if (toBeSaved != null) {
			toBeSaved.setNourut(newValues.getNourut());
			toBeSaved.setKeterangan(newValues.getKeterangan());
			toBeSaved.setHarga(newValues.getHarga());
			toBeSaved.setPctdisc(newValues.getPctdisc());
			toBeSaved.setNilpctdisc(newValues.getNilpctdisc());
			toBeSaved.setNildisc(newValues.getNildisc());
			toBeSaved.setNetto(newValues.getNetto());
		}
		else if (toBeSaved == null) {
			defineDefaultValuesOnAdd(newValues);
		}
		
	}
	
	protected void valRequiredValues(EInvoiceDetailLainLain entity) {
		valRequiredInteger(entity.getNourut(), "invoicemanual.detail.lainlain.nourut.required");
		valRequiredString(entity.getKeterangan(), "invoicemanual.detail.lainlain.keterangan.required");		
		valRequiredDouble(entity.getHarga(), "invoicemanual.detail.lainlain.harga.required");
		valRequiredDouble(entity.getPctdisc(), "invoicemanual.detail.lainlain.pctdisc.required");
		valRequiredDouble(entity.getNilpctdisc(), "invoicemanual.detail.lainlain.nilpctdisc.required");
		valRequiredDouble(entity.getNildisc(), "invoicemanual.detail.lainlain.nildisc.required");
		valRequiredDouble(entity.getNetto(), "invoicemanual.detail.lainlain.netto.required");
	}
	
	protected void manageMinMaxValues(EInvoiceDetailLainLain entity) {
		valMinInteger(entity.getNourut(), 0, "invoicemanual.detail.lainlain.nourut.min.length");
		valMaxInteger(entity.getNourut(), 999, "invoicemanual.detail.lainlain.nourut.max.length");
		//valMaxString(entity.getKeterangan(), 200, "invoicemanual.detail.lainlain.keterangan.max.length");
		valMinDouble(entity.getHarga(), 0D, "invoicemanual.detail.lainlain.harga.min.length");
		valMaxDouble(entity.getHarga(), 999999999D, "invoicemanual.lainlain.harga.max.length");
		valMinDouble(entity.getPctdisc(), 0D, "invoicemanual.detail.lainlain.pctdisc.min.length");
		valMaxDouble(entity.getPctdisc(), 9999D, "invoicemanual.lainlain.pctdisc.max.length");
		valMinDouble(entity.getNilpctdisc(), 0D, "invoicemanual.detail.lainlain.nilpctdisc.min.length");
		valMaxDouble(entity.getNilpctdisc(), 999999999D, "invoicemanual.lainlain.nilpctdisc.max.length");
		valMinDouble(entity.getNildisc(), 0D, "invoicemanual.detail.lainlain.nildisc.min.length");
		valMaxDouble(entity.getNildisc(), 999999999D, "invoicemanual.lainlain.nildisc.max.length");
		valMinDouble(entity.getNetto(), 0D, "invoicemanual.detail.lainlain.netto.min.length");
		valMaxDouble(entity.getNetto(), 999999999D, "invoicemanual.lainlain.netto.max.length");
	}

	protected void manageReferences(EInvoiceDetailLainLain entity) {
	}

	protected void valDelete(EInvoiceDetailLainLain toBeDeleted) {	}
	
	protected void valUniquenessOnAdd(EInvoiceDetailLainLain addedEntity) {
		EInvoiceDetailLainLain entityFromDb = repo.findByBK(addedEntity.getHeader().getId(), addedEntity.getNourut());
		if (entityFromDb != null) {
			throw new BusinessException("invoicemanual.lainlain.detail.bk", addedEntity.getNourut());
		}
	}

	protected void valUniquenessOnEdit(EInvoiceDetailLainLain editedEntity) {
		EInvoiceDetailLainLain entityFromDb = repo.findByBK(editedEntity.getHeader().getId(), editedEntity.getNourut());
		if (entityFromDb != null) {
			if (!editedEntity.getId().equals(entityFromDb.getId())) {
				throw new BusinessException("invoicemanual.lainlain.detail.bk", editedEntity.getNourut());
			}
		}
	}

	protected void preAdd(EInvoiceDetailLainLain entity) {
		// salah satu contoh penggunaannya (di gaji.id) :
		// - set nomor urut item berdasar total data + 1 (TrxInputKaryawanAttachFileService,
		//   TrxInputKaryawanDetailPelatihanService)
		// - menambahkan thumbnail bagi foto di TrxInputKaryawanPhotoService, EmployeePhotoService
		// NB: terus terang bingung membedakan method ini dengan method defineDefaultValuesOnAdd
	}
	
	protected void postAdd(EInvoiceDetailLainLain entity) {
		// salah satu contoh penggunaannya (di gaji.id) :
		// - menandai transaksi ini sebagai transaksi yang terkonfirmasi
		// - mengeksekusi notifikasi (pada SysNotificationService)
		// - edit file admin untuk menandai slip gaji di release
		// - manambah saldo surat ketidakhadiran yang belum konfirmasi
		// - update absensi, edit jadwal kerja, jalankan sp utk proses verfikasi absensi (TrxOverrideJadwalKerjaDetailService)
		// boleh dibilang ini bisa digunakan untuk update saldo-saldo/sejarah/admin/konfirmasi
	}
	
	protected void preEdit(EInvoiceDetailLainLain toBeSaved, EInvoiceDetailLainLain oldEntity) {
		// penjelasan untuk penggunaan mirip dengan preAdd
	}
	
	protected void postEdit(EInvoiceDetailLainLain toBeSaved, EInvoiceDetailLainLain oldEntity) {
		// penjelasan untuk penggunaan mirip dengan postAdd
	}
	
	protected void preDelete(EInvoiceDetailLainLain entity) {
		// penjelasan untuk penggunaan mirip dengan preEdit
	}
	
	protected void postDelete(EInvoiceDetailLainLain entity) {
		// penjelasan untuk penggunaan mirip dengan postAdd
	}
	// ----
	
	/* mungkin berguna nanti waktu sudah diputuskan bagaimana mekanisme teknis otorisasinya	
	private void valAuthAdd() {
		if(!actionAuthorizationService.valHakAdd()) {
			error("NoRightToAdd");
		}
	}
   
	private void valAuthEdit() {
		if(!actionAuthorizationService.valHakEdit()) {
			error("NoRightToChange");
		}
	}
   
	private void valAuthDelete() {
		if(!actionAuthorizationService.valHakDelete()) {
			error("NoRightToDelete");
		}
	}
	*/
}
