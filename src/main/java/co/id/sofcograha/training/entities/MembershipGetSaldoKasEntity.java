package co.id.sofcograha.training.entities;

import co.id.sofcograha.base.exceptions.DevException;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class MembershipGetSaldoKasEntity implements Cloneable{

	@Id
	@Column(name = "id")
	private String id;

	@Column(name="kode_member")
	private String kodeMembership;

	@Column(name="nama_member")
	private String namaMembership;

	@Column(name="nilai_titipan")
	private Double nilaiTitipan;

	@Column(name="nilai_point")
	private Integer nilaiPoint;

	@Version
    @Column(name = "version")
    private Long version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKodeMembership() {
		return kodeMembership;
	}

	public void setKodeMembership(String kodeMembership) {
		this.kodeMembership = kodeMembership;
	}

	public String getNamaMembership() {
		return namaMembership;
	}

	public void setNamaMembership(String namaMembership) {
		this.namaMembership = namaMembership;
	}

	public Double getNilaiTitipan() {
		return nilaiTitipan;
	}

	public void setNilaiTitipan(Double nilaiTitipan) {
		this.nilaiTitipan = nilaiTitipan;
	}

	public Integer getNilaiPoint() {
		return nilaiPoint;
	}

	public void setNilaiPoint(Integer nilaiPoint) {
		this.nilaiPoint = nilaiPoint;
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