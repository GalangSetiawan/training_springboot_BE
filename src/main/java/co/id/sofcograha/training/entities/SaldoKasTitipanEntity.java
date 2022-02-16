package co.id.sofcograha.training.entities;

import co.id.sofcograha.base.exceptions.DevException;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Access(AccessType.FIELD)
@Table(name = "tbl_saldo_kas_titipan", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class SaldoKasTitipanEntity implements Cloneable{

	@Id
	@Column(name = "id")
	private String id;

	@Column(name="nilai_titipan")
	private Double nilaiTitipan;

	@Column(name="nilai_point")
	private Integer nilaiPoint;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_member", referencedColumnName = "id")
	private MasterMembershipEntity dataMembership;

	@Version
	@Column(name="version")
	private Long version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public MasterMembershipEntity getDataMembership() {
		return dataMembership;
	}

	public void setDataMembership(MasterMembershipEntity dataMembership) {
		this.dataMembership = dataMembership;
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