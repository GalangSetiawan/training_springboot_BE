package co.id.sofcograha.training.services;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.extendables.BaseService;
import co.id.sofcograha.base.utils.VersionUtil;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.domain.invoicing.masters.customer.entities.ECustomerGajiId;
import co.id.sofcograha.domain.invoicing.masters.customer.pojos.CustomerGajiId;
import co.id.sofcograha.domain.invoicing.masters.customer.repositories.ECustomerRepository;
import co.id.sofcograha.training.entities.MasterBukuEntity;
import co.id.sofcograha.training.entities.MasterGenreEntity;
import co.id.sofcograha.training.entities.SaldoBukuEntity;
import co.id.sofcograha.training.pojos.MasterBukuPojo;
import co.id.sofcograha.training.repositories.MasterBukuRepository;
import co.id.sofcograha.training.repositories.MasterGenreRepository;
import co.id.sofcograha.training.repositories.SaldoBukuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("masterBukuService")
public class MasterBukuService extends BaseService {
	
	@Autowired private MasterBukuRepository repo;
	@Autowired private MasterGenreRepository repoGenre;
	@Autowired private SaldoBukuRepository repoSaldo;

	public MasterBukuEntity findByBk(String nama) {
		return repo.findByBK(nama);
	}
	
	public MasterBukuEntity findById(final String id) {
		return repo.getOne(id);
	}

	public SearchResult<MasterBukuEntity> search(SearchParameter searchParameter) {
		return repo.search(searchParameter);
	}
    
	public MasterBukuPojo findByNamaBuku(String namaBuku) {
		return MasterBukuPojo.fromEntity(repo.findByNamaBuku(namaBuku));
	}
	
	@Transactional
    public MasterBukuEntity add(MasterBukuPojo pojo) {

		MasterBukuEntity entity = pojo.toEntity();

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

		if(entity.getGenreBuku() != null){
			MasterGenreEntity masterGenre = repoGenre.getOne(entity.getGenreBuku().getId());
			entity.setGenreBuku(masterGenre);
		}

		MasterBukuEntity addedEntityBuku = repo.add(entity);

		//save saldo buku
		SaldoBukuEntity saldoBuku = new SaldoBukuEntity();
		saldoBuku.setDataBuku(addedEntityBuku);
		saldoBuku.setSaldoBuku(addedEntityBuku.getStockBuku());
		repoSaldo.add(saldoBuku);

		
		throwBatchError();
		return addedEntityBuku;
		
    }
       
	@Transactional
	public MasterBukuEntity edit(MasterBukuPojo pojo) {

		MasterBukuEntity entity = pojo.toEntity();

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

		MasterBukuEntity toBeSaved = repo.getOne(entity.getId());
		//ECustomer oldEntity = (ECustomer) toBeSaved.clone();
		
		defineEditableValues(entity, toBeSaved);
		
		throwBatchError();
		return toBeSaved;
		
	}

	@Transactional
	public void delete (String id, Long version) {
		
		valIdVersionRequired(id, version);
		valVersion(id, version, MasterBukuEntity.class.getSimpleName());
		throwBatchError();

		MasterBukuEntity toBeDeleted = repo.getOne(id);
		
		valDelete(toBeDeleted);
		throwBatchError();
		
		repo.delete(toBeDeleted.getId());
		
		throwBatchError();
	}
	
    protected void defineDefaultValuesOnAdd(MasterBukuEntity entity) {
		if (entity.getFlagActive() == null) entity.setFlagActive(BaseConstants.YA);
		if (entity.getVersion() == null) entity.setVersion((long) 1);
	}
    
    protected void valRequiredValues(MasterBukuEntity entity) {
		valRequiredString(entity.getNamaBuku(), "master.buku.nama.buku.required");
		valRequiredString(entity.getKodeBuku(), "master.buku.kode.buku.required");

	}
    
    protected void manageMinMaxValues(MasterBukuEntity entity) {
		valMaxString(entity.getKodeBuku(), 200, "master.buku.kode.buku.max.length");
		valMaxString(entity.getNamaBuku(), 200, "master.buku.nama.buku.max.length");
	}
    
    protected void manageReferences(MasterBukuEntity entity) {
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

    protected void valUniquenessOnAdd(MasterBukuEntity addedEntity) {
		MasterBukuEntity entityFromDb = repo.findByBK(addedEntity.getKodeBuku());
		if (entityFromDb != null) {
			throw new BusinessException("master.buku.bk", addedEntity.getKodeBuku());
		}
	}
    
    private void valVersion(String id, Long version, String entityClassName) {
		valEntityExists(id, entityClassName);
		MasterBukuEntity dbEntity = repo.getOne(id);
		VersionUtil.check(version, dbEntity.getVersion());
	}
    
	private void valEntityExists(String id, String entityClassName) {
		if (repo.getOne(id) == null) {
			throw new BusinessException("data.not.found", entityClassName, id);
		}
	}
	
	protected void valUniquenessOnEdit(MasterBukuEntity editedEntity) {
		MasterBukuEntity entityFromDb = repo.findByBK(editedEntity.getKodeBuku());
		if (entityFromDb != null) {
			if (!editedEntity.getId().equals(entityFromDb.getId())) {
				throw new BusinessException("customer.bk", editedEntity.getKodeBuku());
			}
		}
	}
	
	protected void defineEditableValues(MasterBukuEntity newValues, MasterBukuEntity toBeSaved) {
		
		if (toBeSaved != null) {

			toBeSaved.setNamaBuku(newValues.getNamaBuku());
			toBeSaved.setKodeBuku(newValues.getKodeBuku());
			toBeSaved.setHargaBuku(newValues.getHargaBuku());
			toBeSaved.setFlagActive(newValues.getFlagActive());
//			toBeSaved.setGenreBuku(newValues.getBillrole2());

		}
		else if (toBeSaved == null) {
			defineDefaultValuesOnAdd(newValues);
		}
		
	}
	
	protected void valDelete(MasterBukuEntity toBeDeleted) {	}
    
    
	public MasterBukuEntity get(String id) {
		return repo.getOne(id);
	}
}
