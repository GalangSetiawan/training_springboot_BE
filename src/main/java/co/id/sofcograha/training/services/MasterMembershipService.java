package co.id.sofcograha.training.services;

import co.id.sofcograha.base.exceptions.BusinessException;
import co.id.sofcograha.base.extendables.BaseService;
import co.id.sofcograha.base.utils.VersionUtil;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.*;
import co.id.sofcograha.training.pojos.MasterMembershipPojo;
import co.id.sofcograha.training.repositories.MasterMembershipRepository;
import co.id.sofcograha.training.repositories.SaldoKasTitipanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("masterMembershipService")
public class MasterMembershipService extends BaseService {
	
	@Autowired private MasterMembershipRepository repo;
	@Autowired private SaldoKasTitipanRepository repoSaldoKasTitipan;
	
	public MasterMembershipEntity findByBK(String namaMembership) {
		return repo.findByBK(namaMembership);
	}

	public MasterMembershipEntity findByPoint(String namaMembership) {
		return repo.findByPoint(namaMembership);
	}
	
	public MasterMembershipEntity findById(final String id) {
		return repo.getOne(id);
	}

	public SearchResult<MasterMembershipEntity> search(SearchParameter searchParameter) {
		return repo.search(searchParameter);
	}
    
	public MasterMembershipPojo findByNamaMember(String namaMembership) {
		return MasterMembershipPojo.fromEntity(repo.findByNamaMember(namaMembership));
	}
	
	@Transactional
    public MasterMembershipEntity add(MasterMembershipPojo pojo) {

		MasterMembershipEntity entity = pojo.toEntity();

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

		//save saldo kas titipan
		SaldoKasTitipanEntity saldoKasTitipan = new SaldoKasTitipanEntity();
		saldoKasTitipan.setDataMembership(addedEntity);
		saldoKasTitipan.setNilaiPoint(0);
		saldoKasTitipan.setNilaiTitipan(0.0);
		repoSaldoKasTitipan.add(saldoKasTitipan);
		
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
		if (entity.getVersion() == null) entity.setVersion((long) 1);
	}
    
    protected void valRequiredValues(MasterMembershipEntity entity) {
		valRequiredString(entity.getKodeMembership(), "master.membership.kodeMembership.required");
		valRequiredString(entity.getNamaMembership(), "master.membership.namaMembership.required");
	}
    
    protected void manageMinMaxValues(MasterMembershipEntity entity) {
		valMaxString(entity.getKodeMembership(), 200, "master.membership.kodeMembership.max.length");
		valMaxString(entity.getNamaMembership(), 200, "master.membership.namaMembership.max.length");

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
			throw new BusinessException("master.membership.bk", addedEntity.getKodeMembership());
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
				throw new BusinessException("master.membership.bk", editedEntity.getKodeMembership());
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
