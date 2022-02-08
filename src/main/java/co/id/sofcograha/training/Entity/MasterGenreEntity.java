package co.id.sofcograha.training.Entity;

import co.id.sofcograha.base.exceptions.DevException;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Access(AccessType.FIELD)
@Table(name = "tbl_galang_mst_genre", uniqueConstraints = @UniqueConstraint(columnNames = { "kode_genre" }))
public class MasterGenreEntity implements Cloneable{
	
	@Id
	@Column(name = "id")
	private String id;

	@Column(name="kode_genre", length=10)
	private String kodeGenre;

	@Column(name="nama_genre", length=50)
	private String namaGenre;

	@Column(name="diskon_genre")
	private Double diskonGenre;

	@Version
    @Column(name = "version")
    private Long version;
    

    
    @Transient
    private boolean isDeleted;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKodeGenre() {
		return kodeGenre;
	}

	public void setKodeGenre(String kodeGenre) {
		this.kodeGenre = kodeGenre;
	}

	public String getNamaGenre() {
		return namaGenre;
	}

	public void setNamaGenre(String namaGenre) {
		this.namaGenre = namaGenre;
	}

	public Double getDiskonGenre() {
		return diskonGenre;
	}

	public void setDiskonGenre(Double diskonGenre) {
		this.diskonGenre = diskonGenre;
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