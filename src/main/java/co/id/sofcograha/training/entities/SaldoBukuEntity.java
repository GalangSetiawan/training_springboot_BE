package co.id.sofcograha.training.entities;

import co.id.sofcograha.base.exceptions.DevException;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Access(AccessType.FIELD)
@Table(name = "tbl_galang_saldo_buku", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class SaldoBukuEntity implements Cloneable{

	@Id
	@Column(name="id")
	private String id;

	@Column(name="saldo_buku")
	private Integer saldoBuku;

	@Version
	@Column(name="version")
	private Long version;

	@OneToOne
	@JoinColumn(name = "id_buku", referencedColumnName = "id")
	private MasterBukuEntity dataBuku;

    
    @Transient
    private boolean isDeleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getSaldoBuku() {
		return saldoBuku;
	}

	public void setSaldoBuku(Integer saldoBuku) {
		this.saldoBuku = saldoBuku;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
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