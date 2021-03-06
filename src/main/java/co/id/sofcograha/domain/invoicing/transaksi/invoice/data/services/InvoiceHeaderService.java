package co.id.sofcograha.domain.invoicing.transaksi.invoice.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.extendables.BaseService;
import co.id.sofcograha.base.utils.VersionUtil;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.domain.invoicing.masters.customer.entities.ECustomerGajiId;
import co.id.sofcograha.domain.invoicing.masters.customer.services.CustomerGajiIdService;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities.EInvoiceHeader;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.pojos.InvoiceHeader;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.repositories.EInvoiceHeaderRepository;

@Service("invoiceHeaderService")
public class InvoiceHeaderService extends BaseService {
	
	@Autowired private EInvoiceHeaderRepository repo;
	@Autowired private CustomerGajiIdService customerGajiIdService;
	
	public InvoiceHeader findByBk(String nomor) {
		return InvoiceHeader.fromEntity(repo.findByBK(nomor));
	}
	
	public SearchResult<EInvoiceHeader> search(SearchParameter searchParameter) {
		return repo.search(searchParameter);
	}
	
	@Transactional
	public EInvoiceHeader add(EInvoiceHeader entity) {
		
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
		
		preAdd(entity);
		EInvoiceHeader addedEntity = repo.add(entity);
		postAdd(addedEntity);
			
		throwBatchError();
		return addedEntity;			
	}
	
	@Transactional
	public EInvoiceHeader edit(EInvoiceHeader entity) {
				
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
				
		EInvoiceHeader toBeSaved = repo.getOne(entity.getId());
		EInvoiceHeader oldEntity = (EInvoiceHeader) toBeSaved.clone();
		
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
		valVersion(id, version, EInvoiceHeader.class.getSimpleName());
		throwBatchError();
		
		EInvoiceHeader toBeDeleted = repo.getOne(id);
		
		valDelete(toBeDeleted);
		throwBatchError();
		
		preDelete(toBeDeleted);
		repo.delete(toBeDeleted.getId());
		postDelete(toBeDeleted);
		
		throwBatchError();
		
	}
	
	private void valVersion(String id, Long version, String entityClassName) {
		valEntityExists(id, entityClassName);
		EInvoiceHeader dbEntity = repo.getOne(id);
		VersionUtil.check(version, dbEntity.getVersion());
	}
	
	private void valEntityExists(String id, String entityClassName) {
		if (repo.getOne(id) == null) {
			throw new BusinessException("data.not.found", entityClassName, id);
		}
	}
	
	protected void defineDefaultValuesOnAdd(EInvoiceHeader entity) {
		if (entity.getVersion() == null) entity.setVersion((long) 1);
	}
	
	protected void defineEditableValues(EInvoiceHeader newValues, EInvoiceHeader toBeSaved) {
		
		if (toBeSaved != null) {
			toBeSaved.setNomor(newValues.getNomor());
			toBeSaved.setTgtrn(newValues.getTgtrn());
			toBeSaved.setNmcust(newValues.getNmcust());
			toBeSaved.setNama(newValues.getNama());
			toBeSaved.setAlamat(newValues.getAlamat());
			toBeSaved.setEmail(newValues.getEmail());
			toBeSaved.setStatus(newValues.getStatus());
			toBeSaved.setBruto(newValues.getBruto());
			toBeSaved.setTotdisc(newValues.getTotdisc());
			toBeSaved.setDpp(newValues.getDpp());
			toBeSaved.setPpn(newValues.getPpn());
			toBeSaved.setNetto(newValues.getNetto());
			toBeSaved.setDepused(newValues.getDepused());
			toBeSaved.setFltodep(newValues.getFltodep());
			toBeSaved.setNildep(newValues.getNildep());
			toBeSaved.setTgjtemp(newValues.getTgjtemp());
			toBeSaved.setFlasli(newValues.getFlasli());
			toBeSaved.setNotes(newValues.getNotes());
			toBeSaved.setPctdis(newValues.getPctdis());
			toBeSaved.setNildis(newValues.getNildis());
			toBeSaved.setKetdis(newValues.getKetdis());
			toBeSaved.setKetinv(newValues.getKetinv());
			toBeSaved.setNilbyr(newValues.getNilbyr());
		}
		else if (toBeSaved == null) {
			defineDefaultValuesOnAdd(newValues);
		}
		
	}
	
	protected void valRequiredValues(EInvoiceHeader entity) {
		valRequiredString(entity.getNomor(), "invoicemanual.header.nomor.required");
		valRequiredString(entity.getTgtrn(), "invoicemanual.header.tgtrn.required");
		valRequiredString(entity.getNmcust(), "invoicemanual.header.nmcust.required");
		valRequiredString(entity.getNama(), "invoicemanual.header.nama.required");
		valRequiredString(entity.getAlamat(), "invoicemanual.header.alamat.required");
		valRequiredString(entity.getEmail(), "invoicemanual.header.email.required");
		valRequiredString(entity.getStatus(), "invoicemanual.header.status.required");
		valRequiredString(entity.getFltodep(), "invoicemanual.header.fltodep.required");
		valRequiredDouble(entity.getBruto(), "invoicemanual.header.bruto.required");
		valRequiredDouble(entity.getTotdisc(), "invoicemanual.header.totdisc.required");
		valRequiredDouble(entity.getDpp(), "invoicemanual.header.dpp.required");
		valRequiredDouble(entity.getPpn(), "invoicemanual.header.ppn.required");
		valRequiredDouble(entity.getNetto(), "invoicemanual.header.netto.required");
		valRequiredDouble(entity.getDepused(), "invoicemanual.header.depused.required");
		valRequiredString(entity.getTgjtemp(), "invoicemanual.header.tgjtemp.required");
	}
	
	protected void manageMinMaxValues(EInvoiceHeader entity) {
		valMaxString(entity.getNomor(), 50, "invoicemanual.header.nomor.max.length");
		valMaxString(entity.getTgtrn(), 8, "invoicemanual.header.tgtrn.max.length");
		valMaxString(entity.getNmcust(), 200, "invoicemanual.header.nmcust.max.length");
		valMaxString(entity.getNama(), 200, "invoicemanual.header.nama.max.length");
		valMaxString(entity.getAlamat(), 300, "invoicemanual.header.alamat.max.length");
		valMaxString(entity.getEmail(), 100, "invoicemanual.header.email.max.length");
		valMaxString(entity.getStatus(), 10, "invoicemanual.header.status.max.length");
		valMaxString(entity.getFltodep(), 1, "invoicemanual.header.fltodep.max.length");
		
		valMinDouble(entity.getBruto(), -999999999D, "invoicemanual.header.bruto.min.length");
		valMaxDouble(entity.getBruto(), 999999999D, "invoicemanual.header.bruto.max.length");
		valMinDouble(entity.getTotdisc(), -999999999D, "invoicemanual.header.totdisc.min.length");
		valMaxDouble(entity.getTotdisc(), 999999999D, "invoicemanual.header.totdisc.max.length");
		valMinDouble(entity.getDpp(), -999999999D, "invoicemanual.header.dpp.min.length");
		valMaxDouble(entity.getDpp(), 999999999D, "invoicemanual.header.dpp.max.length");
		valMinDouble(entity.getPpn(), -999999999D, "invoicemanual.header.ppn.min.length");
		valMaxDouble(entity.getPpn(), 999999999D, "invoicemanual.header.ppn.max.length");
		valMinDouble(entity.getNetto(), -999999999D, "invoicemanual.header.netto.min.length");
		valMaxDouble(entity.getNetto(), 999999999D, "invoicemanual.header.netto.max.length");
		valMinDouble(entity.getDepused(), -999999999D, "invoicemanual.header.depused.min.length");
		valMaxDouble(entity.getDepused(), 999999999D, "invoicemanual.header.depused.max.length");
		
		if (entity.getFltodep().equals(BaseConstants.YA)) {
			valMinDouble(entity.getNildep(), -999999999D, "invoicemanual.header.nildep.min.length");
			valMaxDouble(entity.getNildep(), 999999999D, "invoicemanual.header.nildep.max.length");			
		}

		valMaxString(entity.getTgjtemp(), 8, "invoicemanual.header.tgjtemp.max.length");
		valMinDouble(entity.getPctdis(), 0D, "invoicemanual.header.pctdis.min.length");
		valMaxDouble(entity.getPctdis(), 999D, "invoicemanual.header.pctdis.max.length");
		valMinDouble(entity.getNildis(), 0D, "invoicemanual.header.nildis.min.length");
		valMaxDouble(entity.getNildis(), 999999999D, "invoicemanual.header.nildis.max.length");
	}

	protected void manageReferences(EInvoiceHeader entity) {
		if (entity.getCustomer() != null) {
			ECustomerGajiId eFromDb = customerGajiIdService.get(entity.getCustomer().getId());
			if (eFromDb != null) {
				if (eFromDb.getFlakt().equals(BaseConstants.YA)) {
					entity.setCustomer(eFromDb);
				} else {
					batchError("customerGajiId.not.active");
				}
			} else {
				batchError("customerGajiId.not.found");
			}			
		}		

	}

	protected void valDelete(EInvoiceHeader toBeDeleted) {	}
	
	protected void valUniquenessOnAdd(EInvoiceHeader addedEntity) {
		EInvoiceHeader entityFromDb = repo.findByBK(addedEntity.getNomor());
		if (entityFromDb != null) {
			throw new BusinessException("invoicemanual.header.bk");
		}
	}

	protected void valUniquenessOnEdit(EInvoiceHeader editedEntity) {
		EInvoiceHeader entityFromDb = repo.findByBK(editedEntity.getNomor());
		if (entityFromDb != null) {
			if (!editedEntity.getId().equals(entityFromDb.getId())) {
				throw new BusinessException("invoicemanual.header.bk");
			}
		}
	}
	
	protected void preAdd(EInvoiceHeader entity) {
		// salah satu contoh penggunaannya (di gaji.id) :
		// - set nomor urut item berdasar total data + 1 (TrxInputKaryawanAttachFileService,
		//   TrxInputKaryawanDetailPelatihanService)
		// - menambahkan thumbnail bagi foto di TrxInputKaryawanPhotoService, EmployeePhotoService
		// NB: terus terang bingung membedakan method ini dengan method defineDefaultValuesOnAdd
	}
	
	protected void postAdd(EInvoiceHeader entity) {
		// salah satu contoh penggunaannya (di gaji.id) :
		// - menandai transaksi ini sebagai transaksi yang terkonfirmasi
		// - mengeksekusi notifikasi (pada SysNotificationService)
		// - edit file admin untuk menandai slip gaji di release
		// - manambah saldo surat ketidakhadiran yang belum konfirmasi
		// - update absensi, edit jadwal kerja, jalankan sp utk proses verfikasi absensi (TrxOverrideJadwalKerjaDetailService)
		// boleh dibilang ini bisa digunakan untuk update saldo-saldo/sejarah/admin/konfirmasi
	}
	
	protected void preEdit(EInvoiceHeader toBeSaved, EInvoiceHeader oldEntity) {
		// penjelasan untuk penggunaan mirip dengan preAdd
	}
	
	protected void postEdit(EInvoiceHeader toBeSaved, EInvoiceHeader oldEntity) {
		// penjelasan untuk penggunaan mirip dengan postAdd
	}
	
	protected void preDelete(EInvoiceHeader entity) {
		// penjelasan untuk penggunaan mirip dengan preEdit
	}
	
	protected void postDelete(EInvoiceHeader entity) {
		// penjelasan untuk penggunaan mirip dengan postAdd
	}

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
	
	public EInvoiceHeader get(String id) {
		return repo.getOne(id);
	}
	
}
