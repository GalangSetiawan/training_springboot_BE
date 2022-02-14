package co.id.sofcograha.training.services;

import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.extendables.BaseService;
import co.id.sofcograha.base.utils.VersionUtil;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.MasterGenreEntity;
import co.id.sofcograha.training.pojos.MasterGenrePojo;
import co.id.sofcograha.training.repositories.MasterGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("masterGenreService")
public class MasterGenreService extends BaseService {
	
	@Autowired private MasterGenreRepository repo;
	
	public MasterGenreEntity findByBk(String namaGenre) {
		return repo.findByBK(namaGenre);
	}
	
	public MasterGenreEntity findById(final String id) {
		return repo.getOne(id);
	}

	public SearchResult<MasterGenreEntity> search(SearchParameter searchParameter) {
		return repo.search(searchParameter);
	}
    
	public MasterGenrePojo findByNama(String nama) {
		return MasterGenrePojo.fromEntity(repo.findByNama(nama));
	}
	
	@Transactional
    public MasterGenreEntity add(MasterGenrePojo pojo) {

		MasterGenreEntity entity = pojo.toEntity();
		
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

		MasterGenreEntity addedEntity = repo.add(entity);
		
		throwBatchError();
		return addedEntity;	
		
    }
       
	@Transactional
	public MasterGenreEntity edit(MasterGenrePojo pojo) {

		MasterGenreEntity entity = pojo.toEntity();

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

		MasterGenreEntity toBeSaved = repo.getOne(entity.getId());
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

		MasterGenreEntity toBeDeleted = repo.getOne(id);
		
		valDelete(toBeDeleted);
		throwBatchError();
		
		repo.delete(toBeDeleted.getId());
		
		throwBatchError();
	}
	
    protected void defineDefaultValuesOnAdd(MasterGenreEntity entity) {
//		if (entity.getFlakt() == null) entity.setFlakt(BaseConstants.YA);
		if (entity.getVersion() == null) entity.setVersion((long) 1);
	}
    
    protected void valRequiredValues(MasterGenreEntity entity) {
		valRequiredString(entity.getKodeGenre(), "master.genre.kodeGenre.required");
		valRequiredString(entity.getNamaGenre(), "master.genre.namaGenre.required");
	}
    
    protected void manageMinMaxValues(MasterGenreEntity entity) {
		valMaxString(entity.getKodeGenre(), 200, "master.genre.kodeGenre.max.length");
		valMaxString(entity.getNamaGenre(), 200, "master.genre.namaGenre.max.length");

	}
    
    protected void manageReferences(MasterGenreEntity entity) {
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

    protected void valUniquenessOnAdd(MasterGenreEntity addedEntity) {
		MasterGenreEntity entityFromDb = repo.findByBK(addedEntity.getKodeGenre());
		if (entityFromDb != null) {
			throw new BusinessException("customer.bk", addedEntity.getKodeGenre());
		}
	}
    
    private void valVersion(String id, Long version, String entityClassName) {
		valEntityExists(id, entityClassName);
		MasterGenreEntity dbEntity = repo.getOne(id);
		VersionUtil.check(version, dbEntity.getVersion());
	}
    
	private void valEntityExists(String id, String entityClassName) {
		if (repo.getOne(id) == null) {
			throw new BusinessException("data.not.found", entityClassName, id);
		}
	}
	
	protected void valUniquenessOnEdit(MasterGenreEntity editedEntity) {
		MasterGenreEntity entityFromDb = repo.findByBK(editedEntity.getKodeGenre());
		if (entityFromDb != null) {
			if (!editedEntity.getId().equals(entityFromDb.getId())) {
				throw new BusinessException("customer.bk", editedEntity.getKodeGenre());
			}
		}
	}
	
	protected void defineEditableValues(MasterGenreEntity newValues, MasterGenreEntity toBeSaved) {
		
		if (toBeSaved != null) {
			toBeSaved.setKodeGenre(newValues.getKodeGenre());
			toBeSaved.setNamaGenre(newValues.getNamaGenre());
			toBeSaved.setDiskonGenre(newValues.getDiskonGenre());

		}
		else if (toBeSaved == null) {
			defineDefaultValuesOnAdd(newValues);
		}
		
	}
	
	protected void valDelete(MasterGenreEntity toBeDeleted) {	}
    
    
	public MasterGenreEntity get(String id) {
		return repo.getOne(id);
	}
}
