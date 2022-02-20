package co.id.sofcograha.training.services;

import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.extendables.BaseService;
import co.id.sofcograha.base.utils.VersionUtil;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.MasterGenreEntity;
import co.id.sofcograha.training.entities.TrxDetailPembayaran;
import co.id.sofcograha.training.entities.TrxHeaderEntity;
import co.id.sofcograha.training.pojos.TrxDetailPembayaranPojo;
import co.id.sofcograha.training.pojos.TrxHeaderPojo;
import co.id.sofcograha.training.repositories.TrxDetailPembayaranRepository;
import co.id.sofcograha.training.repositories.TrxHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("trxDetailPembayaranService")
public class TrxDetailPembayaranService extends BaseService {

	@Autowired private TrxDetailPembayaranRepository repo;

	public TrxDetailPembayaran findByBk(String nomorTrxHeader) {
		return repo.findByBK(nomorTrxHeader);
	}

	public TrxDetailPembayaran findById(final String id) {
		return repo.getOne(id);
	}

	public SearchResult<TrxDetailPembayaran> search(SearchParameter searchParameter) {
		return repo.search(searchParameter);
	}

//	public TrxDetailPembayaranPojo findByNomorBon(String nomorBon) {
//		return TrxDetailPembayaranPojo.fromEntity(repo.findByNomorBon(nomorBon));
//	}


	@Transactional
    public TrxDetailPembayaran add(TrxDetailPembayaranPojo pojo) {

		TrxDetailPembayaran entity = pojo.toEntity();

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

		TrxDetailPembayaran addedEntity = repo.add(entity);

		throwBatchError();
		return addedEntity;	
		
    }
       
	@Transactional
	public TrxDetailPembayaran edit(TrxDetailPembayaran entity) {
		
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

		TrxDetailPembayaran toBeSaved = repo.getOne(entity.getId());
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

		TrxDetailPembayaran toBeDeleted = repo.getOne(id);
		
		valDelete(toBeDeleted);
		throwBatchError();
		
		repo.delete(toBeDeleted.getId());
		
		throwBatchError();
	}
	
    protected void defineDefaultValuesOnAdd(TrxDetailPembayaran entity) {
//		if (entity.getFlakt() == null) entity.setFlakt(BaseConstants.YA);
		if (entity.getVersion() == null) entity.setVersion((long) 1);
	}
    
    protected void valRequiredValues(TrxDetailPembayaran entity) {
		valRequiredString(entity.getJenisPembayaran(), "trx.header.namaPembeli.required");
	}
    
    protected void manageMinMaxValues(TrxDetailPembayaran entity) {
		valMaxString(entity.getJenisPembayaran(), 200, "trx.header.kodeMembership.max.length");
//		valMaxString(entity.getNamaMembership(), 200, "trx.header.namaMembership.max.length");

	}
    
    protected void manageReferences(TrxDetailPembayaran entity) {
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

    protected void valUniquenessOnAdd(TrxDetailPembayaran addedEntity) {
		TrxDetailPembayaran entityFromDb = repo.findByBK(addedEntity.getId());
		if (entityFromDb != null) {
			throw new BusinessException("master.membership.bk", addedEntity.getId());
		}
	}
    
    private void valVersion(String id, Long version, String entityClassName) {
		valEntityExists(id, entityClassName);
		TrxDetailPembayaran dbEntity = repo.getOne(id);
		VersionUtil.check(version, dbEntity.getVersion());
	}
    
	private void valEntityExists(String id, String entityClassName) {
		if (repo.getOne(id) == null) {
			throw new BusinessException("data.not.found", entityClassName, id);
		}
	}
	
	protected void valUniquenessOnEdit(TrxDetailPembayaran editedEntity) {
		TrxDetailPembayaran entityFromDb = repo.findByBK(editedEntity.getId());
		if (entityFromDb != null) {
			if (!editedEntity.getId().equals(entityFromDb.getId())) {
				throw new BusinessException("master.membership.bk", editedEntity.getId());
			}
		}
	}
	
	protected void defineEditableValues(TrxDetailPembayaran newValues, TrxDetailPembayaran toBeSaved) {
		
		if (toBeSaved != null) {
			toBeSaved.setJenisPembayaran(newValues.getJenisPembayaran());
			toBeSaved.setJumlahPoint(newValues.getJumlahPoint());
			toBeSaved.setNilaiRupiah(newValues.getNilaiRupiah());

		}
		else if (toBeSaved == null) {
			defineDefaultValuesOnAdd(newValues);
		}

	}
	
	protected void valDelete(TrxDetailPembayaran toBeDeleted) {	}

	public TrxDetailPembayaran get(String id) {
		return repo.getOne(id);
	}

}
