package co.id.sofcograha.training.repositories;

import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.TrxDetailPembelianBukuEntity;
import co.id.sofcograha.training.entities.TrxHeaderEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

@Repository
public class TrxDetailPembelianBukuRepository extends SimpleJpaRepository<TrxDetailPembelianBukuEntity, String> {

	protected final EntityManager em;

	public TrxDetailPembelianBukuRepository(EntityManager em) {
		super(TrxDetailPembelianBukuEntity.class, em);
		this.em = em;
	}




	public List<TrxDetailPembelianBukuEntity> findByIdHeader(TrxHeaderEntity trxHeaderEntity){
		String query = "SELECT e FROM TrxDetailPembelianBukuEntity e " +
				"WHERE e.dataHeader = :trxHeaderEntity"  ;

		try {
			return em.createQuery(query, TrxDetailPembelianBukuEntity.class)
					.setParameter("trxHeaderEntity", trxHeaderEntity)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public TrxDetailPembelianBukuEntity findByBK(String id) {
		
		String query = "SELECT e FROM TrxDetailPembelianBukuEntity e " +
					   "WHERE e.id = :id";

		try {
			return em.createQuery(query, TrxDetailPembelianBukuEntity.class)
					.setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
	
	public TrxDetailPembelianBukuEntity getOne(String id) {
		return super.findOne(id);
	}
	
	public TrxDetailPembelianBukuEntity findByNomorBon(String nomorBon) {

		TrxDetailPembelianBukuEntity entity;

		try {
			entity = em.createQuery("FROM TrxDetailPembelianBukuEntity e " +
		                            "WHERE LOWER(e.nomorBon) = LOWER(:nomorBon)", TrxDetailPembelianBukuEntity.class)
					.setParameter("nomorBon", nomorBon)
					.getSingleResult();
		} catch (NoResultException e) {
			entity = null;
		}

		return entity;
	}
	
	public SearchResult<TrxDetailPembelianBukuEntity> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<TrxDetailPembelianBukuEntity> search = new HqlSimpleSearchBuilder<>(TrxDetailPembelianBukuEntity.class, em);

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
	
	public TrxDetailPembelianBukuEntity add(TrxDetailPembelianBukuEntity entity) {
		return save(entity);
	}

	public TrxDetailPembelianBukuEntity edit(TrxDetailPembelianBukuEntity entity) {
		return save(entity);
	}
}