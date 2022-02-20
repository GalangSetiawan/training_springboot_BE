package co.id.sofcograha.training.services;

import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.extendables.BaseService;
import co.id.sofcograha.base.utils.VersionUtil;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.MasterBukuEntity;
import co.id.sofcograha.training.entities.MasterGenreEntity;
import co.id.sofcograha.training.entities.TrxDetailPembayaran;
import co.id.sofcograha.training.entities.TrxDetailPembelianBukuEntity;
import co.id.sofcograha.training.pojos.TrxDetailPembelianBukuPojo;
import co.id.sofcograha.training.repositories.MasterBukuRepository;
import co.id.sofcograha.training.repositories.TrxDetailPembelianBukuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("trxDetailPembelianBukuService")
public class TrxDetailPembelianBukuService extends BaseService {

	@Autowired private TrxDetailPembelianBukuRepository repo;
	@Autowired private MasterBukuRepository masterBukuRepository;

	public TrxDetailPembelianBukuEntity findByBk(String nomorTrxHeader) {
		return repo.findByBK(nomorTrxHeader);
	}

	public TrxDetailPembelianBukuEntity findById(final String id) {
		return repo.getOne(id);
	}

	public SearchResult<TrxDetailPembelianBukuEntity> search(SearchParameter searchParameter) {
		return repo.search(searchParameter);
	}

//	public TrxDetailPembelianBukuEntityPojo findByNomorBon(String nomorBon) {
//		return TrxDetailPembelianBukuEntityPojo.fromEntity(repo.findByNomorBon(nomorBon));
//	}


	@Transactional
    public TrxDetailPembelianBukuEntity add(TrxDetailPembelianBukuPojo pojo) {

		TrxDetailPembelianBukuEntity entity = pojo.toEntity();

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

		//TrxDetailPembelianBukuEntity addedEntity = repo.add(entity);

		throwBatchError();
		return entity;
		
    }
       
	@Transactional
	public TrxDetailPembelianBukuEntity edit(TrxDetailPembelianBukuEntity entity) {
		
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

		TrxDetailPembelianBukuEntity toBeSaved = repo.getOne(entity.getId());
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

		TrxDetailPembelianBukuEntity toBeDeleted = repo.getOne(id);
		
		valDelete(toBeDeleted);
		throwBatchError();
		
		repo.delete(toBeDeleted.getId());
		
		throwBatchError();
	}
	
    protected void defineDefaultValuesOnAdd(TrxDetailPembelianBukuEntity entity) {
//		if (entity.getFlakt() == null) entity.setFlakt(BaseConstants.YA);
		if (entity.getVersion() == null) entity.setVersion((long) 1);
	}
    
    protected void valRequiredValues(TrxDetailPembelianBukuEntity entity) {
		valRequiredInteger(entity.getQty(), "trx.pembelian.buku.qty.required");
	}
    
    protected void manageMinMaxValues(TrxDetailPembelianBukuEntity entity) {
		valMaxInteger(entity.getQty(), 200, "trx.pembelian.buku.qty.max.length");
//		valMaxString(entity.getNamaMembership(), 200, "trx.header.namaMembership.max.length");

	}
    
    protected void manageReferences(TrxDetailPembelianBukuEntity entity) {

		if(entity.getDataBuku() == null){
			batchError("Data buku tidak ditemukan");
		}else{

			MasterBukuEntity dataBuku = masterBukuRepository.getOne(entity.getDataBuku().getId());
			entity.setDataBuku(dataBuku);

		}

	}

    protected void valUniquenessOnAdd(TrxDetailPembelianBukuEntity addedEntity) {
		TrxDetailPembelianBukuEntity entityFromDb = repo.findByBK(addedEntity.getId());
		if (entityFromDb != null) {
			throw new BusinessException("master.membership.bk", addedEntity.getId());
		}
	}
    
    private void valVersion(String id, Long version, String entityClassName) {
		valEntityExists(id, entityClassName);
		TrxDetailPembelianBukuEntity dbEntity = repo.getOne(id);
		VersionUtil.check(version, dbEntity.getVersion());
	}
    
	private void valEntityExists(String id, String entityClassName) {
		if (repo.getOne(id) == null) {
			throw new BusinessException("data.not.found", entityClassName, id);
		}
	}
	
	protected void valUniquenessOnEdit(TrxDetailPembelianBukuEntity editedEntity) {
		TrxDetailPembelianBukuEntity entityFromDb = repo.findByBK(editedEntity.getId());
		if (entityFromDb != null) {
			if (!editedEntity.getId().equals(entityFromDb.getId())) {
				throw new BusinessException("trx.pembelian.buku.bk", editedEntity.getId());
			}
		}
	}
	
	protected void defineEditableValues(TrxDetailPembelianBukuEntity newValues, TrxDetailPembelianBukuEntity toBeSaved) {
		
		if (toBeSaved != null) {
			toBeSaved.setQty(newValues.getQty());
			toBeSaved.setTotalHarga(newValues.getTotalHarga());
			toBeSaved.setPersenDiscBuku(newValues.getPersenDiscBuku());

		}
		else if (toBeSaved == null) {
			defineDefaultValuesOnAdd(newValues);
		}

	}
	
	protected void valDelete(TrxDetailPembelianBukuEntity toBeDeleted) {	}

	public TrxDetailPembelianBukuEntity get(String id) {
		return repo.getOne(id);
	}

}
