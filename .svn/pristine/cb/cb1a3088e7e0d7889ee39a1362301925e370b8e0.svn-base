package co.id.sofcograha.domain.invoicing.commonMasters.comboConstants.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.domain.invoicing.commonMasters.comboConstants.entities.EComboConstants;

@Repository
public class EComboConstantsRepository extends SimpleJpaRepository<EComboConstants, String> {

	protected final EntityManager em;

	public EComboConstantsRepository(EntityManager em) {
		super(EComboConstants.class, em);
		this.em = em;
	}
	
	public EComboConstants findByBK(String id, String rectyp, String reccode) {
		
		String query = "SELECT e FROM EComboConstants e " + 
                       "WHERE e.id = :id AND e.rectyp = :rectyp And e.reccode = :reccode";
	
		try {
			return em.createQuery(query, EComboConstants.class)
				 .setParameter("id", id)
				 .setParameter("rectyp", rectyp)
				 .setParameter("reccode", reccode)
				 .getSingleResult();
		
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	public List<EComboConstants> getByType(String rectyp) {
		List<EComboConstants> entities;

		String query = "FROM EComboConstants e " + 
                       "WHERE e.rectyp = :rectyp";
	  
		try {
			entities = em.createQuery(query, EComboConstants.class)
					.setParameter("rectyp", rectyp)
					.getResultList();
		} catch (NoResultException e) {
			entities = null;
		}

		return entities;
	}
	
	public SearchResult<EComboConstants> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<EComboConstants> search = new HqlSimpleSearchBuilder<>(EComboConstants.class, em);

		String reccode = (String) searchParameter.getValueFromMappedParam("reccode");
		String rectyp = (String) searchParameter.getValueFromMappedParam("rectyp");
		String rectxt = (String) searchParameter.getValueFromMappedParam("rectxt");

		if (reccode != null) {
			search.addWhere("AND reccode = :reccode ");
			search.addParameter("reccode", reccode);
		}

		if (rectyp != null) {
			search.addWhere("AND rectyp = :rectyp ");
			search.addParameter("rectyp", rectyp);
		}

		if (!QueryUtil.isAll(rectxt)) {
            search.addWhere("AND LOWER(rectxt) LIKE LOWER(:rectxt) ");
            search.addParameter("rectxt", QueryUtil.formatStringForLikeFilter(rectxt));
        }

		search.setSort(searchParameter.getSort());
		search.setPaging(searchParameter.getPagination());

		return search.getSearchResult();
	}
	
	public List<EComboConstants> getKomponenNoOtomatisExcludeCounter() {
		List<EComboConstants> entities;

		String query = "FROM EComboConstants e " + 
                       "WHERE e.rectyp = :rectyp And e.reccode <> :reccode";
	  
		try {
			entities = em.createQuery(query, EComboConstants.class)
					.setParameter("rectyp", BaseConstants.CC_NOOTO)
					.setParameter("reccode", BaseConstants.NOOTO_KONSTANTA)
					.getResultList();
		} catch (NoResultException e) {
			entities = null;
		}

		return entities;
	}
		
	public EComboConstants getOne(String id) {
		return super.findOne(id);
	}
}