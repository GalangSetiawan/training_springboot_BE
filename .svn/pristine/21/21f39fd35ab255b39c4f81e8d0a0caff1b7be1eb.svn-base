package co.id.sofcograha.domain.invoicing.commonMasters.comboConstants.entities;

import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import co.id.sofcograha.base.exceptions.DevException;

@Entity
@Access(AccessType.FIELD)
@Table(schema = "public", name = "am40", uniqueConstraints = @UniqueConstraint(columnNames = { "id_am40", "rectyp", "reccode" }))
public class EComboConstants implements Cloneable {

	@Id
	@Column(name = "id_am40")
	private String id;
	
	@Column(name = "rectyp")
	private String rectyp;

	@Column(name = "reccode")
	private String reccode;

	@Column(name = "rectxt")
	private String rectxt;
	
	@Column(name = "flkakt")
    private String flkakt;
	
	@Version
    @Column(name = "version")
    private Integer version;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRectyp() {
		return rectyp;
	}

	public void setRectyp(String rectyp) {
		this.rectyp = rectyp;
	}

	public String getRectxt() {
		return rectxt;
	}

	public void setRectxt(String rectxt) {
		this.rectxt = rectxt;
	}

	public String getFlkakt() {
		return flkakt;
	}

	public void setFlkakt(String flkakt) {
		this.flkakt = flkakt;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getReccode() {
		return reccode;
	}

	public void setReccode(String reccode) {
		this.reccode = reccode;
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

		this.id = UUID.randomUUID().toString();
    
	}

}