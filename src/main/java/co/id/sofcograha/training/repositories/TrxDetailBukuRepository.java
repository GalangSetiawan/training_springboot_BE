package co.id.sofcograha.training.repositories;

import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.TrxDetailBukuEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

@Repository
public class TrxDetailBukuRepository extends SimpleJpaRepository<TrxDetailBukuEntity, String> {

	protected final EntityManager em;

	public TrxDetailBukuRepository(EntityManager em) {
		super(TrxDetailBukuEntity.class, em);
		this.em = em;
	}


	public TrxDetailBukuEntity findByBK(String nomorBon) {
		
		String query = "SELECT e FROM TrxDetailBukuEntity e " +
					   "WHERE e.nomorBon = :nomorBon";

		try {
			return em.createQuery(query, TrxDetailBukuEntity.class)
					.setParameter("nomorBon", nomorBon)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
	
	public TrxDetailBukuEntity getOne(String id) {
		return super.findOne(id);
	}
	
	public TrxDetailBukuEntity findByNomorBon(String nomorBon) {

		TrxDetailBukuEntity entity;

		try {
			entity = em.createQuery("FROM TrxDetailBukuEntity e " +
		                            "WHERE LOWER(e.nomorBon) = LOWER(:nomorBon)", TrxDetailBukuEntity.class)
					.setParameter("nomorBon", nomorBon)
					.getSingleResult();
		} catch (NoResultException e) {
			entity = null;
		}

		return entity;
	}
	
	public SearchResult<TrxDetailBukuEntity> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<TrxDetailBukuEntity> search = new HqlSimpleSearchBuilder<>(TrxDetailBukuEntity.class, em);

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
	
	public TrxDetailBukuEntity add(TrxDetailBukuEntity entity) {
		return save(entity);
	}

	public TrxDetailBukuEntity edit(TrxDetailBukuEntity entity) {
		return save(entity);
	}
}