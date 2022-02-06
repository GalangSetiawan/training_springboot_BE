package co.id.sofcograha.domain.invoicing.transaksi.invoice.invoiceManual.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.extendables.BaseService;
import co.id.sofcograha.base.utils.Message;
import co.id.sofcograha.base.utils.VersionUtil;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.base.utils.threadlocals.LocalErrors;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities.EInvoiceDetailLainLain;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities.EInvoiceHeader;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.pojos.InvoiceDetailLainLain;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.repositories.EInvoiceHeaderRepository;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.services.InvoiceDetailLainLainService;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.services.InvoiceHeaderService;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.invoiceManual.pojos.InvoiceManualComplete;

@Service("invoiceManualCompleteService")
public class InvoiceManualCompleteService extends BaseService {
	
	@Autowired private InvoiceHeaderService invoiceHeaderService;
	@Autowired private InvoiceDetailLainLainService invoiceDetailLainLainService;
	
	@Autowired private EInvoiceHeaderRepository eInvoiceHeaderRepository;
	
	private boolean isErrorDetail = false;
	private boolean isErrorSubDetail = false;
	
	public InvoiceManualComplete findByBk(String nomor) {
		return InvoiceManualComplete.setFromEntity(eInvoiceHeaderRepository.findByBK(nomor));
	}

	public SearchResult<EInvoiceHeader> search(SearchParameter searchParameter) {
		return eInvoiceHeaderRepository.search(searchParameter);
	}
	
	public EInvoiceHeader getInvoiceInitialTerakhirByTanggal(String idMi010, String idMi001, String tgtrn) {
		return eInvoiceHeaderRepository.getInvoiceInitialTerakhirByTanggal(idMi010, idMi001, tgtrn);
	}
	
	@Transactional
	public EInvoiceHeader add(InvoiceManualComplete pojo, String jenisTransaksi) {
		
		EInvoiceHeader entityHeader = pojo.getEntityHeader();
		
		valAdaData(entityHeader);
		valDetailNotEmpty(pojo.detailLainLain);
		throwBatchError();
		
		EInvoiceHeader entityAdded = invoiceHeaderService.add(entityHeader);
		throwBatchError();
		
		isErrorDetail = false;
		isErrorSubDetail = false;
		
		addDetailLainLain(entityAdded, pojo);
		
		if (isErrorDetail) {
			batchErrorWithData("invoicemanual.komplit.error.in.detail", pojo);
		}
		if (isErrorSubDetail) {
			batchErrorWithData("invoicemanual.komplit.error.in.subdetail", pojo);
		}		
		throwBatchError();
		
		return entityAdded;

	}
	
	private void valAdaData(EInvoiceHeader entity) {
		if (entity == null) {
			batchError("invoicemanual.komplit.no.data");
		}
	}
	
	private void valDetailNotEmpty(List<InvoiceDetailLainLain> detailLainLain) {
		
		if ((detailLainLain == null || detailLainLain.isEmpty()))  {
			
			batchError("invoicemanual.komplit.no.detail");
		}
	}
	
	private void valNomorNotEmpty(InvoiceManualComplete pojo) {

		if (pojo.header.nomor == null || pojo.header.nomor.trim().equals("")) {

			batchError("invoicemanual.komplit.edit.nomor.tidak.boleh.kosong");
		}
	}

	private void addDetailLainLain(EInvoiceHeader eHeader, InvoiceManualComplete pojo) {
		
		List<InvoiceDetailLainLain> details = pojo.detailLainLain;
		
		for (InvoiceDetailLainLain detail: details) {
			
			if (!detail.isSelect) {
				// hanya simpan untuk yang tidak dicentang hapus
				
				EInvoiceDetailLainLain entityDetail = detail.toEntity();
				
				entityDetail.setHeader(eHeader);
				
				entityDetail = invoiceDetailLainLainService.add(entityDetail);
				
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
						
						if (detail.errorMsg == null) {
							detail.errorMsg = new ArrayList<Message>();
						}
			
						detail.errorMsg.clear();
						detail.errorMsg.add(message);					
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
	public EInvoiceHeader edit(InvoiceManualComplete pojo) {
		
		EInvoiceHeader entityHeader = pojo.getEntityHeader();
		
		valAdaData(entityHeader);
		valDetailNotEmpty(pojo.detailLainLain);
		throwBatchError();
		
		valNomorNotEmpty(pojo);
		throwBatchError();
		
		EInvoiceHeader newHeader = invoiceHeaderService.get(entityHeader.getId());
		
		isErrorDetail = false;
		isErrorSubDetail = false;
		
		editDetailLainLain(newHeader, pojo);
		
		if (isErrorDetail) {
			batchErrorWithData("invoicemanual.komplit.error.in.detail", pojo);
		}
		if (isErrorSubDetail) {
			batchErrorWithData("invoicemanual.komplit.error.in.subdetail", pojo);
		}		
		
		throwBatchError();

		newHeader = invoiceHeaderService.edit(entityHeader);
		throwBatchError();
		
		return newHeader;
	}
	
	private void editDetailLainLain(EInvoiceHeader entityHeader, InvoiceManualComplete pojo) {
		
		List<InvoiceDetailLainLain> details = pojo.detailLainLain;
		for (InvoiceDetailLainLain detail: details) {
			
			if (detail.id != null && !detail.id.equals("")) {
				invoiceDetailLainLainService.delete(detail.id, detail.version);
			}
			
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
					
					if (detail.errorMsg == null) {
						detail.errorMsg = new ArrayList<Message>();
					}
		
					detail.errorMsg.add(message);					
				}
				
				// bersihkan error yang ada di LocalError
				removeBatchErrors();
			}
		}
		
		if (!isErrorDetail) {
			addDetailLainLain(entityHeader, pojo);
		}
		
	}
	
	@Transactional
	public void delete (String id, Long version) {
		
		valIdVersionRequired(id, version);
		valRootVersion(id, version);
		throwBatchError();
		
		EInvoiceHeader eTransaksiJurnalHeader = invoiceHeaderService.get(id);
				
		InvoiceManualComplete pojo = InvoiceManualComplete.setFromEntity(eTransaksiJurnalHeader);
		
		isErrorDetail = false;

		// delete detail lain lain
		for (InvoiceDetailLainLain detail: pojo.detailLainLain) {
			
			invoiceDetailLainLainService.delete(detail.id, detail.version);
			
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
					
					if (detail.errorMsg == null) {
						detail.errorMsg = new ArrayList<Message>();
					}
		
					detail.errorMsg.add(message);					
				}
				
				// bersihkan error yang ada di LocalError
				removeBatchErrors();
			}
		}

		// untuk error di grid
		if (isErrorDetail) {
			batchErrorWithData("invoicemanual.komplit.error.in.detail", pojo);
		}
		throwBatchError();		
		
		// delete trx header
		invoiceHeaderService.delete(id, version);
	}
	
	protected void valRootVersion(String id, Long version) {
		valEntityExists(id);
		EInvoiceHeader dbEntity = invoiceHeaderService.get(id);
		VersionUtil.check(version, dbEntity.getVersion());
	}
	
	private void valEntityExists(String id) {
		if (invoiceHeaderService.get(id) == null) {
			throw new BusinessException("data.not.found", id);
		}
	}
	
}
