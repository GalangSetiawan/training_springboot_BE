package co.id.sofcograha.training.Entity;

import co.id.sofcograha.base.exceptions.DevException;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Access(AccessType.FIELD)
@Table(name = "tbl_galang_range_poin", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class RangePointEntity implements Cloneable{

	@Id
	@Column(name = "id")
	private String id;

	@Column(name="min_range")
	private Double minRange;

	@Column(name="max_range")
	private Double maxRange;

	@Column(name="point")
	private Integer point;

    @Transient
    private boolean isDeleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getMinRange() {
		return minRange;
	}

	public void setMinRange(Double kodeMembership) {
		this.minRange = minRange;
	}

	public Double getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(Double maxRange) {
		this.maxRange = maxRange;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
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