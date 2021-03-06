package co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import co.id.sofcograha.base.exceptions.DevException;

@Entity
@Access(AccessType.FIELD)
@Table(name = "ti006", uniqueConstraints = @UniqueConstraint(columnNames = { "id_ti001", "nourut" }))
public class EInvoiceDetailLainLain implements Cloneable {
	
	@Id
	@Column(name = "id_ti006")
	private String id;
	
	@Column(name = "nourut")
	private Integer nourut;

	@Column(name = "keterangan")
	private String keterangan;

	@Column(name = "harga")
	private Double harga;
	
	@Column(name = "pctdisc")
	private Double pctdisc;
	
	@Column(name = "nilpctdisc")
	private Double nilpctdisc;
	
	@Column(name = "nildisc")
	private Double nildisc;
	
	@Column(name = "netto")
	private Double netto;
	
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
  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ti001")
	private EInvoiceHeader header;  

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getNourut() {
		return nourut;
	}

	public void setNourut(Integer nourut) {
		this.nourut = nourut;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public Double getHarga() {
		return harga;
	}

	public void setHarga(Double harga) {
		this.harga = harga;
	}

	public Double getPctdisc() {
		return pctdisc;
	}

	public void setPctdisc(Double pctdisc) {
		this.pctdisc = pctdisc;
	}

	public Double getNilpctdisc() {
		return nilpctdisc;
	}

	public void setNilpctdisc(Double nilpctdisc) {
		this.nilpctdisc = nilpctdisc;
	}

	public Double getNildisc() {
		return nildisc;
	}

	public void setNildisc(Double nildisc) {
		this.nildisc = nildisc;
	}

	public Double getNetto() {
		return netto;
	}

	public void setNetto(Double netto) {
		this.netto = netto;
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

	public EInvoiceHeader getHeader() {
		return header;
	}

	public void setHeader(EInvoiceHeader header) {
		this.header = header;
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