package co.id.sofcograha.training.entities;

import co.id.sofcograha.base.exceptions.DevException;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Access(AccessType.FIELD)
@Table(name = "tbl_galang_trx_detail", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class TrxDetailBuku implements Cloneable{

	@Id
	@Column(name="id")
	private String id;

	@Version
	@Column(name="version")
	private Long version;

	@Column(name="qty")
	private Double qty;

	@Column(name="total_harga")
	private Double totalHarga;

	@Column(name="persen_disc_genre")
	private Double persenDiscGenre;

	@Column(name="nilai_disc_genre")
	private Double nilaiDiscGenre;

	@Column(name="harga_setelah_disc_genre")
	private Double hargaSetelahDiscGenre;

	@Column(name="nilai_disc_header")
	private Double nilaiDiscHeader;

	@Column(name="nilai_disc_proposional")
	private Double nilaiDiscProposional;

	@ManyToOne
	@JoinColumn(name = "id_header", referencedColumnName = "id")
	private TrxHeaderEntity dataHeader;

	@OneToOne
	@JoinColumn(name = "id_buku", referencedColumnName = "id")
	private MasterBukuEntity dataBuku;

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

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getTotalHarga() {
		return totalHarga;
	}

	public void setTotalHarga(Double totalHarga) {
		this.totalHarga = totalHarga;
	}

	public Double getPersenDiscGenre() {
		return persenDiscGenre;
	}

	public void setPersenDiscGenre(Double persenDiscGenre) {
		this.persenDiscGenre = persenDiscGenre;
	}

	public Double getNilaiDiscGenre() {
		return nilaiDiscGenre;
	}

	public void setNilaiDiscGenre(Double nilaiDiscGenre) {
		this.nilaiDiscGenre = nilaiDiscGenre;
	}

	public Double getHargaSetelahDiscGenre() {
		return hargaSetelahDiscGenre;
	}

	public void setHargaSetelahDiscGenre(Double hargaSetelahDiscGenre) {
		this.hargaSetelahDiscGenre = hargaSetelahDiscGenre;
	}

	public Double getNilaiDiscHeader() {
		return nilaiDiscHeader;
	}

	public void setNilaiDiscHeader(Double nilaiDiscHeader) {
		this.nilaiDiscHeader = nilaiDiscHeader;
	}

	public Double getNilaiDiscProposional() {
		return nilaiDiscProposional;
	}

	public void setNilaiDiscProposional(Double nilaiDiscProposional) {
		this.nilaiDiscProposional = nilaiDiscProposional;
	}

	public TrxHeaderEntity getDataHeader() {
		return dataHeader;
	}

	public void setDataHeader(TrxHeaderEntity dataHeader) {
		this.dataHeader = dataHeader;
	}

	public MasterBukuEntity getDataBuku() {
		return dataBuku;
	}

	public void setDataBuku(MasterBukuEntity dataBuku) {
		this.dataBuku = dataBuku;
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