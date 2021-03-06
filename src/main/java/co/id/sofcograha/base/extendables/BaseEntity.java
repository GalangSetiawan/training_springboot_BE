package co.id.sofcograha.base.extendables;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import co.id.sofcograha.base.exceptions.DevException;
import co.id.sofcograha.base.utils.TimeUtil;

@MappedSuperclass
public abstract class BaseEntity implements Cloneable {

	@Id
	@Column(name = "id")
	private String id;

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

	protected <R extends BaseEntity> R root() {
		return null;
	}

	@PrePersist
	private void prePersist() {
		Date currentDate = new Date();

		this.id = UUID.randomUUID().toString();

		this.usrcrt = "user";
		//this.tglcrt = new SimpleDateFormat("yyyyMMdd").format(currentDate);
		//this.jamcrt = new SimpleDateFormat("HHmmss").format(currentDate);
		this.tglcrt = TimeUtil.convertDateToYyyyMmDd(currentDate);
		this.jamcrt = TimeUtil.convertDateToHhMmSs(currentDate);
	}

	@PreUpdate
	private void preUpdate() {
		Date currentDate = new Date();

		this.usrupd = "user";
		//this.tglupd = new SimpleDateFormat("yyyyMMdd").format(currentDate);
		//this.jamupd = new SimpleDateFormat("HHmmss").format(currentDate);
		this.tglupd = TimeUtil.convertDateToYyyyMmDd(currentDate);
		this.jamupd = TimeUtil.convertDateToHhMmSs(currentDate);
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new DevException("Clone not supported", e);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}
