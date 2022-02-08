package co.id.sofcograha.training.Repository;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.Entity.MasterJenisTransaksiEntity;
import co.id.sofcograha.training.Entity.MasterMembershipEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class MasterJenisTransaksiRepository extends SimpleJpaRepository<MasterJenisTransaksiEntity, String> {

	protected final EntityManager em;

	public MasterJenisTransaksiRepository(EntityManager em) {
		super(MasterJenisTransaksiEntity.class, em);
		this.em = em;
	}

	public MasterJenisTransaksiEntity findByBK(String namaTransaksi) {
		
		String query = "SELECT e FROM MasterJenisTransaksiEntity e " +
					   "WHERE e.namaTransaksi = :namaTransaksi";

		try {
			return em.createQuery(query, MasterJenisTransaksiEntity.class)
					.setParameter("namaTransaksi", namaTransaksi)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
	
	public MasterJenisTransaksiEntity getOne(String id) {
		return super.findOne(id);
	}
	
	public MasterJenisTransaksiEntity findByNama(String nama) {

		MasterJenisTransaksiEntity entity;

		try {
			entity = em.createQuery("FROM MasterJenisTransaksiEntity e " +
		                            "WHERE LOWER(e.nama) = LOWER(:nama)", MasterJenisTransaksiEntity.class)
//					.setParameter("flakt", BaseConstants.YA)
					.setParameter("nama", nama)
					.getSingleResult();
		} catch (NoResultException e) {
			entity = null;
		}

		return entity;
	}
	
	public SearchResult<MasterJenisTransaksiEntity> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<MasterJenisTransaksiEntity> search = new HqlSimpleSearchBuilder<>(MasterJenisTransaksiEntity.class, em);

		String kodeJenisTransaksi = (String) searchParameter.getValueFromMappedParam("kodeJenisTransaksi");
		String namaTransaksi = (String) searchParameter.getValueFromMappedParam("namaTransaksi");


		if (!QueryUtil.isAll(kodeJenisTransaksi)) {
            search.addWhere("AND LOWER(kodeJenisTransaksi) LIKE LOWER(:kodeJenisTransaksi) ");
            search.addParameter("kodeMembership", QueryUtil.formatStringForLikeFilter(kodeJenisTransaksi));
        }

		if (!QueryUtil.isAll(namaTransaksi)) {
            search.addWhere("AND LOWER(namaTransaksi) LIKE LOWER(:namaTransaksi) ");
            search.addParameter("namaTransaksi", QueryUtil.formatStringForLikeFilter(namaTransaksi));
        }



		search.setSort(searchParameter.getSort());
		search.setPaging(searchParameter.getPagination());

		return search.getSearchResult();
	}
	
	public MasterJenisTransaksiEntity add(MasterJenisTransaksiEntity entity) {
		return save(entity);
	}

	public MasterJenisTransaksiEntity edit(MasterJenisTransaksiEntity entity) {
		return save(entity);
	}
}