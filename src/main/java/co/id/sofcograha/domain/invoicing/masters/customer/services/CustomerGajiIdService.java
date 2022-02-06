package co.id.sofcograha.domain.invoicing.masters.customer.services;

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
import co.id.sofcograha.domain.invoicing.masters.customer.pojos.CustomerGajiId;
import co.id.sofcograha.domain.invoicing.masters.customer.repositories.ECustomerRepository;

@Service("customerGajiIdService")
public class CustomerGajiIdService extends BaseService {
	
	@Autowired private ECustomerRepository repo;
	
	public ECustomerGajiId findByBk(String nama) {
		return repo.findByBK(nama);
	}
	
	public ECustomerGajiId findById(final String id) {
		return repo.getOne(id);
	}

	public SearchResult<ECustomerGajiId> search(SearchParameter searchParameter) {
		return repo.search(searchParameter);
	}
    
	public CustomerGajiId findByNama(String nama) {
		return CustomerGajiId.fromEntity(repo.findByNama(nama));
	}
	
	@Transactional
    public ECustomerGajiId add(ECustomerGajiId entity) {
		
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
		
		ECustomerGajiId addedEntity = repo.add(entity);
		
		throwBatchError();
		return addedEntity;	
		
    }
       
	@Transactional
	public ECustomerGajiId edit(ECustomerGajiId entity) {
		
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
		
		ECustomerGajiId toBeSaved = repo.getOne(entity.getId());
		//ECustomer oldEntity = (ECustomer) toBeSaved.clone();
		
		defineEditableValues(entity, toBeSaved);
		
		throwBatchError();
		return toBeSaved;
		
	}

	@Transactional
	public void delete (String id, Long version) {
		
		valIdVersionRequired(id, version);
		valVersion(id, version, ECustomerGajiId.class.getSimpleName());
		throwBatchError();
		
		ECustomerGajiId toBeDeleted = repo.getOne(id);
		
		valDelete(toBeDeleted);
		throwBatchError();
		
		repo.delete(toBeDeleted.getId());
		
		throwBatchError();
	}
	
    protected void defineDefaultValuesOnAdd(ECustomerGajiId entity) {
		if (entity.getFlakt() == null) entity.setFlakt(BaseConstants.YA);
		if (entity.getVersion() == null) entity.setVersion((long) 1);
	}
    
    protected void valRequiredValues(ECustomerGajiId entity) {
		valRequiredString(entity.getNama(), "customer.nama.required");
		valRequiredString(entity.getPicnama(), "customer.picnama.required");
		valRequiredString(entity.getPicrole(), "customer.picrole.required");
		valRequiredString(entity.getPicalamat(), "customer.picalamat.required");
		valRequiredString(entity.getPicnumber(), "customer.picnumber.required");
		valRequiredString(entity.getPicemail(), "customer.picemail.required");
//		valRequiredString(entity.getBillnama(), "customer.billnama.required");
//		valRequiredString(entity.getBillrole(), "customer.billrole.required");
//		valRequiredString(entity.getBillalamat(), "customer.billalamat.required");
//		valRequiredString(entity.getBillnumber(), "customer.billnumber.required");
//		valRequiredString(entity.getBillemail(), "customer.billemail.required");
		valRequiredString(entity.getVabca(), "customer.vabca.required");
//		valRequiredString(entity.getBillcust2(), "customer.billcust2.required");
//		valRequiredString(entity.getBillnama2(), "customer.billnama2.required");
//		valRequiredString(entity.getBillrole2(), "customer.billrole2.required");
//		valRequiredString(entity.getBillalamat2(), "customer.billalamat2.required");
//		valRequiredString(entity.getBillnumber2(), "customer.billnumber2.required");
//		valRequiredString(entity.getBillemail2(), "customer.billemail2.required");
	}
    
    protected void manageMinMaxValues(ECustomerGajiId entity) {
		valMaxString(entity.getNama(), 200, "customer.nama.max.length");
		valMaxString(entity.getPicnama(), 200, "customer.picnama.max.length");
		valMaxString(entity.getPicrole(), 100, "customer.picrole.max.length");
		valMaxString(entity.getPicalamat(), 300, "customer.picalamat.max.length");
		valMaxString(entity.getPicnumber(), 100, "customer.picnumber.max.length");
		valMaxString(entity.getPicemail(), 100, "customer.picemail.max.length");
		valMaxString(entity.getBillnama(), 200, "customer.billnama.max.length");
		valMaxString(entity.getBillrole(), 100, "customer.billrole.max.length");
		valMaxString(entity.getBillalamat(), 300, "customer.billalamat.max.length");
		valMaxString(entity.getBillnumber(), 100, "customer.billnumber.max.length");
		valMaxString(entity.getBillemail(), 100, "customer.billemail.max.length");
		valMaxString(entity.getVabca(), 100, "customer.vabca.max.length");
		valMaxString(entity.getBillcust2(), 200, "customer.billcust2.max.length");
		valMaxString(entity.getBillnama2(), 200, "customer.billnama2.max.length");
		valMaxString(entity.getBillrole2(), 100, "customer.billrole2.max.length");
		valMaxString(entity.getBillalamat2(), 300, "customer.billalamat2.max.length");
		valMaxString(entity.getBillnumber2(), 100, "customer.billnumber2.max.length");
		valMaxString(entity.getBillemail2(), 100, "customer.billemail2.max.length");
	}
    
    protected void manageReferences(ECustomerGajiId entity) {
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

    protected void valUniquenessOnAdd(ECustomerGajiId addedEntity) {
    	ECustomerGajiId entityFromDb = repo.findByBK(addedEntity.getNama());
		if (entityFromDb != null) {
			throw new BusinessException("customer.bk", addedEntity.getNama());
		}
	}
    
    private void valVersion(String id, Long version, String entityClassName) {
		valEntityExists(id, entityClassName);
		ECustomerGajiId dbEntity = repo.getOne(id);
		VersionUtil.check(version, dbEntity.getVersion());
	}
    
	private void valEntityExists(String id, String entityClassName) {
		if (repo.getOne(id) == null) {
			throw new BusinessException("data.not.found", entityClassName, id);
		}
	}
	
	protected void valUniquenessOnEdit(ECustomerGajiId editedEntity) {
		ECustomerGajiId entityFromDb = repo.findByBK(editedEntity.getNama());
		if (entityFromDb != null) {
			if (!editedEntity.getId().equals(entityFromDb.getId())) {
				throw new BusinessException("customer.bk", editedEntity.getNama());
			}
		}
	}
	
	protected void defineEditableValues(ECustomerGajiId newValues, ECustomerGajiId toBeSaved) {
		
		if (toBeSaved != null) {
			toBeSaved.setNama(newValues.getNama());
			toBeSaved.setPicnama(newValues.getPicnama());
			toBeSaved.setPicrole(newValues.getPicrole());
			toBeSaved.setPicnumber(newValues.getPicnumber());
			toBeSaved.setPicemail(newValues.getPicemail());
			toBeSaved.setPicalamat(newValues.getPicalamat());
			toBeSaved.setBillnama(newValues.getBillnama());
			toBeSaved.setBillrole(newValues.getBillrole());
			toBeSaved.setBillnumber(newValues.getBillnumber());
			toBeSaved.setBillemail(newValues.getBillemail());
			toBeSaved.setBillalamat(newValues.getBillalamat());
			toBeSaved.setBillcust2(newValues.getBillcust2());
			toBeSaved.setBillnama2(newValues.getBillnama2());
			toBeSaved.setBillrole2(newValues.getBillrole2());
			toBeSaved.setBillnumber2(newValues.getBillnumber2());
			toBeSaved.setBillemail2(newValues.getBillemail2());
			toBeSaved.setBillalamat2(newValues.getBillalamat2());
			toBeSaved.setVabca(newValues.getVabca());
			toBeSaved.setFlakt(newValues.getFlakt());
			toBeSaved.setFlmainva(newValues.getFlmainva());
		}
		else if (toBeSaved == null) {
			defineDefaultValuesOnAdd(newValues);
		}
		
	}
	
	protected void valDelete(ECustomerGajiId toBeDeleted) {	}
    
    
	public ECustomerGajiId get(String id) {
		return repo.getOne(id);
	}
}
