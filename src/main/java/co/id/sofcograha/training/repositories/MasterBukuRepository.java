package co.id.sofcograha.training.repositories;

import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.MasterBukuEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class MasterBukuRepository extends SimpleJpaRepository<MasterBukuEntity, String> {

	protected final EntityManager em;

	public MasterBukuRepository(EntityManager em) {
		super(MasterBukuEntity.class, em);
		this.em = em;
	}
	
	public MasterBukuEntity findByBK(String kodeBuku) {
		
		String query = "SELECT e FROM MasterBukuEntity e " +
					   "WHERE e.kodeBuku = :kodeBuku";

		try {
			return em.createQuery(query, MasterBukuEntity.class)
					.setParameter("kodeBuku", kodeBuku)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
	
	public MasterBukuEntity getOne(String id) {
		return super.findOne(id);
	}
	
	public MasterBukuEntity findByNama(String namaBuku) {

		MasterBukuEntity entity;

		try {
			entity = em.createQuery("FROM MasterBukuEntity e " +
		                            "WHERE e.flagActive= :flagActive And " +
					                        "LOWER(e.namaBuku) = LOWER(:namaBuku)", MasterBukuEntity.class)
					.setParameter("flagActive", true)
					.setParameter("namaBuku", namaBuku)
					.getSingleResult();
		} catch (NoResultException e) {
			entity = null;
		}

		return entity;
	}
	
	public SearchResult<MasterBukuEntity> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<MasterBukuEntity> search = new HqlSimpleSearchBuilder<>(MasterBukuEntity.class, em);

		String kodeBuku = (String) searchParameter.getValueFromMappedParam("kodeBuku");
		String namaBuku = (String) searchParameter.getValueFromMappedParam("namaBuku");


		if (!QueryUtil.isAll(kodeBuku)) {
            search.addWhere("AND LOWER(kodeBuku) LIKE LOWER(:kodeBuku) ");
            search.addParameter("kodeBuku", QueryUtil.formatStringForLikeFilter(kodeBuku));
        }

		if (!QueryUtil.isAll(namaBuku)) {
            search.addWhere("AND LOWER(namaBuku) LIKE LOWER(:namaBuku) ");
            search.addParameter("namaBuku", QueryUtil.formatStringForLikeFilter(namaBuku));
        }


		search.setSort(searchParameter.getSort());
		search.setPaging(searchParameter.getPagination());

		return search.getSearchResult();
	}
	
	public MasterBukuEntity add(MasterBukuEntity entity) {
		return save(entity);
	}

	public MasterBukuEntity edit(MasterBukuEntity entity) {
		return save(entity);
	}
}