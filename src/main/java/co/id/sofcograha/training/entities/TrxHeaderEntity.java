package co.id.sofcograha.training.entities;

import co.id.sofcograha.base.exceptions.DevException;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Access(AccessType.FIELD)
@Table(name = "tbl_galang_trx_header", uniqueConstraints = @UniqueConstraint(columnNames = { "nomor_bon" }))
public class TrxHeaderEntity implements Cloneable{

	@Id
	@Column(name="id")
	private String id;

	@Column(name="tanggal_bon")
	private Date tanggalBon;

	@Column(name="nomor_bon")
	private String nomorBon;

	@Column(name="nama_pembeli")
	private String namaPembeli;

	@Column(name="discount_header")
	private Integer discountHeader;

	@Column(name="nilai_kembalian")
	private Double nilaiKembalian;

	@Column(name="total_pembayaran")
	private Double totalPembayaran;

	@Column(name="total_pembelian_buku")
	private Double totalPembelianBuku;

	@Column(name="nilai_diskon_header")
	private Double nilaiDiskonHeader;

	@Column(name="flag_dapat_promo_5_pertama")
	private Boolean flagDapatPromo5Pertama;

	@Column(name="flag_kembalian")
	private Boolean flagKembalian;

	@Column(name="ppn")
	private Integer PPN;

	@Column(name="keterangan")
	private String keterangan;

	@Column(name="dpp")
	private Double DPP;

	@Version
	@Column(name="version")
	private Long version;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_member")
	private MasterMembershipEntity dataMembership;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_jenis_transaksi")
	private MasterJenisTransaksiEntity dataJenisTransaksi;

	@OneToMany(mappedBy="dataHeader")
	private List<TrxDetailPembelianBukuEntity> trxDetailPembelianBuku;

	@OneToMany(mappedBy="transaksiHeader")
	private List<TrxDetailPembayaran> trxDetailPembayaran;

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

	public String getNamaPembeli() {
		return namaPembeli;
	}

	public void setNamaPembeli(String namaPembeli) {
		this.namaPembeli = namaPembeli;
	}

	public Integer getDiscountHeader() {
		return discountHeader;
	}

	public void setDiscountHeader(Integer discountHeader) {
		this.discountHeader = discountHeader;
	}

	public Double getNilaiKembalian() {
		return nilaiKembalian;
	}

	public void setNilaiKembalian(Double nilaiKembalian) {
		this.nilaiKembalian = nilaiKembalian;
	}

	public Double getTotalPembayaran() {
		return totalPembayaran;
	}

	public void setTotalPembayaran(Double totalPembayaran) {
		this.totalPembayaran = totalPembayaran;
	}

	public Double getTotalPembelianBuku() {
		return totalPembelianBuku;
	}

	public void setTotalPembelianBuku(Double totalPembelianBuku) {
		this.totalPembelianBuku = totalPembelianBuku;
	}

	public Double getNilaiDiskonHeader() {
		return nilaiDiskonHeader;
	}

	public void setNilaiDiskonHeader(Double nilaiDiskonHeader) {
		this.nilaiDiskonHeader = nilaiDiskonHeader;
	}

	public Boolean getFlagDapatPromo5Pertama() {
		return flagDapatPromo5Pertama;
	}

	public void setFlagDapatPromo5Pertama(Boolean flagDapatPromo5Pertama) {
		this.flagDapatPromo5Pertama = flagDapatPromo5Pertama;
	}

	public Boolean getFlagKembalian() {
		return flagKembalian;
	}

	public void setFlagKembalian(Boolean flagKembalian) {
		this.flagKembalian = flagKembalian;
	}

	public Integer getPPN() {
		return PPN;
	}

	public void setPPN(Integer PPN) {
		this.PPN = PPN;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public Double getDPP() {
		return DPP;
	}

	public void setDPP(Double DPP) {
		this.DPP = DPP;
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

	public List<TrxDetailPembelianBukuEntity> getTrxDetailPembelianBuku() {
		return trxDetailPembelianBuku;
	}

	public void setTrxDetailPembelianBuku(List<TrxDetailPembelianBukuEntity> trxDetailPembelianBuku) {
		this.trxDetailPembelianBuku = trxDetailPembelianBuku;
	}

	public List<TrxDetailPembayaran> getTrxDetailPembayaran() {
		return trxDetailPembayaran;
	}

	public void setTrxDetailPembayaran(List<TrxDetailPembayaran> trxDetailPembayaran) {
		this.trxDetailPembayaran = trxDetailPembayaran;
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