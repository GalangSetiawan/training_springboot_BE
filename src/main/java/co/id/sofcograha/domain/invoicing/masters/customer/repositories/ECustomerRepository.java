package co.id.sofcograha.domain.invoicing.masters.customer.repositories;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.domain.invoicing.masters.customer.entities.ECustomerGajiId;

@Repository
public class ECustomerRepository extends SimpleJpaRepository<ECustomerGajiId, String> {

	protected final EntityManager em;

	public ECustomerRepository(EntityManager em) {
		super(ECustomerGajiId.class, em);
		this.em = em;
	}
	
	public ECustomerGajiId findByBK(String nama) {
		
		String query = "SELECT e FROM ECustomerGajiId e " + 
					   "WHERE e.nama = :nama";

		try {
			return em.createQuery(query, ECustomerGajiId.class)
					.setParameter("nama", nama)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
	
	public ECustomerGajiId getOne(String id) {
		return super.findOne(id);
	}
	
	public ECustomerGajiId findByNama(String nama) {

		ECustomerGajiId entity;

		try {
			entity = em.createQuery("FROM ECustomerGajiId e " + 
		                            "WHERE e.flakt = :flakt And " +
					                        "LOWER(e.nama) = LOWER(:nama)", ECustomerGajiId.class)
					.setParameter("flakt", BaseConstants.YA)
					.setParameter("nama", nama)
					.getSingleResult();
		} catch (NoResultException e) {
			entity = null;
		}

		return entity;
	}
	
	public SearchResult<ECustomerGajiId> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<ECustomerGajiId> search = new HqlSimpleSearchBuilder<>(ECustomerGajiId.class, em);

		String nama = (String) searchParameter.getValueFromMappedParam("nama");
		String picnama = (String) searchParameter.getValueFromMappedParam("picnama");
		String picalamat = (String) searchParameter.getValueFromMappedParam("picalamat");
		String picemail = (String) searchParameter.getValueFromMappedParam("picemail");
		String billnama = (String) searchParameter.getValueFromMappedParam("billnama");
		String billalamat = (String) searchParameter.getValueFromMappedParam("billalamat");
		String billemail = (String) searchParameter.getValueFromMappedParam("billemail");
		String billcust2 = (String) searchParameter.getValueFromMappedParam("billcust2");
		String billnama2 = (String) searchParameter.getValueFromMappedParam("billnama2");
		String flakt = (String) searchParameter.getValueFromMappedParam("flakt");

		if (!QueryUtil.isAll(nama)) {
            search.addWhere("AND LOWER(nama) LIKE LOWER(:nama) ");
            search.addParameter("nama", QueryUtil.formatStringForLikeFilter(nama));
        }

		if (!QueryUtil.isAll(picnama)) {
            search.addWhere("AND LOWER(picnama) LIKE LOWER(:picnama) ");
            search.addParameter("picnama", QueryUtil.formatStringForLikeFilter(picnama));
        }

		if (!QueryUtil.isAll(picalamat)) {
            search.addWhere("AND LOWER(picalamat) LIKE LOWER(:picalamat) ");
            search.addParameter("picalamat", QueryUtil.formatStringForLikeFilter(picalamat));
        }

		if (!QueryUtil.isAll(picemail)) {
            search.addWhere("AND LOWER(picemail) LIKE LOWER(:picemail) ");
            search.addParameter("picemail", QueryUtil.formatStringForLikeFilter(picemail));
        }

		if (!QueryUtil.isAll(billnama)) {
            search.addWhere("AND LOWER(billnama) LIKE LOWER(:billnama) ");
            search.addParameter("billnama", QueryUtil.formatStringForLikeFilter(billnama));
        }

		if (!QueryUtil.isAll(billalamat)) {
            search.addWhere("AND LOWER(billalamat) LIKE LOWER(:billalamat) ");
            search.addParameter("billalamat", QueryUtil.formatStringForLikeFilter(billalamat));
        }

		if (!QueryUtil.isAll(billemail)) {
            search.addWhere("AND LOWER(billemail) LIKE LOWER(:billemail) ");
            search.addParameter("billemail", QueryUtil.formatStringForLikeFilter(billemail));
        }

		if (!QueryUtil.isAll(billcust2)) {
            search.addWhere("AND LOWER(billcust2) LIKE LOWER(:billcust2) ");
            search.addParameter("billcust2", QueryUtil.formatStringForLikeFilter(billcust2));
        }

		if (!QueryUtil.isAll(billnama2)) {
            search.addWhere("AND LOWER(billnama2) LIKE LOWER(:billnama2) ");
            search.addParameter("billnama2", QueryUtil.formatStringForLikeFilter(billnama2));
        }

	    if (!QueryUtil.isAll(flakt)) {
	      search.addWhere("AND LOWER(flakt) LIKE LOWER(:flakt) ");
	      search.addParameter("flakt", QueryUtil.formatStringForLikeFilter(flakt));
	    }

		search.setSort(searchParameter.getSort());
		search.setPaging(searchParameter.getPagination());

		return search.getSearchResult();
	}
	
	public ECustomerGajiId add(ECustomerGajiId entity) {
		return save(entity);
	}

	public ECustomerGajiId edit(ECustomerGajiId entity) {
		return save(entity);
	}
}