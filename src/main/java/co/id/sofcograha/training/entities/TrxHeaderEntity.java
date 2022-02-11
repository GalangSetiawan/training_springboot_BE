package co.id.sofcograha.training.entities;

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

	@Column(name="jenis_pembayaran")
	private String jenisPembayaran;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getTanggalBon() {
		return tanggalBon;
	}

	public void setTanggalBon(Date tanggalBon) {
		this.tanggalBon = tanggalBon;
	}

	public String getNomorBon() {
		return nomorBon;
	}

	public void setNomorBon(String nomorBon) {
		this.nomorBon = nomorBon;
	}

	public Double getDiscountHeader() {
		return discountHeader;
	}

	public void setDiscountHeader(Double discountHeader) {
		this.discountHeader = discountHeader;
	}

	public String getNamaPembeli() {
		return namaPembeli;
	}

	public void setNamaPembeli(String namaPembeli) {
		this.namaPembeli = namaPembeli;
	}

	public String getFlagDapatPromo5Pertama() {
		return flagDapatPromo5Pertama;
	}

	public void setFlagDapatPromo5Pertama(String flagDapatPromo5Pertama) {
		this.flagDapatPromo5Pertama = flagDapatPromo5Pertama;
	}

	public String getFlagMember() {
		return flagMember;
	}

	public void setFlagMember(String flagMember) {
		this.flagMember = flagMember;
	}

	public String getJenisPembayaran() {
		return jenisPembayaran;
	}

	public void setJenisPembayaran(String jenisPembayaran) {
		this.jenisPembayaran = jenisPembayaran;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public MasterMembershipEntity getDataMembership() {
		return dataMembership;
	}

	public void setDataMembership(MasterMembershipEntity dataMembership) {
		this.dataMembership = dataMembership;
	}

	public MasterJenisTransaksiEntity getDataJenisTransaksi() {
		return dataJenisTransaksi;
	}

	public void setDataJenisTransaksi(MasterJenisTransaksiEntity dataJenisTransaksi) {
		this.dataJenisTransaksi = dataJenisTransaksi;
	}

	public List<TrxDetailBuku> getTrxDetailBuku() {
		return trxDetailBuku;
	}

	public void setTrxDetailBuku(List<TrxDetailBuku> trxDetailBuku) {
		this.trxDetailBuku = trxDetailBuku;
	}

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