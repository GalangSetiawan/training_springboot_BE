package co.id.sofcograha.training.entities;

import co.id.sofcograha.base.exceptions.DevException;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Access(AccessType.FIELD)
@Table(name = "tbl_membership", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class MasterMembershipEntity implements Cloneable{

	@Id
	@Column(name = "id")
	private String id;

	@Column(name="kode_member")
	private String kodeMembership;

	@Column(name="nama_member")
	private String namaMembership;

	@OneToOne()
	@JoinColumn(name = "id_kas_titipan", referencedColumnName = "id")
	private SaldoKasTitipanEntity saldoKasTitipan;

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

	public SaldoKasTitipanEntity getSaldoKasTitipan() {
		return saldoKasTitipan;
	}

	public void setSaldoKasTitipan(SaldoKasTitipanEntity saldoKasTitipan) {
		this.saldoKasTitipan = saldoKasTitipan;
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