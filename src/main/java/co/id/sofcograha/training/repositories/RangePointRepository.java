package co.id.sofcograha.training.repositories;

import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.MasterJenisTransaksiEntity;
import co.id.sofcograha.training.entities.RangePointEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class RangePointRepository extends SimpleJpaRepository<RangePointEntity, String> {

	protected final EntityManager em;

	public RangePointRepository(EntityManager em) {
		super(RangePointEntity.class, em);
		this.em = em;
	}

	public MasterJenisTransaksiEntity findByBK(String namaTransaksi) {
		
		String query = "SELECT e FROM RangePointEntity e " +
					   "WHERE e.namaTransaksi = :namaTransaksi";

		try {
			return em.createQuery(query, MasterJenisTransaksiEntity.class)
					.setParameter("namaTransaksi", namaTransaksi)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
	
	public RangePointEntity getOne(String id) {
		return super.findOne(id);
	}
	
	public RangePointEntity findByNama(String nama) {

		RangePointEntity entity;

		try {
			entity = em.createQuery("FROM RangePointEntity e " +
		                            "WHERE LOWER(e.nama) = LOWER(:nama)", RangePointEntity.class)
//					.setParameter("flakt", BaseConstants.YA)
					.setParameter("nama", nama)
					.getSingleResult();
		} catch (NoResultException e) {
			entity = null;
		}

		return entity;
	}
	
	public SearchResult<RangePointEntity> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<RangePointEntity> search = new HqlSimpleSearchBuilder<>(RangePointEntity.class, em);

		String kodeMembership = (String) searchParameter.getValueFromMappedParam("kodeJenisTransaksi");
		String namaMembership = (String) searchParameter.getValueFromMappedParam("namaTransaksi");


		if (!QueryUtil.isAll(kodeMembership)) {
            search.addWhere("AND LOWER(kodeMembership) LIKE LOWER(:kodeMembership) ");
            search.addParameter("kodeMembership", QueryUtil.formatStringForLikeFilter(kodeMembership));
        }

		if (!QueryUtil.isAll(namaMembership)) {
            search.addWhere("AND LOWER(namaMembership) LIKE LOWER(:namaMembership) ");
            search.addParameter("namaMembership", QueryUtil.formatStringForLikeFilter(namaMembership));
        }



		search.setSort(searchParameter.getSort());
		search.setPaging(searchParameter.getPagination());

		return search.getSearchResult();
	}
	
	public RangePointEntity add(RangePointEntity entity) {
		return save(entity);
	}

	public RangePointEntity edit(RangePointEntity entity) {
		return save(entity);
	}
}