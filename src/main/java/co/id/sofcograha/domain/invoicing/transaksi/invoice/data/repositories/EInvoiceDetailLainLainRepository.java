package co.id.sofcograha.domain.invoicing.transaksi.invoice.data.repositories;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities.EInvoiceDetailLainLain;

@Repository
public class EInvoiceDetailLainLainRepository extends SimpleJpaRepository<EInvoiceDetailLainLain, String> {

	protected final EntityManager em;

	public EInvoiceDetailLainLainRepository(EntityManager em) {
		super(EInvoiceDetailLainLain.class, em);
		this.em = em;
	}
	
	public EInvoiceDetailLainLain findByBK(String idTi001, Integer nourut) {
		
		String query = "SELECT e FROM EInvoiceDetailLainLain e " + 
                       "WHERE e.header.id = :idTi001 And " +
  	                         "e.nourut = :nourut";
		
		try {
			return em.createQuery(query, EInvoiceDetailLainLain.class)
					 .setParameter("idTi001", idTi001)
					 .setParameter("nourut", nourut)
					 .getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public EInvoiceDetailLainLain getOne(String id) {
		return super.findOne(id);
	}
	
	public SearchResult<EInvoiceDetailLainLain> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<EInvoiceDetailLainLain> search = new HqlSimpleSearchBuilder<>(EInvoiceDetailLainLain.class, em);

		search.setSort(searchParameter.getSort());
		search.setPaging(searchParameter.getPagination());

		return search.getSearchResult();
	}
	
	public EInvoiceDetailLainLain add(EInvoiceDetailLainLain entity) {
		return save(entity);
	}

	public EInvoiceDetailLainLain edit(EInvoiceDetailLainLain entity) {
		return save(entity);
	}
}