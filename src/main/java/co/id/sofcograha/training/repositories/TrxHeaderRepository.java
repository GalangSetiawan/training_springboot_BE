package co.id.sofcograha.training.repositories;

import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.*;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class TrxHeaderRepository extends SimpleJpaRepository<TrxHeaderEntity, String> {

	protected final EntityManager em;

	public TrxHeaderRepository(EntityManager em) {
		super(TrxHeaderEntity.class, em);
		this.em = em;
	}

	public List<TrxHeaderEntity> get5DataPertamaByTanggalTrx(Date tanggalBon){
		String query = "SELECT e FROM TrxHeaderEntity e " +
				"WHERE e.tanggalBon = :tanggalBon " +
				"AND e.dataMembership is not null "+
				"ORDER BY e.tanggalBon DESC " ;

		try {
			return em.createQuery(query, TrxHeaderEntity.class)
					.setParameter("tanggalBon", tanggalBon)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public TrxHeaderEntity findByBK(String nomorBon) {
		
		String query = "SELECT e FROM TrxHeaderEntity e " +
					   "WHERE e.nomorBon = :nomorBon";

		try {
			return em.createQuery(query, TrxHeaderEntity.class)
					.setParameter("nomorBon", nomorBon)
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

		String nomorBon = (String) searchParameter.getValueFromMappedParam("nomorBon");
		String namaPembeli = (String) searchParameter.getValueFromMappedParam("namaPembeli");
		Date tanggalAwal = (Date) searchParameter.getValueFromMappedParam("tanggalAwal");
		Date tanggalAkhir = (Date) searchParameter.getValueFromMappedParam("tanggalAkhir");

		if (!QueryUtil.isAll(nomorBon)) {
            search.addWhere("AND LOWER(nomorBon) LIKE LOWER(:nomorBon) ");
            search.addParameter("nomorBon", QueryUtil.formatStringForLikeFilter(nomorBon));
        }

		if (!QueryUtil.isAll(namaPembeli)) {
            search.addWhere("AND LOWER(namaPembeli) LIKE LOWER(:namaPembeli) ");
            search.addParameter("namaPembeli", QueryUtil.formatStringForLikeFilter(namaPembeli));
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

	public TrxHeaderEntity getLastData() {

		Query query;

		StringBuilder queryStringBuilder = new StringBuilder();
		queryStringBuilder.append("SELECT 	* \n");
		queryStringBuilder.append("FROM 	tbl_galang_trx_header \n");
		queryStringBuilder.append("order by	nomor_bon desc \n");
		queryStringBuilder.append("limit 1 \n");

		query = em.createNativeQuery(queryStringBuilder.toString(), TrxHeaderEntity.class);

		List<TrxHeaderEntity> list = query.getResultList();

		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	public List<TrxPembelianBukuLaporanEntity> getLaporanPenjualan() {

		Query query;

		StringBuilder queryStringBuilder = new StringBuilder();
		queryStringBuilder.append("select 	public.sys_guid() as id, EXTRACT(MONTH FROM A.tanggal_bon ) as bulan, sum(B.qty) as total_qty_penjualan, sum(B.total_harga) as total_nominal_penjualan \n");
		queryStringBuilder.append("from 	tbl_galang_trx_header A \n");
		queryStringBuilder.append("left join tbl_trx_detail_pembelian_buku B on B.id_header = A.id \n");
		queryStringBuilder.append("group by bulan \n");

		query = em.createNativeQuery(queryStringBuilder.toString(), TrxPembelianBukuLaporanEntity.class);

		List<TrxPembelianBukuLaporanEntity> list = query.getResultList();

		return list;
	}

}