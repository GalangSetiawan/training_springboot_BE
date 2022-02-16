package co.id.sofcograha.training.repositories;

import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.TrxDetailPembayaran;
import co.id.sofcograha.training.entities.TrxHeaderEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class TrxDetailPembayaranRepository extends SimpleJpaRepository<TrxDetailPembayaran, String> {

	protected final EntityManager em;

	public TrxDetailPembayaranRepository(EntityManager em) {
		super(TrxDetailPembayaran.class, em);
		this.em = em;
	}
	
	public TrxDetailPembayaran findByBK(String id) {
		
		String query = "SELECT e FROM TrxDetailPembayaran e " +
					   "WHERE e.id = :id";

		try {
			return em.createQuery(query, TrxDetailPembayaran.class)
					.setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
	
	public TrxDetailPembayaran getOne(String id) {
		return super.findOne(id);
	}
	
	public TrxDetailPembayaran findByNomorBon(String nomorBon) {

		TrxDetailPembayaran entity;

		try {
			entity = em.createQuery("FROM TrxDetailPembayaran e " +
		                            "WHERE LOWER(e.nomorBon) = LOWER(:nomorBon)", TrxDetailPembayaran.class)
					.setParameter("nomorBon", nomorBon)
					.getSingleResult();
		} catch (NoResultException e) {
			entity = null;
		}

		return entity;
	}
	
	public SearchResult<TrxDetailPembayaran> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<TrxDetailPembayaran> search = new HqlSimpleSearchBuilder<>(TrxDetailPembayaran.class, em);

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
	
	public TrxDetailPembayaran add(TrxDetailPembayaran entity) {
		return save(entity);
	}

	public TrxDetailPembayaran edit(TrxDetailPembayaran entity) {
		return save(entity);
	}
}