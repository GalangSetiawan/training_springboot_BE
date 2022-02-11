package co.id.sofcograha.training.repositories;

import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.MasterGenreEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class MasterGenreRepository extends SimpleJpaRepository<MasterGenreEntity, String> {

	protected final EntityManager em;

	public MasterGenreRepository(EntityManager em) {
		super(MasterGenreEntity.class, em);
		this.em = em;
	}
	
	public MasterGenreEntity findByBK(String nama) {
		
		String query = "SELECT e FROM MasterGenreEntity e " +
					   "WHERE e.nama = :nama";

		try {
			return em.createQuery(query, MasterGenreEntity.class)
					.setParameter("nama", nama)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
	
	public MasterGenreEntity getOne(String id) {
		return super.findOne(id);
	}
	
	public MasterGenreEntity findByNama(String nama) {

		MasterGenreEntity entity;

		try {
			entity = em.createQuery("FROM MasterGenreEntity e " +
		                            "WHERE LOWER(e.nama) = LOWER(:nama)", MasterGenreEntity.class)
					.setParameter("nama", nama)
					.getSingleResult();
		} catch (NoResultException e) {
			entity = null;
		}

		return entity;
	}
	
	public SearchResult<MasterGenreEntity> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<MasterGenreEntity> search = new HqlSimpleSearchBuilder<>(MasterGenreEntity.class, em);

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
	
	public MasterGenreEntity add(MasterGenreEntity entity) {
		return save(entity);
	}

	public MasterGenreEntity edit(MasterGenreEntity entity) {
		return save(entity);
	}
}