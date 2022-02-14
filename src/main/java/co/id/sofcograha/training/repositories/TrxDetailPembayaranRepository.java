package co.id.sofcograha.training.repositories;

import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.TrxHeaderEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class TrxDetailPembayaranRepository extends SimpleJpaRepository<TrxHeaderEntity, String> {

	protected final EntityManager em;

	public TrxDetailPembayaranRepository(EntityManager em) {
		super(TrxHeaderEntity.class, em);
		this.em = em;
	}
	
	public TrxHeaderEntity findByBK(String id) {
		
		String query = "SELECT e FROM TrxDetailPembayaran e " +
					   "WHERE e.id = :id";

		try {
			return em.createQuery(query, TrxHeaderEntity.class)
					.setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
	
	public TrxHeaderEntity getOne(String id) {
		return super.findOne(id);
	}
	
	public TrxHeaderEntity findByNomorBon(String nomorBon) {

		TrxHeaderEntity entity;

		try {
			entity = em.createQuery("FROM TrxHeaderEntity e " +
		                            "WHERE LOWER(e.nomorBon) = LOWER(:nomorBon)", TrxHeaderEntity.class)
					.setParameter("nomorBon", nomorBon)
					.getSingleResult();
		} catch (NoResultException e) {
			entity = null;
		}

		return entity;
	}
	
	public SearchResult<TrxHeaderEntity> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<TrxHeaderEntity> search = new HqlSimpleSearchBuilder<>(TrxHeaderEntity.class, em);

		String kodeGenre = (String) searchParameter.getValueFromMappedParam("kodeGenre");
		String namaGenre = (String) searchParameter.getValueFromMappedParam("namaGenre");


		if (!QueryUtil.isAll(kodeGenre)) {
            search.addWhere("AND LOWER(kodeGenre) LIKE LOWER(:kodeGenre) ");
            search.addParameter("kodeGenre", QueryUtil.formatStringForLikeFilter(kodeGenre));
        }

		if (!QueryUtil.isAll(namaGenre)) {
            search.addWhere("AND LOWER(namaGenre) LIKE LOWER(:namaGenre) ");
            search.addParameter("namaGenre", QueryUtil.formatStringForLikeFilter(namaGenre));
        }



		search.setSort(searchParameter.getSort());
		search.setPaging(searchParameter.getPagination());

		return search.getSearchResult();
	}
	
	public TrxHeaderEntity add(TrxHeaderEntity entity) {
		return save(entity);
	}

	public TrxHeaderEntity edit(TrxHeaderEntity entity) {
		return save(entity);
	}
}