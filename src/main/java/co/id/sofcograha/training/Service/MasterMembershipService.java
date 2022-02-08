package co.id.sofcograha.training.Service;

import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.extendables.BaseService;
import co.id.sofcograha.base.utils.VersionUtil;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.Entity.MasterGenreEntity;
import co.id.sofcograha.training.Entity.MasterMembershipEntity;
import co.id.sofcograha.training.Repository.MasterGenreRepository;
import co.id.sofcograha.training.Repository.MasterMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("masterMembershipService")
public class MasterMembershipService extends BaseService {
	
	@Autowired private MasterMembershipRepository repo;
	
	public MasterMembershipEntity findByBk(String nama) {
		return repo.findByBK(nama);
	}
	
	public MasterMembershipEntity findById(final String id) {
		return repo.getOne(id);
	}

	public SearchResult<MasterMembershipEntity> search(SearchParameter searchParameter) {
		return repo.search(searchParameter);
	}
    
//	public MasterGenreEntity findByNama(String nama) {
//		return MasterGenreEntity.fromEntity(repo.findByNama(nama));
//	}
	
	@Transactional
    public MasterMembershipEntity add(MasterMembershipEntity entity) {
		
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

		MasterMembershipEntity addedEntity = repo.add(entity);
		
		throwBatchError();
		return addedEntity;	
		
    }
       
	@Transactional
	public MasterMembershipEntity edit(MasterMembershipEntity entity) {
		
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

		MasterMembershipEntity toBeSaved = repo.getOne(entity.getId());
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

		MasterMembershipEntity toBeDeleted = repo.getOne(id);
		
		valDelete(toBeDeleted);
		throwBatchError();
		
		repo.delete(toBeDeleted.getId());
		
		throwBatchError();
	}
	
    protected void defineDefaultValuesOnAdd(MasterMembershipEntity entity) {
//		if (entity.getFlakt() == null) entity.setFlakt(BaseConstants.YA);
		if (entity.getVersion() == null) entity.setVersion((long) 1);
	}
    
    protected void valRequiredValues(MasterMembershipEntity entity) {
		valRequiredString(entity.getKodeMembership(), "master.genre.kodeMembership.required");
		valRequiredString(entity.getNamaMembership(), "master.genre.namaMembership.required");
	}
    
    protected void manageMinMaxValues(MasterMembershipEntity entity) {
		valMaxString(entity.getKodeMembership(), 200, "master.genre.kodeMembership.max.length");
		valMaxString(entity.getNamaMembership(), 200, "master.genre.namaMembership.max.length");

	}
    
    protected void manageReferences(MasterMembershipEntity entity) {
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

    protected void valUniquenessOnAdd(MasterMembershipEntity addedEntity) {
		MasterMembershipEntity entityFromDb = repo.findByBK(addedEntity.getKodeMembership());
		if (entityFromDb != null) {
			throw new BusinessException("customer.bk", addedEntity.getKodeMembership());
		}
	}
    
    private void valVersion(String id, Long version, String entityClassName) {
		valEntityExists(id, entityClassName);
		MasterMembershipEntity dbEntity = repo.getOne(id);
		VersionUtil.check(version, dbEntity.getVersion());
	}
    
	private void valEntityExists(String id, String entityClassName) {
		if (repo.getOne(id) == null) {
			throw new BusinessException("data.not.found", entityClassName, id);
		}
	}
	
	protected void valUniquenessOnEdit(MasterMembershipEntity editedEntity) {
		MasterMembershipEntity entityFromDb = repo.findByBK(editedEntity.getKodeMembership());
		if (entityFromDb != null) {
			if (!editedEntity.getId().equals(entityFromDb.getId())) {
				throw new BusinessException("customer.bk", editedEntity.getKodeMembership());
			}
		}
	}
	
	protected void defineEditableValues(MasterMembershipEntity newValues, MasterMembershipEntity toBeSaved) {
		
		if (toBeSaved != null) {
			toBeSaved.setKodeMembership(newValues.getKodeMembership());
			toBeSaved.setNamaMembership(newValues.getNamaMembership());
		}
		else if (toBeSaved == null) {
			defineDefaultValuesOnAdd(newValues);
		}
		
	}
	
	protected void valDelete(MasterMembershipEntity toBeDeleted) {	}
    
    
	public MasterMembershipEntity get(String id) {
		return repo.getOne(id);
	}
}
