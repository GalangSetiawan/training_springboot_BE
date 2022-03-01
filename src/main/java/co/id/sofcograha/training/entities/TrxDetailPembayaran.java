package co.id.sofcograha.training.entities;

import co.id.sofcograha.base.exceptions.DevException;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Access(AccessType.FIELD)
@Table(name = "tbl_detail_trx_pembayaran", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class TrxDetailPembayaran implements Cloneable{

	@Id
	@Column(name="id")
	private String id;

	@OneToOne
	@JoinColumn(name = "id_header", referencedColumnName = "id")
	private TrxHeaderEntity transaksiHeader;

	@Version
	@Column(name="version")
	private Long version;

	@Column(name="jenis_pembayaran")
	private String jenisPembayaran;

	@Column(name="nilai_rupiah")
	private Double nilaiRupiah;

	@Column(name="jumlah_point")
	private Integer jumlahPoint;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getJenisPembayaran() {
		return jenisPembayaran;
	}

	public void setJenisPembayaran(String jenisPembayaran) {
		this.jenisPembayaran = jenisPembayaran;
	}

	public Double getNilaiRupiah() {
		return nilaiRupiah;
	}

	public void setNilaiRupiah(Double nilaiRupiah) {
		this.nilaiRupiah = nilaiRupiah;
	}

	public Integer getJumlahPoint() {
		return jumlahPoint;
	}

	public void setJumlahPoint(Integer jumlahPoint) {
		this.jumlahPoint = jumlahPoint;
	}

	public TrxHeaderEntity getTransaksiHeader() {
		return transaksiHeader;
	}

	public void setTransaksiHeader(TrxHeaderEntity transaksiHeader) {
		this.transaksiHeader = transaksiHeader;
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