package co.id.sofcograha.training.repositories;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities.EInvoiceHeader;
import co.id.sofcograha.training.entities.MasterMembershipEntity;
import co.id.sofcograha.training.entities.MembershipGetSaldoKasEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class MasterMembershipRepository extends SimpleJpaRepository<MasterMembershipEntity, String> {

	protected final EntityManager em;

	public MasterMembershipRepository(EntityManager em) {
		super(MasterMembershipEntity.class, em);
		this.em = em;
	}


	public MasterMembershipEntity findByBK(String namaMembership) {
		
		String query = "SELECT e FROM MasterMembershipEntity e " +
					   "WHERE e.namaMembership = :namaMembership";

		try {
			return em.createQuery(query, MasterMembershipEntity.class)
					.setParameter("namaMembership", namaMembership)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	public MembershipGetSaldoKasEntity findByPoint(String namaMembership) {

		Query query;

		StringBuilder queryStringBuilder = new StringBuilder();

		queryStringBuilder.append("SELECT B.id, A.kode_member, A.nama_member, B.nilai_titipan, B.nilai_point, A.version " +
				"FROM tbl_membership A " +
				"JOIN tbl_saldo_kas_titipan B ON B.id_member = A.id " +
				"WHERE A.nama_member = :namaMembership");

		query = em.createNativeQuery(queryStringBuilder.toString(), MembershipGetSaldoKasEntity.class);

		query.setParameter("namaMembership", namaMembership);
		List<MembershipGetSaldoKasEntity> list = query.getResultList();

		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}

	}
	
	public MasterMembershipEntity getOne(String id) {
		return super.findOne(id);
	}
	
	public MasterMembershipEntity findByNamaMember(String namaMembership) {

		MasterMembershipEntity entity;

		try {
			entity = em.createQuery("FROM MasterMembershipEntity e " +
		                            "WHERE LOWER(e.namaMembership) = LOWER(:namaMembership)", MasterMembershipEntity.class)
					.setParameter(namaMembership, namaMembership)
					.getSingleResult();
		} catch (NoResultException e) {
			entity = null;
		}

		return entity;
	}
	
	public SearchResult<MasterMembershipEntity> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<MasterMembershipEntity> search = new HqlSimpleSearchBuilder<>(MasterMembershipEntity.class, em);

		String kodeMembership = (String) searchParameter.getValueFromMappedParam("kodeMembership");
		String namaMembership = (String) searchParameter.getValueFromMappedParam("namaMembership");


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
	
	public MasterMembershipEntity add(MasterMembershipEntity entity) {
		return save(entity);
	}

	public MasterMembershipEntity edit(MasterMembershipEntity entity) {
		return save(entity);
	}
}