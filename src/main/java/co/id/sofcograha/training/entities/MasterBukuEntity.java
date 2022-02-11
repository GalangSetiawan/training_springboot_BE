package co.id.sofcograha.training.entities;

import co.id.sofcograha.base.exceptions.DevException;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Access(AccessType.FIELD)
@Table(name = "tbl_galang_mst_buku", uniqueConstraints = @UniqueConstraint(columnNames = { "kode_buku" }))
public class MasterBukuEntity implements Cloneable{

	@Id
	@Column(name = "id")
	private String id;

	@Column(name="kode_buku")
	private String kodeBuku;

	@Column(name="nama_buku")
	private String namaBuku;

	@Column(name="harga")
	private Double hargaBuku;

	@Column(name="stock_buku")
	private Integer stockBuku;

	@Column(name="active")
	private String flagActive;

	@OneToOne (cascade=CascadeType.ALL)
	@JoinColumn(name = "id_genre", referencedColumnName = "id")
	private MasterGenreEntity genreBuku;

	@Version
    @Column(name = "version")
    private Long version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKodeBuku() {
		return kodeBuku;
	}

	public void setKodeBuku(String kodeBuku) {
		this.kodeBuku = kodeBuku;
	}

	public String getNamaBuku() {
		return namaBuku;
	}

	public void setNamaBuku(String namaBuku) {
		this.namaBuku = namaBuku;
	}

	public Double getHargaBuku() {
		return hargaBuku;
	}

	public void setHargaBuku(Double hargaBuku) {
		this.hargaBuku = hargaBuku;
	}

	public Integer getStockBuku() {
		return stockBuku;
	}

	public void setStockBuku(Integer stockBuku) {
		this.stockBuku = stockBuku;
	}

	public String getFlagActive() {
		return flagActive;
	}

	public void setFlagActive(String flagActive) {
		this.flagActive = flagActive;
	}

	public MasterGenreEntity getGenreBuku() {
		return genreBuku;
	}

	public void setGenreBuku(MasterGenreEntity genreBuku) {
		this.genreBuku = genreBuku;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
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