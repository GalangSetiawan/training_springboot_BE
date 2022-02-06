package co.id.sofcograha.domain.invoicing.transaksi.invoice.data.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.QueryUtil;
import co.id.sofcograha.base.utils.searchData.HqlSimpleSearchBuilder;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities.EInvoiceHeader;

@Repository
public class EInvoiceHeaderRepository extends SimpleJpaRepository<EInvoiceHeader, String> {

	protected final EntityManager em;

	public EInvoiceHeaderRepository(EntityManager em) {
		super(EInvoiceHeader.class, em);
		this.em = em;
	}
	
	public EInvoiceHeader findByBK(String nomor) {
		
		String query = "SELECT e FROM EInvoiceHeader e " + 
                       "WHERE e.nomor = :nomor";
		
		try {
			return em.createQuery(query, EInvoiceHeader.class)
					 .setParameter("nomor", nomor)
					 .getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public EInvoiceHeader getOne(String id) {

		String query = "SELECT e FROM EInvoiceHeader e " + 
                       "WHERE e.id = :id";
	
		try {
			return em.createQuery(query, EInvoiceHeader.class)
					.setParameter("id", id)
					.getSingleResult();
		
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
    @SuppressWarnings("unchecked")
	public EInvoiceHeader getInvoiceInitialTerakhirByTanggal(String idMi010, String idMi001, String tgtrn) {
    	
        Query query;
      
        StringBuilder queryStringBuilder = new StringBuilder();
        queryStringBuilder.append("select a.* " + 
        		                  "from ti001 a " + 
        		                  "left join ti007 b on b.id_ti001 = a.id_ti001 " + 
        		                  "left join public.am90 c on c.id_am90 = a.id_am90 " + 
        		                  "where a.nomor = (select max(x.nomor) " + 
        		                  "                 from ti001 x " + 
        		                  "                 left join ti007 y on y.id_ti001 = x.id_ti001 " + 
        		                  "                 left join public.am90 z on z.id_am90 = x.id_am90 " + 
         		                  "                 where z.kode = :kodetrn and y.id_ti007 is not null and x.tgtrn <= :tgtrn and " + 
        		                  "                       x.id_mi010 = :idMi010 and " + 
        		                  "                       x.id_mi001 = :idMi001)"); 
        
        query = em.createNativeQuery(queryStringBuilder.toString(), EInvoiceHeader.class);
        
        query.setParameter("kodetrn", BaseConstants.JENIS_TRX_INVOICE_MANUAL);
        query.setParameter("tgtrn", tgtrn);
        query.setParameter("idMi010", idMi010);
        query.setParameter("idMi001", idMi001);
        
        List<Object> list = query.getResultList();

        if (!list.isEmpty()) {
        	return (EInvoiceHeader) list.get(0);
        } else {
        	return null;
        }
    }
	
	public SearchResult<EInvoiceHeader> search(SearchParameter searchParameter) {
		HqlSimpleSearchBuilder<EInvoiceHeader> search = new HqlSimpleSearchBuilder<>(EInvoiceHeader.class, em);
		
		String nomor = (String) searchParameter.getValueFromMappedParam("nomor");
		String nama = (String) searchParameter.getValueFromMappedParam("nama");
		String alamat = (String) searchParameter.getValueFromMappedParam("alamat");
		String email = (String) searchParameter.getValueFromMappedParam("email");
		String customer = (String) searchParameter.getValueFromMappedParam("customer");
		String status = (String) searchParameter.getValueFromMappedParam("status");
		String fltodep = (String) searchParameter.getValueFromMappedParam("fltodep");
		String tahun = (String) searchParameter.getValueFromMappedParam("tahun");
		String bulan = (String) searchParameter.getValueFromMappedParam("bulan");
		String idMi010 = (String) searchParameter.getValueFromMappedParam("idMi010");

		if (!QueryUtil.isAll(nomor)) {
            search.addWhere("AND LOWER(nomor) LIKE LOWER(:nomor) ");
            search.addParameter("nomor", QueryUtil.formatStringForLikeFilter(nomor));
        }

		if (!QueryUtil.isAll(nama)) {
            search.addWhere("AND LOWER(nama) LIKE LOWER(:nama) ");
            search.addParameter("nama", QueryUtil.formatStringForLikeFilter(nama));
        }

		if (!QueryUtil.isAll(alamat)) {
            search.addWhere("AND LOWER(alamat) LIKE LOWER(:alamat) ");
            search.addParameter("alamat", QueryUtil.formatStringForLikeFilter(alamat));
        }

		if (!QueryUtil.isAll(email)) {
            search.addWhere("AND LOWER(email) LIKE LOWER(:email) ");
            search.addParameter("email", QueryUtil.formatStringForLikeFilter(email));
        }

		if (!QueryUtil.isAll(customer)) {
            search.addWhere("AND LOWER(customer.nama) LIKE LOWER(:customer) ");
            search.addParameter("customer", QueryUtil.formatStringForLikeFilter(customer));
        }

		if (idMi010 != null) {
            search.addWhere("AND customer.id = :idMi010 ");
            search.addParameter("idMi010", idMi010);
        }

//	    if (!QueryUtil.isAll(status)) {
//	      search.addWhere("AND LOWER(status) LIKE LOWER(:status) ");
//	      search.addParameter("status", QueryUtil.formatStringForLikeFilter(status));
//	    }
		System.out.println("-"+status+"-");
	    if (status != null && !status.equals("")) {
	    	if (status.equals("UNPAID")) {
	  	      search.addWhere("AND (status = 'SENT' OR status = 'NOSENT') ");
	    	} else {
	    		search.addWhere("AND status = :status ");
	    		search.addParameter("status", status);	    			    		
	    	}
	    }

	    if (!QueryUtil.isAll(fltodep)) {
	    	search.addWhere("AND LOWER(fltodep) LIKE LOWER(:fltodep) ");
	    	search.addParameter("fltodep", QueryUtil.formatStringForLikeFilter(fltodep));
	    }
	    
	    if (tahun != null) {
	    	String strTahunBulan = tahun;
	    	
	    	if (bulan != null) {
	    		strTahunBulan = strTahunBulan + bulan;
	    	}
	    	
            search.addWhere("AND LOWER(tgtrn) LIKE LOWER(:tahunBulan) ");
            search.addParameter("tahunBulan", QueryUtil.formatStringForLikeFilter(strTahunBulan));
	    }
	    
		search.setSort(searchParameter.getSort());
		search.setPaging(searchParameter.getPagination());

		return search.getSearchResult();
	}
	
	public EInvoiceHeader add(EInvoiceHeader entity) {
		return saveAndFlush(entity);
	}

	public EInvoiceHeader edit(EInvoiceHeader entity) {
		return saveAndFlush(entity);
	}
}