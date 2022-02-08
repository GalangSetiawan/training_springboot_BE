package co.id.sofcograha.training.Entity;

import co.id.sofcograha.base.exceptions.DevException;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Access(AccessType.FIELD)
@Table(name = "tbl_galang_trx_header", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class TrxHeaderEntity implements Cloneable{

	@Id
	@Column(name="id")
	private String id;

	@Column(name="tanggal_bon")
	private Date tanggalBon;

	@Column(name="nomor_bon")
	private String nomorBon;

	@Column(name="discount_header")
	private Double discountHeader;

	@Column(name="nama_pembeli")
	private String namaPembeli;

	@Column(name="flag_dapat_promo_5_pertama")
	private String flagDapatPromo5Pertama;

	@Column(name="flag_member")
	private String flagMember;

	@Column(name="flag_member")
	private String jenis_pembayaran;

	@Version
	@Column(name="version")
	private Long version;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_membership")
	private MasterMembershipEntity dataMembership;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_trx")
	private MasterJenisTransaksiEntity dataJenisTransaksi;

	@OneToMany(mappedBy="dataHeader")
	private List<TrxDetailBuku> trxDetailBuku;

    
    @Transient
    private boolean isDeleted;



	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new DevException("Clone not supported", e);
		}
	}

    @PrePersist
	private void prePersist() {	
    	
      Date currentDate = new Date();
      
      this.id = UUID.randomUUID().toString();
      
//      this.usrcrt = "user";
//      this.tglcrt = new SimpleDateFormat("yyyyMMdd").format(currentDate);
//      this.jamcrt = new SimpleDateFormat("HHmmss").format(currentDate);
      
    }
    
    @PreUpdate
	private void preUpdate() {
		Date currentDate = new Date();
		
//		this.usrupd = "user";
//		this.tglupd = new SimpleDateFormat("yyyyMMdd").format(currentDate);
//		this.jamupd = new SimpleDateFormat("HHmmss").format(currentDate);
	}
    
}