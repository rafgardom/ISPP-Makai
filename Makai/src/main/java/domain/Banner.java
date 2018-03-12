
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Banner extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Banner() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private byte[]	picture;
	private Integer	totalViews;
	private Integer	currentViews;


	public byte[] getPicture() {
		return this.picture;
	}
	public void setPicture(final byte[] picture) {
		this.picture = picture;
	}

	@Min(0)
	public Integer getTotalViews() {
		return this.totalViews;
	}
	public void setTotalViews(final Integer totalViews) {
		this.totalViews = totalViews;
	}

	@Min(0)
	public Integer getCurrentViews() {
		return this.currentViews;
	}
	public void setCurrentViews(final Integer currentViews) {
		this.currentViews = currentViews;
	}

}
