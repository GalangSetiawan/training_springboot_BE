package co.id.sofcograha.training.repositories;

import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.MasterMembershipEntity;
import co.id.sofcograha.training.entities.SaldoBukuEntity;
import co.id.sofcograha.training.entities.SaldoKasTitipanEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class SaldoKasTitipanRepository extends SimpleJpaRepository<SaldoKasTitipanEntity, String> {

	protected final EntityManager em;

	public SaldoKasTitipanRepository(EntityManager em) {
		super(SaldoKasTitipanEntity.class, em);
		this.em = em;
	}
	
	public SaldoKasTitipanEntity findByBK(String id) {
		
		String query = "SELECT e FROM SaldoKasTitipanEntity e " +
					   "WHERE e.id = :id";

		try {
			return em.createQuery(query, SaldoKasTitipanEntity.class)
					.setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
	
	public SaldoKasTitipanEntity getOne(String id) {
		return super.findOne(id);
	}

	public SaldoKasTitipanEntity findByIdMember(MasterMembershipEntity idMember) {

		SaldoKasTitipanEntity entity;

		try {
			entity = em.createQuery("FROM SaldoKasTitipanEntity e " +
		                            "WHERE e.dataMembership = :idMember ", SaldoKasTitipanEntity.class)
					.setParameter("idMember", idMember)
					.getSingleResult();
		} catch (NoResultException e) {
			entity = null;
		}

		return entity;
	}
	
	public SearchResult<SaldoKasTitipanEntity> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<SaldoKasTitipanEntity> search = new HqlSimpleSearchBuilder<>(SaldoKasTitipanEntity.class, em);

		String id = (String) searchParameter.getValueFromMappedParam("id");
		String idMember = (String) searchParameter.getValueFromMappedParam("idMember");


		if (!QueryUtil.isAll(id)) {
            search.addWhere("AND LOWER(id) LIKE LOWER(:id) ");
            search.addParameter("id", QueryUtil.formatStringForLikeFilter(id));
        }

		if (!QueryUtil.isAll(idMember)) {
            search.addWhere("AND LOWER(idMember) LIKE LOWER(:idMember) ");
            search.addParameter("idMember", QueryUtil.formatStringForLikeFilter(idMember));
        }


		search.setSort(searchParameter.getSort());
		search.setPaging(searchParameter.getPagination());

		return search.getSearchResult();
	}
	
	public SaldoKasTitipanEntity add(SaldoKasTitipanEntity entity) {
		return save(entity);
	}

	public SaldoKasTitipanEntity edit(SaldoKasTitipanEntity entity) {
		return save(entity);
	}
}