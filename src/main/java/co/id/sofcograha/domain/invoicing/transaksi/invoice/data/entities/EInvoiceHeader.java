package co.id.sofcograha.domain.invoicing.transaksi.invoice.data.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import co.id.sofcograha.base.exceptions.DevException;
import co.id.sofcograha.domain.invoicing.masters.customer.entities.ECustomerGajiId;

@Entity
@Access(AccessType.FIELD)
@Table(name = "ti001", uniqueConstraints = @UniqueConstraint(columnNames = { "nomor" }))
public class EInvoiceHeader implements Cloneable {

	@Id
	@Column(name = "id_ti001")
	private String id;

	@Column(name = "nomor")
	private String nomor;

	@Column(name = "tgtrn")
	private String tgtrn;

	@Column(name = "nmcust")
	private String nmcust;

	@Column(name = "nama")
	private String nama;

	@Column(name = "alamat")
	private String alamat;

	@Column(name = "email")
	private String email;

	@Column(name = "status")
	private String status;

	@Column(name = "bruto")
	private Double bruto;

	@Column(name = "totdisc")
	private Double totdisc;

	@Column(name = "dpp")
	private Double dpp;

	@Column(name = "ppn")
	private Double ppn;

	@Column(name = "netto")
	private Double netto;

	@Column(name = "fltodep")
	private String fltodep;

	@Column(name = "nildep")
	private Double nildep;

	@Column(name = "depused")
	private Double depused;

	@Column(name = "tgjtemp")
	private String tgjtemp;
	
	@Column(name = "flasli")
	private String flasli;
	
	@Column(name = "notes")
	private String notes;
	
	@Column(name = "pctdis")
	private Double pctdis;

	@Column(name = "nildis")
	private Double nildis;

	@Column(name = "ketdis")
	private String ketdis;
	
	@Column(name = "ketinv")
	private String ketinv;
	
	@Column(name = "nilbyr")
	private Double nilbyr;

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

	@OneToOne
	@JoinColumn(name = "id_mi010")
	private ECustomerGajiId customer;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "header")
	private List<EInvoiceDetailLainLain> detailLainLain;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomor() {
		return nomor;
	}

	public void setNomor(String nomor) {
		this.nomor = nomor;
	}

	public String getTgtrn() {
		return tgtrn;
	}

	public void setTgtrn(String tgtrn) {
		this.tgtrn = tgtrn;
	}

	public String getNmcust() {
		return nmcust;
	}

	public void setNmcust(String nmcust) {
		this.nmcust = nmcust;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getBruto() {
		return bruto;
	}

	public void setBruto(Double bruto) {
		this.bruto = bruto;
	}

	public Double getTotdisc() {
		return totdisc;
	}

	public void setTotdisc(Double totdisc) {
		this.totdisc = totdisc;
	}

	public Double getDpp() {
		return dpp;
	}

	public void setDpp(Double dpp) {
		this.dpp = dpp;
	}

	public Double getPpn() {
		return ppn;
	}

	public void setPpn(Double ppn) {
		this.ppn = ppn;
	}

	public Double getNetto() {
		return netto;
	}

	public void setNetto(Double netto) {
		this.netto = netto;
	}

	public String getFltodep() {
		return fltodep;
	}

	public void setFltodep(String fltodep) {
		this.fltodep = fltodep;
	}

	public Double getNildep() {
		return nildep;
	}

	public void setNildep(Double nildep) {
		this.nildep = nildep;
	}

	public Double getDepused() {
		return depused;
	}

	public void setDepused(Double depused) {
		this.depused = depused;
	}

	public String getTgjtemp() {
		return tgjtemp;
	}

	public void setTgjtemp(String tgjtemp) {
		this.tgjtemp = tgjtemp;
	}

	public String getFlasli() {
		return flasli;
	}

	public void setFlasli(String flasli) {
		this.flasli = flasli;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Double getPctdis() {
		return pctdis;
	}

	public void setPctdis(Double pctdis) {
		this.pctdis = pctdis;
	}

	public Double getNildis() {
		return nildis;
	}

	public void setNildis(Double nildis) {
		this.nildis = nildis;
	}

	public String getKetdis() {
		return ketdis;
	}

	public void setKetdis(String ketdis) {
		this.ketdis = ketdis;
	}

	public String getKetinv() {
		return ketinv;
	}

	public void setKetinv(String ketinv) {
		this.ketinv = ketinv;
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

	public ECustomerGajiId getCustomer() {
		return customer;
	}

	public void setCustomer(ECustomerGajiId customer) {
		this.customer = customer;
	}

	public List<EInvoiceDetailLainLain> getDetailLainLain() {
		return detailLainLain;
	}

	public void setDetailLainLain(List<EInvoiceDetailLainLain> detailLainLain) {
		this.detailLainLain = detailLainLain;
	}

	public Double getNilbyr() {
		return nilbyr;
	}

	public void setNilbyr(Double nilbyr) {
		this.nilbyr = nilbyr;
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