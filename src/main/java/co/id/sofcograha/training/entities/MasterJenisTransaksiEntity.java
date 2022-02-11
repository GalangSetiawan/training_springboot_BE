package co.id.sofcograha.training.entities;

import co.id.sofcograha.base.exceptions.DevException;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Access(AccessType.FIELD)
@Table(name = "tbl_galang_mst_jenis_transaksi", uniqueConstraints = @UniqueConstraint(columnNames = { "kode" }))
public class MasterJenisTransaksiEntity implements Cloneable{

	@Id
	@Column(name = "id")
	private String id;

	@Column(name="kode", length=10)
	private String kodeJenisTransaksi;

	@Column(name="nama_transaksi", length=50)
	private String namaTransaksi;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKodeJenisTransaksi() {
		return kodeJenisTransaksi;
	}

	public void setKodeJenisTransaksi(String kodeJenisTransaksi) {
		this.kodeJenisTransaksi = kodeJenisTransaksi;
	}

	public String getNamaTransaksi() {
		return namaTransaksi;
	}

	public void setNamaTransaksi(String namaTransaksi) {
		this.namaTransaksi = namaTransaksi;
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