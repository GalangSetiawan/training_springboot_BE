package co.id.sofcograha.domain.invoicing.masters.customer.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import co.id.sofcograha.base.exceptions.DevException;

@Entity
@Access(AccessType.FIELD)
@Table(name = "mi010", uniqueConstraints = @UniqueConstraint(columnNames = { "nama" }))
public class ECustomerGajiId implements Cloneable{
	
	@Id
	@Column(name = "id_mi010")
	private String id;
	
	@Column(name = "nama")
	private String nama;

	@Column(name = "picnama")
	private String picnama;

	@Column(name = "picrole")
	private String picrole;

	@Column(name = "picnumber")
	private String picnumber;

	@Column(name = "picemail")
	private String picemail;

	@Column(name = "picalamat")
	private String picalamat;

	@Column(name = "billnama")
	private String billnama;

	@Column(name = "billrole")
	private String billrole;

	@Column(name = "billnumber")
	private String billnumber;

	@Column(name = "billemail")
	private String billemail;

	@Column(name = "billalamat")
	private String billalamat;
	
	@Column(name = "billcust2")
	private String billcust2;

	@Column(name = "billnama2")
	private String billnama2;

	@Column(name = "billrole2")
	private String billrole2;

	@Column(name = "billnumber2")
	private String billnumber2;

	@Column(name = "billemail2")
	private String billemail2;

	@Column(name = "billalamat2")
	private String billalamat2;
	
	@Column(name = "vabca")
	private String vabca;
	
	@Column(name = "flakt")
    private String flakt;

	@Column(name = "flmainva")
    private String flmainva;

	@Version
    @Column(name = "version")
    private Long version;
    
    @Column(name = "usrcrt")
    private String usrcrt;
    
    @Column(name = "tglcrt")
    private String tglcrt;
    
    @Column(name = "jamcrt")
    private String jamcrt;
    
    @Column(name = "usrupd")
    private String usrupd;
    
    @Column(name = "tglupd")
    private String tglupd;
    
    @Column(name = "jamupd")
    private String jamupd;
    
    @Transient
    private boolean isDeleted;
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getPicnama() {
		return picnama;
	}

	public void setPicnama(String picnama) {
		this.picnama = picnama;
	}

	public String getPicrole() {
		return picrole;
	}

	public void setPicrole(String picrole) {
		this.picrole = picrole;
	}

	public String getPicnumber() {
		return picnumber;
	}

	public void setPicnumber(String picnumber) {
		this.picnumber = picnumber;
	}

	public String getPicemail() {
		return picemail;
	}

	public void setPicemail(String picemail) {
		this.picemail = picemail;
	}

	public String getPicalamat() {
		return picalamat;
	}

	public void setPicalamat(String picalamat) {
		this.picalamat = picalamat;
	}

	public String getBillnama() {
		return billnama;
	}

	public void setBillnama(String billnama) {
		this.billnama = billnama;
	}

	public String getBillrole() {
		return billrole;
	}

	public void setBillrole(String billrole) {
		this.billrole = billrole;
	}

	public String getBillnumber() {
		return billnumber;
	}

	public void setBillnumber(String billnumber) {
		this.billnumber = billnumber;
	}

	public String getBillemail() {
		return billemail;
	}

	public void setBillemail(String billemail) {
		this.billemail = billemail;
	}

	public String getBillalamat() {
		return billalamat;
	}

	public void setBillalamat(String billalamat) {
		this.billalamat = billalamat;
	}

	public String getBillcust2() {
		return billcust2;
	}

	public void setBillcust2(String billcust2) {
		this.billcust2 = billcust2;
	}

	public String getBillnama2() {
		return billnama2;
	}

	public void setBillnama2(String billnama2) {
		this.billnama2 = billnama2;
	}

	public String getBillrole2() {
		return billrole2;
	}

	public void setBillrole2(String billrole2) {
		this.billrole2 = billrole2;
	}

	public String getBillnumber2() {
		return billnumber2;
	}

	public void setBillnumber2(String billnumber2) {
		this.billnumber2 = billnumber2;
	}

	public String getBillemail2() {
		return billemail2;
	}

	public void setBillemail2(String billemail2) {
		this.billemail2 = billemail2;
	}

	public String getBillalamat2() {
		return billalamat2;
	}

	public void setBillalamat2(String billalamat2) {
		this.billalamat2 = billalamat2;
	}

	public String getVabca() {
		return vabca;
	}

	public void setVabca(String vabca) {
		this.vabca = vabca;
	}

	public String getFlakt() {
		return flakt;
	}

	public void setFlakt(String flakt) {
		this.flakt = flakt;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getUsrcrt() {
		return usrcrt;
	}

	public void setUsrcrt(String usrcrt) {
		this.usrcrt = usrcrt;
	}

	public String getTglcrt() {
		return tglcrt;
	}

	public void setTglcrt(String tglcrt) {
		this.tglcrt = tglcrt;
	}

	public String getJamcrt() {
		return jamcrt;
	}

	public void setJamcrt(String jamcrt) {
		this.jamcrt = jamcrt;
	}

	public String getUsrupd() {
		return usrupd;
	}

	public void setUsrupd(String usrupd) {
		this.usrupd = usrupd;
	}

	public String getTglupd() {
		return tglupd;
	}

	public void setTglupd(String tglupd) {
		this.tglupd = tglupd;
	}

	public String getJamupd() {
		return jamupd;
	}

	public void setJamupd(String jamupd) {
		this.jamupd = jamupd;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getFlmainva() {
		return flmainva;
	}

	public void setFlmainva(String flmainva) {
		this.flmainva = flmainva;
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
      
      this.usrcrt = "user";
      this.tglcrt = new SimpleDateFormat("yyyyMMdd").format(currentDate);
      this.jamcrt = new SimpleDateFormat("HHmmss").format(currentDate);
      
    }
    
    @PreUpdate
	private void preUpdate() {
		Date currentDate = new Date();
		
		this.usrupd = "user";
		this.tglupd = new SimpleDateFormat("yyyyMMdd").format(currentDate);
		this.jamupd = new SimpleDateFormat("HHmmss").format(currentDate);
	}
    
}