package co.id.sofcograha.training.repositories;

import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.MasterBukuEntity;
import co.id.sofcograha.training.entities.SaldoBukuEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class SaldoBukuRepository extends SimpleJpaRepository<SaldoBukuEntity, String> {

	protected final EntityManager em;

	public SaldoBukuRepository(EntityManager em) {
		super(SaldoBukuEntity.class, em);
		this.em = em;
	}
	
	public SaldoBukuEntity findByBK(String id) {
		
		String query = "SELECT e FROM SaldoBukuEntity e " +
					   "WHERE e.id = :id";

		try {
			return em.createQuery(query, SaldoBukuEntity.class)
					.setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
	
	public SaldoBukuEntity getOne(String id) {
		return super.findOne(id);
	}

	public SaldoBukuEntity findById(String id) {

		String query = "SELECT e FROM SaldoBukuEntity e " +
				"WHERE e.id = :id";

		try {
			return em.createQuery(query, SaldoBukuEntity.class)
					.setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	public SaldoBukuEntity findByDataBuku(MasterBukuEntity dataBuku) {

		String query = "SELECT e FROM SaldoBukuEntity e " +
				"WHERE e.dataBuku = :dataBuku";
		try {
			return em.createQuery(query, SaldoBukuEntity.class)
					.setParameter("dataBuku", dataBuku)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
	
	public SearchResult<SaldoBukuEntity> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<SaldoBukuEntity> search = new HqlSimpleSearchBuilder<>(SaldoBukuEntity.class, em);

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
	
	public SaldoBukuEntity add(SaldoBukuEntity entity) {
		return save(entity);
	}

	public SaldoBukuEntity edit(SaldoBukuEntity entity) {
		return save(entity);
	}
}